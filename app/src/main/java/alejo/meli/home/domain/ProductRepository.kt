package alejo.meli.home.domain

import alejo.meli.home.domain.model.Product

interface ProductRepository {
    suspend fun getSearchedProducts(search: String): List<Product>
    suspend fun getDetailProduct(product: String): Product
}