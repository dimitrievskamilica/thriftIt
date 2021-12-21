package com.example.thriftit

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import timber.log.Timber
import java.io.File
import java.io.IOException
import java.util.*

const val MY_SP_FILE_NAME = "myshared.data"
const val MY_FILE_NAME = "mydata.json"

class MyApplication: Application() {

    lateinit var sharedPref: SharedPreferences
    lateinit var thriftShops:MutableList<ThriftShop>
    private lateinit var gson: Gson
    private lateinit var file: File

    override fun onCreate() {
        super.onCreate()
        sharedPref = getSharedPreferences(MY_SP_FILE_NAME, Context.MODE_PRIVATE)
        thriftShops = mutableListOf<ThriftShop>()
        //thriftShops.add(ThriftShop("GoodWill","Smetanova",33))
        gson = Gson()
        file = File(filesDir, MY_FILE_NAME)
        if (!containsID()) {
            saveID(UUID.randomUUID().toString().replace("-", ""))
        }
        loadData()
        //saveData()
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
    fun saveData() {
        try {
            //for FileUtils import org.apache.commons.io.FileUtils
            //in gradle implementation 'org.apache.commons:commons-io:1.3.2'
            org.apache.commons.io.FileUtils.writeStringToFile(file, gson.toJson(thriftShops))
            Timber.d("Save to file.")
        } catch (e: IOException) {
            Timber.d("Can't save " + file.path)
        }
    }
    fun loadData() {
        val listPersonType = object : TypeToken<MutableList<ThriftShop>>() {}.type
       // var hotel=Hotel(2)
        thriftShops = try { //www
            Timber.d("My file data:${org.apache.commons.io.FileUtils.readFileToString(file)}")
            gson.fromJson(
                org.apache.commons.io.FileUtils.readFileToString(file),
                listPersonType
            )
        } catch (e: IOException) {
            Timber.d("No file init data.")
            thriftShops
        }
    }
}