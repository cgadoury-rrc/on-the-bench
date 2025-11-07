package com.cgadoury.onthebench.api

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.cgadoury.onthebench.api.model.standing.Standing
import com.cgadoury.onthebench.api.model.standing.StandingData
import retrofit2.Call
import retrofit2.Response
import kotlin.collections.emptyList

/**
 * Purpose - Standing Manager - manages NHL standings data
 */
class StandingManager {

    private var _standingsResponse = mutableStateOf<List<Standing?>>(emptyList())

    val standingsResponse: MutableState<List<Standing?>>
        @Composable get() = remember {
            _standingsResponse
        }
    init {
        getCurrentStandings()
    }

    /**
     *
     */
    private fun getCurrentStandings() {
        val service = NhlApi.retrofitService.getCurrentStandings()

        service.enqueue(object: retrofit2.Callback<StandingData> {
            override fun onResponse(
                call: Call<StandingData?>,
                response: Response<StandingData?>
            ) {
                if (response.isSuccessful) {
                    Log.i("Data", "Standings data is ready to use.")

                    _standingsResponse.value = response.body()?.standings ?: emptyList()
                    Log.i("DataStream", _standingsResponse.value.toString())
                }
            }

            override fun onFailure(
                call: Call<StandingData?>,
                t: Throwable
            ) {
                Log.d("get error", "${t.message}")
            }
        })
    }
}