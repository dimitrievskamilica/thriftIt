package com.example.thriftit

import android.app.Application

class MyApplication: Application() {
    lateinit var thriftShops:MutableList<ThriftShop>
    override fun onCreate() {
        super.onCreate()
        thriftShops = mutableListOf<ThriftShop>()
    }
}