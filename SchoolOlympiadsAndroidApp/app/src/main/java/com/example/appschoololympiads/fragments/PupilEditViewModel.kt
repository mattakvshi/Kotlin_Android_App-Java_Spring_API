package com.example.appschoololympiads.fragments

import androidx.lifecycle.ViewModel
import com.example.appschoololympiads.model.Pupil
import com.example.appschoololympiads.repository.DataRepository
import java.util.Date

// PupilEditViewModel является ViewModel в архитектуре MVVM
// Он обеспечивает хранение и управление данными, конкретно школьниками (Pupil), и предоставляет эти данные пользовательскому интерфейсу (AK представлению AK фрагменту AK View) для отображения
class PupilEditViewModel : ViewModel() {

    // Поле для хранения текущей выбранной олимпиады из репозитория
    val olympiad get() = DataRepository.getInstance().olympiad.value

    // Поле для хранения участника, данные которого будут изменяться
    private var _pupil: Pupil? = null
    var pupil: Pupil?
        get() = _pupil
        set(value) {
            this._pupil = value
        }

    // Хранение текущей даты в миллисекундах (от какого-то января что-то там 1900 года)
    var date: Long = Date().time

    // Функция добавления нового школьника в репозиторий
    fun appendPupil(pupilsName: String, pupilsRegion: String, pupilsPhone: String, pupilsGrade: Int,
                    eventDate: Long, schoolSubject: String, pupilsScores: Int, pupilsMark: Int) {

        // Создание нового школьника с заданными параметрами
        val newPupil = Pupil()
        newPupil.olympiadID = olympiad!!.id
        newPupil.pupilsName = pupilsName
        newPupil.pupilsRegion = pupilsRegion
        newPupil.pupilsPhone = pupilsPhone
        newPupil.pupilsGrade = pupilsGrade
        newPupil.eventDate = eventDate
        newPupil.schoolSubject = schoolSubject
        newPupil.pupilsScores = pupilsScores
        newPupil.pupilsMark = pupilsMark

        // Добавление нового школьника в репозиторий
        DataRepository.getInstance().addPupil(newPupil)
    }

    // Функция обновления существующего школьника в репозитории
    fun updatePupil(pupilsName: String, pupilsRegion: String, pupilsPhone: String, pupilsGrade: Int,
                    eventDate: Long, schoolSubject: String, pupilsScores: Int, pupilsMark: Int) {

        // Проверка существования школьника для обновления
        if (_pupil != null) {
            // Обновление данных участника.
            _pupil!!.pupilsName = pupilsName
            _pupil!!.pupilsRegion = pupilsRegion
            _pupil!!.pupilsPhone = pupilsPhone
            _pupil!!.pupilsGrade = pupilsGrade
            _pupil!!.eventDate = eventDate
            _pupil!!.schoolSubject = schoolSubject
            _pupil!!.pupilsScores = pupilsScores
            _pupil!!.pupilsMark = pupilsMark

            // Сохранение обновленных данных школьника в репозиторий
            DataRepository.getInstance().editPupil(_pupil!!)
        }
    }
}