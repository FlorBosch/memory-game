package com.games.memorygame.persistence

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

import com.games.memorygame.model.Score

import io.reactivex.Flowable

@Dao
interface ScoreDao {

    @get:Query("SELECT * FROM scores ORDER BY scorePoints DESC")
    val allScores: Flowable<List<Score>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertScore(score: Score)

    @Query("DELETE FROM scores")
    fun deleteAllScores()
}
