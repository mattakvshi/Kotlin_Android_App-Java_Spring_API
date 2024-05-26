package com.example.appschoololympiads.API

import com.example.appschoololympiads.model.Pupil
import com.google.gson.annotations.SerializedName

// для получения результата (ответа) выполненого POST запроса.
// Данные помечены как lateinit, это значит, что они будут инициализированы позже (когда мы получим ответ от сервера).
class PupilPostResult {
    @SerializedName("") lateinit var resultString: Pupil
}
