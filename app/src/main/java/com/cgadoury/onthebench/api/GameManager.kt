package com.cgadoury.onthebench.api

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.cgadoury.onthebench.api.model.game.Game
import com.cgadoury.onthebench.api.model.game.GameData
import retrofit2.Call
import retrofit2.Response

/**
 * Purpose - Game Manager - manages NHL game data
 */
class GameManager {

    private var _gamesResponse = mutableStateOf<List<Game?>>(emptyList())

    val gamesResponse: MutableState<List<Game?>>
        @Composable get() = remember {
            _gamesResponse
        }
    init {
        getGamesToday()
    }

    /**
     * Purpose - Get Games Today - gets today's NHL games
     * @return Unit
     */
    private fun getGamesToday() {
        val service = NhlApi.retrofitService.getGamesToday()

        service.enqueue(object: retrofit2.Callback<GameData> {
            override fun onResponse(
                call: Call<GameData?>,
                response: Response<GameData?>
            ) {
                if (response.isSuccessful) {
                    Log.i("Data", "Game data is ready to use.")

                    _gamesResponse.value = response.body()?.games ?: emptyList()
                    Log.i("DataStream", _gamesResponse.value.toString())
                }
            }

            override fun onFailure(
                call: Call<GameData?>,
                t: Throwable)
            {
                Log.d("get error", "${t.message}")
            }
        })
    }
}