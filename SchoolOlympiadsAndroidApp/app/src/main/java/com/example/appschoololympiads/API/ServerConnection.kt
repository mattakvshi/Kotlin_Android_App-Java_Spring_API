package com.example.appschoololympiads.API

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val CONNECT_TIMEOUT_INTERVAL_SEC = 10L
private const val READ_TIMEOUT_INTERVAL_SEC = 10L
private const val WRITE_TIMEOUT_INTERVAL_SEC = 10L
private const val BASE_URL = "http://192.168.0.107:8080/shcool/"

// Класс, отвечающий за настройку и получение клиента Retrofit для взаимодействия с сервером через API.
// Retrofit преобразует API в интерфейс Java.
object ServerConnection {
    private var retrofit: Retrofit? = null

    // Настраиваем OkHttpClient, который будет использован Retrofit для сетевых запросов.
    private val client = OkHttpClient.Builder()
        .connectTimeout(CONNECT_TIMEOUT_INTERVAL_SEC, TimeUnit.SECONDS)
        .readTimeout(READ_TIMEOUT_INTERVAL_SEC, TimeUnit.SECONDS)
        .writeTimeout(WRITE_TIMEOUT_INTERVAL_SEC, TimeUnit.SECONDS)
        .build()

    // Gson используется для сериализации и десериализации данных. Он преобразует объекты Java и Kotlin в их JSON представление и обратно.
    var gson = GsonBuilder()
        .setDateFormat("yyyy.MM.dd")
        .create()

    fun getClient(): Retrofit {
        if(retrofit == null){
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                // addConverterFactory сообщает Retrofit, что данные должны быть сериализованы и десериализованы в JSON используя библиотеку Gson.
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
        return retrofit!!
    }
}