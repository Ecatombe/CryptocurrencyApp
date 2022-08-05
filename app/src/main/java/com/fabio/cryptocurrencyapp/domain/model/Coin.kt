package com.fabio.cryptocurrencyapp.domain.model

// This data class is exactly as CoinDto BUT without all the fields that we don't need
// we still need to map it (function described in CoinDto)

data class Coin(
    val id: String,
    val isActive: Boolean,
    val name: String,
    val rank: Int,
    val symbol: String,
)
