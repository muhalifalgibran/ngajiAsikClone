package id.alif.footbalmatchschedule.presenter


import com.google.gson.Gson
import id.alif.footbalmatchschedule.api.ApiRepository
import id.alif.footbalmatchschedule.api.TheSportDBApi
import id.alif.footbalmatchschedule.main.LastMatchFragment
import id.alif.footbalmatchschedule.model.ResponseApi
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class LastMatchPresenter(private val view: LastMatchFragment,
                         private val apiRepository: ApiRepository,
                         private val gson: Gson){

    fun getLastMatch(league : String?){
       // view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getLastMatch(league)),
                ResponseApi::class.java)

            uiThread {
                view.hideLoading()
                view.showTeamList(data.events)
            }
        }
    }


}