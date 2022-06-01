package com.stupkalex.shoppinglist.di

import android.app.Application
import com.stupkalex.shoppinglist.data.AppDatabase
import com.stupkalex.shoppinglist.data.ShopListDao
import com.stupkalex.shoppinglist.data.ShopListRepositoryImpl
import com.stupkalex.shoppinglist.domain.ShopListRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindShopListRepository(impl: ShopListRepositoryImpl): ShopListRepository

    companion object{
        @ApplicationScope
        @Provides
        fun provideShopListDao(application: Application): ShopListDao {
            return AppDatabase.getInstance(application).shopListDao()
        }
    }
}