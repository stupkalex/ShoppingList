package com.stupkalex.shoppinglist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.stupkalex.shoppinglist.R
import com.stupkalex.shoppinglist.app.ShoppingListApp
import javax.inject.Inject

class MainActivity : AppCompatActivity(), ShopItemFragment.OnEditingFinishedListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var shopListAdapter: ShopListAdapter
    private var shopItemContainer : FragmentContainerView? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    val component by lazy {
        (application as ShoppingListApp).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        shopItemContainer = findViewById(R.id.shop_item_container)
        setupRecyclerView()
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        viewModel.shopList.observe(this) {
            shopListAdapter.submitList(it)
        }

        val buttonAddShopItem = findViewById<FloatingActionButton>(R.id.button_add_shop_item)
        buttonAddShopItem.setOnClickListener {
            if(isOnePadMode()) {
                val intent = ShopItemDetailActivity.newIntentAddItem(this)
                startActivity(intent)
            } else {
                launchFragment(ShopItemFragment.newInstanceAddItem())
            }
        }
    }


    private fun launchFragment(fragment: Fragment){
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .add(R.id.shop_item_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun isOnePadMode(): Boolean{
        return shopItemContainer == null
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.rv_shop_list)
        with(recyclerView) {
            shopListAdapter = ShopListAdapter()
            adapter = shopListAdapter
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.VIEW_TYPE_DISABLED,
                ShopListAdapter.MAX_POOL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.VIEW_TYPE_ENABLED,
                ShopListAdapter.MAX_POOL_SIZE
            )
        }
        setupLongClickListener()
        setupClickListener()
        setupSwipeListener(recyclerView)
    }

    private fun setupLongClickListener() {
        shopListAdapter.onShopItemLongClick = {
            viewModel.changeEnabledState(it)
        }
    }

    private fun setupClickListener() {
        shopListAdapter.onShopItemClickListener = {
            if(isOnePadMode()) {
                val intent = ShopItemDetailActivity.newIntentEditItem(this, it.id)
                startActivity(intent)
            } else {
                launchFragment(ShopItemFragment.newInstanceEditItem(it.id))
            }
        }
    }

    private fun setupSwipeListener(recyclerView: RecyclerView) {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = shopListAdapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteShopItem(item)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    override fun onEditingFinish() {
        Toast.makeText(this@MainActivity, getString(R.string.done), Toast.LENGTH_SHORT).show()
        supportFragmentManager.popBackStack()
    }
}