package com.stupkalex.shoppinglist.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.stupkalex.shoppinglist.domain.ShopItem
import com.stupkalex.shoppinglist.domain.ShopListRepository
import java.lang.RuntimeException
import kotlin.random.Random

class ShopListRepositoryImpl(application: Application) : ShopListRepository {

    val shopListDao = AppDatabase.getInstance(application).shopListDao()
    val mapper = ShopListMapper()

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