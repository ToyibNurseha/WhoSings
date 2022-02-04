package com.toyibnurseha.whosings.ui.login

import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.toyibnurseha.whosings.db.model.UserEntity
import com.toyibnurseha.whosings.repository.Repository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

@ExperimentalCoroutinesApi
class LoginViewModel @ViewModelInject constructor(private val repository: Repository) :
    ViewModel() {

    val filledUser: MutableLiveData<UserEntity> = MutableLiveData(UserEntity())

    fun getLoggedUser() = liveData {
        repository.getLoggedUser().collect {
            emit(it)
        }
    }

    fun bind(editText: EditText) {
        editText.doOnTextChanged { text, _, _, _ ->
            CoroutineScope(Dispatchers.Main).launch {
                filledUser.value = UserEntity(text.toString(), false)
            }
        }
    }

    fun insertUser() = runBlocking {
        filledUser.value?.let {
            it.isLogged = true
            repository.insertUser(it).collect { result ->
                if (result) {
                    filledUser.value = it
                }
            }
        }
    }
}