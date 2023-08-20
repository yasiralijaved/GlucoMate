package com.o2.glucomate.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.o2.glucomate.db.tables.GlucoseEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface GlucoseEntryDao {
    @Query("SELECT * FROM glucose_entry ORDER BY date DESC")
    fun getAll(): Flow<List<GlucoseEntry>>

    @Insert
    fun insert(glucoseEntry: GlucoseEntry)

    @Delete
    fun delete(glucoseEntry: GlucoseEntry)
}