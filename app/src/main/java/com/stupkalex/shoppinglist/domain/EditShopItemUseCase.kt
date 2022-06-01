package com.stupkalex.shoppinglist.domain

import javax.inject.Inject

class EditShopItemUseCase @Inject constructor(
    val shopListRepository: ShopListRepository
    ) {

    suspend fun editShopItem(shopItem: ShopItem) {
        shopListRepository.editShopItem(shopItem)
    }

}