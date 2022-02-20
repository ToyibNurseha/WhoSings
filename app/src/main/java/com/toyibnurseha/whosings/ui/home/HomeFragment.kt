package com.toyibnurseha.whosings.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.toyibnurseha.whosings.db.model.UserEntity
import com.toyibnurseha.whosings.R
import com.toyibnurseha.whosings.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private var user: UserEntity? = null

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
        binding.btnPlay.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_gameFragment, Bundle().apply {
                putParcelable("user", user)
            })
        }

        binding.btnScores.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_leaderboardFragment)
        }
    }

}