package id.alif.footbalmatchschedule.model

data class ResponseApi (
    val events: List<LastMatchTeam>, val event: List<LastMatchTeam>,
    val teams: List<Team>, val player: List<PlayersModel>)