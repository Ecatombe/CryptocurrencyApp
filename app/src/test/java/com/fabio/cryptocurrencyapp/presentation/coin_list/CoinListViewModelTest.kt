package com.fabio.cryptocurrencyapp.presentation.coin_list

import com.fabio.cryptocurrencyapp.common.Resource
import com.fabio.cryptocurrencyapp.data.remote.dto.toCoin
import com.fabio.cryptocurrencyapp.domain.model.Coin
import com.fabio.cryptocurrencyapp.domain.use_case.get_coins.GetCoinsUseCase
import com.fabio.cryptocurrencyapp.mocks.Mocks
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


@ExperimentalCoroutinesApi
class CoinListViewModelTest {

    private lateinit var viewModel: CoinListViewModel

    private val dispatcher = TestCoroutineDispatcher()

    @RelaxedMockK
    lateinit var getCoinsUseCase: GetCoinsUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when use case return success then viewModel state has list coins`() = runBlockingTest {
        val flow = flow {
            emit(Resource.Success(Mocks.coinList.map { it.toCoin() }))
        }
        every { getCoinsUseCase.invoke() } returns (flow)
        viewModel = CoinListViewModel(getCoinsUseCase)
        val state = viewModel.state.value
        assertEquals(state.isLoading, false)
        assertEquals(state.error, "")
        assertEquals(state.coins, Mocks.coinList.map { it.toCoin() })
    }

    @Test
    fun `when use case return error then viewModel state is error`() = runBlockingTest {
        val flow = flow {
            emit(Resource.Error<List<Coin>>("message", null))
        }
        every { getCoinsUseCase.invoke() } returns (flow)
        viewModel = CoinListViewModel(getCoinsUseCase)
        val state = viewModel.state.value
        assertEquals(state.isLoading, false)
        assertEquals(state.error, "message")
        assertEquals(state.coins, emptyList<List<Coin>>())
    }

    @Test
    fun `when use case return loading then viewModel state is loading`() = runBlockingTest {
        val flow = flow {
            emit(Resource.Loading<List<Coin>>(null))
        }
        every { getCoinsUseCase.invoke() } returns (flow)
        viewModel = CoinListViewModel(getCoinsUseCase)
        val state = viewModel.state.value
        assertEquals(state.isLoading, true)
        assertEquals(state.error, "")
        assertEquals(state.coins, emptyList<List<Coin>>())
    }

}
