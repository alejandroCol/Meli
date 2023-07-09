package alejo.meli.home.data.mapper

import alejo.meli.home.data.dto.DataProduct
import alejo.meli.home.data.dto.DataResponseSearch
import alejo.meli.home.domain.model.Product
import alejo.meli.home.domain.model.ResponseSearch

fun DataResponseSearch.toDomainEntity(): ResponseSearch = ResponseSearch(
    results.map { it.toDomainEntity() }.toMutableList()
)

fun ResponseSearch.toDto(): DataResponseSearch = DataResponseSearch(
    products.map { it.toDto() }.toMutableList()
)

fun DataProduct.toDomainEntity(): Product = Product(
    title,
    thumbnail,
    price
)

fun Product.toDto(): DataProduct = DataProduct(
    title,
    thumbnail,
    price
)
