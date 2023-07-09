package alejo.meli.home.presentation.search

import alejo.meli.core.presentation.BaseViewModel
import alejo.meli.home.domain.model.Product
import alejo.meli.home.domain.usecase.GetProductsSearchedUseCase
import alejo.meli.home.presentation.listener.ProductListener
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getProductsSearched: GetProductsSearchedUseCase
) : BaseViewModel<HomeViewState, HomeNavigation>(), ProductListener {

    private val _lastSearch = MutableLiveData<String>()
    val lastSearch: LiveData<String> get() = _lastSearch

    fun searchProducts(search: String) {
        setState(HomeViewState.Loading)
        viewModelScope.launch {
            try {
                val products = getProductsSearched(search)
                _lastSearch.value = search
                handleProductsResponse(products)
            } catch (throwable: Throwable) {
                setState(HomeViewState.Error)
            }
        }
    }

    private fun handleProductsResponse(products: List<Product>?) {
        when {
            products != null && products.isNotEmpty() -> setState(HomeViewState.Content(products))
            else -> setEmpty()
        }
    }

    private fun setEmpty() {
        setState(HomeViewState.Empty)
    }

    companion object {
        const val ARG_PRODUCT = "arg_product"
    }

    override fun onProductClicked(product: Product) {
        navigateTo(HomeNavigation.Detail(product))
    }
}
