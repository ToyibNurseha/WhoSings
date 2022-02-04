package com.toyibnurseha.whosings.ui.leaderboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.toyibnurseha.whosings.R
import com.toyibnurseha.whosings.databinding.FragmentLeaderboardBinding
import com.toyibnurseha.whosings.utils.toScoreDataList
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class LeaderboardFragment : Fragment() {

    private lateinit var binding : FragmentLeaderboardBinding

    private val viewModel: LeaderboardViewModel by viewModels()

    private lateinit var leaderboardAdapter: LeaderboardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        leaderboardAdapter = LeaderboardAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLeaderboardBinding.inflate(inflater, container ,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvScore.apply {
            adapter = leaderboardAdapter
            layoutManager = LinearLayoutManager(activity)
        }
        viewModel.getUsers().observe(viewLifecycleOwner) {
            if(it.data != null) {
                leaderboardAdapter.setData(it.data.toScoreDataList(requireContext()))
            }
        }
    }

}