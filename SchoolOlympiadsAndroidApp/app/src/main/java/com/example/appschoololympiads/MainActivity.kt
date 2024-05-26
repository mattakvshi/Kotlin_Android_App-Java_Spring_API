package com.example.appschoololympiads

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.appschoololympiads.fragments.DetailPupilFragment
import com.example.appschoololympiads.fragments.OlympiadsFragment
import com.example.appschoololympiads.fragments.PupilEditFragment
import com.example.appschoololympiads.fragments.PupilsFragment
import com.example.appschoololympiads.model.Pupil



// MainActivity - главная активность нашего приложения. Заботится об отображении и взаимодействии с фрагментами.
class MainActivity : AppCompatActivity(), ActivityInterface {

    companion object {
        // Идентификаторы фрагментов, которые мы будем отображать в активности.
        const val olympiadsId = 0
        const val pupilId = 1     // Рудимент (Нужно было для Recycle View, а для View Pager нет)
        const val pupilEdit = 2
        const val detailPupilId = 3
    }


    // Интерфейс используется для объявления методов с использованием полиморфизма в функции onOptionsItemSelected
    interface Edit {
        fun append()
        fun edit()
        fun delete()
    }


    // Переменные MenuItem используются для работы с пунктами меню в ActionBar активности
    private var _miNewOlympiad: MenuItem? = null
    private var _miEditOlympiad: MenuItem? = null
    private var _miDeleteOlympiad: MenuItem? = null

    // текущий отображаемый фрагмент
    private var currentFragmentId = -1


    // Переопределение стандартного метода onCreate, который вызывается при создании активности
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Устанавливаем макет для этой активности
        setContentView(R.layout.activity_main)

        // Назад
        onBackPressedDispatcher.addCallback(this) {
            if (supportFragmentManager.backStackEntryCount > 0) {
                supportFragmentManager.popBackStack()
                // В зависимости от текущего фрагмента выполняем соответствующие действия
                when (currentFragmentId) {
                    olympiadsId -> {
                        finish()
                    }
                    pupilEdit -> {
                        currentFragmentId = olympiadsId
                        //updateTitle("Заказы для отдела ${DataRepository.getInstance().department.value!!.name}")
                    }
                    detailPupilId -> {
                        currentFragmentId = olympiadsId
                        //updateTitle("Заказы для отдела ${DataRepository.getInstance().department.value!!.name}")
                    }
                    else -> {}
                }
                updateMenuView()
            }
            else {
                finish()
            }
        }
        // Устанавливаем начальный фрагмент
        setFragment(olympiadsId)
    }


    // onCreateOptionsMenu вызывается при создании меню ActionBar. Используется для определения макета меню.
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        _miNewOlympiad = menu?.findItem(R.id.miNewOlympiad)
        _miEditOlympiad = menu?.findItem(R.id.miEditOlympiad)
        _miDeleteOlympiad = menu?.findItem(R.id.miDeleteOlympiad)

        updateMenuView()
        return true
    }

    // onOptionsItemSelected вызывается, когда пользователь нажимает на элемент меню ActionBar.
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.miNewOlympiad -> {
                val fedit: Edit = OlympiadsFragment.getInstance()
                fedit.append()
                true
            }
            R.id.miEditOlympiad -> {
                val fedit: Edit = OlympiadsFragment.getInstance()
                fedit.edit()
                true
            }
            R.id.miDeleteOlympiad -> {
                val fedit: Edit = OlympiadsFragment.getInstance()
                fedit.delete()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    // Обновляет заголовок активности
    override fun updateTitle(newTitle: String) {
        title = newTitle
    }

    // Заменяет текущий фрагмент на новый в зависимости от полученного идентификатора и обновляет видимость элементов меню
    override fun setFragment(fragmentId: Int, pupil: Pupil?) {
        currentFragmentId = fragmentId
        when (fragmentId) {
            olympiadsId -> { setFragment(OlympiadsFragment.getInstance()) }
            pupilEdit -> { setFragment(PupilEditFragment.newInstance(pupil)) }
            detailPupilId -> {
                setFragment(DetailPupilFragment.newInstance(pupil!!))
                updateTitle("Информация о школьнике")
            }
        }
        updateMenuView()
    }

    // Обновляет видимость элементов меню, основываясь на текущем фрагменте
    private fun updateMenuView() {
        _miNewOlympiad?.isVisible = currentFragmentId == olympiadsId
        _miEditOlympiad?.isVisible = currentFragmentId == olympiadsId
        _miDeleteOlympiad?.isVisible = currentFragmentId == olympiadsId
    }

    // Устанавливает выбранный фрагмент на экран
    private fun setFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fcvMain, fragment)
            .addToBackStack(null)
            .commit()
        updateMenuView()
    }
}