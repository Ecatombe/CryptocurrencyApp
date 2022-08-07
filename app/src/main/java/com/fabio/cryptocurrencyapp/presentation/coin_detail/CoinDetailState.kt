package com.fabio.cryptocurrencyapp.presentation.coin_detail

import com.fabio.cryptocurrencyapp.domain.model.Coin
import com.fabio.cryptocurrencyapp.domain.model.CoinDetail

data class CoinDetailState(
    val isLoading: Boolean = false,
    val coin: CoinDetail? = null,
    val error: String = ""
)
