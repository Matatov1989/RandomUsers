package com.example.randomusers.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.randomusers.databinding.FragmentDetailsUserBinding
import com.example.randomusers.model.UserModel


class DetailsUserFragment : BaseFragment() {

    private lateinit var binding: FragmentDetailsUserBinding
    private lateinit var user: UserModel

    private var listener: OnDetailsUserFragment? = null
    interface OnDetailsUserFragment {
        fun onInitToolBar(title: String, isBackBtn: Boolean)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnDetailsUserFragment) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getUserData()
    }

    private fun getUserData() {
        arguments?.let {
            user = DetailsUserFragmentArgs.fromBundle(it).userData
            binding.user = user
            val title = "${user.name.firstName} ${user.name.lastName}"
            listener?.onInitToolBar(title, true)
        }
    }
}
