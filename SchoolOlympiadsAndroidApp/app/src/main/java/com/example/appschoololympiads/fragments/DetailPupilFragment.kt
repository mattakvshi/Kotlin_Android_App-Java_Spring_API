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

class DetailPupilFragment : Fragment() {

    private lateinit var pupil: Pupil

    companion object {
        fun newInstance(pupil: Pupil): DetailPupilFragment {
            return DetailPupilFragment().apply { this.pupil = pupil }
        }
    }

    private lateinit var viewModel: DetailPupilViewModel
    private lateinit var _binding: FragmentDetailPupilBinding
    val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailPupilBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(DetailPupilViewModel::class.java)
        viewModel.pupil = this.pupil

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