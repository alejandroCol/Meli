package alejo.meli.home.presentation.search

import alejo.meli.home.domain.model.Product

sealed interface HomeViewState {
    object Error : HomeViewState
    object Loading : HomeViewState
    object Empty : HomeViewState
    data class Content(
        val products: List<Product>
    ) : HomeViewState
}

sealed interface HomeNavigation {
    data class Detail(val cityName: String) : HomeNavigation
}
