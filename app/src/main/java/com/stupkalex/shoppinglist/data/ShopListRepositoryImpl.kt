package com.stupkalex.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.stupkalex.shoppinglist.domain.ShopItem
import com.stupkalex.shoppinglist.domain.ShopListRepository
import javax.inject.Inject

class ShopListRepositoryImpl @Inject constructor(
    val shopListDao : ShopListDao,
    val mapper : ShopListMapper
) : ShopListRepository {

    override fun getShopList(): LiveData<List<ShopItem>> = Transformations.map(
        shopListDao.getShopList()
    ) {
        mapper.mapListDbModelToEntity(it)
    }

    override suspend fun getShopItem(shopItemId: Int): ShopItem {
        val shopItem = shopListDao.getShopItem(shopItemId)
        return mapper.mapDbModelToEntity(shopItem)
    }

    override suspend fun editShopItem(shopItem: ShopItem) {
        shopListDao.addShopItem(mapper.mapEntityToDbModel(shopItem))
    }

    override suspend fun addShopItem(shopItem: ShopItem) {
        shopListDao.addShopItem(mapper.mapEntityToDbModel(shopItem))
    }

    override suspend fun deleteShopItem(shopItem: ShopItem) {
        shopListDao.deleteShopItem(shopItem.id)
    }
}