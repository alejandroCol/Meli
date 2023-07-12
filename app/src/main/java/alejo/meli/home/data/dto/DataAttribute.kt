package alejo.meli.home.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DataAttribute(
    @Json(name = "name") val name: String?,
    @Json(name = "value_name") val value_name: String?
)
