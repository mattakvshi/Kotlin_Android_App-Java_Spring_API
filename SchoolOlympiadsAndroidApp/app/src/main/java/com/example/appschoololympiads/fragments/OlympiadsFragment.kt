package com.example.appschoololympiads.fragments

import android.app.AlertDialog
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.appschoololympiads.ActivityInterface
import com.example.appschoololympiads.MainActivity
import com.example.appschoololympiads.R
import com.example.appschoololympiads.databinding.FragmentOlympiadsBinding
import com.example.appschoololympiads.model.Olympiad
import com.example.appschoololympiads.repository.DataRepository
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


// Данный класс часть паттерна (View) MVVM и является фрагментом, управляющим отображением списка олимпиад.
class OlympiadsFragment : Fragment(), MainActivity.Edit {

    // Здесь мы определяем компаньон-объект (такой объект есть только один на весь класс) и метод `getInstance()`,
    // который возвращает одиночный экземпляр класса `OlympiadsFragment` (или если его нет - создает).
    // Singleton pattern гарантирует создание только одного экземпляра объекта OlympiadFragment во всех случаях использования.
    companion object {
        private var INSTANCE: OlympiadsFragment? = null

        fun getInstance(): OlympiadsFragment {
            if (INSTANCE == null) INSTANCE = OlympiadsFragment()
            return INSTANCE ?: throw Exception("OlympiadsFragment не создан!")
        }
    }

    // Объявляем переменные, используемые в классе.

    // `viewModel` содержит бизнес-логику этого экрана и обновляется при изменении данных
    private lateinit var viewModel: OlympiadsViewModel
    private lateinit var _binding: FragmentOlympiadsBinding
    // а `tabPosition` используется для сохранения текущей позиции вкладки.
    private var tabPosition: Int = 0

    // Здесь инициализируются переменные. `_binding` и `binding` используются для работы с пользовательским интерфейсом этого фрагмента
    val binding get() = _binding

    // Функция onCreateView() вызывается для создания макета фрагмента.
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOlympiadsBinding.inflate(inflater, container, false)
        return binding.root
    }

    // функция onViewCreated() вызывается сразу после создания представления. Здесь мы инициализируем ViewModel и обновляем UI при изменении данных в LiveData.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(OlympiadsViewModel::class.java)
        DataRepository.getInstance().olympiad.observe(viewLifecycleOwner){
            val ma = (requireActivity() as ActivityInterface)
            ma.updateTitle("${viewModel.olympiad?.olympiadName}")
        }
        viewModel.olympiadList.observe(viewLifecycleOwner) {
            createUI(it)
            //Log.d("fff", viewModel.olympiadList.value!!.size.toString())
        }
    }

    // Внутренний класс `OlympiadPageAdapter` исключительно для управления отображением списка олимпиад на вьюпейджере.
    // Он наследуется от `FragmentStateAdapter`, создает фрагмент для каждой олимпиады в списке и управляет его жизненным циклом.
    private inner class OlympiadPageAdapter(fa: FragmentActivity, private val olympiadList: List<Olympiad>?): FragmentStateAdapter(fa) {
        // Метод просто возвращает количество элементов в списке олимпиад.
        override fun getItemCount(): Int {
            return (olympiadList?.size ?: 0)
        }

        // Метод вызывается для каждого элемента на странице вьюпейджера, чтобы создать фрагмент, который будет отображать данные.
        // Возвращается новый экземпляр фрагмента PupilsFragment с данными определенной олимпиады.
        override fun createFragment(position: Int): Fragment {
            return PupilsFragment.newInstance(olympiadList!![position])
        }
    }


    // Метод используется для создания пользовательского интерфейса для фрагмента на основе данных, полученных из списка олимпиад.
    private fun createUI(olympiadList: List<Olympiad>) {
        // Очищаем все вкладки и устанавливаем новые табы для каждой олимпиады из списка.
        binding.tlOlympiad.clearOnTabSelectedListeners()
        binding.tlOlympiad.removeAllTabs()

        for (i in 0  until  (olympiadList.size)) {
            binding.tlOlympiad.addTab(binding.tlOlympiad.newTab().apply {
                text = olympiadList.get(i).olympiadName
            })
        }

        // Создаем и устанавливаем адаптер с помощью которого ViewPager будет знать, какие фрагменты отображать для каждой вкладки.
        val adapter = OlympiadPageAdapter(requireActivity(), viewModel.olympiadList.value)
        binding.vpPupils.adapter = adapter
        TabLayoutMediator(binding.tlOlympiad, binding.vpPupils, true, true) {
                tab, pos ->
            tab.text = olympiadList.get(pos).olympiadName
        }.attach()
        tabPosition = 0
        if (viewModel.olympiad != null)
            tabPosition = if (viewModel.getOlympiadListPosition >= 0)
                viewModel.getOlympiadListPosition
            else
                0
        viewModel.setCurrentOlympiad(tabPosition)
        binding.tlOlympiad.selectTab(binding.tlOlympiad.getTabAt(tabPosition), true)

        binding.tlOlympiad.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tabPosition = tab?.position!!
                viewModel.setCurrentOlympiad(olympiadList[tabPosition])
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }

    override fun append() {
        appendOlympiad()
    }

    override fun edit() {
        if (viewModel.olympiad != null)
            editOlympiad(viewModel.olympiad!!)
    }

    override fun delete() {
        if (viewModel.olympiad != null)
            deleteOlympiad(viewModel.olympiad!!)
    }

