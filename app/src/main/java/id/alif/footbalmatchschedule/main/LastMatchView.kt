package id.alif.footbalmatchschedule.main


import id.alif.footbalmatchschedule.model.LastMatchTeam

interface LastMatchView {
        fun showLoading()
        fun hideLoading()
        fun showTeamList(data: List<LastMatchTeam>)
}