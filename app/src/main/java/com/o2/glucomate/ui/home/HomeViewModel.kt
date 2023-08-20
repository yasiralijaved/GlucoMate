package com.o2.glucomate.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.o2.glucomate.db.AppDatabase
import com.o2.glucomate.db.tables.GlucoseEntry
import com.o2.glucomate.repository.GlucoseEntryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel (application: Application) : AndroidViewModel(application = application) {
    private val repository: GlucoseEntryRepository

    // Backing property to avoid state updates from other classes
    private val _glucoseEntries = MutableStateFlow<List<GlucoseEntry>>(emptyList())
    // The UI collects from this StateFlow to get its state updates
    val glucoseEntries: StateFlow<List<GlucoseEntry>> = _glucoseEntries

    init {
        val dao = AppDatabase.getDatabase(application).glucoseEntryDao()
        repository = GlucoseEntryRepository(dao)

        viewModelScope.launch {
            repository.getAll().collect { entries ->
                _glucoseEntries.value = entries
            }
        }
    }

    fun addGlucoseEntry(entry: GlucoseEntry) {
        viewModelScope.launch (Dispatchers.IO) {
            repository.insert(glucoseEntry = entry)
        }
    }

    fun deleteGlucoseEntry(entry: GlucoseEntry) {
        viewModelScope.launch (Dispatchers.IO) {
            repository.delete(glucoseEntry = entry)
        }
    }
}