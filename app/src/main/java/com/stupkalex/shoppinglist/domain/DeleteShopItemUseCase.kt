package com.stupkalex.shoppinglist.domain

import javax.inject.Inject

class DeleteShopItemUseCase @Inject constructor(
    val shopListRepository: ShopListRepository
    ) {

    suspend fun deleteShopItem(shopItem: ShopItem) {
        shopListRepository.deleteShopItem(shopItem)
    }

}