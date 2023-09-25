package com.example.colanportfolio.data.local

import android.content.Context
import android.content.SharedPreferences
import com.example.colanportfolio.ColanPortfolio

class SharedPreferenceImp {

    private var mSharedPreferences: SharedPreferences
    private val PREFERENCE_NAME = "Gmmco"

    init {
        val mContext = ColanPortfolio.getInstance()
        this.mSharedPreferences = mContext!!.getSharedPreferences(
                PREFERENCE_NAME,
                Context.MODE_PRIVATE
        )

    }

    fun setString(key: String, value: String) {
        mSharedPreferences.edit().putString(key, value).apply()
    }

    fun getString(key: String): String? {
        return mSharedPreferences.getString(key, "")
    }

    fun setInt(key: String, value: Int) {
        mSharedPreferences.edit().putInt(key, value).apply()
    }

    fun getInt(key: String): Int? {
        return mSharedPreferences.getInt(key, 0)
    }

    fun clear() {
        try {
            mSharedPreferences.edit().clear().apply()
        } catch (e: Exception) {
        }
    }

    fun containsKey(key: String): Boolean {
        return mSharedPreferences.contains(key)
    }

}