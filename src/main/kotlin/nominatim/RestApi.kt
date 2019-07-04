package nominatim

import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val NOMINATIM_URL = "http://nominatim.openstreetmap.org"
private const val FORMAT = "jsonv2"
private const val LIMIT = 1

class RestApi {
    private val api: NominatimApi = retrofit().create(NominatimApi::class.java)

    fun getPlace(city: String): List<Place> = runBlocking {
                return@runBlocking api.search(city, FORMAT, LIMIT)
            }

    fun getPlace(lat: Double, lon: Double): List<Place> = runBlocking {
                return@runBlocking api.reverse(lat, lon, FORMAT)
            }

    private fun retrofit(): Retrofit = Retrofit.Builder()
            .baseUrl(NOMINATIM_URL)
            .client(client())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private fun client(): OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging())
            .build()

    private fun logging(): HttpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.NONE)
}
