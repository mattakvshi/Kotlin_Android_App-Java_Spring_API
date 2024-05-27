package com.example.appschoololympiads.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.appschoololympiads.model.Olympiad
import com.example.appschoololympiads.repository.DataRepository

// Этот класс OlympiadsViewModel является ViewModel в архитектуре MVVM.
// ViewModel — это класс, который содержит (и управляет) данные и бизнес-логику, которые требуются UI-контроллеру для отображения пользовательского интерфейса.
class OlympiadsViewModel : ViewModel() {

    // Здесь хранится список олимпиад. LiveData хранит данные, и может сообщать об их изменении, обеспечивая активное обновление пользовательского интерфейса в реальном времени.
    var olympiadList: LiveData<List<Olympiad>> = DataRepository.getInstance().olympiads
    var olympiad: Olympiad? = null //хранит текущую выбранную олимпиаду.

    // В блоке init мы наблюдаем (слушаем?) за LiveData (с помощью метода observeForever).
    // Когда LiveData обновляется, мы обновляем переменную в ViewModel.
    init {
        DataRepository.getInstance().olympiad.observeForever { olymp ->
            olympiad = olymp ?: Olympiad()
        }
    }

    // удалить олимпиаду из репозитория (в репазитории соответствующий метод осуществляет POST запрос на сервер)
    fun deleteOlympiad(olympiad: Olympiad) {
        DataRepository.getInstance().deleteOlympiad(olympiad)
    }

    // новая олимпиада добавляется в репозиторий (в репазитории соответствующий метод осуществляет POST запрос на сервер) (ну и дальше так же)
    fun appendOlympiad(name: String) {
        val olympiad = Olympiad()
        olympiad.olympiadName = name
        DataRepository.getInstance().addOlympiad(olympiad)
    }

    fun updateOlympiad(olympiad: Olympiad) {
        DataRepository.getInstance().editOlympiad(olympiad)
    }


    // позволяет установить текущую олимпиаду по сущьности целиком
    fun setCurrentOlympiad(olympiad: Olympiad) {
        DataRepository.getInstance().setCurrentOlympiad(olympiad)
    }

    // позволяет установить текущую олимпиаду по ее позиции в списке
    fun setCurrentOlympiad(position: Int) {
        if ((olympiadList.value?.size ?: 0) > position)
            olympiadList.value?.let { DataRepository.getInstance().setCurrentOlympiad(it.get(position)) }
    }

    // возвращаем индекс текущей олимпиады в списке
    val getOlympiadListPosition get() = olympiadList.value?.indexOfFirst { it.id == olympiad?.id } ?: -1
}