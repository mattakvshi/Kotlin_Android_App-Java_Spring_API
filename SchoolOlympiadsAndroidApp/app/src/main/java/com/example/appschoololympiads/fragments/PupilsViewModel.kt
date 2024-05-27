package com.example.appschoololympiads.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.Observer
import com.example.appschoololympiads.model.Olympiad
import com.example.appschoololympiads.model.Pupil
import com.example.appschoololympiads.repository.DataRepository

// Это класс, который является частью архитектуры MVVM и служит для предоставления данных для PupilsFragment. (ViewModel)
// В этом классе хранятся и обрабатываются данные, связанные с школьниками.
class PupilsViewModel : ViewModel() {

    // Переменная для хранения выбранной олимпиады.
    lateinit var olympiad: Olympiad

    // MutableLiveData содержит список всех школьников. Он отслеживает изменения в данных и уведомляет об этих изменениях.
    var pupilList: MutableLiveData<List<Pupil>> = MutableLiveData()
    // Переменная, которая содержит текущего выбранного участника.
    private var _pupil: Pupil? = null
    val pupil get() = _pupil

    // Observer, который наблюдает за изменениями в списке участников и обновляет список участников для текущей выбранной олимпиады.
    private val pupilListObserver = Observer<List<Pupil>> { list ->
        pupilList.postValue(
            list.filter {it.olympiadID == olympiad?.id} as MutableList<Pupil>
        )
    }

    // Метод для установки олимпиады и инициализации слушателей для школьников и текущего школьника.
    fun set_Olympiad(olympiad: Olympiad){
        this.olympiad = olympiad
        DataRepository.getInstance().pupils.observeForever(pupilListObserver)
        DataRepository.getInstance().pupil.observeForever {
            _pupil = it
        }
    }

    // Метод для сортировки списка школьников по имени.
    fun sortedByName(){
        val sortedOrders = pupilList.value?.sortedBy { it.pupilsName }
        pupilList.postValue(sortedOrders!!)
    }

    // Метод для сортировки списка школьников по региону.
    fun sortedByRegion(){
        val sortedOrders = pupilList.value?.sortedBy { it.pupilsRegion }
        pupilList.postValue(sortedOrders!!)
    }

    // Метод для удаления текущего школьника.
    fun deletePupil() {
        if (pupil != null) {
            DataRepository.getInstance().deletePupil(pupil!!)
        }
    }

    // Метод для изменения текущего школьника.
    fun setCurrentPupil(pupil: Pupil) {
        DataRepository.getInstance().setCurrentPupil(pupil)
    }

}