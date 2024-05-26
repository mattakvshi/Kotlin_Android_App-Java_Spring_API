package com.example.appschoololympiads.DataBase

import androidx.room.TypeConverter
import java.util.UUID

// Этот класс используется Room для преобразования типов данных, которые не поддерживаются базой данных SQLite в Android.
// В данном случае, конвертируем UUID в String для сохранения в базе и обратно
// (Так же как понимаю этоещё для передачи UUID в JSON стракой, так как и на сервере на Java я работаю с UUID,
// и тут, и в базу он вроде ложится, в Postgre так точно, но вот в JSON не ложится.).

class TypeConverter {
    @TypeConverter
    fun toUUID(uuid: String?): UUID? {
        return UUID.fromString(uuid)
    }

    @TypeConverter
    fun fromUUID(uuid: UUID?): String? {
        return uuid?.toString()
    }
}