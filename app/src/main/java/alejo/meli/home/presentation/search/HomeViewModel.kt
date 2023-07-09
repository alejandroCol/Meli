package alejo.meli.home.presentation.search

import alejo.meli.core.presentation.BaseViewModel
import alejo.meli.home.domain.model.Product
import alejo.meli.home.domain.usecase.GetProductsSearchedUseCase
import alejo.meli.home.presentation.listener.ProductListener
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getProductsSearched: GetProductsSearchedUseCase
) : BaseViewModel<HomeViewState, HomeNavigation>(), ProductListener {

    fun searchProducts(search: String) {
        setState(HomeViewState.Loading)
        viewModelScope.launch {
            try {
                val products = getProductsSearched(search)
                handleProductsResponse(products)
            } catch (throwable: IOException) {
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

    fun cleanSearch() {
        setState(HomeViewState.Empty)
    }

    companion object {
        const val ARG_PRODUCT = "arg_product"
    }

    override fun onProductClicked(product: Product) {
        navigateTo(HomeNavigation.Detail(product))
    }
}
