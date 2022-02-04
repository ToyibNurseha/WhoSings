package com.toyibnurseha.whosings.utils

import android.content.Context
import com.toyibnurseha.whosings.db.model.ScoreEntity
import com.toyibnurseha.whosings.db.model.UserEntity
import com.toyibnurseha.whosings.local.Artist
import com.toyibnurseha.whosings.local.Snippet
import com.toyibnurseha.whosings.local.Track
import com.toyibnurseha.whosings.local.Score
import com.toyibnurseha.whosings.models.ParentObjectData
import com.toyibnurseha.whosings.models.TrackData
import dagger.hilt.android.qualifiers.ApplicationContext

fun ParentObjectData.toArtistList(): List<Artist> {
    return this.parentObjectData?.body?.trackList?.map {
        Artist(
            name = it.track?.artistName ?: "",
            track = it.track?.toTrack()
        )
    } ?: emptyList()
}

fun TrackData?.toTrack(): Track? {
    this ?: return null
    return Track(
        id = this.id ?: 0,
        name = this.trackName ?: ""
    )
}

fun ParentObjectData.toSnippetLyrics(trackId: Long): Snippet {
    return Snippet(
        text = this.parentObjectData?.body?.snippetData?.text ?: "",
        trackId = trackId
    )
}

fun List<Artist>.toRandomTrack(): Track? {
    return this.shuffled().first().track
}

fun List<ScoreEntity>.getMaxScore(): Int {
    return this.maxOfOrNull {
        it.score
    } ?: 0
}

fun List<UserEntity>.toScoreDataList(@ApplicationContext context: Context): List<Score> {

    val result = mutableListOf<Score>()

    val scores = this.sortedByDescending {
        it.scores.getMaxScore()
    }.mapIndexed { index, userEntity ->
        Score(
            rank = "${index + 1}",
            username = userEntity.name,
            score = userEntity.scores.getMaxScore().toString()
        )
    }

    result.addAll(scores)

    return result
}