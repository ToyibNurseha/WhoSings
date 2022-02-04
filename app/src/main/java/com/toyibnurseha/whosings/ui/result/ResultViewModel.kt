package com.toyibnurseha.whosings.ui.result

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.toyibnurseha.whosings.db.model.UserEntity
import com.toyibnurseha.whosings.repository.Repository
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class ResultViewModel @ViewModelInject constructor(private val repo: Repository) : ViewModel() {

    fun logout(user: UserEntity) {
        user.isLogged = false
        repo.updateUser(user)
    }

}