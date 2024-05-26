package com.example.appschoololympiads.DataBase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.appschoololympiads.model.Olympiad
import com.example.appschoololympiads.model.Pupil
import kotlinx.coroutines.flow.Flow
import java.util.UUID


// @Dao аннотация используется для указания интерфейса взаимодействия с базой данных.
// В этом интерфейсе определяются все методы для работы с данными в базе данных.

@Dao
interface SchoolOlympiadsDAO {


    // OLYMPIADS ------------------------------------------------------------------------------------

    // @Query аннотация использует SQL для получения данных из базы данных.
    // Список всех олимпиад будет возвращен в виде потока Flow.
    // Flow позволяет обрабатывать асинхронные события и обновлять UI в реальном времени при изменении данных.

    @Query("SELECT * FROM olympiads ORDER BY olympiadName")
    fun getAllOlympiads(): Flow<List<Olympiad>>

    // @Insert, @Update и @Delete аннотации используются для операций с данными.
    // onConflict stratagy определяет, что делать, если встретился конфликт при вставке данных.
    // REPLACE означает, что старые данные будут заменены новыми.
    // suspend fun указывает, что функция может быть приостановлена и возобновлена без блокирования потока.

    @Insert(entity = Olympiad::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOlympiad(olympiad: Olympiad)

    @Update(entity = Olympiad::class)
    suspend fun updateOlympiad(olympiad: Olympiad)

    @Insert(entity = Olympiad::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListOlympiads(olympiadList: List<Olympiad>)

    @Delete(entity = Olympiad::class)
    suspend fun deleteOlympiad(olympiad: Olympiad)

    @Query("DELETE FROM olympiads")
    suspend fun deleteAllOlympiads()


    // PUPILS ----------------------------------------------------------------------------------------

    @Query("SELECT * FROM pupils")
    fun getAllPupils(): Flow<List<Pupil>>

    @Query("SELECT * FROM pupils WHERE olympiadID=:id")
    fun getAllPupilsByOlympiad(id: UUID): Flow<List<Pupil>>

    @Insert(entity = Pupil::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPupil(pupil: Pupil)

    @Update(entity = Pupil::class)
    suspend fun updatePupil(pupil: Pupil)

    @Insert(entity = Pupil::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListPupils(pupilList: List<Pupil>)

    @Delete(entity = Pupil::class)
    suspend fun deletePupil(pupil: Pupil)

    @Query("DELETE FROM pupils")
    suspend fun deleteAllPupils()

}