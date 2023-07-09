package alejo.meli.home.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DataShipping(
    @Json(name = "free_shipping") val free_shipping: Boolean
)