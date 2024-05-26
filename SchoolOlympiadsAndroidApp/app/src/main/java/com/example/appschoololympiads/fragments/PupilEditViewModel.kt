package com.example.appschoololympiads.fragments

import androidx.lifecycle.ViewModel
import com.example.appschoololympiads.model.Pupil
import com.example.appschoololympiads.repository.DataRepository
import java.util.Date

class PupilEditViewModel : ViewModel() {

    val olympiad get() = DataRepository.getInstance().olympiad.value

    private var _pupil: Pupil? = null
    var pupil: Pupil?
        get() = _pupil
        set(value) {
            this._pupil = value
        }

    var date: Long = Date().time

    fun appendPupil(pupilsName: String, pupilsRegion: String, pupilsPhone: String, pupilsGrade: Int,
                    eventDate: Long, schoolSubject: String, pupilsScores: Int, pupilsMark: Int) {
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
        DataRepository.getInstance().addPupil(newPupil)
    }

    fun updatePupil(pupilsName: String, pupilsRegion: String, pupilsPhone: String, pupilsGrade: Int,
                    eventDate: Long, schoolSubject: String, pupilsScores: Int, pupilsMark: Int) {
        if (_pupil != null) {
            _pupil!!.pupilsName = pupilsName
            _pupil!!.pupilsRegion = pupilsRegion
            _pupil!!.pupilsPhone = pupilsPhone
            _pupil!!.pupilsGrade = pupilsGrade
            _pupil!!.eventDate = eventDate
            _pupil!!.schoolSubject = schoolSubject
            _pupil!!.pupilsScores = pupilsScores
            _pupil!!.pupilsMark = pupilsMark
            DataRepository.getInstance().editPupil(_pupil!!)
        }
    }
}