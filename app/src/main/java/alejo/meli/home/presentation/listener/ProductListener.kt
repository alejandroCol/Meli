package alejo.meli.home.presentation.listener

import alejo.meli.home.domain.model.Product

interface ProductListener {
    fun onProductClicked(product: Product)
}