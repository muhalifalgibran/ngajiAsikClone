package id.alif.footbalmatchschedule.model

import com.google.gson.annotations.SerializedName

data class HomeBadge(
                    @SerializedName("strTeamBadge")
                    var strTeamBadgeHome: String? = null)