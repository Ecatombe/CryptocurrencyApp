package com.fabio.cryptocurrencyapp.domain.use_case.get_coins

import com.fabio.cryptocurrencyapp.data.remote.dto.toCoin
import com.fabio.cryptocurrencyapp.domain.model.Coin
import com.fabio.cryptocurrencyapp.domain.repository.CoinRepository
import com.fabio.cryptocurrencyapp.mocks.Mocks
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.HttpException
import retrofit2.Response

@ExperimentalCoroutinesApi
class GetCoinsUseCaseTest {

    lateinit var getCoinsUseCase: GetCoinsUseCase

    @Mock
    lateinit var repository: CoinRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getCoinsUseCase = GetCoinsUseCase(repository)
    }

    @Test
    fun `when getCoins is success return 2 items, where the second is the formatted coins list`() =
        runBlockingTest {
            whenever(repository.getCoins()).thenReturn(Mocks.coinList)
            val result = getCoinsUseCase.invoke().toList()
            Assert.assertEquals(result.count(), 2)
            Assert.assertEquals(result[0].data, null)
            Assert.assertEquals(
                result[1].data as List<Coin>,
                Mocks.coinList.map { it.toCoin() }
            )
        }

    @Test
    fun `when getCoins is HttpsException return error message`() = runBlockingTest {
        whenever(repository.getCoins()).thenThrow(
            HttpException(
                Response.error<Any>(409, ResponseBody.create("plain/text".toMediaType(), ""))
            )
        )
        val result = getCoinsUseCase.invoke().toList()
        Assert.assertEquals(result.count(), 2)
        Assert.assertEquals(result[0].data, null)
        Assert.assertEquals(result[1].message, "HTTP 409 Response.error()")
    }
}