//    private fun appendOlympiad() {
//        val mDialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_olympiads, null)
//        val inputName = mDialogView.findViewById<EditText>(R.id.etNameOlympiad)
//        AlertDialog.Builder(requireContext(),  R.style.MyAlertDialogStyle)
//            .setTitle("Информация об олимпиаде:")
//            .setView(mDialogView)
//            .setPositiveButton("Добавить") { _, _ ->
//                if (inputName.text.isNotBlank()) {
//                    viewModel.appendOlympiad(inputName.text.toString())
//                }
//            }
//            .setNegativeButton("Отмена", null)
//            .setCancelable(true)
//            .create()
//            .show()
//    }

    //РЕАЛИЗАЦИЯ С ИСПОЛЬЗОВАНИЕМ КАСТОМНЫХ СТИЛЕЙ И ЗАДАНИЯ АКЦЕНТНОГО ЦВЕТА КНОПКАМ
    private fun appendOlympiad() {
        val mDialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_olympiads, null)
        val inputName = mDialogView.findViewById<EditText>(R.id.etNameOlympiad)
        val alertDialog = AlertDialog.Builder(requireContext(),  R.style.MyAlertDialogStyle)
            .setTitle("Информация об олимпиаде:")
            .setView(mDialogView)
            .setPositiveButton("Добавить", null)
            .setNegativeButton("Отмена", null)
            .setCancelable(true)
            .create()

        alertDialog.setOnShowListener {
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                if (inputName.text.isNotBlank()) {
                    viewModel.appendOlympiad(inputName.text.toString())
                    alertDialog.dismiss() // закрыть диалог
                } else {
                    inputName.error = "Поле не должно быть пустым"
                    inputName.requestFocus()
                }

            }
            val buttonColor = ContextCompat.getColor(requireContext(), R.color.accent)
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(buttonColor)
            alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(buttonColor)
        }

        alertDialog.show()
    }



//    private fun editOlympiad(olympiad: Olympiad) {
//        val mDialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_olympiads, null)
//        val inputName = mDialogView.findViewById<EditText>(R.id.etNameOlympiad)
//        inputName.setText(olympiad.olympiadName)
//        AlertDialog.Builder(requireContext(),  R.style.MyAlertDialogStyle)
//            .setTitle("Информация об олимпиаде:")
//            .setView(mDialogView)
//            .setPositiveButton("Изменить") { _, _ ->
//                if (inputName.text.isNotBlank()) {
//                    olympiad.olympiadName = inputName.text.toString()
//                    viewModel.updateOlympiad(olympiad)
//                }
//            }
//            .setNegativeButton("Отмена", null)
//            .setCancelable(true)
//            .create()
//            .show()
//    }

    //РЕАЛИЗАЦИЯ С ИСПОЛЬЗОВАНИЕМ КАСТОМНЫХ СТИЛЕЙ И ЗАДАНИЯ АКЦЕНТНОГО ЦВЕТА КНОПКАМ
    private fun editOlympiad(olympiad: Olympiad) {
        val mDialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_olympiads, null)
        val inputName = mDialogView.findViewById<EditText>(R.id.etNameOlympiad)
        inputName.setText(olympiad.olympiadName)

        val alertDialog = AlertDialog.Builder(requireContext(),  R.style.MyAlertDialogStyle)
            .setTitle("Информация об олимпиаде:")
            .setView(mDialogView)
            .setPositiveButton("Изменить", null)
            .setNegativeButton("Отмена", null)
            .setCancelable(true)
            .create()

        alertDialog.setOnShowListener {
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                if (inputName.text.isNotBlank()) {
                    olympiad.olympiadName = inputName.text.toString()
                    viewModel.updateOlympiad(olympiad)
                    alertDialog.dismiss() // закрыть диалог
                } else {
                    inputName.error = "Поле не должно быть пустым"
                    inputName.requestFocus()
                }
            }
            val buttonColor = ContextCompat.getColor(requireContext(), R.color.accent)
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(buttonColor)
            alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(buttonColor)
        }

        alertDialog.show()
    }


//    private fun deleteOlympiad(olympiad: Olympiad) {
//        AlertDialog.Builder(requireContext(),  R.style.MyAlertDialogStyle)
//            .setTitle("Удаление")
//            .setMessage("Вы действительно хотите олимпиаду ${olympiad.olympiadName}?")
//            .setPositiveButton("Да") { _, _ ->
//                viewModel.deleteOlympiad(olympiad)
//            }
//            .setNegativeButton("Нет", null)
//            .setCancelable(true)
//            .create()
//            .show()
//    }

    //РЕАЛИЗАЦИЯ С ИСПОЛЬЗОВАНИЕМ КАСТОМНЫХ СТИЛЕЙ И ЗАДАНИЯ АКЦЕНТНОГО ЦВЕТА КНОПКАМ
    private fun deleteOlympiad(olympiad: Olympiad) {
        val alertDialog = AlertDialog.Builder(requireContext(),  R.style.MyAlertDialogStyle)
            .setTitle("Удаление")
            .setMessage("Вы действительно хотите олимпиаду ${olympiad.olympiadName}?")
            .setPositiveButton("Да", null)
            .setNegativeButton("Нет", null)
            .setCancelable(true)
            .create()

        alertDialog.setOnShowListener {
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                viewModel.deleteOlympiad(olympiad)
                alertDialog.dismiss()
            }
            val buttonColor = ContextCompat.getColor(requireContext(), R.color.accent)
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(buttonColor)
            alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(buttonColor)
        }

        alertDialog.show()
    }
}