package alejo.meli.home.presentation.search

import alejo.meli.core.presentation.OneTimeEvent
import alejo.meli.home.domain.usecase.GetProductsSearchedUseCase
import alejo.meli.home.utils.CoroutineTestExtension
import alejo.meli.home.utils.InstantExecutorExtension
import alejo.meli.home.utils.StubBuilder.Companion.getFakeProducts
import alejo.meli.home.utils.StubBuilder.Companion.query
import android.accounts.NetworkErrorException
import androidx.lifecycle.Observer
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.slot
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension

@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class)
class HomeViewModelTest {

    // Subject under test
    private lateinit var homeViewModel: HomeViewModel

    @MockK
    private lateinit var getProductsSearched: GetProductsSearchedUseCase

    @RelaxedMockK
    private lateinit var navigationObserver: Observer<OneTimeEvent<HomeNavigation>>

    @RelaxedMockK
    private lateinit var homeStateObserver: Observer<HomeViewState>

    @JvmField
    @RegisterExtension
    val coroutineExtension = CoroutineTestExtension()

    private val homeCapturedState = slot<HomeViewState>()
    private val homeStateList = arrayListOf<HomeViewState>()
    private val navigationCaptured = slot<OneTimeEvent<HomeNavigation>>()
    private val navigationList = arrayListOf<HomeNavigation>()

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)

        homeViewModel = HomeViewModel(
            getProductsSearched
        )

        homeViewModel.navigationLiveData.observeForever(navigationObserver)
        homeViewModel.stateLiveData.observeForever(homeStateObserver)

        coEvery { homeStateObserver.onChanged(capture(homeCapturedState)) } answers {
            homeStateList.add(homeCapturedState.captured)
        }

        coEvery { navigationObserver.onChanged(capture(navigationCaptured)) } answers {
            navigationList.add(navigationCaptured.captured.peekContent()!!)
        }
    }

    @AfterEach
    fun tearDown() {
        homeViewModel.navigationLiveData.removeObserver(navigationObserver)
        homeViewModel.stateLiveData.removeObserver(homeStateObserver)

        homeStateList.clear()
        navigationList.clear()
    }

    @Nested
    inner class ViewInitialized {
        @Test
        fun `Given query to search, When get products, Then show Content state`() =
            runBlocking {
                // Given
                coEvery { getProductsSearched(query) } returns getFakeProducts()

                // When
                homeViewModel.searchProducts(query)

                // Then
                assertEquals(2, homeStateList.size)
                assertEquals(HomeViewState.Loading, homeStateList.first())
                assertEquals(HomeViewState.Content(getFakeProducts()), homeStateList.last())
            }
    }

    @Test
    fun `Given query to search, When get empty list product, Then show Empty state`() =
        runBlocking {
            // Given
            coEvery { getProductsSearched(query) } returns emptyList()

            // When
            homeViewModel.searchProducts(query)

            // Then
            assertEquals(2, homeStateList.size)
            assertEquals(HomeViewState.Loading, homeStateList.first())
            assertEquals(HomeViewState.Empty, homeStateList.last())
        }

    @Test
    fun `Given query to search, When get exception service, Then show Error state`() =
        runBlocking {
            // Given
            coEvery { getProductsSearched(query) } throws NetworkErrorException()

            // When
            homeViewModel.searchProducts(query)

            // Then
            assertEquals(2, homeStateList.size)
            assertEquals(HomeViewState.Loading, homeStateList.first())
            assertEquals(HomeViewState.Error, homeStateList.last())
        }
}
