package com.example.thriftit

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import java.util.*

const val MY_SP_FILE_NAME = "myshared.data"

class MyApplication: Application() {

    lateinit var sharedPref: SharedPreferences
    lateinit var thriftShops:MutableList<ThriftShop>

    override fun onCreate() {
        super.onCreate()
        sharedPref = getSharedPreferences(MY_SP_FILE_NAME, Context.MODE_PRIVATE)
        thriftShops = mutableListOf<ThriftShop>()
        if (!containsID()) {
            saveID(UUID.randomUUID().toString().replace("-", ""))
        }
    }
    fun saveID(id: String) {
        with(sharedPref.edit()) {
            putString("ID", id)
            apply()
        }
    }

    fun containsID(): Boolean {
        return sharedPref.contains("ID")
    }
    fun getID(): String? {
        return sharedPref.getString("ID", "DefaultNoData")
    }
}