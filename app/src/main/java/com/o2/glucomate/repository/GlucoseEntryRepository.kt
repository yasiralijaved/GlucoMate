package com.o2.glucomate.repository

import com.o2.glucomate.db.dao.GlucoseEntryDao
import com.o2.glucomate.db.tables.GlucoseEntry
import kotlinx.coroutines.flow.Flow

class GlucoseEntryRepository (private val glucoseEntryDao: GlucoseEntryDao) {
    suspend fun insert(glucoseEntry: GlucoseEntry) =
        glucoseEntryDao.insert(glucoseEntry = glucoseEntry)

    suspend fun getAll() : Flow<List<GlucoseEntry>> =
        glucoseEntryDao.getAll()

    suspend fun delete(glucoseEntry: GlucoseEntry) =
        glucoseEntryDao.delete(glucoseEntry)
}