package com.ss.gpacalculator.ui.overall

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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
        addOrDeleteListItem()
        calculateGrade()

        return binding.root
    }

    private fun init() {
        GpaAdapter().apply {
            if (viewModel.totalSubjectList.isNotEmpty()) {
                this.differ.submitList(viewModel.totalSubjectList)
                binding.totalList.adapter = this
            } else {
                val subject = SubjectModel(1, 0, 0)
                viewModel.totalSubjectList.add(subject)
                this.differ.submitList(viewModel.totalSubjectList)
                binding.totalList.adapter = this
            }
        }
    }

    private fun addOrDeleteListItem() {
        binding.add.setOnClickListener {
            val subject = SubjectModel((viewModel.totalSubjectList.size + 1), 0, 0)
            viewModel.totalSubjectList.add(subject)
            GpaAdapter().apply {
                this.differ.submitList(viewModel.totalSubjectList)
                binding.totalList.adapter = this
            }
        }

        binding.remove.setOnClickListener {
            if (viewModel.totalSubjectList.isNotEmpty()) {
                viewModel.totalSubjectList.removeLast()
                GpaAdapter().apply {
                    this.differ.submitList(viewModel.totalSubjectList)
                    binding.totalList.adapter = this
                }
            }
        }
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
                    viewModel.totalSubjectList.forEach {
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}