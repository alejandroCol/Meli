package alejo.meli.home.presentation.detail

import alejo.meli.core.presentation.BaseViewModel
import alejo.meli.home.domain.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor() :
    BaseViewModel<ProductDetailViewState, DetailNavigation>() {

    fun showDetail(product: Product) {
        setState(ProductDetailViewState.Loading)
        setState(ProductDetailViewState.Content(product))
    }
}
