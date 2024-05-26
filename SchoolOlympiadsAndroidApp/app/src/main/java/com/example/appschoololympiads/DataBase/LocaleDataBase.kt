package com.example.appschoololympiads.DataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.appschoololympiads.model.Olympiad
import com.example.appschoololympiads.model.Pupil

// @Database аннотация является контейнером для базы данных и описывает сущности (таблицы) базы данных и ее версию(для миграций вроде).
// Этот абстрактный класс должен быть подклассом RoomDatabase.
@Database(
    entities = [Olympiad::class, Pupil::class],
    version = 1,  // Вроде как нужно для миграций
    exportSchema = false
)
@TypeConverters(TypeConverter::class) // используем класс TypeConverter для конвертации UUID в String и обратно
abstract class LocaleDataBase : RoomDatabase() {

    // определяем DAO
    abstract fun schoolOlympiadsDAO(): SchoolOlympiadsDAO

    companion object {
        // создаем базу данных с паттерном Singleton (объект INSTANCE может быть создан только один раз).
        @Volatile
        private var INSTANCE: LocaleDataBase? = null

        // этот метод будет вызываться извне для получения объекта базы данных
        fun getDatabase(context: Context): LocaleDataBase {
            // если объект уже создан, то возвращаем его, иначе создаем новую базу данных
            return INSTANCE ?: synchronized(this) {
                buildDatabase(context).also { INSTANCE = it }
            }
        }

        // вспомогательный метод для создания базы данных
        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            LocaleDataBase::class.java,
            "school_olympiads_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

}