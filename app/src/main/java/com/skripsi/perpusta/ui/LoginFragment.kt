package com.skripsi.perpusta.ui


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.skripsi.perpusta.R
import com.skripsi.perpusta.data.datastore.SessionManager
import com.skripsi.perpusta.data.network.RemoteDataSource
import com.skripsi.perpusta.databinding.FragmentLoginBinding
import com.skripsi.perpusta.repository.AuthRepository
import com.skripsi.perpusta.viewmodel.AuthViewModel
import com.skripsi.perpusta.viewmodel.ViewModelFactory
import com.skripsi.perpusta.data.result.Result
import com.skripsi.perpusta.extensions.ReminderBroadcastReceiver
import com.skripsi.perpusta.ui.dialogfragment.LoginDialogFragment
import com.skripsi.perpusta.viewmodel.CirculationViewModel

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController
    private lateinit var viewModel: AuthViewModel
    private lateinit var circulationViewModel: CirculationViewModel
    private val apiService = RemoteDataSource().createApiService()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(AuthRepository(apiService))
        ). get(AuthViewModel::class.java)

        circulationViewModel = ViewModelProvider(this).get(CirculationViewModel::class.java)

        binding.loginButton.setOnClickListener {
            val npm = binding.username.text.toString()
            val password = binding.password.text.toString()

            if (npm.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Username and password cannot be empty",
                    Toast.LENGTH_SHORT).show()
            } else {
                viewModel.loginUser(npm, password)
            }
        }
        observeLoginResult()
    }

    private fun observeLoginResult() {
        viewModel.loginResult.observe(viewLifecycleOwner, { result ->
            Log.d("LoginFragment", "Result: $result")

            when (result) {
                    is Result.Success -> {
                        val user = result.data

                        val userId = user.user?.iD ?: ""
                        val token = result.data.token ?: ""
                        val fullName = user.user?.fName ?: ""

                        val sessionManager = SessionManager(requireContext())
                        sessionManager.saveUserData(userId, token, fullName)

                        circulationViewModel.fetchDueDate(userId)
                    }
                    is Result.Failure -> {
                    Toast.makeText(requireContext(), "Login failed", Toast.LENGTH_SHORT).show()
                }
                else -> {
                }
            }
        })

        circulationViewModel.dueDate.observe(viewLifecycleOwner, { dueDate ->
            if (!dueDate.isNullOrEmpty()) {
                val reminderBroadcastReceiver = ReminderBroadcastReceiver()
                Log.d("LoginFragment", "Scheduling return book notification")

                val intent = Intent(requireContext(), ReminderBroadcastReceiver::class.java).apply {
                    putExtra("dueDate", dueDate)
                }

                requireContext().sendBroadcast(intent)

                Log.d("LoginFragment", "Return book notification scheduled successfully")
                Log.d("LoginFragment", "Received due date: $dueDate")

                LoginDialogFragment.show(parentFragmentManager)

                view?.postDelayed({
                    LoginDialogFragment.dismiss(parentFragmentManager)
                    navController.navigate(R.id.action_loginFragment_to_homeFragment)
                }, 2000)
            } else {
                navController.navigate(R.id.action_loginFragment_to_homeFragment)
            }
                })

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
