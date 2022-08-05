package com.fabio.cryptocurrencyapp.domain.use_case.get_coins

import com.fabio.cryptocurrencyapp.common.Resource
import com.fabio.cryptocurrencyapp.data.remote.dto.toCoin
import com.fabio.cryptocurrencyapp.domain.model.Coin
import com.fabio.cryptocurrencyapp.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCoinsUseCase @Inject constructor(
    private val repository: CoinRepository
) {

/*
    Use case should only have one public function, only the major feature that the ViewModel will contain
    Putting all the use case here is BAD because the purpose of the use case is not fulfilled anymore

    Some people prefer to create a execute function instead, byt we are overriding the invoke function so we
    can basically call this use case as if it was a function ... which is nice
    We are using flow so wer can emit multiple values over a period of time and all the type of emit
    are all the one already planned for the Resource class
 */

    operator fun invoke(): Flow<Resource<List<Coin>>> = flow {
        try {
            emit(Resource.Loading())
            val coins = repository.getCoins().map { it.toCoin() }
            emit(Resource.Success(coins))
        }
        // When we get a response code that does not start with a 2
        catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An expected error occured"))
        }
        // When our api can't really talk to the actual remote (exe no internet connection or server offline)
        catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet Connection"))
        }
    }
}