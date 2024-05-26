package com.example.appschoololympiads

import android.app.Application
import android.content.Context
import com.example.appschoololympiads.model.Olympiad
import com.example.appschoololympiads.repository.DataRepository

// Класс App наследуется от Application и представляет собой главный класс приложения.
class App : Application() {

    // В блоке инициализации мы устанавливаем instance равным текущему объекту.
    // Это позволяет нам получить доступ к объекту App из любой точки приложения.
    init {
        instance = this
    }

    companion object {
        // Объявляем переменную instance, которая хранит ссылку на объект App
        private var instance: App? = null

        // Этот геттер позволяет получить контекст приложения в любой точке программы.
        val context
            get() = applicationContext()

        // Возвращает контекст приложения
        private fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    // Переопределяем метод onCreate(), который вызывается при создании приложения.
    override fun onCreate() {
        super.onCreate()
        // fetchSchoolAPI() - сетевой запрос, который загружает данные из API при создании приложения.
        DataRepository.getInstance().fetchSchoolAPI()

    }
}