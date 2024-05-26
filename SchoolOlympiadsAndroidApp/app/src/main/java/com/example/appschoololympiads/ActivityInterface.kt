package com.example.appschoololympiads

import com.example.appschoololympiads.model.Pupil

// ActivityInterface - интерфейс, который обеспечивает общение между активностью и фрагментами
interface ActivityInterface {
    // Обновляет заголовок активности
    fun updateTitle(newTitle: String)

    // Устанавливает фрагмент с заданным id и
    // дополнительно принимает часть данных (некоторых учеников), которые могут быть отображены в фрагменте.
    fun setFragment(fragmentId: Int, pupil: Pupil? = null)
}