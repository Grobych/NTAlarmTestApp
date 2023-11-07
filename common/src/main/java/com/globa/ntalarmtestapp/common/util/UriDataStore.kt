package com.globa.ntalarmtestapp.common.util

import android.content.Context
import android.net.Uri
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UriDataStore @Inject constructor(
    @ApplicationContext private val context: Context
){
    private val Context.dataStore by preferencesDataStore(name = "uri_datastore")
    private val URI_KEY = stringPreferencesKey("uri")
    fun getUri() = context.dataStore.data.map { preferences ->
        val savedValue = preferences[URI_KEY]
        if (savedValue != null) Uri.parse(savedValue)
        else null
    }

    suspend fun saveUri(uri: Uri) {
        context.dataStore.edit {preferences ->
            preferences[URI_KEY] = uri.toString()
        }
    }
}