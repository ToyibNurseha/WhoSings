package com.toyibnurseha.whosings.ui.leaderboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.toyibnurseha.whosings.databinding.FragmentLeaderboardBinding
import com.toyibnurseha.whosings.db.model.UserEntity
import com.toyibnurseha.whosings.local.Score
import com.toyibnurseha.whosings.utils.toScoreDataList
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class LeaderboardFragment : Fragment() {

    private lateinit var binding: FragmentLeaderboardBinding

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
        binding = FragmentLeaderboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // plain table rank (replaced by mp android chart)
//        binding.rvScore.apply {
//            adapter = leaderboardAdapter
//            layoutManager = LinearLayoutManager(requireContext())
//        }

        viewModel.getUsers().observe(viewLifecycleOwner) {
            if (it.data != null) {
                initBarChart(it.data.toScoreDataList())

                // to set for plain table rank with recyclerview
//                leaderboardAdapter.setData(it.data.toScoreDataList())
            }
        }
    }

    private fun initBarChart(usersData: List<Score>) {
        val entriesBar = ArrayList<BarEntry>()
        val labels = ArrayList<String>()
        for (i in usersData.indices) {
            val barEntry = BarEntry(i.toFloat(), usersData[i].score.toFloat())
            entriesBar.add(barEntry)
        }
        for (i in usersData.indices) {
            val user = usersData[i].username
            labels.add(user)
        }
        val barDataset = BarDataSet(entriesBar, "Leaderboard").apply {
            colors = ColorTemplate.MATERIAL_COLORS.toMutableList()
        }
        binding.chartBar.apply {
            xAxis.apply {
                setDrawGridLines(false)
                valueFormatter = IndexAxisValueFormatter(labels)
            }
            axisRight.apply {
                setDrawGridLines(false)
                isEnabled = false
                setDrawLabels(true)
            }
            animateY(1500)
            legend.apply {
                isEnabled = false
            }
            data = BarData(barDataset)
            description.isEnabled = false
            isDoubleTapToZoomEnabled = false
            xAxis.apply {
                isEnabled = true
                position = XAxis.XAxisPosition.BOTTOM
                xAxis.labelCount = labels.size
            }
            setTouchEnabled(false)
            setDrawBarShadow(false)
            setDrawGridBackground(false)
            invalidate()
        }
    }

}