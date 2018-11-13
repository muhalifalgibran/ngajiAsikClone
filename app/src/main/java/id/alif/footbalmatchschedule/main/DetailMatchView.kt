package id.alif.footbalmatchschedule.main

import id.alif.footbalmatchschedule.model.AwayBadge
import id.alif.footbalmatchschedule.model.DetailMatch
import id.alif.footbalmatchschedule.model.HomeBadge

interface DetailMatchView  {
    fun showTeamDetail(data: List<DetailMatch>, homeBadge: List<HomeBadge>, awayBadge: List<AwayBadge>)
}