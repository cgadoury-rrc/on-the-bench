package com.cgadoury.onthebench.api

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.cgadoury.onthebench.api.model.roster.Defensemen
import com.cgadoury.onthebench.api.model.roster.Forward
import com.cgadoury.onthebench.api.model.roster.Goaly
import com.cgadoury.onthebench.api.model.roster.RosterData
import retrofit2.Call
import retrofit2.Response

/**
 * Purpose - Roster Manager - manage data from the roster api response.
 */
class RosterManager(teamAbbrev: String) {

    private var _forwardResponse = mutableStateOf<List<Forward>>(emptyList())
    private var _defensemenResponse = mutableStateOf<List<Defensemen>>(emptyList())
    private var _goalyResponse = mutableStateOf<List<Goaly>>(emptyList())

    val forwardResponse: MutableState<List<Forward>>
        @Composable get() = remember {
            _forwardResponse
        }

    val defensemenResponse: MutableState<List<Defensemen>>
        @Composable get() = remember {
            _defensemenResponse
        }

    val goalyResponse: MutableState<List<Goaly>>
        @Composable get() = remember {
            _goalyResponse
        }

    init {
        getCurrentRoster(teamAbbrev = teamAbbrev)
    }

    /**
     *
     */
    private fun getCurrentRoster(teamAbbrev: String) {
        val service = NhlApi.retrofitService.getCurrentRoster(teamAbbrev)

        service.enqueue(object: retrofit2.Callback<RosterData> {
            override fun onResponse(
                call: Call<RosterData?>,
                response: Response<RosterData?>)
            {
                if (response.isSuccessful) {
                    Log.i("Data", "Roster data is ready to use.")

                    _forwardResponse.value = response.body()?.forwards ?: emptyList()
                    _defensemenResponse.value = response.body()?.defensemen ?: emptyList()
                    _goalyResponse.value = response.body()?.goalies ?: emptyList()

                    Log.i("DataStream", _forwardResponse.value.toString())
                    Log.i("DataStream", _defensemenResponse.value.toString())
                    Log.i("DataStream", _goalyResponse.value.toString())
                }
            }

            override fun onFailure(
                call: Call<RosterData?>,
                t: Throwable
            ) {
                Log.d("get error", "${t.message}")
            }
        }
        )
    }
}