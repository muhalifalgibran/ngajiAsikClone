package id.alif.footbalmatchschedule.model

import com.google.gson.annotations.SerializedName
import java.sql.Date
import java.text.DateFormat

data class LastMatchTeam(
        @SerializedName("idEvent")
        var teamIdLast: String? = null,

        @SerializedName("strEvent")
        var strEvent: String? = null,

        @SerializedName("strSeason")
        var strSeason: String? = null,

        @SerializedName("idHomeTeam")
        var idHomeTeam: String? = null,

        @SerializedName("idAwayTeam")
        var idAwayTeam: String? = null,

        @SerializedName("strHomeTeam")
        var strHomeTeam: String? = null,

        @SerializedName("strAwayTeam")
        var strAwayTeam: String? = null,

        @SerializedName("intHomeScore")
        var intHomeScore: String? = null,

        @SerializedName("intAwayScore")
        var intAwayScore: String? = null,

        @SerializedName("strDate")
        var strDate: String? = null,

        @SerializedName("dateEvent")
        var dateEvent: String? = null,

        @SerializedName("strTime")
        var strTime: String? = null,

        @SerializedName("strHomeLineupGoalkeeper")
        var strHomeLineupGoalkeeper: String? = null,

        @SerializedName("strHomeLineupDefense")
        var strHomeLineupDefense: String? = null,

        @SerializedName("strHomeLineupMidfield")
        var strHomeLineupMidfield: String? = null,

        @SerializedName("strHomeLineupForward")
        var strHomeLineupForward: String? = null,

        @SerializedName("strHomeLineupSubstitutes")
        var strHomeLineupSubstitutes: String? = null,

        @SerializedName("strAwayLineupGoalkeeper")
        var strAwayLineupGoalkeeper: String? = null,

        @SerializedName("strAwayLineupMidfield")
        var strAwayLineupMidfield: String? = null,

        @SerializedName("strAwayLineupForward")
        var strAwayLineupForward: String? = null,

        @SerializedName("strAwayLineupSubstitutes")
        var strAwayLineupSubstitutes: String? = null

)