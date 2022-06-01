package com.stupkalex.shoppinglist.domain

import javax.inject.Inject

class GetShopItemUseCase @Inject constructor(
    val shopListRepository: ShopListRepository
    ) {

    suspend fun getShopItem(shopItemId: Int) : ShopItem {
       return shopListRepository.getShopItem(shopItemId)
    }

}