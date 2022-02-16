package com.toyibnurseha.whosings.ui.game

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.toyibnurseha.whosings.R
import com.toyibnurseha.whosings.databinding.FragmentGameBinding
import com.toyibnurseha.whosings.db.model.UserEntity
import com.toyibnurseha.whosings.interfaces.ITimer
import com.toyibnurseha.whosings.manager.TimerManager
import com.toyibnurseha.whosings.ui.GameAdapter
import com.toyibnurseha.whosings.utils.Constant
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class GameFragment : Fragment(), ITimer {

    private lateinit var binding: FragmentGameBinding

    private val viewModel: GameViewModel by viewModels()

    private lateinit var user: UserEntity

    private lateinit var gameAdapter: GameAdapter

    private var score = 0
    private var questionNumber = 1

    @Inject
    lateinit var quizTimer: TimerManager

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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        quizTimer.initTimer(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gameAdapter = GameAdapter()
        setAdapter()
        getChartArtist()

        binding.tvQuestion.text = resources.getString(R.string.question_1, questionNumber)

        binding.timerPr.max = Constant.MAX_TIMER.toInt()

        viewModel.snippetLyric.observe(viewLifecycleOwner) {
            binding.tvLyrics.text = it?.text ?: ""
        }

        viewModel.matchCorrectness.observe(viewLifecycleOwner) { isCorrect ->
            isCorrect ?: return@observe
            matchCorrectness(isCorrect)
        }

        viewModel.score.observe(viewLifecycleOwner) {
            score = it
            binding.tvScore.text = resources.getString(R.string.current_score, score)
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
            quizTimer.cancel()
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
        showLoading(true)
        viewModel.getChartArtists(user).observe(viewLifecycleOwner) {
            if (it.data != null) {
                showLoading(false)
                gameAdapter.setData(it.data.toMutableList())
                questionNumber++
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if(isLoading) {
            binding.layoutLoading.visibility = View.VISIBLE
            binding.timerPr.visibility = View.GONE
        }else {
            binding.layoutLoading.visibility = View.GONE
            binding.timerPr.visibility = View.VISIBLE
            quizTimer.startTimer()
        }
    }

    override fun onDetach() {
        super.onDetach()
        quizTimer.cancel()
    }

    override fun onTimerFinish() {
        binding.timerPr.progress = Constant.MAX_TIMER.toInt()
        matchCorrectness(false)
    }

    override fun onTimerRun(millis: Long) {
        binding.timerPr.progress = Constant.MAX_TIMER.toInt() - millis.toInt()
    }

    override fun onResume() {
        super.onResume()
        if (quizTimer.isFinished()) {
            matchCorrectness(false)
        }
    }

}