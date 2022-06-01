package com.stupkalex.shoppinglist.domain

import javax.inject.Inject

class AddShopItemUseCase @Inject constructor(
    val shopListRepository: ShopListRepository
    ) {

    suspend fun addShopItem(shopItem: ShopItem){
        shopListRepository.addShopItem(shopItem)
    }

}