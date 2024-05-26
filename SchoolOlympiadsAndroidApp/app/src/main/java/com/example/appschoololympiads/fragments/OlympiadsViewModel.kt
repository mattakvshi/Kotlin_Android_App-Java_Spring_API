package com.example.appschoololympiads.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.appschoololympiads.model.Olympiad
import com.example.appschoololympiads.repository.DataRepository

class OlympiadsViewModel : ViewModel() {

    var olympiadList: LiveData<List<Olympiad>> = DataRepository.getInstance().olympiads
    var olympiad: Olympiad? = null

    init {
        DataRepository.getInstance().olympiad.observeForever { olymp ->
            olympiad = olymp ?: Olympiad()
        }
    }

    fun deleteOlympiad(olympiad: Olympiad) {
        DataRepository.getInstance().deleteOlympiad(olympiad)
    }

    fun appendOlympiad(name: String) {
        val olympiad = Olympiad()
        olympiad.olympiadName = name
        DataRepository.getInstance().addOlympiad(olympiad)
    }

    fun updateOlympiad(olympiad: Olympiad) {
        DataRepository.getInstance().editOlympiad(olympiad)
    }

    fun setCurrentOlympiad(olympiad: Olympiad) {
        DataRepository.getInstance().setCurrentOlympiad(olympiad)
    }

    fun setCurrentOlympiad(position: Int) {
        if ((olympiadList.value?.size ?: 0) > position)
            olympiadList.value?.let { DataRepository.getInstance().setCurrentOlympiad(it.get(position)) }
    }

    val getOlympiadListPosition get() = olympiadList.value?.indexOfFirst { it.id == olympiad?.id } ?: -1
}