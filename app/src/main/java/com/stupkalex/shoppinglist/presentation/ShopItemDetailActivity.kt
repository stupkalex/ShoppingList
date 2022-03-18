package com.stupkalex.shoppinglist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.stupkalex.shoppinglist.R
import com.stupkalex.shoppinglist.domain.ShopItem


class ShopItemDetailActivity : AppCompatActivity(), ShopItemFragment.OnEditingFinishedListener {



    private var screenMode = MODE_UNKNOWN
    private var shopItemId = UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item_detail)
        parseIntent()
        if(savedInstanceState == null) {
            changeModeActivity()
        }
    }

    private fun changeModeActivity() {
        val fragment = when (screenMode) {
            ADD_MODE -> ShopItemFragment.newInstanceAddItem()
            EDIT_MODE -> ShopItemFragment.newInstanceEditItem(shopItemId)
            else -> throw RuntimeException("Mode not found")
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.shop_item_container, fragment)
            .commit()
    }



     private fun parseIntent() {
         if (!intent.hasExtra(SHOP_ITEM_EXTRA_MODE)) {
             throw RuntimeException("Intent not found")
         }
         val mode = intent.getStringExtra(SHOP_ITEM_EXTRA_MODE)
         if (mode != ADD_MODE && mode != EDIT_MODE) {
             throw RuntimeException("Mode not found")
         }
         screenMode = mode
         if (screenMode == EDIT_MODE) {
             if (!intent.hasExtra(SHOP_ITEM_EXTRA_ID)) {
                 throw RuntimeException("ShopItemId not found")
             }
             shopItemId = intent.getIntExtra(SHOP_ITEM_EXTRA_ID, UNDEFINED_ID)

         }
     }

    companion object {
        private const val SHOP_ITEM_EXTRA_MODE = "shop_item_extra_mode"
        private const val ADD_MODE = "add_mode"
        private const val EDIT_MODE = "edit_mode"
        private const val SHOP_ITEM_EXTRA_ID = "shop_item_extra_id"
        private const val MODE_UNKNOWN = " "
        private const val UNDEFINED_ID = -1

        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, ShopItemDetailActivity::class.java)
            intent.putExtra(SHOP_ITEM_EXTRA_MODE, ADD_MODE)
            return intent
        }

        fun newIntentEditItem(context: Context, shopItemId: Int): Intent {
            val intent = Intent(context, ShopItemDetailActivity::class.java)
            intent.putExtra(SHOP_ITEM_EXTRA_MODE, EDIT_MODE)
            intent.putExtra(SHOP_ITEM_EXTRA_ID, shopItemId)
            return intent
        }

    }

    override fun onEditingFinish() {
        finish()
    }
}