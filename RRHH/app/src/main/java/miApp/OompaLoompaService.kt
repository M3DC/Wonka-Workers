package miApp

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface OompaLoompaService {

    @GET("oompa-loompas")
    suspend fun getOompaLoompas(@Query("page") page: Int): Response<List<OompaLoompa>>

    @GET("oompa-loompas/{id}")
    suspend fun getOompaLoompaDetails(@Path("id") id: Int): Response<OompaLoompa>

}
