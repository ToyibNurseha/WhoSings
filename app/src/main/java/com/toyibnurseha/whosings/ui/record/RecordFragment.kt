package com.toyibnurseha.whosings.ui.record

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.toyibnurseha.whosings.databinding.FragmentRecordBinding
import com.toyibnurseha.whosings.db.model.UserEntity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecordFragment : Fragment() {

    private lateinit var binding: FragmentRecordBinding
    private lateinit var recordAdapter: RecordAdapter
    private var user: UserEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recordAdapter = RecordAdapter()
        user = arguments?.getParcelable("user")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRecordBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupData()
    }

    private fun setupData() {
        user?.scores?.let { recordAdapter.setData(it) }
        binding.rvScore.apply {
            adapter = recordAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, true)
            isNestedScrollingEnabled = false
        }
    }

}