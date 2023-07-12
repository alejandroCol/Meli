package alejo.meli.home.domain.model

import java.io.Serializable

data class Product(
    val title: String,
    val thumbnail: String,
    val price: String,
    val originalPrice: String?,
    val shipping: Shipping,
    val officialStoreName: String?,
    val condition: String?,
    val attributes: List<Attribute>?
) : Serializable {
    enum class Condition(val code: String) {
        NEW("new"),
        USED("used")
    }
}
