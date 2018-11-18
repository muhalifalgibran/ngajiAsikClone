package id.alif.footbalmatchschedule.presenter

import com.google.gson.Gson
import id.alif.footbalmatchschedule.api.ApiRepository
import id.alif.footbalmatchschedule.api.TheSportDBApi
import id.alif.footbalmatchschedule.main.DetailLastMatch
import id.alif.footbalmatchschedule.model.ResponseApiBadgeAway
import id.alif.footbalmatchschedule.model.ResponseApiBadgeHome
import id.alif.footbalmatchschedule.model.ResponseDetail
import id.alif.footbalmatchschedule.util.CoroutineContextProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailMatchPresenter(private val view: DetailLastMatch,
                           private val apiRepository: ApiRepository,
                           private val gson: Gson, private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getDetailMatch(match: String?, home: String?, away: String?){
        GlobalScope.launch(context.main) {

            val data = gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getDetail(match)).await(),ResponseDetail::class.java)
            val homeLogo = gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getHomeLogo(home)).await(),ResponseApiBadgeHome::class.java)
            val awayLogo = gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getAwayLogo(away)).await(),ResponseApiBadgeAway::class.java)
                 view.bindItem(data.events,homeLogo.teams,awayLogo.teams)

        }
    }


}