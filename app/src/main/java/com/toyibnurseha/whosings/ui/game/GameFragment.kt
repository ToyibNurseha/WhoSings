package com.toyibnurseha.whosings.ui.game

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.toyibnurseha.whosings.db.model.UserEntity
import com.toyibnurseha.whosings.R
import com.toyibnurseha.whosings.databinding.FragmentGameBinding
import com.toyibnurseha.whosings.ui.GameAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class GameFragment : Fragment() {

    private lateinit var binding: FragmentGameBinding

    private val viewModel: GameViewModel by viewModels()

    private lateinit var user: UserEntity

    private lateinit var gameAdapter: GameAdapter

    private var TAG = "taggame"

    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        user = requireArguments().getSerializable("user") as UserEntity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gameAdapter = GameAdapter()
        setAdapter()
        getChartArtist()
        viewModel.snippetLyric.observe(viewLifecycleOwner) {
            binding.tvLyrics.text = it?.text ?: ""
        }
        viewModel.matchCorrectness.observe(viewLifecycleOwner) { isCorrect ->
            isCorrect ?: return@observe
            matchCorrectness(isCorrect)
        }
        viewModel.score.observe(viewLifecycleOwner) {
            score = it
            binding.tvScore.text = "Current Score : $score"
        }
        selectArtist()
    }

    private fun matchCorrectness(isCorrect: Boolean) {
        if (isCorrect) {
            viewModel.getChartArtists(user).removeObservers(viewLifecycleOwner)
            getChartArtist()
        } else {
            findNavController().navigate(
                R.id.action_gameFragment_to_resultFragment,
                Bundle().apply {
                    putSerializable("user", user)
                    putInt("score", score)
                },
                NavOptions.Builder().setPopUpTo(R.id.gameFragment, true).build()
            )
        }
    }

    private fun selectArtist() {
        gameAdapter.setOnItemClickListener {
            viewModel.selectArtist(it, user)
        }
    }

    private fun setAdapter() {
        binding.rvArtists.apply {
            adapter = gameAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun getChartArtist() {
        viewModel.getChartArtists(user).observe(viewLifecycleOwner) {
            if (it.data != null) {
                gameAdapter.setData(it.data.toMutableList())
            }
        }
    }

}