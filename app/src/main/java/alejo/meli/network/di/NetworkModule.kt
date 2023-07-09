package alejo.meli.network.di

import alejo.meli.core.data.DomainErrorFactory
import alejo.meli.home.data.datasource.remote.ProductRemoteDataSource
import alejo.meli.home.data.repository.DefaultProductRepository
import alejo.meli.network.Urls.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Singleton
    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ProductRemoteDataSource =
        retrofit.create(ProductRemoteDataSource::class.java)

    @Singleton
    @Provides
    fun providesRepository(apiService: ProductRemoteDataSource, errorFactory: DomainErrorFactory) =
        DefaultProductRepository(apiService, errorFactory)
}
