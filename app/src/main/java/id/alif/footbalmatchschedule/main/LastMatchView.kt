package id.alif.footbalmatchschedule.main

import com.google.gson.JsonObject
import id.alif.footbalmatchschedule.model.DetailMatch
import id.alif.footbalmatchschedule.model.LastMatchTeam

interface LastMatchView {
        fun showLoading()
        fun hideLoading()
        fun showTeamList(data: List<LastMatchTeam>)
}