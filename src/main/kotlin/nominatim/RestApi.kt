package nominatim

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RestApi {

    private val api: NominatimApi
    private val format: String = "jsonv2"
    private val limit = 1

    init {
        val retrofit = retrofit()
        api = retrofit.create(NominatimApi::class.java)
    }

    fun getPlace(city: String): Call<List<Place>> = api.search(city, format, limit)

    fun getPlace(lat: Double, lon: Double): Call<List<Place>> = api.reverse(lat, lon, format)

    private fun retrofit(): Retrofit = Retrofit.Builder()
            .baseUrl("http://nominatim.openstreetmap.org")
            .client(client())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private fun client(): OkHttpClient = OkHttpClient.Builder()
            //.addInterceptor(logging())
            .build()

    private fun logging(): HttpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
}