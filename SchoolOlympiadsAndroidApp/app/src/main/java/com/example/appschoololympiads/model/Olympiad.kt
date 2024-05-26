package com.example.appschoololympiads.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

// Объявляем класс Olympiad, который будет представлять запись в таблице `olympiads`.
// Он аннотирован как `@Entity`, что указывает Room на то, что этот класс является сущностью базы данных.

@Entity(
    tableName = "olympiads",    // Указываем имя таблицы в базе данных
    indices = [Index("id")]     // Задаем индекс по полю `id` для ускорения запросов
)
data class Olympiad( // Создаем класс данных `Olympiad`. Классы данных упрощают создание классов, хранящих только данные (Я так понимаю работают как @Data в Lombok)
    @PrimaryKey var id: UUID = UUID.randomUUID(), // Это главный ключ каждой олимпиады, который должен быть уникальным. Ключ создается автоматически.
    var olympiadName: String = "" // Это поле для хранения имени олимпиады
)