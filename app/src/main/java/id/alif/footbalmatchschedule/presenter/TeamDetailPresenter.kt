package id.alif.footbalmatchschedule.presenter

import org.jetbrains.anko.*
import com.google.gson.Gson
import id.alif.footbalmatchschedule.api.ApiRepository
import id.alif.footbalmatchschedule.api.TheSportDBApi
import id.alif.footbalmatchschedule.main.TeamsView
import id.alif.footbalmatchschedule.model.ResponseApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TeamDetailPresenter(private val view: TeamsView,
                          private val apiRepository: ApiRepository,
                          private val gson: Gson) {

    fun getTeamDetail(teamId: String) {
        view.showLoading()

        GlobalScope.launch(Dispatchers.Main) {
            val data = gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getTeamDetail(teamId)).await(),
                ResponseApi::class.java
            )

            view.hideLoading()
            view.showTeamList(data.teams)
         }

        }
}
