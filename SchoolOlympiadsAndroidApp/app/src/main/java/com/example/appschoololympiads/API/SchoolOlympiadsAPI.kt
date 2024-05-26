package com.example.appschoololympiads.API

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

// SchoolOlympiadsAPI определяет все эндпоинты API, которые мы собираемся использовать в приложении, удобно структурированные под Retrofit.
interface SchoolOlympiadsAPI {
    // эндпоинт для получения олимпиад. Возвращает Call с ответом, содержащим список олимпиад.
    @GET("olympiads")
    fun getOlympiads(): Call<OlympiadResponse>

    // эндпоинт для отправки данных об олимпиаде на сервер. Возвращает Call с ответом о результатах отправки.
    // @Headers определяет HTTP заголовки, которые должны быть включены в запрос.
    @Headers("Content-Type: application/json")
    @POST("olympiads")
    fun postOlympiad(@Body postOlympiad: OlympiadPost): Call<OlympiadPostResult>

    // эндпоинт для получения списка школьников
    @GET("pupils")
    fun getPupils(): Call<PupilsResponse>

    // эндпоинт для отправки информации о учащихся
    @Headers("Content-Type: application/json")
    @POST("pupils")
    fun postPupils(@Body postPupil: PupilPost): Call<PupilPostResult>
}