package com.stupkalex.shoppinglist.presentation

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.stupkalex.shoppinglist.R
import com.stupkalex.shoppinglist.domain.ShopItem

class ShopItemFragment : Fragment() {

    private lateinit var viewModel: ShopItemDetailViewModel
    private lateinit var textInputLayoutName: TextInputLayout
    private lateinit var textInputLayoutCount: TextInputLayout
    private lateinit var textInputEditTextName: TextInputEditText
    private lateinit var textInputEditTextCount: TextInputEditText
    private lateinit var saveButton: Button
    private lateinit var onEditingFinishedListener: OnEditingFinishedListener

    private var screenMode = MODE_UNKNOWN
    private var shopItemId = ShopItem.UNDEFINED_ID

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shop_item, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OnEditingFinishedListener){
            onEditingFinishedListener = context
        } else {
            throw java.lang.RuntimeException("Activity must be implemented OnEditingFinishedListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ShopItemDetailViewModel::class.java]
        initViews(view)
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
        viewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
            onEditingFinishedListener.onEditingFinish()
        }
        viewModel.errorInputName.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.text_error_name)
            } else {
                null
            }
            textInputLayoutName.error = message
        }

        viewModel.errorInputCount.observe(viewLifecycleOwner) {
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
            viewModel.addShopItem(
                textInputEditTextName.text?.toString(),
                textInputEditTextCount.text?.toString()
            )
        }
    }

    private fun launchEditMode() {
        viewModel.getShopItem(shopItemId)
        viewModel.shopItem.observe(viewLifecycleOwner) {
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

    private fun parseParams() {
        val args = requireArguments()
        if (!args.containsKey(SHOP_ITEM_EXTRA_MODE)) {
            throw RuntimeException("Intent not found")
        }
        val mode = args.getString(SHOP_ITEM_EXTRA_MODE)
        if (mode != ADD_MODE && mode != EDIT_MODE) {
            throw RuntimeException("Mode not found")
        }
        screenMode = mode
        if (screenMode == EDIT_MODE) {
            if (!args.containsKey(SHOP_ITEM_EXTRA_ID)) {
                throw RuntimeException("ShopItemId not found")
            }
            shopItemId = args.getInt(SHOP_ITEM_EXTRA_ID, ShopItem.UNDEFINED_ID)

        }
    }


    private fun initViews(view: View) {
        textInputLayoutName = view.findViewById(R.id.textInputLayoutName)
        textInputLayoutCount = view.findViewById(R.id.textInputLayoutCount)
        textInputEditTextName = view.findViewById(R.id.textInputEditTextName)
        textInputEditTextCount = view.findViewById(R.id.textInputEditTextCount)
        saveButton = view.findViewById(R.id.saveButton)
    }

    interface OnEditingFinishedListener{
        fun onEditingFinish()
    }

    companion object {
        private const val SHOP_ITEM_EXTRA_MODE = "shop_item_extra_mode"
        private const val ADD_MODE = "add_mode"
        private const val EDIT_MODE = "edit_mode"
        private const val SHOP_ITEM_EXTRA_ID = "shop_item_extra_id"
        private const val MODE_UNKNOWN = " "

        fun newInstanceAddItem(): Fragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SHOP_ITEM_EXTRA_MODE, ADD_MODE)
                }
            }
        }

        fun newInstanceEditItem(shopItemId: Int): Fragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SHOP_ITEM_EXTRA_MODE, EDIT_MODE)
                    putInt(SHOP_ITEM_EXTRA_ID, shopItemId)
                }
            }
        }

    }
}