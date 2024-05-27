package com.example.appschoololympiads.fragments

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.appschoololympiads.R
import com.example.appschoololympiads.databinding.FragmentDetailPupilBinding
import com.example.appschoololympiads.model.Pupil
import java.util.Date

// DetailPupilFragment - этот фрагмент отображает информацию о школьнике только для чтения (Детали)
class DetailPupilFragment : Fragment() {

    // информация о текущем ученике
    private lateinit var pupil: Pupil

    companion object {
        fun newInstance(pupil: Pupil): DetailPupilFragment {
            return DetailPupilFragment().apply { this.pupil = pupil }
        }
    }

    // ViewModel для этого фрагмента
    private lateinit var viewModel: DetailPupilViewModel
    // Определение привязки для этого фрагмента
    private lateinit var _binding: FragmentDetailPupilBinding // Наверно это грубо но мне нравится представлять,
    // что процесс биндинга похож на DOM обьект в вебе, такое же объектное представление древовидной структуры пользовательского интерфейса
    val binding get() = _binding


    // А методы жизненного цикла похожи на Хуки в React или Vue
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailPupilBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Инициализация ViewModel
        viewModel = ViewModelProvider(this).get(DetailPupilViewModel::class.java)
        viewModel.pupil = this.pupil

        // Привязка данных ученика к View
        binding.tvOlympiad.text = viewModel.olympiad.value!!.olympiadName
        binding.tvNamePupil.text = viewModel.pupil.pupilsName
        binding.tvRegion.text = viewModel.pupil.pupilsRegion
        binding.tvPhone.text = viewModel.pupil.pupilsPhone
        binding.tvGrade.text = viewModel.pupil.getGrade().toString()
        binding.tvEventDate.text = Date(viewModel.pupil.eventDate).toString()
        binding.tvSubject.text = viewModel.pupil.schoolSubject
        binding.tvScores.text = viewModel.pupil.pupilsScores.toString()
        binding.tvMark.text = viewModel.pupil.getGrade().toString()

        binding.btnBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }
}