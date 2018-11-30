package id.alif.footbalmatchschedule.main

import id.alif.footbalmatchschedule.model.PlayersModel

interface PlayerView{
    fun playersList(data: List<PlayersModel>)
}