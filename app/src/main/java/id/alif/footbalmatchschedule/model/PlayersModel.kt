package id.alif.footbalmatchschedule.model

import com.google.gson.annotations.SerializedName

data class PlayersModel(
        @SerializedName("idPlayer")
        var idPlayer: String? = null,

        @SerializedName("strPlayer")
        var strPlayer: String? = null,

        @SerializedName("strPosition")
        var strPosition: String? = null,

        @SerializedName("strHeight")
        var strHeight: String? = null,

        @SerializedName("strWeight")
        var strWeight: String? = null,

        @SerializedName("strThumb")
        var strThumb: String? = null,

        @SerializedName("strDescriptionEN")
        var strDescriptionEN: String? = null,

        @SerializedName("strCutout")
        var strCutout: String? = null
)