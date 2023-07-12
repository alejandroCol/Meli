package alejo.meli.home.utils

import alejo.meli.home.domain.model.Product
import alejo.meli.home.domain.model.Shipping

class StubBuilder {
    companion object {
        const val query: String = "Motorola"

        fun getFakeProducts(): List<Product> = listOf(
            Product(
                "Motorola",
                "image.com",
                "5000",
                "6000",
                Shipping(true),
                "Store",
                "new",
                null
            )
        )
    }
}
