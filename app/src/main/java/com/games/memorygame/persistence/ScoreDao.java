package com.themobilecompany.memorygame.persistence;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.themobilecompany.memorygame.model.Score;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface ScoreDao {

    @Query("SELECT * FROM scores ORDER BY scorePoints DESC")
    Flowable<List<Score>> getAllScores();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertScore(Score score);

    @Query("DELETE FROM scores")
    void deleteAllScores();
}
