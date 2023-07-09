package alejo.meli.home.data.datasource.remote

import alejo.meli.home.data.dto.DataResponseSearch
import alejo.meli.network.Urls
import alejo.meli.utils.MoshiEndpoint
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductRemoteDataSource {
    @MoshiEndpoint
    @GET(Urls.SEARCH)
    suspend fun fetchSearchProducts(@Query("q") search: String): DataResponseSearch
}
