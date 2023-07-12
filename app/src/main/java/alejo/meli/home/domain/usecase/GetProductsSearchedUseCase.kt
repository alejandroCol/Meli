package alejo.meli.home.domain.usecase

import alejo.meli.home.domain.ProductRepository
import alejo.meli.home.domain.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetProductsSearchedUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(search: String): List<Product> {
        return withContext(Dispatchers.IO) {
            productRepository.getSearchedProducts(search)
        }
    }
}
