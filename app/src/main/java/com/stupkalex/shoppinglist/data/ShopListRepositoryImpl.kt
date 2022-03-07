package com.stupkalex.shoppinglist.data

import com.stupkalex.shoppinglist.domain.ShopItem
import com.stupkalex.shoppinglist.domain.ShopListRepository
import java.lang.RuntimeException

object ShopListRepositoryImpl: ShopListRepository {

    private var shopList = mutableListOf<ShopItem>()
    private var autoIncrementId = 0;

    override fun getShopList(): List<ShopItem> {
        return shopList
    }

    override fun getShopItem(shopItemId: Int): ShopItem {
       return shopList.find { it.id == shopItemId } ?: throw RuntimeException("Shop item id$shopItemId not found")
    }

    override fun editShopItem(shopItem: ShopItem) {
        val oldShopItem = getShopItem(shopItem.id)
        deleteShopItem(oldShopItem)
        shopList.add(shopItem)
    }

    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.UNDEFINED_ID) {
            shopItem.id = autoIncrementId++
        }
        shopList.add(shopItem)
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
    }
}