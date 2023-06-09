package miApp

import okhttp3.OkHttpClient
import com.google.gson.JsonObject
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class OompaLoompaApiService {

    private val client: OkHttpClient

    init {
        // Configuración del cliente HTTP con interceptor de registro
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    fun getOompaLoompas(page: Int): List<OompaLoompa> {
        val url = "https://2q2woep105.execute-api.eu-west-1.amazonaws.com/napptilus/oompa-loompas?page=$page"
        val request = Request.Builder()
            .url(url)
            .build()

        val response = client.newCall(request).execute()
        val json = response.body?.string()

        val gson = Gson()
        val oompaLoompaObject = gson.fromJson(json, JsonObject::class.java)
        val oompaLoompasListJson = oompaLoompaObject.getAsJsonArray("results")
        val oompaLoompasListType = object : TypeToken<List<OompaLoompa>>() {}.type

        return gson.fromJson(oompaLoompasListJson, oompaLoompasListType)
    }

}