package alejo.meli.home.domain.usecase

import alejo.meli.home.domain.ProductRepository
import alejo.meli.home.utils.CoroutineTestExtension
import alejo.meli.home.utils.StubBuilder.Companion.getFakeProducts
import alejo.meli.home.utils.StubBuilder.Companion.query
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(CoroutineTestExtension::class)
class GetProductsSearchedUseCaseTest {

    // Subject under test
    private lateinit var getProductsSearched: GetProductsSearchedUseCase

    @MockK
    private lateinit var productRepository: ProductRepository

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)

        getProductsSearched = GetProductsSearchedUseCase(
            productRepository
        )
    }

    @Test
    fun `Given query search, when the app tries to find products using that query, Then return the products list`() =
        runBlocking {
            //  Given
            coEvery { productRepository.getSearchedProducts(query) } returns getFakeProducts()

            // When
            val result = getProductsSearched(query)

            // Then
            coVerify(exactly = 1) { productRepository.getSearchedProducts(query) }

            assertEquals(getFakeProducts(), result)
        }
}
