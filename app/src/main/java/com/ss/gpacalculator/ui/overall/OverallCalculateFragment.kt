package com.ss.gpacalculator.ui.overall

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.ss.gpacalculator.R
import com.ss.gpacalculator.adapter.GpaAdapter
import com.ss.gpacalculator.databinding.FragmentOverallCalculateBinding
import com.ss.gpacalculator.model.SubjectModel
import com.ss.gpacalculator.ui.CalculateViewModel
import java.text.DecimalFormat

class OverallCalculateFragment : Fragment() {

    private var _binding: FragmentOverallCalculateBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CalculateViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOverallCalculateBinding.inflate(inflater, container, false)

        init()
        deleteListItem()
        calculateGrade()

        return binding.root
    }

    private fun init() {
        setHasOptionsMenu(true)
        GpaAdapter().apply {
            if (viewModel.overallSubjectList.isNotEmpty()) {
                this.differ.submitList(viewModel.overallSubjectList)
                binding.overallList.adapter = this
            } else {
                val subject = SubjectModel(1, 0, 0)
                viewModel.overallSubjectList.add(subject)
                this.differ.submitList(viewModel.overallSubjectList)
                binding.overallList.adapter = this
            }
        }
    }

    private fun deleteListItem() {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                if (viewModel.overallSubjectList.isNotEmpty()) {
                    val subject = viewModel.overallSubjectList[viewHolder.adapterPosition]
                    viewModel.overallSubjectList.forEach {
                        if (it.number > viewHolder.adapterPosition + 1)
                            it.number -= 1
                    }
                    GpaAdapter().apply {
                        viewModel.overallSubjectList.remove(subject)
                        this.differ.submitList(viewModel.overallSubjectList)
                        binding.overallList.adapter = this
                    }
                    Snackbar.make(requireView(), "Subject Successfully Deleted", Snackbar.LENGTH_SHORT).show()
                }
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binding.overallList)
    }

    private fun calculateGrade() {
        binding.calculate.setOnClickListener {
            val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)

            when {
                binding.oldGpa.text.toString().isEmpty() -> {
                    binding.oldGpaHeader.isErrorEnabled = true
                    binding.oldGpaHeader.error = "Add previous GPA"
                }
                binding.oldCredit.text.toString().isEmpty() -> {
                    binding.oldGpaHeader.isErrorEnabled = false
                    binding.oldCreditHeader.isErrorEnabled = true
                    binding.oldCreditHeader.error = "Add previous credit"
                }
                else -> {
                    binding.oldGpaHeader.isErrorEnabled = false
                    binding.oldCreditHeader.isErrorEnabled = false

                    var totalCredit = 0.0
                    var totalScore = 0.0
                    viewModel.overallSubjectList.forEach {
                        if (it.grade != 0 && it.credit != 0) {
                            val credit = it.credit.toDouble()
                            val grade = when (it.grade) {
                                1 -> 5.0
                                2 -> 4.75
                                3 -> 4.5
                                4 -> 4.0
                                5 -> 3.5
                                6 -> 3.0
                                7 -> 2.5
                                8 -> 2.0
                                9 -> 1.0
                                else -> 0.0
                            }
                            totalCredit += credit
                            totalScore += credit * grade
                        }
                    }
                    val oldGpa = binding.oldGpa.text.toString().toDouble()
                    val oldCredit: Double = binding.oldCredit.text.toString().toDouble()
                    val oldScore = oldGpa * oldCredit
                    var gpa = ((totalScore + oldScore) / (totalCredit + oldCredit))
                    val decimalFormat = DecimalFormat("#.##")

                    if (gpa.toString() == "NaN") gpa = 0.0

                    val dialog = Dialog(requireContext())
                    dialog.setContentView(R.layout.result_popup_massege)
                    val resultGPA: TextView = dialog.findViewById(R.id.result)
                    resultGPA.text = decimalFormat.format(gpa)
                    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    dialog.show()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.add_menu, menu )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.add_menu) {
            GpaAdapter().apply {
                val subject = SubjectModel((viewModel.overallSubjectList.size + 1), 0, 0)
                viewModel.overallSubjectList.add(subject)
                GpaAdapter().apply {
                    this.differ.submitList(viewModel.overallSubjectList)
                    binding.overallList.adapter = this
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}