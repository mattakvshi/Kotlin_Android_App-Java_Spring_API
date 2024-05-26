package com.example.appschoololympiads.repository

import com.example.appschoololympiads.model.Olympiad
import com.example.appschoololympiads.model.Pupil
import kotlinx.coroutines.flow.Flow
import java.util.UUID

//Это интерфейс для операций с базой данных. Каждая функция представляет собой операцию, которую можно выполнить с базой даннных.

interface SchoolOlympiadRepository {
    //Наши методы для загрузки всех олимпиад и олимпиад по ID школы
    fun getAllOlympiads(): Flow<List<Olympiad>>
    fun getAllPupils(): Flow<List<Pupil>>
    fun getAllPupilsByOlympiad(id: UUID): Flow<List<Pupil>>

    //Методы для добавления новых записей в базу данных
    suspend fun insertOlympiad(olympiad: Olympiad)
    suspend fun insertPupil(pupil: Pupil)
    suspend fun insertListOlympiads(olympiadList: List<Olympiad>)
    suspend fun insertListPupils(pupilList: List<Pupil>)

    //Методы для обновления записей в базе данных
    suspend fun updateOlympiad(olympiad: Olympiad)
    suspend fun updatePupil(pupil: Pupil)

    //Методы для удаления записей из базы данных.
    suspend fun deleteOlympiad(olympiad: Olympiad)
    suspend fun deletePupil(pupil: Pupil)
    suspend fun deleteAllOlympiads()
    suspend fun deleteAllPupils()
}