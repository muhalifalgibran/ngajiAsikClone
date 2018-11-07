package id.alif.footbalmatchschedule.model

import com.google.gson.annotations.SerializedName

data class AwayBadge (
                    @SerializedName("strTeamBadge")
                    var strTeamBadgeAway: String? = null)