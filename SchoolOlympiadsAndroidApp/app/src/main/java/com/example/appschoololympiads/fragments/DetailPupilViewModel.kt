package com.example.appschoololympiads.fragments

import androidx.lifecycle.ViewModel
import com.example.appschoololympiads.model.Pupil
import com.example.appschoololympiads.repository.DataRepository


// Он обеспечивает хранение и управление данными об школьнике,
// которые предоставляются пользовательскому интерфейсу для отображения на экране с деталями о школьнике
class DetailPupilViewModel : ViewModel() {

    lateinit var pupil: Pupil

    // Использует репозиторий данных для получения информации об олимпиаде, связанной с учеником
    // Одно из преимуществ использования ViewModel - это возможность хранения и управления данными,
    // которые находятся вне жизненного цикла пользовательского интерфейса
    // и могут быть доступны во многих местах приложения
    // Что в свою очередь, возвращаясь к моим грубым сравнениям, похоже на стейт менежмент во фронте
    var olympiad = DataRepository.getInstance().olympiad
}