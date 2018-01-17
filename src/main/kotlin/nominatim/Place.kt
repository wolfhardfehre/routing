package nominatim

import com.google.gson.annotations.SerializedName

class Place {
    @SerializedName("osm_type") val osmType: String = "node"
    @SerializedName("osm_id") val osmId: String = "27418664"
    val lat: String = "0.0"
    val lon: String = "0.0"
    @SerializedName("display_name") val displayName: String = ""
    @SerializedName("category") val kind: String = ""
    val type: String = ""
    val importance: Double = 0.0
}