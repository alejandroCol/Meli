package alejo.meli.home.data.mapper

import alejo.meli.home.data.dto.DataProduct
import alejo.meli.home.data.dto.DataResponseSearch
import alejo.meli.home.data.dto.DataShipping
import alejo.meli.home.domain.model.Product
import alejo.meli.home.domain.model.ResponseSearch
import alejo.meli.home.domain.model.Shipping

fun DataResponseSearch.toDomainEntity(): ResponseSearch = ResponseSearch(
    results.map { it.toDomainEntity() }.toMutableList()
)

fun ResponseSearch.toDto(): DataResponseSearch = DataResponseSearch(
    products.map { it.toDto() }.toMutableList()
)

fun DataProduct.toDomainEntity(): Product = Product(
    title,
    thumbnail,
    price,
    shipping.toDomainEntity()
)

fun Product.toDto(): DataProduct = DataProduct(
    title,
    thumbnail,
    price,
    shipping.toDto()
)

fun DataShipping.toDomainEntity(): Shipping = Shipping(
    free_shipping
)

fun Shipping.toDto(): DataShipping = DataShipping(
    freeShipping
)
