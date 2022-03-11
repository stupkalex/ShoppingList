package com.stupkalex.shoppinglist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stupkalex.shoppinglist.data.ShopListRepositoryImpl
import com.stupkalex.shoppinglist.domain.AddShopItemUseCase
import com.stupkalex.shoppinglist.domain.EditShopItemUseCase
import com.stupkalex.shoppinglist.domain.GetShopItemUseCase
import com.stupkalex.shoppinglist.domain.ShopItem
import java.lang.Exception

class ShopItemDetailViewModel : ViewModel() {

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen

    private val _shopItem = MutableLiveData<ShopItem>()
    val shopItem: LiveData<ShopItem>
        get() = _shopItem

    private val repository = ShopListRepositoryImpl

    private val getShopItemUseCase = GetShopItemUseCase(repository)
    private val addShopItemUseCase = AddShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    private fun getShopItem(shopItemId: Int) {
       val item = getShopItemUseCase.getShopItem(shopItemId)
        _shopItem.value = item
    }

    private fun addShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        if (fieldValidate(name, count)) {
            _shopItem.value?.let {
                val shopItem = it.copy(name = name, count = count)
                addShopItemUseCase.addShopItem(shopItem)
                finishWork()
            }
        }
    }

    private fun editShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        if (fieldValidate(name, count)) {
            val shopItem = ShopItem(name, count, true)
            editShopItemUseCase.editShopItem(shopItem)
            finishWork()
        }
    }

    private fun parseName(inputName: String?): String {
        return inputName?.trim() ?: ""
    }

    private fun parseCount(inputCount: String?): Int {
        return try {
            inputCount?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }

    private fun fieldValidate(name: String, count: Int): Boolean {
        var result = true
        if (name.isBlank()) {
            result = false
            _errorInputName.value = true
        }
        if (count <= 0) {
            result = false
            _errorInputCount.value = true
        }
        return result
    }

    public fun resetErrorInputName() {
        _errorInputName.value = false
    }

    public fun resetErrorInputCount() {
        _errorInputCount.value = false
    }

    private fun finishWork(){
        _shouldCloseScreen.value = Unit
    }

}