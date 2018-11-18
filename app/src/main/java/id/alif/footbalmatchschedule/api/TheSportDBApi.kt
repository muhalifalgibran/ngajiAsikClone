package id.alif.footbalmatchschedule.api

import android.net.Uri
import com.google.gson.JsonObject
import id.alif.footbalmatchschedule.BuildConfig

object TheSportDBApi {

    fun getLastMatch(league: String?): String {
        val url = BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/eventspastleague.php?id=" + league
        return url
    }

    fun getNextMatch(league: String?): String {

        val url = BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/eventsnextleague.php?id=" + league
        return url

    }

    fun getDetail(match: String?): String {
        val url = BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/lookupevent.php?id=" + match
        return url
    }

    fun getHomeLogo(home: String?): String {
        val url = BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/lookupteam.php?id=" + home
        return url
    }

    fun getAwayLogo(away: String?): String {
        val url = BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/lookupteam.php?id=" + away
        return url
    }
}


