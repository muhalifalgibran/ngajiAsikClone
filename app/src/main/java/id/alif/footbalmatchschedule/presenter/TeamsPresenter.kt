package id.alif.footbalmatchschedule.presenter

import com.google.gson.Gson
import id.alif.footbalmatchschedule.api.ApiRepository
import id.alif.footbalmatchschedule.api.TheSportDBApi
import id.alif.footbalmatchschedule.fragment.TeamsFragment
import id.alif.footbalmatchschedule.model.ResponseApi
import id.alif.footbalmatchschedule.util.CoroutineContextProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class TeamsPresenter(private val view: TeamsFragment,
                     private val apiRepository: ApiRepository,
                     private val gson: Gson,
                     private val context: CoroutineContextProvider = CoroutineContextProvider()){

    fun getTeamList(league: String?){
        view.showLoading()
        GlobalScope.launch (context.main){

            val data = gson.fromJson(apiRepository.
                doRequest(TheSportDBApi.getTeams(league)).await(), ResponseApi::class.java)

            view.hideLoading()
            view.showTeamList(data.teams)
        }
        }
    fun getTeamSearch(league: String?){
        view.showLoading()
        GlobalScope.launch (context.main){

            val data = gson.fromJson(apiRepository.
                doRequest(TheSportDBApi.getTeamName(league)).await(), ResponseApi::class.java)

            view.hideLoading()
            view.showTeamList(data.teams)
        }

    }
}





