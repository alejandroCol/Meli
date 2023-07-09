package alejo.meli.home.presentation.detail

import alejo.meli.home.domain.model.Product

sealed interface ProductDetailViewState {
    object Loading : ProductDetailViewState
    data class Content(val detailProduct: Product) : ProductDetailViewState
}

sealed interface DetailNavigation