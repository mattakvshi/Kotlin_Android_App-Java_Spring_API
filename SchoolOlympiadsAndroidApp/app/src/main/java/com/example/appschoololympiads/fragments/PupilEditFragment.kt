package com.example.appschoololympiads.fragments

import android.content.Context
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.appschoololympiads.ActivityInterface
import com.example.appschoololympiads.R
import com.example.appschoololympiads.databinding.FragmentPupilEditBinding
import com.example.appschoololympiads.model.Pupil
import java.text.SimpleDateFormat
import java.util.regex.Pattern


// PupilEditFragment - этот фрагмент предоставляет форму для редактирования или добавления школьника
class PupilEditFragment : Fragment() {

    // Поле для хранения информации о ученике
    private var pupil: Pupil? = null

    // Массивы для выпадающих списков (Spinner).
    private lateinit var gradeArray: Array<String>
    private lateinit var markArray: Array<String>

    companion object {
        fun newInstance(pupil: Pupil? = null): PupilEditFragment {
            return PupilEditFragment().apply { this.pupil = pupil }
        }
    }

    private lateinit var viewModel: PupilEditViewModel
    private lateinit var _binding: FragmentPupilEditBinding
    val binding get() = _binding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPupilEditBinding.inflate(inflater, container, false)

        gradeArray = resources.getStringArray(R.array.grade)
        val adapterGrade = ArrayAdapter(requireContext(),
            android.R.layout.simple_spinner_item, gradeArray)

        binding.spGrade.adapter = adapterGrade

        markArray = resources.getStringArray(R.array.mark)
        val adapterMark = ArrayAdapter(requireContext(),
            android.R.layout.simple_spinner_item, markArray)

        binding.spMark.adapter = adapterMark

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Если есть школьник, информацию о котором нужно открыть - помещаем его в ViewModel и заполняем форму его данными
        viewModel.pupil = this.pupil
        if (viewModel.pupil != null)
            pullForm()

        binding.cvDate.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val dateFormat = SimpleDateFormat("yyyy-MM-dd")
            val customDate = dateFormat.parse("${year}-${month}-${dayOfMonth}")
            viewModel.date = customDate!!.time
        }

        // обработчики событий для кнопок "Сохранить" и "Отмена"

        binding.btnSave.setOnClickListener {

            // проверка полей на пустоту
            if (
                    binding.etFIO.text.toString().isEmpty() ||
                    binding.etRegion.text.toString().isEmpty() ||
                    binding.etPhone.text.toString().isEmpty() ||
                    binding.etSubject.text.toString().isEmpty() ||
                    binding.etScore.text.toString().isEmpty()
                ) {
                Toast.makeText(requireContext(), "Пожалуйста, заполните все поля", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if(!validatePhoneNumber()) {  // Если введенный номер телефона не валиден
                binding.etPhone.error = "Неверный формат номера телефона"
                binding.etPhone.requestFocus()
                return@setOnClickListener
            }

            if (viewModel.pupil == null){
                viewModel.appendPupil(
                    binding.etFIO.text.toString(),
                    binding.etRegion.text.toString(),
                    binding.etPhone.text.toString(),
                    binding.spGrade.selectedItemPosition,
                    viewModel.date,
                    binding.etSubject.text.toString(),
                    binding.etScore.text.toString().toInt(),
                    binding.spMark.selectedItemPosition
                )
            }
            else {
                viewModel.updatePupil(
                    binding.etFIO.text.toString(),
                    binding.etRegion.text.toString(),
                    binding.etPhone.text.toString(),
                    binding.spGrade.selectedItemPosition,
                    viewModel.date,
                    binding.etSubject.text.toString(),
                    binding.etScore.text.toString().toInt(),
                    binding.spMark.selectedItemPosition
                )
            }

            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.btnCancell.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

    }

    // метод для проверки валидности номера телефона.
    private fun validatePhoneNumber(): Boolean {
        val phoneNumber = binding.etPhone.text.toString()
        // шаблон регулярного выражения определяет формат номера телефона
        val pattern = Pattern.compile("^(\\+7|8)?[-.\\s]?\\(?\\d{3}\\)?[-.\\s]?\\d{3}[-.\\s]?\\d{2}[-.\\s]?\\d{2}$")
        return pattern.matcher(phoneNumber).matches()
    }

    // Заполнение формы данными ученика из ViewModel
    private fun pullForm() {
        binding.etFIO.setText(viewModel.pupil!!.pupilsName)
        binding.etRegion.setText(viewModel.pupil!!.pupilsRegion)
        binding.etPhone.setText(viewModel.pupil!!.pupilsPhone)
        binding.spGrade.setSelection(viewModel.pupil!!.pupilsGrade)
        binding.cvDate.setDate(viewModel.pupil!!.eventDate)
        binding.etSubject.setText(viewModel.pupil!!.schoolSubject)
        binding.etScore.setText(viewModel.pupil!!.pupilsScores.toString())
        binding.spMark.setSelection(viewModel.pupil!!.pupilsMark)
    }


    // Когда фрагмент прикрепляется к Activity, мы инициализируем ViewModel, чтобы в ней были данные, относящиеся к UI (Шапку меняем)
    override fun onAttach(context: Context) {
        viewModel = ViewModelProvider(this).get(PupilEditViewModel::class.java)
        (requireContext() as ActivityInterface).updateTitle("Олимпиада ${viewModel.olympiad!!.olympiadName}")
        super.onAttach(context)
    }

}