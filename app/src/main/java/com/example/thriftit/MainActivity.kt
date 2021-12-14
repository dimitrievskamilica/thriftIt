package com.example.thriftit

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {
    lateinit var app: MyApplication
    val resultContract = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            app.thriftShops.add( ThriftShop(
                data?.getStringExtra("name")!!,
                data.getStringExtra("street")!!,
                data.getIntExtra("streetNumber",1)
            )
            )
           // app.saveData()
           // Log.i("MainActivityAdd",app.data.guests.last().toString())

        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        app = application as MyApplication
    }

    fun about(view: android.view.View) {

    }
    fun AddThriftShop(view: android.view.View) {
        val intent = Intent(this,AddThriftShopActivity::class.java)
        resultContract.launch(intent)
    }
    fun showThriftShops(view: android.view.View) {
        val intent = Intent(this,ShowThriftShopsActivity::class.java)
        startActivity(intent)
    }

    fun exit(view: android.view.View) {}
}