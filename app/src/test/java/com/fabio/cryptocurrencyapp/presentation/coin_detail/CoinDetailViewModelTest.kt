package com.fabio.cryptocurrencyapp.presentation.coin_detail

import androidx.lifecycle.SavedStateHandle
import com.fabio.cryptocurrencyapp.common.Constants
import com.fabio.cryptocurrencyapp.common.Resource
import com.fabio.cryptocurrencyapp.data.remote.dto.toCoinDetail
import com.fabio.cryptocurrencyapp.domain.model.CoinDetail
import com.fabio.cryptocurrencyapp.domain.use_case.get_coin.GetCoinUseCase
import com.fabio.cryptocurrencyapp.mocks.Mocks
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class CoinDetailViewModelTest {

    private lateinit var viewModel: CoinDetailViewModel

    private val dispatcher = TestCoroutineDispatcher()

    @RelaxedMockK
    lateinit var getCoinUseCase: GetCoinUseCase

    @RelaxedMockK
    lateinit var savedStateHandle: SavedStateHandle

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(dispatcher)
        savedStateHandle.set(Constants.PARAM_COIN_ID, COIN_ID)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when use case return success then viewModel state has list coins`() = runBlockingTest {
        val flow = flow {
            emit(Resource.Success(Mocks.coinDetailDto.toCoinDetail()))
        }
        every { getCoinUseCase.invoke(any()) } returns (flow)
        viewModel = CoinDetailViewModel(getCoinUseCase, savedStateHandle)
        val state = viewModel.state.value
        Assert.assertEquals(state.isLoading, false)
        Assert.assertEquals(state.error, "")
        Assert.assertEquals(state.coin, Mocks.coinDetailDto.toCoinDetail())
    }

    @Test
    fun `when use case return error then viewModel state is error`() = runBlockingTest {
        val flow = flow {
            emit(Resource.Error<CoinDetail>("message", null))
        }
        every { getCoinUseCase.invoke(any()) } returns (flow)
        viewModel = CoinDetailViewModel(getCoinUseCase, savedStateHandle)
        val state = viewModel.state.value
        Assert.assertEquals(state.isLoading, false)
        Assert.assertEquals(state.error, "message")
        Assert.assertEquals(state.coin, null)
    }

    @Test
    fun `when use case return loading then viewModel state is loading`() = runBlockingTest {
        val flow = flow {
            emit(Resource.Loading<CoinDetail>(null))
        }
        every { getCoinUseCase.invoke(any()) } returns (flow)
        viewModel = CoinDetailViewModel(getCoinUseCase, savedStateHandle)
        val state = viewModel.state.value
        Assert.assertEquals(state.isLoading, true)
        Assert.assertEquals(state.error, "")
        Assert.assertEquals(state.coin, null)
    }

    companion object {
        const val COIN_ID = "coin_id"
    }

}
