package com.example.randomusers.fragments.list

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.randomusers.R
import com.example.randomusers.adapter.UserListAdapter
import com.example.randomusers.data.UsersListUiState
import com.example.randomusers.databinding.FragmentUsersListBinding
import com.example.randomusers.fragments.BaseFragment
import com.example.randomusers.model.UserModel
import kotlinx.coroutines.launch


class UsersListFragment : BaseFragment() {

    private lateinit var binding: FragmentUsersListBinding
    private lateinit var viewModel: UsersListViewModel
    private lateinit var userListAdapter: UserListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUsersListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[UsersListViewModel::class.java]

        initObserve()
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchUsers(false)
    }



    private fun initObserve() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.usersUiState.collect { uiState ->
                    when (uiState) {
                        is UsersListUiState.Success -> {
                            initList(uiState.users)
                        }

                        is UsersListUiState.Error -> {
                            showToast(getString(R.string.strError, uiState.message))
                        }

                        is UsersListUiState.Loading -> {
                            if (uiState.isLoad)
                                showCustomProgressDialog()
                            else
                                hideProgressDialog()
                        }

                        else -> {}
                    }
                }
            }
        }
    }

    private fun initList(list: MutableList<UserModel>) {
        userListAdapter = UserListAdapter(
            list,
            onItemClick = { user ->
                navigateToDetailsFragment(user)
            },
            onItemLongClick = { email ->
                openEmailApp(email)
            })
        binding.recyclerViewUsers.adapter = userListAdapter
    }

    private fun navigateToDetailsFragment(user: UserModel) {
        val action = UsersListFragmentDirections.actionUsersListFragmentToDetailsUserFragment(user)
        findNavController().navigate(action)
    }

    private fun openEmailApp(email: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
            putExtra(Intent.EXTRA_SUBJECT, "Your Subject Here")
        }
        if (intent.resolveActivity(requireContext().packageManager) != null) {
            requireContext().startActivity(intent)
        } else {
            showToast(getString(R.string.noEmailAppFound))
        }
    }

    fun updateList() {
        viewModel.fetchUsers(true)
    }

    fun sortUsersByAge(isAscending: Boolean) {
        userListAdapter.sortListByAge(isAscending)
    }
}