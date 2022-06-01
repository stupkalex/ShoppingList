package com.stupkalex.shoppinglist.di

import androidx.lifecycle.ViewModel
import com.stupkalex.shoppinglist.presentation.MainViewModel
import com.stupkalex.shoppinglist.presentation.ShopItemDetailViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(MainViewModel::class)
    @Binds
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @IntoMap
    @ViewModelKey(ShopItemDetailViewModel::class)
    @Binds
    fun bindShopItemDetailViewModel(viewModel: ShopItemDetailViewModel): ViewModel
}