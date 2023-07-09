package alejo.meli.home.utils

import alejo.meli.home.domain.model.Product

class StubBuilder {
    companion object {
        const val query: String = "Motorola"

        fun getFakeProducts(): List<Product> = listOf(
            Product("Motorola", "image.com", "5000")
        )
    }
}