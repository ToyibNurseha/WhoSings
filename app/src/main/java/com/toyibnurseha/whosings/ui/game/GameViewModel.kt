package com.toyibnurseha.whosings.ui.game

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.toyibnurseha.whosings.db.model.ScoreEntity
import com.toyibnurseha.whosings.db.model.UserEntity
import com.toyibnurseha.whosings.local.Artist
import com.toyibnurseha.whosings.local.Snippet
import com.toyibnurseha.whosings.repository.Repository
import com.toyibnurseha.whosings.repository.WhoSingRepository
import com.toyibnurseha.whosings.utils.Resource
import com.toyibnurseha.whosings.utils.toRandomTrack
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect


class GameViewModel @ViewModelInject constructor(
    private val repo: WhoSingRepository,
    private val userRepo: Repository
) : ViewModel() {

    private var chartPage = 1

    val snippetLyric: MutableLiveData<Snippet?> = MutableLiveData(null)

    val matchCorrectness: MutableLiveData<Boolean> = MutableLiveData(null)

    val score: MutableLiveData<Int> = MutableLiveData(0)

    fun getChartArtists(user: UserEntity) = liveData<Resource<List<Artist>>> {
        if (chartPage <= 5) { // to limit the amount of quiz questions
            repo.getChartArtists(chartPage).collect {
                it.data?.toRandomTrack()?.let { track ->

                    repo.getSnippetLyrics(track.id).collect { snippetResource ->

                        if (snippetResource.data != null) {
                            snippetLyric.value = snippetResource.data
                        }
                    }
                }
                emit(it)
            }
        } else {
            user.scores.add(ScoreEntity(score = score.value!!))
            repo.updateUser(user)
            matchCorrectness.value = false
        }
    }

    @ExperimentalCoroutinesApi
    fun selectArtist(artist: Artist, user: UserEntity) {
        artist.track ?: return
        snippetLyric.value ?: return

        matchCorrectness.value = if (artist.track!!.id == snippetLyric.value!!.trackId) {
            chartPage++
            score.value = score.value!! + 1
            true
        } else {
            user.scores.add(ScoreEntity(score = score.value!!))
            repo.updateUser(user)
            false
        }
    }

}