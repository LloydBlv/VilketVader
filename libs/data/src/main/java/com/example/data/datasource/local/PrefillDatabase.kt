package com.example.data.datasource.local

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class PrefillDatabase @Inject constructor(
    private val prefillHelper: dagger.Lazy<PrefillHelper>
): RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        CoroutineScope(Dispatchers.IO).launch {
            prefillHelper.get().prefill(getInitialLocations())
        }
    }
}