package com.example.appschoololympiads.API

import com.example.appschoololympiads.model.Olympiad
import com.google.gson.annotations.SerializedName

// для чтения выполненных GET запросов.
// Переменная items будет содержать полученный список олимпиад.
class OlympiadResponse {
    @SerializedName("result") lateinit var items: List<Olympiad>
}
