package com.stupkalex.shoppinglist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.stupkalex.shoppinglist.R

private lateinit var viewModel: ShopItemDetailViewModel

class ShopItemDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item_detail)
        viewModel = ViewModelProvider(this)[ShopItemDetailViewModel::class.java]
    }
}