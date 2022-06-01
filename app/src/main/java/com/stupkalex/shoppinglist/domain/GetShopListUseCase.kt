package com.stupkalex.shoppinglist.domain

import androidx.lifecycle.LiveData
import javax.inject.Inject

class GetShopListUseCase @Inject constructor(
    val shopListRepository: ShopListRepository
    ) {

    fun getShopList() : LiveData<List<ShopItem>> {
        return shopListRepository.getShopList()
    }

}