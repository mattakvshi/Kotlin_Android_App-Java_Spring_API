package com.example.appschoololympiads.API

import com.example.appschoololympiads.model.Pupil
import com.google.gson.annotations.SerializedName

// для отправки данных на сервер через POST запрос.
// @SerializedName используется для указания ключа, под которым будет хранится каждое значение в JSON при сериализации.
data class PupilPost(
    @SerializedName("action") val action: Int,
    @SerializedName("pupils") val pupils: Pupil
)
