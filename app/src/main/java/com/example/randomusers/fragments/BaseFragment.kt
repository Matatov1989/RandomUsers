package com.example.randomusers.fragments

import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.randomusers.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class BaseFragment : Fragment() {

    private lateinit var progressDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        progressDialog = Dialog(requireContext())
    }

    fun hideProgressDialog() {
        progressDialog.dismiss()
    }

    fun showCustomProgressDialog() {
        progressDialog.setContentView(R.layout.dialog_custom_progress)
        progressDialog.show()
    }

    fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}