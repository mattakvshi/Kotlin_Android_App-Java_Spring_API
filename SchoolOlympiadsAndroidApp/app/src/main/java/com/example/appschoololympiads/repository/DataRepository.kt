package com.example.appschoololympiads.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.example.appschoololympiads.API.OlympiadPost
import com.example.appschoololympiads.API.OlympiadPostResult
import com.example.appschoololympiads.API.OlympiadResponse
import com.example.appschoololympiads.API.PupilPost
import com.example.appschoololympiads.API.PupilPostResult
import com.example.appschoololympiads.API.PupilsResponse
import com.example.appschoololympiads.API.SchoolOlympiadsAPI
import com.example.appschoololympiads.API.ServerConnection
import com.example.appschoololympiads.App
import com.example.appschoololympiads.DataBase.LocaleDataBase
import com.example.appschoololympiads.model.Olympiad
import com.example.appschoololympiads.model.Pupil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.IllegalStateException

const val APPEND = 11
const val UPDATE = 12
const val DELETE = 13

const val TAG_SERVER = "SERVER"

//У нас есть private constructor для имплементации шаблона Singleton, это означает,
// что мы можем создать только один экземпляр этого класса во всем приложении.

class DataRepository private constructor() {

    companion object {
        private var INSTANCE: DataRepository? = null

        // Получаем экземпляр репозитория. Используем паттерн Singleton, чтобы создавать только один экземпляр репозитория.
        fun getInstance(): DataRepository {
            if (INSTANCE == null) {
                INSTANCE = DataRepository()
            }
            return INSTANCE ?: throw IllegalStateException("DataRepository Репозиторий не инициализирован.")
        }
    }

    //Далее обращаемся к DAO для локальной базы данных с помощью localeDB.
    private val localeDB by lazy {
        DBRepository(LocaleDataBase.getDatabase(App.context).schoolOlympiadsDAO())
    }

    // Создаем скоуп для корутин, который будет привязан к жизненному циклу вьюхи (на уровне ViewModel, Activity или Fragment).
    private val myCoroutineScope = CoroutineScope(Dispatchers.Main)

    // Отменяем все корутины при уничтожении, чтобы избежать утечки памяти
    fun onDestroy() {
        myCoroutineScope.cancel()
    }

    //schoolAPI - это экземпляр интерфейса для доступа к удаленному API чрезе библиотеку Retrofit.
    private var schoolAPI = ServerConnection.getClient().create(SchoolOlympiadsAPI::class.java)

    //Список всех олимпиад и школьников берется с DAO и трансформируется в LiveData для использования в ViewModel.
    val olympiads: LiveData<List<Olympiad>> = localeDB.getAllOlympiads().asLiveData()
    val pupils: LiveData<List<Pupil>> = localeDB.getAllPupils().asLiveData()
    var olympiad: MutableLiveData<Olympiad> = MutableLiveData()
    var pupil: MutableLiveData<Pupil> = MutableLiveData()


    //Методы setCurrentOlympiad и setCurrentPupil используются для задания текущих олимпиады и ученика.
    fun setCurrentOlympiad(_olympiad: Olympiad) {
        olympiad.postValue(_olympiad)
    }

    fun setCurrentPupil(_pupil: Pupil) {
        pupil.postValue(_pupil)
    }


    //Каждый запрос к серверу определен в функциях fetchSchoolAPI() и fetchPupils(), а также в postOlympiad и postPupil.


    fun fetchSchoolAPI() {

        //Сделать запрос на сервер для получения данных
        //enqueue - позволяет выполнять асинхронные http-запросы
        //Callback - позволяет обработать ответ от сервера

        schoolAPI.getOlympiads().enqueue(object : Callback<OlympiadResponse> {
            override fun onFailure(call: Call<OlympiadResponse>, t: Throwable) {
                // Обрабатываем ошибку загрузки данных с сервера выводим инфу в логи
                Log.d(TAG_SERVER, "Ошибка получения списка олимпиад")
            }

            override fun onResponse(
                call: Call<OlympiadResponse>,
                responce: Response<OlympiadResponse>
            ) {
                // При успешном получении данных, очищаем базу данных и записываем новые данные в нее. Затем получаем данные о школьниках.
                val olympiads = responce.body()
                val items = olympiads?.items ?: emptyList()
                Log.d(TAG_SERVER, "Получаем список олипиад. Количество записей=${items.size}")
                myCoroutineScope.launch {
                    localeDB.deleteAllOlympiads()
                    localeDB.insertListOlympiads(items)
                    fetchPupils()
                }
            }
        })
    }

