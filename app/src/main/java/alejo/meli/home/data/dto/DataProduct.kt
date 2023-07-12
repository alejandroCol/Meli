package alejo.meli.home.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DataProduct(
    @Json(name = "title") val title: String,
    @Json(name = "thumbnail") val thumbnail: String,
    @Json(name = "price") val price: String,
    @Json(name = "original_price") val original_price: String?,
    @Json(name = "shipping") val shipping: DataShipping,
    @Json(name = "official_store_name") val official_store_name: String?,
    @Json(name = "condition") val condition: String?,
    @Json(name = "attributes") val attributes: List<DataAttribute>?
)
