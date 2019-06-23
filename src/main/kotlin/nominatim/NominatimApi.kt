package nominatim

import retrofit2.http.GET
import retrofit2.http.Query

interface NominatimApi {

    @GET("/search")
    suspend fun search(@Query("q") query: String,
               @Query("format") format: String,
               @Query("limit") limit: Int
    ): List<Place>

    @GET("/reverse")
    suspend fun reverse(
            @Query("lat") lat: Double,
            @Query("lon") lon: Double,
            @Query("format") format: String
    ): List<Place>
}
