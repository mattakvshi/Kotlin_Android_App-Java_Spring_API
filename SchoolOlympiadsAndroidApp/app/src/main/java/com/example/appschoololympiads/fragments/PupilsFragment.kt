package com.example.appschoololympiads.fragments

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appschoololympiads.ActivityInterface
import com.example.appschoololympiads.MainActivity
import com.example.appschoololympiads.R
import com.example.appschoololympiads.databinding.FragmentPupilsBinding
import com.example.appschoololympiads.model.Olympiad
import com.example.appschoololympiads.model.Pupil


// Фрагмент PupilsFragment отображает список участников конкретной олимпиады
// Он использует RecyclerView для отображения списка. ViewModel (PupilsViewModel) хранит данные, которые нужны UI
class PupilsFragment : Fragment() {

    private lateinit var olympiad: Olympiad

    companion object {
        private var INSTANCE: PupilsFragment? = null

        fun getInstance(): PupilsFragment {
            if (INSTANCE == null) INSTANCE = PupilsFragment()
            return INSTANCE ?: throw Exception("PupilsFragment не создан!")
        }

        fun newInstance(olympiad: Olympiad): PupilsFragment {
            return PupilsFragment().apply { this.olympiad = olympiad }
        }
    }

    private lateinit var viewModel: PupilsViewModel
    private lateinit var _binding: FragmentPupilsBinding
    val binding get() = _binding


    // Метод жизненного цикла фрагмента (создает представление фрагмента и инициализирует привязку данных)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPupilsBinding.inflate(inflater, container, false)

        binding.rvPupils.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        return binding.root
    }

    // Метод жизненного цикла фрагмента (вызывается, когда представление фрагмента создано)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Прослушиваем изменения списка участников и обновляем адаптер RecyclerView
        viewModel.set_Olympiad(olympiad)
        viewModel.pupilList.observe(viewLifecycleOwner) {
            binding.rvPupils.adapter = PupilAdapter(it)
        }

        // Устанавливаем обработчик кликов на кнопку добавления школьника
        binding.fabAdd.setOnClickListener {
            (requireContext() as ActivityInterface).setFragment(MainActivity.pupilEdit, null)
        }
    }

    // Метод жизненного цикла фрагмента, вызывается при возвращении к фрагменту
    override fun onResume() {
        super.onResume()

        // Обновляем адаптер RecyclerView с новыми данными
        binding.rvPupils.adapter = PupilAdapter(viewModel.pupilList.value ?: listOf())
    }

    // Внутренний класс создан для управления элементами списка в RecyclerView
    private inner class PupilAdapter(private val items: List<Pupil>): RecyclerView.Adapter<PupilAdapter.ItemHolder>() {

        // Создаем новый элемент списка (новый ViewHolder)
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): PupilAdapter.ItemHolder {
            val view = layoutInflater.inflate(R.layout.item_pupil, parent, false)
            return ItemHolder(view)
        }

        // Возвращаем количество элементов в списке
        override fun getItemCount(): Int = items.size

        // Заполняем элемент списка данными
        override fun onBindViewHolder(holder: PupilAdapter.ItemHolder, position: Int) {
            holder.bind(viewModel.pupilList.value!![position])
        }

        private var lastView: View? = null
        private fun updateCurrentView(view: View) {
            lastView?.findViewById<ConstraintLayout>(R.id.clItem)?.setBackgroundColor(
                ContextCompat.getColor(requireContext(), R.color.white))
            lastView?.findViewById<LinearLayout>(R.id.llButtons)?.visibility = View.GONE
            view.findViewById<ConstraintLayout>(R.id.clItem)?.setBackgroundColor(
                ContextCompat.getColor(requireContext(), R.color.accent))
            view?.findViewById<LinearLayout>(R.id.llButtons)?.visibility = View.VISIBLE
            lastView = view
        }

        // содержит представление отдельного элемента списка и обрабатывает события, связанные с этим элементом.
        private inner class ItemHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

            // Здесь инициализируются виджеты отдельного элемента списка
            private var clItem = itemView.findViewById<ConstraintLayout>(R.id.clItem)
            private var tvName = itemView.findViewById<TextView>(R.id.tvName)
            private var tvAddress = itemView.findViewById<TextView>(R.id.tvAddress)
            private var tvNameText = itemView.findViewById<TextView>(R.id.tvNameText)
            private var tvAddressText = itemView.findViewById<TextView>(R.id.tvAddressText)
            private var ibInfo = itemView.findViewById<ImageButton>(R.id.ibInfo)
            private var ibEdit = itemView.findViewById<ImageButton>(R.id.ibEdit)
            private var ibDelete = itemView.findViewById<ImageButton>(R.id.ibDelete)

            // Биндим данные участника с представлением элемента списка
            fun bind(pupil: Pupil) {
                if (pupil == viewModel.pupil)
                    updateCurrentView(itemView)

                tvName.text = pupil.pupilsName
                tvAddress.text = pupil.pupilsRegion

                val clickItem = View.OnClickListener {
                    viewModel.setCurrentPupil(pupil)
                    updateCurrentView(itemView)
                }

                val longClickName = View.OnLongClickListener {
                    it.callOnClick()
                    viewModel.sortedByName()
                    updateCurrentView(itemView)
                    true
                }

                val longClickRegion = View.OnLongClickListener {
                    it.callOnClick()
                    viewModel.sortedByRegion()
                    updateCurrentView(itemView)
                    true
                }

                clItem.setOnClickListener(clickItem)
                clItem.setOnLongClickListener{
                    it.callOnClick()
                    val toast = Toast.makeText(requireContext(), "класс: ${pupil.getGrade()}", Toast.LENGTH_SHORT)
                    toast.show()
                    true
                }
                tvName.setOnLongClickListener(longClickName)
                tvNameText.setOnLongClickListener(longClickName)

                tvAddress.setOnLongClickListener(longClickRegion)
                tvAddressText.setOnLongClickListener(longClickRegion)

                ibInfo.setOnClickListener {
                    (requireActivity() as ActivityInterface).setFragment(MainActivity.detailPupilId, pupil)
                }

                ibEdit.setOnClickListener {
                    (requireContext() as ActivityInterface).setFragment(MainActivity.pupilEdit, pupil)
                }

                ibDelete.setOnClickListener {
                    deletePupil()
                }
            }
        }
    }

