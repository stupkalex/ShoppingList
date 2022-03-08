package com.stupkalex.shoppinglist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.stupkalex.shoppinglist.R

private lateinit var viewModel: MainViewModel

private var count = 0

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopList.observe(this){
            Log.i("TEST_SHOP_LIST", it.toString())
            if(count == 0){
                count++
                viewModel.changeEnabledState(it[0])
            }
        }
    }
}