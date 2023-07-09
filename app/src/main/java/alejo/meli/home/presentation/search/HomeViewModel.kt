package alejo.meli.home.presentation.search

import alejo.meli.core.presentation.BaseViewModel
import alejo.meli.home.domain.model.Product
import alejo.meli.home.domain.usecase.GetProductsSearchedUseCase
import alejo.meli.home.presentation.listener.ProductListener
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getProductsSearched: GetProductsSearchedUseCase,
) : BaseViewModel<HomeViewState, HomeNavigation>(), ProductListener {

    var emptyList = true

    fun searchProducts(search: String) {
        if (emptyList) setState(HomeViewState.Loading)
        viewModelScope.launch {
            try {
                val products = getProductsSearched(search)
                products?.let {
                    if (it.isNotEmpty()) {
                        emptyList = false
                        setState(HomeViewState.Content(it))
                    } else setEmpty()
                } ?: setEmpty()
            } catch (throwable: Throwable) {
                setState(HomeViewState.Error)
            }
        }
    }

    private fun setEmpty() {
        emptyList = true
        setState(HomeViewState.Empty)
    }

    fun cleanSearch() {
        setState(HomeViewState.Empty)
    }

    companion object {
        const val ARG_PRODUCT = "arg_product"
    }

    override fun onProductClicked(product: Product) {
        navigateTo(HomeNavigation.Detail(product.title))
    }
}