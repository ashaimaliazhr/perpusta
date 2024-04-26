package com.skripsi.perpusta.ui.dialogfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.skripsi.perpusta.R

class LoginDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login_dialog, container, false)
    }

    companion object {
        const val TAG = "LoginDialogFragment"

        fun show(fragmentManager: FragmentManager) {
            val dialogFragment = LoginDialogFragment()
            dialogFragment.show(fragmentManager, TAG)
        }

        fun dismiss(fragmentManager: FragmentManager) {
            val dialogFragment = fragmentManager.findFragmentByTag(TAG) as? LoginDialogFragment
            dialogFragment?.dismiss()
        }
    }


}