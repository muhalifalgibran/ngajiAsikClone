package id.alif.footbalmatchschedule.presenter

import com.google.gson.Gson
import id.alif.footbalmatchschedule.api.ApiRepository
import id.alif.footbalmatchschedule.api.TheSportDBApi
import id.alif.footbalmatchschedule.main.DetailLastMatch
import id.alif.footbalmatchschedule.model.ResponseApiBadgeAway
import id.alif.footbalmatchschedule.model.ResponseApiBadgeHome
import id.alif.footbalmatchschedule.model.ResponseDetail
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class DetailMatchPresenter(private val view: DetailLastMatch,
                           private val apiRepository: ApiRepository,
                           private val gson: Gson) {

    fun getDetailMatch(match: String?, home: String?, away: String?){
        doAsync {
            val data = gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getDetail(match)),ResponseDetail::class.java)
            val homeLogo = gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getHomeLogo(home)),ResponseApiBadgeHome::class.java)
            val awayLogo = gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getAwayLogo(away)),ResponseApiBadgeAway::class.java)
            uiThread {
                 view.bindItem(data.events,homeLogo.teams,awayLogo.teams)
            }
        }
    }


}