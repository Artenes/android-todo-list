package bok.artenes.todoapp.utils

import android.content.Context
import android.content.SharedPreferences
import bok.artenes.todoapp.BuildConfig
import bok.artenes.todoapp.common.ObservableValue

/**
 * Wrapper around android's [SharedPreferences].
 * This makes testing easier by decoupling framework implementation details.
 */
class KeyValueRepository(context: Context) {

    private val sharedPreference = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)

    /**
     * Instead of client code having to worry about [SharedPreferences.OnSharedPreferenceChangeListener]
     * they will be able to do something like:
     *
     *   repo.get(key).observe { value ->  }
     *
     * The callbacks android enforces us to use will be handled by the wrapper.
     */
    private val observers = mutableMapOf<String, ObservableValue<String>>()

    /**
     * Listener implementation that will update [observers].
     */
    private val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
        observers[key]?.value = getValue(key)
    }

    init {
        sharedPreference.registerOnSharedPreferenceChangeListener(listener)
    }

    /**
     * Put a value in the repository.
     */
    fun put(key: String, value: String) {
        sharedPreference.edit().putString(key, value).apply()
    }

    /**
     * Get a observable value for the given key.
     */
    fun get(key: String): ObservableValue<String> {
        return observers[key] ?: ObservableValue(getValue(key)).also {
            observers[key] = it
        }
    }

    /**
     * Gets a value from the [SharedPreferences] for the given key.
     */
    fun getValue(key: String): String {
        return sharedPreference.getString(key, "") ?: ""
    }

    companion object {
        private const val FILE_NAME = "${BuildConfig.APPLICATION_ID}.TASKS"
    }

}