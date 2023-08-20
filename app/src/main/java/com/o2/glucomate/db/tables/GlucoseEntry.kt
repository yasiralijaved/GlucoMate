package com.o2.glucomate.db.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity (tableName = "glucose_entry")
data class GlucoseEntry(
    @ColumnInfo(name = "date") val createdAt: Date,
    @ColumnInfo(name = "state") val state: State,
    @ColumnInfo(name = "level") val level: Float,
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
}

enum class State : StateLabel {
    FASTING {
        override fun getLabel() = "Fasting"
    },
    BEFORE_MEAL {
        override fun getLabel() = "Before Meal"
    },
    AFTER_MEAL {
        override fun getLabel() = " After Meal"
    },
    BEFORE_BED_TIME {
        override fun getLabel() = "Before Bed"
    }
}

interface StateLabel {
    fun getLabel(): String
}
