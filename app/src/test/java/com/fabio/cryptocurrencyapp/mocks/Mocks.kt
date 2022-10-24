package com.fabio.cryptocurrencyapp.mocks

import com.fabio.cryptocurrencyapp.data.remote.dto.*

object Mocks {
    private val mockedCoin = CoinDto(
        "btc-bitcoin",
        true,
        false,
        "Bitcoin",
        1,
        "BTC",
        "coin"
    )

    val coinList = listOf(mockedCoin, mockedCoin, mockedCoin)

    private val tag = Tag(
        435,
        14,
        "proof-of-work",
        "Proof Of Work",
    )

    private val tags = listOf(tag, tag, tag)

    val coinDetailDto = CoinDetailDto(
        "Bitcoin is a cryptocurrency and worldwide payment system. It is the first decentralized digital currency, as the system works without a central bank or single administrator.",
        "developmentStatus",
        "firstDataAt",
        true,
        "hashAlgorithm",
        "id",
        true,
        true,
        "lastDataAt",
        Links(
            listOf("explorer"),
            listOf("facebook"),
            listOf("reddit"),
            listOf("source_code"),
            listOf("website"),
            listOf("youtube")
        ),
        listOf(),
        "message",
        "name",
        true,
        "orgStructure",
        "proofType",
        1,
        "startedAt",
        "symbol",
        tags,
        listOf(
            TeamMember(
                "satoshi-nakamoto",
                "Satoshi Nakamoto",
                "Founder"
            ),
            TeamMember(
                "marco-falke",
                "Marco Falke",
                "Blockchain Developer"
            )
        ),
        "type",
        Whitepaper(
            "https://static.coinpaprika.com/storage/cdn/whitepapers/215.pdf",
            "https://static.coinpaprika.com/storage/cdn/whitepapers/217.jpg"
        )
    )

}