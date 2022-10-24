package com.fabio.cryptocurrencyapp.data.repository

import com.fabio.cryptocurrencyapp.data.remote.CoinPaprikaApi
import com.fabio.cryptocurrencyapp.mocks.Mocks.coinDetailDto
import com.fabio.cryptocurrencyapp.mocks.Mocks.coinList
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations


class CoinRepositoryImplTest {

    @Mock
    lateinit var api: CoinPaprikaApi

    private lateinit var repositoryImpl: CoinRepositoryImpl

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        repositoryImpl = CoinRepositoryImpl(api)
    }

    @Test
    fun `when getCoins then return coins list`() = runBlocking {
//        GIVEN
        whenever(api.getCoins()).thenReturn(coinList)
//        WHEN
        val result = repositoryImpl.getCoins()
//        THEN
        assertEquals(result, coinList)
    }

    @Test
    fun `when getCoinById then return coin details`() = runBlocking {
//        GIVEN
        whenever(api.getCoinById(any())).thenReturn(coinDetailDto)
//        WHEN
        val result = repositoryImpl.getCoinById("coinId")
//        THEN
        assertEquals(result, coinDetailDto)
    }
}