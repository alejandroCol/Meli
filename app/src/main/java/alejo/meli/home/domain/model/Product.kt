package alejo.meli.home.domain.model

import java.io.Serializable

data class Product(
    val title: String,
    val thumbnail: String
) : Serializable
