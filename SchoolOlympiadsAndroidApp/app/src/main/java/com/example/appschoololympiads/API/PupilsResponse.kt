package com.example.appschoololympiads.API

import com.example.appschoololympiads.model.Pupil
import com.google.gson.annotations.SerializedName

// для чтения выполненных GET запросов.
// Переменная items будет содержать полученный список учеников.
class PupilsResponse {
    @SerializedName("result") lateinit var items: List<Pupil>
}
