package com.toyibnurseha.whosings.ui.result

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.toyibnurseha.whosings.R
import com.toyibnurseha.whosings.db.model.UserEntity
import com.toyibnurseha.whosings.databinding.FragmentResultBinding
import com.toyibnurseha.whosings.utils.Constant.Companion.MAX_SCORE
import com.toyibnurseha.whosings.utils.getLastScore
import com.toyibnurseha.whosings.utils.getMaxScore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class ResultFragment : Fragment() {

    private lateinit var binding: FragmentResultBinding
    private val viewModel: ResultViewModel by viewModels()

    private var user: UserEntity? = null
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        user = arguments?.getParcelable("user")
        score = requireArguments().getInt("score")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showAnimation()

        binding.tvScore.text = resources.getString(R.string.your_score, score)
        binding.tvHighestScore.text =
            resources.getString(R.string.highest_score_is, user?.scores?.getMaxScore())
        binding.tvLastScore.text =
            resources.getString(R.string.latest_score, user?.scores?.getLastScore())

        binding.btnLogout.setOnClickListener {
            user?.let { userData -> viewModel.logout(userData) }
            findNavController().navigate(
                R.id.action_resultFragment_to_loginFragment,
                null,
                NavOptions.Builder().setPopUpTo(R.id.homeFragment, true).build()
            )
        }

        binding.btnReplay.setOnClickListener {
            findNavController().navigate(
                R.id.action_resultFragment_to_gameFragment,
                Bundle().apply {
                    putParcelable("user", user)
                },
                NavOptions.Builder().setPopUpTo(R.id.resultFragment, true).build()
            )
        }

        binding.btnHome.setOnClickListener {
            findNavController().navigate(
                R.id.action_resultFragment_to_homeFragment,
                Bundle().apply {
                    putParcelable("user", user)
                },
                NavOptions.Builder().setPopUpTo(R.id.resultFragment, true).build()
            )
        }
    }

    private fun showAnimation() {
        if (score != MAX_SCORE) {
            //lost
            binding.ivResultWin.visibility = View.GONE
            binding.tvResult.text = resources.getString(R.string.you_lost, user?.name)
        } else {
            //win
            binding.ivResultLost.visibility = View.GONE
            binding.tvResult.text = resources.getString(R.string.well_done, user?.name)
        }
    }

}