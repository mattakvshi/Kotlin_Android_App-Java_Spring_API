package com.example.appschoololympiads.repository

import com.example.appschoololympiads.DataBase.SchoolOlympiadsDAO
import com.example.appschoololympiads.model.Olympiad
import com.example.appschoololympiads.model.Pupil
import kotlinx.coroutines.flow.Flow
import java.util.UUID

//Это реализация интерфейса с использованием описателя доступа к объектам (Data Access Object, DAO).
//Каждая функция связана со специальным методом в DAO.

class DBRepository(val dao: SchoolOlympiadsDAO): SchoolOlympiadRepository {
    //Мы работаем через интерфейс Room DAO.
    //Room требует, чтобы мы вызывали методы на уровне DAO через каналы или корутины.

    // Получаем всех учеников и олимпиады
    override fun getAllOlympiads(): Flow<List<Olympiad>> = dao.getAllOlympiads()
    override fun getAllPupils(): Flow<List<Pupil>> = dao.getAllPupils()
    override fun getAllPupilsByOlympiad(id: UUID): Flow<List<Pupil>> = dao.getAllPupilsByOlympiad(id)

    // Добавляем новые объекты в базу данных
    // Используем ключевое слово suspend для управления подвеской и возобновлением функций
    override suspend fun insertOlympiad(olympiad: Olympiad) = dao.insertOlympiad(olympiad)
    override suspend fun insertPupil(pupil: Pupil) = dao.insertPupil(pupil)
    override suspend fun insertListOlympiads(olympiadList: List<Olympiad>) = dao.insertListOlympiads(olympiadList)
    override suspend fun insertListPupils(pupilList: List<Pupil>) = dao.insertListPupils(pupilList)

    // Обновляем данные в базе данных
    override suspend fun updateOlympiad(olympiad: Olympiad) = dao.updateOlympiad(olympiad)
    override suspend fun updatePupil(pupil: Pupil) = dao.updatePupil(pupil)

    // Удаляем объекты из базы данных
    override suspend fun deleteOlympiad(olympiad: Olympiad) = dao.deleteOlympiad(olympiad)
    override suspend fun deletePupil(pupil: Pupil) = dao.deletePupil(pupil)
    override suspend fun deleteAllOlympiads() = dao.deleteAllOlympiads()
    override suspend fun deleteAllPupils() = dao.deleteAllPupils()
}