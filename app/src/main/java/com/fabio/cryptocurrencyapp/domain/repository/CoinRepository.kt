package com.fabio.cryptocurrencyapp.domain.repository

import com.fabio.cryptocurrencyapp.data.remote.dto.CoinDetailDto
import com.fabio.cryptocurrencyapp.data.remote.dto.CoinDto

interface CoinRepository {

    suspend fun getCoins(): List<CoinDto>

    suspend fun getCoinById(coinId: String): CoinDetailDto
}