package id.alif.footbalmatchschedule.presenter

import com.google.gson.Gson
import id.alif.footbalmatchschedule.api.ApiRepository
import id.alif.footbalmatchschedule.api.TheSportDBApi
import id.alif.footbalmatchschedule.main.DetailPlayerActivity
import id.alif.footbalmatchschedule.model.ResponseApi
import id.alif.footbalmatchschedule.util.CoroutineContextProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailPlayerPresenter(private val view: DetailPlayerActivity,
                            private val apiRepository: ApiRepository,
                            private val gson: Gson, private val context: CoroutineContextProvider = CoroutineContextProvider()
) {

    fun getPlayerDetail(name: String){
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getPlayer(name)).await(),
                ResponseApi::class.java)

            view.playersList(data.player)

        }
    }


}