    private fun fetchPupils() {
        schoolAPI.getPupils().enqueue(object : Callback<PupilsResponse>{
            override fun onResponse(call: Call<PupilsResponse>, responce: Response<PupilsResponse>) {
                // При успешном получении данных, очищаем базу данных и записываем новые данные в нее. Затем получаем данные о школьниках.
                val pupils = responce.body()
                val items = pupils?.items ?: emptyList()
                Log.d(TAG_SERVER, "Получаем список школьников. Количество записей=${items.size}")
                myCoroutineScope.launch {
                    localeDB.deleteAllPupils()
                    localeDB.insertListPupils(items)
                }
            }

            override fun onFailure(call: Call<PupilsResponse>, t: Throwable) {
                // Обрабатываем ошибку загрузки данных с сервера выводим инфу в логи
                Log.d(TAG_SERVER, "Ошибка получения списка школьников")
            }

        })
    }

    private fun postOlympiad(action: Int, olympiad: Olympiad) {
        //Отправляем данные об олимпиадах и actions на сервер и обрабатываем ответ от сервера

        schoolAPI.postOlympiad(OlympiadPost(action, olympiad)).enqueue(object : Callback<OlympiadPostResult>{
            override fun onResponse(p0: Call<OlympiadPostResult>, p1: Response<OlympiadPostResult>) {
                Log.d(TAG_SERVER, "Запрос с олимпиадой = ${olympiad.olympiadName}, отправлен с кодом=${action}}")
                fetchSchoolAPI()
            }

            override fun onFailure(p0: Call<OlympiadPostResult>, p1: Throwable) {
                Log.d(TAG_SERVER, "Ошибка запроса с кодом=${action}, для олимпиады=${olympiad.olympiadName}")
            }

        })
    }

    private fun postPupil(action: Int, pupil: Pupil) {
        //Отправляем данные об школьниках и actions на сервер и обрабатываем ответ от сервера

        schoolAPI.postPupils(PupilPost(action, pupil)).enqueue(object : Callback<PupilPostResult>{
            override fun onResponse(p0: Call<PupilPostResult>, p1: Response<PupilPostResult>) {
                Log.d(TAG_SERVER, "Запрос с щкольником = ${pupil.pupilsName}, отправлен с кодом=${action}}")
                fetchSchoolAPI()
            }

            override fun onFailure(p0: Call<PupilPostResult>, p1: Throwable) {
                Log.d(TAG_SERVER, "Ошибка запроса с кодом=${action}, для школьника=${pupil.pupilsName}")
            }

        })
    }


    //В методах add, edit и delete используются обычные действия CRUD (create, read, update, delete),
    // которые выполняются на удаленном сервере (ну или не очень удалённом пока что) (сервер писалл на Java + Lombok + Spring + Hibernate + PostgreSQL).

    //ТИПА МИНИ "ИНТЕРФЕЙС" ВЗАИМОДЕЙСТВИЯ С ВЕРХНИМИ ФУНКЦИЯМИ НО УЖЕ С КОДОМ ДЕЙСТВИЯ


    fun addOlympiad(olympiad: Olympiad) {
        postOlympiad(APPEND, olympiad)
    }

    fun editOlympiad(olympiad: Olympiad) {
        postOlympiad(UPDATE, olympiad)
    }

    fun deleteOlympiad(olympiad: Olympiad) {
        postOlympiad(DELETE, olympiad)
    }

    fun addPupil(pupil: Pupil) {
        postPupil(APPEND, pupil)
    }

    fun editPupil(pupil: Pupil) {
        postPupil(UPDATE, pupil)
    }

    fun deletePupil(pupil: Pupil) {
        postPupil(DELETE, pupil)
    }

}