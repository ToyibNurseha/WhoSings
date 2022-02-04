package com.toyibnurseha.whosings.ui.result

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.toyibnurseha.whosings.db.model.UserEntity
import com.toyibnurseha.whosings.databinding.FragmentResultBinding
import com.toyibnurseha.whosings.utils.Constant.Companion.MAX_SCORE
import com.toyibnurseha.whosings.utils.getMaxScore

class ResultFragment : Fragment() {

    private lateinit var binding: FragmentResultBinding

    private lateinit var user: UserEntity

    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        user = requireArguments().getSerializable("user") as UserEntity
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

        binding.tvScore.text = "Your score is ${score.toString()}"
        binding.tvHighestScore.text = "Highest Score is ${user.scores.getMaxScore()}"

        binding.btnLogout.setOnClickListener {

        }

        binding.btnReplay.setOnClickListener {

        }
    }

    private fun showAnimation() {
        if (score != MAX_SCORE) {
            //lost
            binding.ivResultWin.visibility = View.GONE
            binding.tvResult.text = "OHH, YOU LOST ${user.name}"
        }else {
            //win
            binding.ivResultLost.visibility = View.VISIBLE
            binding.tvResult.text = "WELL DONE ${user.name}"
        }
    }

}