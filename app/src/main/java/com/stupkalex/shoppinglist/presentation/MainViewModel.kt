package com.stupkalex.shoppinglist.presentation

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stupkalex.shoppinglist.data.ShopListRepositoryImpl
import com.stupkalex.shoppinglist.domain.DeleteShopItemUseCase
import com.stupkalex.shoppinglist.domain.EditShopItemUseCase
import com.stupkalex.shoppinglist.domain.GetShopListUseCase
import com.stupkalex.shoppinglist.domain.ShopItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val repository = ShopListRepositoryImpl(application)

    val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    val editShopItemUseCase = EditShopItemUseCase(repository)
    val getShopListUseCase = GetShopListUseCase(repository)

    val shopList = getShopListUseCase.getShopList()

    fun deleteShopItem(shopItem: ShopItem) {
        viewModelScope.launch {
            deleteShopItemUseCase.deleteShopItem(shopItem)
        }
    }

    fun changeEnabledState(shopItem: ShopItem) {
        viewModelScope.launch {
            val newShopItem = shopItem.copy(enabled = !shopItem.enabled)
            editShopItemUseCase.editShopItem(newShopItem)
        }
    }
}