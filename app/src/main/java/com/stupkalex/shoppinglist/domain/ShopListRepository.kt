package com.stupkalex.shoppinglist.domain

interface ShopListRepository {

    fun getShopList() : List<ShopItem>

    fun getShopItem(shopItemId: Int) : ShopItem

    fun editShopItem(shopItem: ShopItem)

    fun addShopItem(shopItem: ShopItem)

    fun deleteShopItem(shopItem: ShopItem)

}