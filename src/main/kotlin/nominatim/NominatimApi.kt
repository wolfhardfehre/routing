package nominatim

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NominatimApi {

    @GET("/search")
    fun search(@Query("q") query: String,
               @Query("format") format: String,
               @Query("limit") limit: Int
    ): Call<List<Place>>

    @GET("/reverse")
    fun reverse(
            @Query("lat") lat: Double,
            @Query("lon") lon: Double,
            @Query("format") format: String
    ): Call<List<Place>>
}