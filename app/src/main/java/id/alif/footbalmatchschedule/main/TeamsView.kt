package id.alif.footbalmatchschedule.main

import id.alif.footbalmatchschedule.model.Team

interface TeamsView {
    fun showLoading()
    fun hideLoading()
    fun showTeamList(data: List<Team>)
}