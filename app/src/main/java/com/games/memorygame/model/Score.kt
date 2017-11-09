package com.games.memorygame.model


import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

import java.util.Date
import java.util.UUID

@Entity(tableName = "scores")
data class Score(
        var user: String? = null,
        var scorePoints: Int? = null,
        @PrimaryKey
        var id: String = UUID.randomUUID().toString(),
        var date: Date = Date()
)
