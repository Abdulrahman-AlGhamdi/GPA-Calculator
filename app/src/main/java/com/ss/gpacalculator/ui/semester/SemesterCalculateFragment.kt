package com.ss.gpacalculator.ui.semester

import android.app.Dialog
import android.content.Context.INPUT_METHOD_SERVICE
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.ss.gpacalculator.R
import com.ss.gpacalculator.adapter.GpaAdapter
import com.ss.gpacalculator.databinding.FragmentSemesterCalculateBinding
import com.ss.gpacalculator.model.SubjectModel
import java.text.DecimalFormat

class SemesterCalculateFragment : Fragment() {

    private var _binding: FragmentSemesterCalculateBinding? = null
    private val binding get() = _binding!!
    var subjectList = ArrayList<SubjectModel>()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSemesterCalculateBinding.inflate(inflater, container, false)

        init()
        addOrDeleteListItem()
        calculateGrade()

        return binding.root
    }

    private fun init() {
        GpaAdapter().apply {
            val subject = SubjectModel(1, 0, 0)
            subjectList.add(subject)
            this.differ.submitList(subjectList)
            binding.semesterList.adapter = this
        }
    }

    private fun addOrDeleteListItem() {
        binding.add.setOnClickListener {
            GpaAdapter().apply {
                val subject = SubjectModel((subjectList.size + 1), 0, 0)
                subjectList.add(subject)
                this.differ.submitList(subjectList)
                binding.semesterList.adapter = this
            }
        }

        binding.remove.setOnClickListener {
            if (subjectList.isNotEmpty()) {
                subjectList.removeLast()
                GpaAdapter().apply {
                    this.differ.submitList(subjectList)
                    binding.semesterList.adapter = this
                }
            }
        }
    }

    private fun calculateGrade() {
        binding.calculate.setOnClickListener {
            val imm = requireActivity().getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)

            var totalCredit = 0.0
            var totalScore = 0.0
            subjectList.forEach {
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
            var gpa = (totalScore / totalCredit)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}