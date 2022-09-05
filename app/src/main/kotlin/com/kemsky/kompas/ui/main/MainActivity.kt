package com.kemsky.kompas.ui.main

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.kemsky.kompas.databinding.ActivityMainBinding
import com.kemsky.kompas.ui.main.adapter.AllUserAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    private var binding: ActivityMainBinding? = null

    private val userAdapter = AllUserAdapter()

    private lateinit var queryTextListener: SearchView.OnQueryTextListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        configureRecyclerView()
        configureSearchView()
    }


    private fun configureRecyclerView() {
        binding?.rvUsers?.layoutManager = LinearLayoutManager(this@MainActivity)
        binding?.rvUsers?.setHasFixedSize(true)

        binding?.rvUsers?.adapter = userAdapter

        lifecycleScope.launch {
            viewModel.listUserData.collectLatest { pagedData ->
                userAdapter.submitData(pagedData)
            }
        }

        userAdapter.refresh()
    }

    private fun configureSearchView() {
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        binding?.searchView?.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        queryTextListener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                // OnChange
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                // OnSubmit
                val paged = userAdapter.snapshot().items.distinct().filter { result ->
                    result.login?.lowercase()?.startsWith(query.toString()) == true
                }
                userAdapter.submitData(lifecycle, PagingData.from(paged))
                userAdapter.notifyItemChanged(0)
                return false
            }
        }

        binding?.searchView?.setOnQueryTextListener(queryTextListener)

        binding?.searchView?.setOnCloseListener {
            Timber.e("Closed")
            configureRecyclerView()
            return@setOnCloseListener false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }


}