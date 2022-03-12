package com.stupkalex.shoppinglist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.stupkalex.shoppinglist.R
import com.stupkalex.shoppinglist.domain.ShopItem


class ShopItemDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: ShopItemDetailViewModel
    private lateinit var textInputLayoutName: TextInputLayout
    private lateinit var textInputLayoutCount: TextInputLayout
    private lateinit var textInputEditTextName: TextInputEditText
    private lateinit var textInputEditTextCount: TextInputEditText
    private lateinit var saveButton: Button

    private var screenMode = MODE_UNKNOWN
    private var shopItemId = ShopItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item_detail)
        parseIntent()
        viewModel = ViewModelProvider(this)[ShopItemDetailViewModel::class.java]
        initViews()
        setupEditTextChangedListener()
        changeModeActivity()
        setupObserveError()


    }

    private fun changeModeActivity() {
        when (screenMode) {
            ADD_MODE -> launchAddMode()
            EDIT_MODE -> launchEditMode()
        }
    }

    private fun setupObserveError() {
        viewModel.shouldCloseScreen.observe(this) {
            finish()
        }
        viewModel.errorInputName.observe(this) {
            val message = if (it) {
                getString(R.string.text_error_name)
            } else {
                null
            }
            textInputLayoutName.error = message
        }


        viewModel.errorInputCount.observe(this) {
            val message = if (it) {
                getString(R.string.text_error_count)
            } else {
                null
            }
            textInputLayoutCount.error = message
        }
    }

    private fun launchAddMode() {
        saveButton.setOnClickListener {
            viewModel.addShopItem(textInputEditTextName.text?.toString(), textInputEditTextCount.text?.toString())
        }
    }

    private fun launchEditMode() {
        viewModel.getShopItem(shopItemId)
        viewModel.shopItem.observe(this) {
            textInputEditTextName.setText(it.name)
            textInputEditTextCount.setText(it.count.toString())
        }
        saveButton.setOnClickListener {
            viewModel.editShopItem(
                textInputEditTextName.text?.toString(),
                textInputEditTextCount.text?.toString()
            )
        }
    }

    private fun setupEditTextChangedListener() {
        textInputEditTextName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        textInputEditTextCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputCount()
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

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
            shopItemId = intent.getIntExtra(SHOP_ITEM_EXTRA_ID, ShopItem.UNDEFINED_ID)

        }
    }

    private fun initViews() {
        textInputLayoutName = findViewById(R.id.textInputLayoutName)
        textInputLayoutCount = findViewById(R.id.textInputLayoutCount)
        textInputEditTextName = findViewById(R.id.textInputEditTextName)
        textInputEditTextCount = findViewById(R.id.textInputEditTextCount)
        saveButton = findViewById(R.id.saveButton)
    }

    companion object {
        private const val SHOP_ITEM_EXTRA_MODE = "shop_item_extra_mode"
        private const val ADD_MODE = "add_mode"
        private const val EDIT_MODE = "edit_mode"
        private const val SHOP_ITEM_EXTRA_ID = "shop_item_extra_id"
        private const val MODE_UNKNOWN = " "

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
}