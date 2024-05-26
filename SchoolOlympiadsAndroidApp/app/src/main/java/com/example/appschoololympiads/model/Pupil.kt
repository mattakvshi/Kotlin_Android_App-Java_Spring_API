package com.example.appschoololympiads.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID

// Объявляем класс Pupil, который будет представлять запись о каждом ученике
// и его участии в олимпиаде в таблице `pupils`.
@Entity(
    tableName = "pupils",
    indices = [Index("id"), Index("olympiadID")],
    foreignKeys = [
        ForeignKey(
            entity = Olympiad::class, // Нам нужно установить связь (внешний ключ) с объектом Olympiad
            parentColumns = ["id"], // В колонке `id` класса `Olympiad`
            childColumns = ["olympiadID"], // Находится внешний ключ для этого класса
            onDelete = ForeignKey.CASCADE // Если соответствующая запись в parentColumn будет удалена, то все связанные с ней записи в этой таблице тоже удаляются
        )
    ]
)
data class Pupil(
    @PrimaryKey var id: UUID = UUID.randomUUID(), // Главный ключ каждого ученика, который должен быть уникальным
    var olympiadID: UUID? = null, // В этом поле хранится id олимпиады, в которой участвует ученик, это внешний ключ
    var pupilsName: String = "", // Здесь хранится имя ученика
    var pupilsRegion: String = "", // Здесь хранится регион ученика
    var pupilsPhone: String = "", // Здесь хранится номер телефона ученика
    var pupilsGrade: Int = 0, // Здесь хранится класс ученика
    var eventDate: Long = Date().time, // Здесь хранится дата участия ученика в олимпиаде
    var schoolSubject: String = "", // Здесь хранится предмет, по которому ученик участвовал в олимпиаде
    var pupilsScores: Int = 0, // Здесь хранится количество баллов, которые ученик набрал на олимпиаде
    var pupilsMark: Int = 0 // Здесь хранится оценка, которую ученик получил на олимпиаде
){
    // Функции для получения класса и оценки ученика с учетом поправок
    fun getGrade(): Int = pupilsGrade+1 //так как с нуля
    fun getMark(): Int = pupilsMark+2 //так как с нуля, а нужно с 2
}