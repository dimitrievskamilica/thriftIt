package com.example.thriftit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.thriftit.databinding.ActivityAddThriftShopBinding
import kotlin.random.Random

class AddThriftShopActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddThriftShopBinding
    private var pos : Int = -1
    lateinit var app: MyApplication
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_thrift_shop)
        app = application as MyApplication
        binding = ActivityAddThriftShopBinding.inflate(layoutInflater)
        setContentView(binding.root);
        if(intent.hasExtra("position")) {
            pos =intent.getIntExtra("position",-1)
            if(pos!=-1) {
                binding.buttonAdd.text = "Update"
                binding.editTextName.setText(app.thriftShops[pos].name)
                binding.editTextStreet.setText(app.thriftShops[pos].street)
                binding.editTextStreetNumber.setText(app.thriftShops[pos].streetNumber.toString())
                binding.checkBox.isChecked = app.thriftShops[pos].sale
                binding.buttonAdd.setTextKeepState("Update")
            }

        }
    }

    fun back(view: android.view.View) {
        finish();
    }
    fun addTriftShop(view: android.view.View) {
        val intent = Intent()
        intent.putExtra("name",binding.editTextName.text.toString())
        intent.putExtra("street",binding.editTextStreet.text.toString())
        intent.putExtra("streetNumber",binding.editTextStreetNumber.text.toString().toInt())
        if(binding.checkBox.isChecked){
            intent.putExtra("sale",true)
        }else{
            intent.putExtra("sale",false)
        }

        if(pos!=-1) {
            intent.putExtra("position", pos.toString().toInt())
        }
        setResult(RESULT_OK,intent)
        finish()
    }
}