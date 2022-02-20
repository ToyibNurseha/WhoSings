package com.toyibnurseha.whosings.ui.leaderboard

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.toyibnurseha.whosings.db.model.UserEntity
import com.toyibnurseha.whosings.repository.Repository
import com.toyibnurseha.whosings.utils.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect

@ExperimentalCoroutinesApi
class LeaderboardViewModel @ViewModelInject constructor(private val repo: Repository) : ViewModel() {

    fun getUsers() = liveData {
        repo.getUsers().collect {
            emit(it)
        }
    }

}