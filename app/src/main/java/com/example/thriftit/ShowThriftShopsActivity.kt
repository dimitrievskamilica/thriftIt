package com.example.thriftit

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thriftit.databinding.ActivityShowThriftShopsBinding
import timber.log.Timber

class ShowThriftShopsActivity : AppCompatActivity() {
    lateinit var app: MyApplication
    private lateinit var binding: ActivityShowThriftShopsBinding
    private lateinit var adapter: ThriftShopsAdapter
    var getContent =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                if (data != null) {
                    val name = data.getStringExtra("name")
                    val street = data.getStringExtra("street")
                    val streetNumber = data.getIntExtra("streetNumber",0)
                    val sale = data.getBooleanExtra("sale",false)
                    val pos = data.getIntExtra("position", -1)

                    if (name != null && street != null ) {
                        if (pos != -1 ) {
                            app.thriftShops[pos].name = name
                            app.thriftShops[pos].street = street
                            app.thriftShops[pos].streetNumber = streetNumber.toInt()
                            app.thriftShops[pos].sale=sale

                        }
                    }
                    app.saveData()
                    adapter.notifyItemChanged(pos)
                }
            }
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_thrift_shops)
        app = application as MyApplication
        binding = ActivityShowThriftShopsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ThriftShopsAdapter(app.thriftShops, object:ThriftShopsAdapter.MyOnClick{
            override fun onLongClick(p0: View?, pos: Int) {
                Timber.d("Here code comes ${pos}.")
                val data = intent
                data.putExtra("SELECTED_ID", app.thriftShops[pos].id)
                val builder =
                    AlertDialog.Builder(this@ShowThriftShopsActivity) //access context from inner class
                //set title for alert dialog
                builder.setTitle("Delete")
                builder.setMessage("Thrift shop ${app.thriftShops[pos].toString()}")
                builder.setIcon(android.R.drawable.ic_dialog_alert)
                builder.setPositiveButton("Yes") { dialogInterface, which -> //performing positive action
                    Toast.makeText(applicationContext, "clicked yes", Toast.LENGTH_LONG).show()

                    app.thriftShops.remove(app.thriftShops[pos])
                    adapter.notifyItemRemoved(pos)
                    setResult(RESULT_OK, data)
                    app.saveData()
                }
                builder.setNeutralButton("Cancel") { dialogInterface, which -> //performing cancel action
                    Toast.makeText(
                        applicationContext,
                        "clicked cancel\n operation cancel",
                        Toast.LENGTH_LONG
                    ).show()
                }
                builder.setNegativeButton("No") { dialogInterface, which -> //performing negative action
                    Toast.makeText(applicationContext, "clicked No", Toast.LENGTH_LONG).show()
                }
                // Create the AlertDialog
                val alertDialog: AlertDialog = builder.create()
                alertDialog.setCancelable(false)
                alertDialog.show()

            }

            override fun onClick(p0: View?, pos:Int) {
                Timber.d("Here code comes ${pos}.")
                var addIntent= Intent(getApplicationContext(), AddThriftShopActivity::class.java)
                addIntent.putExtra("position", pos.toString().toInt())

                setResult(RESULT_OK, addIntent)
                getContent.launch(addIntent)

            }

        })
        binding.recyclerView.adapter = adapter
        //adapter.notifyDataSetChanged();
        //Timber.d("Items ${app.data.guests.size}")
    }
}