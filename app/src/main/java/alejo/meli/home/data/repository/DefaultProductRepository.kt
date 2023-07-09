package alejo.meli.home.data.repository

import alejo.meli.core.data.DomainErrorFactory
import alejo.meli.core.data.runWith
import alejo.meli.home.data.datasource.remote.ProductRemoteDataSource
import alejo.meli.home.data.mapper.toDomainEntity
import alejo.meli.home.domain.ProductRepository
import alejo.meli.home.domain.model.Product
import alejo.meli.utils.Singleton

class DefaultProductRepository(
    private val remoteDataSource: ProductRemoteDataSource,
    private val errorFactory: DomainErrorFactory
) : ProductRepository {

    override suspend fun getSearchedProducts(search: String): List<Product> {
        return runWith(errorFactory) {
            val remote = remoteDataSource.fetchSearchProducts(search)
            remote.toDomainEntity().products
        }
    }

    companion object : Singleton() {
        @JvmStatic
        fun getInstance(
            remoteDataSource: ProductRemoteDataSource,
            errorFactory: DomainErrorFactory
        ) = get {
            DefaultProductRepository(remoteDataSource, errorFactory)
        }
    }
}
