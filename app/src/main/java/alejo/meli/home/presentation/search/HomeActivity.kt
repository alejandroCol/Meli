package alejo.meli.home.presentation.search

import alejo.meli.R
import alejo.meli.core.presentation.OneTimeEventObserver
import alejo.meli.databinding.ActivityHomeBinding
import alejo.meli.home.domain.model.Product
import alejo.meli.home.presentation.adapter.ProductAdapter
import alejo.meli.home.presentation.detail.ProductDetailActivity
import alejo.meli.home.presentation.search.HomeViewModel.Companion.ARG_PRODUCT
import alejo.meli.utils.DebouncingQueryTextListener
import alejo.meli.utils.hide
import alejo.meli.utils.show
import alejo.meli.utils.showKeyboard
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        observeStates()
        observeNavigation()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupList()
        setupSearchView()
    }

    private fun setupList() {
        binding.productsRecyclerview.apply {
            layoutManager = LinearLayoutManager(this@HomeActivity)
            setHasFixedSize(true)
            adapter = ProductAdapter(viewModel)
            addItemDecoration(DividerItemDecoration(context, 0))
        }
    }

    private fun setupSearchView() {
        binding.run {
            searchView.setOnQueryTextListener(
                DebouncingQueryTextListener(
                    this@HomeActivity.lifecycle
                ) { newText ->
                    val searchQuery = newText?.takeIf { it.isNotEmpty() }
                    if (searchQuery != viewModel.lastSearch.value) {
                        viewModel.searchProducts(searchQuery ?: "")
                    }
                }
            )
            searchView.setIconifiedByDefault(false)
            searchView.requestFocus()
            searchView.showKeyboard()
        }
    }

    private fun observeNavigation() {
        viewModel.navigationLiveData.observe(
            this,
            OneTimeEventObserver {
                handleNavigation(it)
            }
        )
    }

    private fun observeStates() {
        viewModel.stateLiveData.observe(this) { handleViewState(it) }
    }

    private fun handleNavigation(navigation: HomeNavigation) {
        when (navigation) {
            is HomeNavigation.Detail -> openDetail(navigation.product)
        }
    }

    private fun openDetail(product: Product) {
        val intent = Intent(this, ProductDetailActivity::class.java)
        intent.putExtra(ARG_PRODUCT, product)
        startActivity(intent)
    }

    private fun handleViewState(state: HomeViewState) {
        when (state) {
            is HomeViewState.Content -> showContent(state.products)
            is HomeViewState.Empty -> showEmpty()
            is HomeViewState.Loading -> showLoading(true)
            is HomeViewState.Error -> showError()
        }
    }

    private fun showError() {
        showLoading(false)
        Snackbar.make(
            binding.searchView,
            getString(R.string.error_connection),
            Snackbar.LENGTH_LONG
        ).show()
    }

    private fun showEmpty() {
        showLoading(false)
        binding.productsRecyclerview.hide()
        binding.layoutEmptySearch.show()
    }

    private fun showContent(productList: List<Product>) {
        showLoading(false)
        binding.run {
            layoutEmptySearch.hide()
            productsRecyclerview.show()
            productsRecyclerview.apply {
                with(adapter as ProductAdapter) {
                    products = productList
                    notifyDataSetChanged()
                }
            }
        }
    }

    private fun showLoading(show: Boolean) {
        binding.run {
            if (show) {
                lavLoading.playAnimation()
                lavLoading.show()
                productsRecyclerview.hide()
                layoutEmptySearch.hide()
            } else {
                lavLoading.pauseAnimation()
                lavLoading.hide()
                productsRecyclerview.show()
            }
        }
    }
}