//    private fun deletePupil(){
//        AlertDialog.Builder(requireContext())
//            .setTitle("Удаление")
//            .setMessage("Вы действительно хотите удалить школьника ${viewModel.pupil?.pupilsName ?: ""}?")
//            .setPositiveButton("Да") { _, _ ->
//                viewModel.deletePupil()
//            }
//            .setNegativeButton("Нет", null)
//            .setCancelable(true)
//            .create()
//            .show()
//    }

    //РЕАЛИЗАЦИЯ С ИСПОЛЬЗОВАНИЕМ КАСТОМНЫХ СТИЛЕЙ И ЗАДАНИЯ АКЦЕНТНОГО ЦВЕТА КНОПКАМ
    private fun deletePupil() {
        val alertDialog = AlertDialog.Builder(requireContext(), R.style.MyAlertDialogStyle)
            .setTitle("Удаление")
            .setMessage("Вы действительно хотите удалить школьника ${viewModel.pupil?.pupilsName ?: ""}?")
            .setPositiveButton("Да", null)
            .setNegativeButton("Нет", null)
            .setCancelable(true)
            .create()

        alertDialog.setOnShowListener {
            val buttonColor = ContextCompat.getColor(requireContext(), R.color.accent)

            val positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
            positiveButton.setTextColor(buttonColor)
            positiveButton.setOnClickListener {
                viewModel.deletePupil()
                alertDialog.dismiss()
            }

            val negativeButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)
            negativeButton.setTextColor(buttonColor)
        }

        alertDialog.show() // Диалоговое окно подтверждения удаления участника
    }

    // Когда фрагмент прикрепляется к Activity, мы инициализируем ViewModel, чтобы в ней были данные, относящиеся к UI
    override fun onAttach(context: Context) {
        viewModel = ViewModelProvider(this).get(PupilsViewModel::class.java)
        super.onAttach(context)
    }
}