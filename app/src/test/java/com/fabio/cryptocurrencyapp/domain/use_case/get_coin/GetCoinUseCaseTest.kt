package com.fabio.cryptocurrencyapp.domain.use_case.get_coin

import com.fabio.cryptocurrencyapp.data.remote.dto.toCoinDetail
import com.fabio.cryptocurrencyapp.domain.model.CoinDetail
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
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.HttpException
import retrofit2.Response

@ExperimentalCoroutinesApi
class GetCoinUseCaseTest {

    lateinit var getCoinUseCase: GetCoinUseCase

    @Mock
    lateinit var repository: CoinRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getCoinUseCase = GetCoinUseCase(repository)
    }

    @Test
    fun `when getCoin is success return 2 items, where the second is the formatted coin detail`() =
        runBlockingTest {
            whenever(repository.getCoinById(any())).thenReturn(Mocks.coinDetailDto)
            val result = getCoinUseCase.invoke("coinId").toList()
            assertEquals(result.count(), 2)
            assertEquals(result[0].data, null)
            assertEquals(
                result[1].data as CoinDetail,
                Mocks.coinDetailDto.toCoinDetail()
            )
        }

    @Test
    fun `when getCoin is HttpsException return error message`() = runBlockingTest {
        whenever(repository.getCoinById(any())).thenThrow(
            HttpException(
                Response.error<Any>(409, ResponseBody.create("plain/text".toMediaType(), ""))
            )
        )
        val result = getCoinUseCase.invoke("coinId").toList()
        assertEquals(result.count(), 2)
        assertEquals(result[0].data, null)
        assertEquals(result[1].message, "HTTP 409 Response.error()")
    }
}