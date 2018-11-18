package id.alif.footbalmatchschedule.presenter

import com.google.gson.Gson
import id.alif.footbalmatchschedule.api.ApiRepository
import id.alif.footbalmatchschedule.api.TheSportDBApi
import id.alif.footbalmatchschedule.main.NextMatchFragment
import id.alif.footbalmatchschedule.model.ResponseApi
import id.alif.footbalmatchschedule.util.CoroutineContextProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NextMatchPresenter (private val view: NextMatchFragment,
                          private val apiRepository: ApiRepository,
                          private val gson: Gson, private val context: CoroutineContextProvider = CoroutineContextProvider()
){

    fun getNextMatch(league : String?){

        GlobalScope.launch(context.main) {
            val data = gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getNextMatch(league)).await(),
                ResponseApi::class.java)

                view.hideLoading()
                view.showTeamList(data.events)

        }
    }
}