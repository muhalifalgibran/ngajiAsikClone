package id.alif.footbalmatchschedule.presenter

import com.google.gson.Gson
import id.alif.footbalmatchschedule.api.ApiRepository
import id.alif.footbalmatchschedule.api.TheSportDBApi
import id.alif.footbalmatchschedule.main.PlayerView
import id.alif.footbalmatchschedule.main.TeamsView
import id.alif.footbalmatchschedule.model.ResponseApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PlayersPresenter(private val view: PlayerView,
                          private val apiRepository: ApiRepository,
                          private val gson: Gson
) {

    fun getPlayers(playerId: String?) {
        GlobalScope.launch(Dispatchers.Main) {
            val data = gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getPlayerList(playerId)).await(),
                ResponseApi::class.java
            )
            view.playersList(data.player)
        }

    }
}