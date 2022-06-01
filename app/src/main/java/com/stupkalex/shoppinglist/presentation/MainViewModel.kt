package com.stupkalex.shoppinglist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stupkalex.shoppinglist.domain.DeleteShopItemUseCase
import com.stupkalex.shoppinglist.domain.EditShopItemUseCase
import com.stupkalex.shoppinglist.domain.GetShopListUseCase
import com.stupkalex.shoppinglist.domain.ShopItem
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    val deleteShopItemUseCase: DeleteShopItemUseCase,
    val editShopItemUseCase: EditShopItemUseCase,
    val getShopListUseCase: GetShopListUseCase
) : ViewModel() {

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