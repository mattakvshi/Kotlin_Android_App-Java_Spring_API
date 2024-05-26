package com.example.appschoololympiads.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.Observer
import com.example.appschoololympiads.model.Olympiad
import com.example.appschoololympiads.model.Pupil
import com.example.appschoololympiads.repository.DataRepository

class PupilsViewModel : ViewModel() {

    lateinit var olympiad: Olympiad

    var pupilList: MutableLiveData<List<Pupil>> = MutableLiveData()
    private var _pupil: Pupil? = null
    val pupil get() = _pupil

    private val pupilListObserver = Observer<List<Pupil>> { list ->
        pupilList.postValue(
            list.filter {it.olympiadID == olympiad?.id} as MutableList<Pupil>
        )
    }

    fun set_Olympiad(olympiad: Olympiad){
        this.olympiad = olympiad
        DataRepository.getInstance().pupils.observeForever(pupilListObserver)
        DataRepository.getInstance().pupil.observeForever {
            _pupil = it
        }
    }

    fun sortedByName(){
        val sortedOrders = pupilList.value?.sortedBy { it.pupilsName }
        pupilList.postValue(sortedOrders!!)
    }

    fun sortedByRegion(){
        val sortedOrders = pupilList.value?.sortedBy { it.pupilsRegion }
        pupilList.postValue(sortedOrders!!)
    }


    fun deletePupil() {
        if (pupil != null) {
            DataRepository.getInstance().deletePupil(pupil!!)
        }
    }

    fun setCurrentPupil(pupil: Pupil) {
        DataRepository.getInstance().setCurrentPupil(pupil)
    }

}