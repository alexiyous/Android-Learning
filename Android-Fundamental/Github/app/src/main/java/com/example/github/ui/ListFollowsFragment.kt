package com.example.github.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.github.data.response.FollowerResponseItem
import com.example.github.databinding.FragmentListFollowsBinding

class ListFollowsFragment : Fragment() {

    private lateinit var _binding: FragmentListFollowsBinding
    private lateinit var viewModel: UserDetailViewModel

    companion object{
        const val ARG_POSITION = "position"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentListFollowsBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(UserDetailViewModel::class.java)

        // IMPLEMENT LIST OF FOLLOWS HERE FROM VIEWMODEL
        var position = 0
        arguments?.let {
            position = it.getInt(ARG_POSITION)
        }

        if (position == 1) {
            viewModel.followerList.observe(viewLifecycleOwner) { userFollowers ->
                setUserFollows(userFollowers)
            }
        } else {
            viewModel.followingList.observe(viewLifecycleOwner) { userFollowing ->
                    setUserFollows(userFollowing)
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { value ->
            showLoading(value)
        }
    }

    private fun setUserFollows(users: List<FollowerResponseItem>) {
        _binding.followsList.layoutManager = LinearLayoutManager(context)
        val userAdapter = ListFollowsAdapter()
        userAdapter.submitList(users)
        _binding.followsList.adapter = userAdapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            _binding.progressBar.visibility = View.VISIBLE
        } else {
            _binding.progressBar.visibility = View.GONE
        }
    }
}