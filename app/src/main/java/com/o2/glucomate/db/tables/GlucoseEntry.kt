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

    fun getIntensity() : Intensity {
        return when(state) {
            State.FASTING -> {
                if(level < 70) return Intensity.LOW
                else if(level > 126) return Intensity.HIGH
                else return Intensity.NORMAL
            }
            State.BEFORE_MEAL -> {
                if(level < 170) return Intensity.LOW
                else if(level > 226) return Intensity.HIGH
                else return Intensity.NORMAL
            }
            State.AFTER_MEAL -> {
                if(level < 170) return Intensity.LOW
                else if(level > 226) return Intensity.HIGH
                else return Intensity.NORMAL
            }
            State.BEFORE_BED_TIME -> {
                if(level < 170) return Intensity.LOW
                else if(level > 226) return Intensity.HIGH
                else return Intensity.NORMAL
            }
        }
    }
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

enum class Intensity : StateLabel {
    NORMAL {
        override fun getLabel() = "NORMAL"
    },
    HIGH {
        override fun getLabel() = "HIGH"
    },
    LOW {
        override fun getLabel() = "LOW"
    }
}
