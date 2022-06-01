package com.stupkalex.shoppinglist.app

import android.app.Application
import com.stupkalex.shoppinglist.di.DaggerApplicationComponent

class ShoppingListApp: Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }

}