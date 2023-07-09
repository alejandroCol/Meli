package alejo.meli.home.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DataResponseSearch(
    @Json(name = "results") val results: List<DataProduct>
)