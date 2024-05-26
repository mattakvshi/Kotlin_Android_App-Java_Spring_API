package com.example.appschoololympiads.fragments

import androidx.lifecycle.ViewModel
import com.example.appschoololympiads.model.Pupil
import com.example.appschoololympiads.repository.DataRepository

class DetailPupilViewModel : ViewModel() {

    lateinit var pupil: Pupil
    var olympiad = DataRepository.getInstance().olympiad
}