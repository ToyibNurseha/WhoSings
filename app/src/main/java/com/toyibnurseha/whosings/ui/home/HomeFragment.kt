package com.toyibnurseha.whosings.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.toyibnurseha.whosings.db.model.UserEntity
import com.toyibnurseha.whosings.R
import com.toyibnurseha.whosings.databinding.FragmentHomeBinding
import com.toyibnurseha.whosings.ui.result.ResultViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private var user: UserEntity? = null
    private val viewModel: ResultViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        user = arguments?.getParcelable("user")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onClickListener()

    }

    private fun onClickListener() {
        binding.btnPlay.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_gameFragment, Bundle().apply {
                putParcelable("user", user)
            })
        }

        binding.btnRecord.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_recordFragment, Bundle().apply {
                putParcelable("user", user)
            })
        }

        binding.btnScores.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_leaderboardFragment)
        }

        binding.btnLogout.setOnClickListener {
            user?.let { userData -> viewModel.logout(userData) }
            findNavController().navigate(
                R.id.action_resultFragment_to_loginFragment,
                null,
                NavOptions.Builder().setPopUpTo(R.id.homeFragment, true).build()
            )
        }
    }

}