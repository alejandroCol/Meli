package alejo.meli.home.di

import alejo.meli.core.data.DomainErrorFactory
import alejo.meli.home.data.datasource.remote.ProductRemoteDataSource
import alejo.meli.home.data.repository.DefaultProductRepository
import alejo.meli.home.domain.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class HomeModule {

    @Provides
    fun provideProductRepository(
        productRemoteDataSource: ProductRemoteDataSource,
        domainErrorFactory: DomainErrorFactory
    ): ProductRepository =
        DefaultProductRepository.getInstance(
            productRemoteDataSource,
            domainErrorFactory
        )

    @Provides
    fun provideDomainErrorFactory() =
        DomainErrorFactory()
}
