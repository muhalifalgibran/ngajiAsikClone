package id.alif.footbalmatchschedule.database

data class Favorite(val id: Long?, val idEvent: String?, val strHomeTeam: String?, val strAwayTeam: String?,
                    val idHomeTeam:String?, val idAwayTeam: String? ,val intHomeScore: String?,
                    val intAwayScore: String?, val strDate: String?){

    companion object {
        const val TABLE_FAVORITE: String = "TABLE_FAVORITE"
        const val ID: String = "ID_"
        const val ID_EVENT: String = "ID_EVENT"
        const val STRHOMETEAM: String = "STRHOMETEAM"
        const val STRAWAYTEAM: String = "STRAWAYTEAM"
        const val IDHOMETEAM: String = "IDHOMETEAM"
        const val IDAWAYTEAM: String = "IDAWAYTEAM"
        const val INTHOMESCORE: String = "INTHOMESCORE"
        const val INTAWAYSCORE: String = "INTAWAYSCORE"
        const val STRDATE: String = "STRDATE"
    }

}