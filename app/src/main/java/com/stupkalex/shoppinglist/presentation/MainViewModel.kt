package com.stupkalex.shoppinglist.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stupkalex.shoppinglist.data.ShopListRepositoryImpl
import com.stupkalex.shoppinglist.domain.DeleteShopItemUseCase
import com.stupkalex.shoppinglist.domain.EditShopItemUseCase
import com.stupkalex.shoppinglist.domain.GetShopListUseCase
import com.stupkalex.shoppinglist.domain.ShopItem

class MainViewModel : ViewModel() {

    val repository = ShopListRepositoryImpl

    val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    val editShopItemUseCase = EditShopItemUseCase(repository)
    val getShopListUseCase = GetShopListUseCase(repository)

    val shopList = getShopListUseCase.getShopList()

    fun deleteShopItem(shopItem: ShopItem) {
        deleteShopItemUseCase.deleteShopItem(shopItem)
    }

    fun changeEnabledState(shopItem: ShopItem) {
        val newShopItem = shopItem.copy(enabled = !shopItem.enabled)
        editShopItemUseCase.editShopItem(newShopItem)
    }



}