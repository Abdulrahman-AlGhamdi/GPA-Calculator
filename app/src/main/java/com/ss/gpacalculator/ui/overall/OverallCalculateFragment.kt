package com.ss.gpacalculator.ui.overall

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.ss.gpacalculator.R
import com.ss.gpacalculator.databinding.FragmentOverallCalculateBinding
import com.ss.gpacalculator.ui.common.GpaCalculatorViewModel
import com.ss.gpacalculator.ui.common.GpaCalculatorAdapter
import com.ss.gpacalculator.utils.showSnackBar
import com.ss.gpacalculator.utils.viewBinding

class OverallCalculateFragment : Fragment(R.layout.fragment_overall_calculate) {

    private val binding by viewBinding(FragmentOverallCalculateBinding::bind)
    private val viewModel: GpaCalculatorViewModel by viewModels()
    private val adapter = GpaCalculatorAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        deleteListItem()
        calculateGrade()
    }

    private fun init() {
        setHasOptionsMenu(true)
        adapter.differ.submitList(viewModel.subjectList)
        binding.overallList.adapter = adapter
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
            ): Boolean = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                adapter.differ.submitList(viewModel.removeItem(viewHolder.adapterPosition))
                binding.overallList.adapter = adapter
                requireView().showSnackBar("Subject Successfully Deleted")
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binding.overallList)
    }

    private fun calculateGrade() = binding.calculate.setOnClickListener {
            val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)

        when {
            binding.oldGpa.text.toString().isEmpty() -> {
                binding.oldGpaHeader.isErrorEnabled = true
                binding.oldGpaHeader.error = "Add previous GPA"
            }
            binding.oldCredit.text.toString().isEmpty() -> {
                binding.oldGpaHeader.isErrorEnabled    = false
                binding.oldCreditHeader.isErrorEnabled = true
                binding.oldCreditHeader.error = "Add previous credit"
            }
            else -> {
                binding.oldGpaHeader.isErrorEnabled    = false
                binding.oldCreditHeader.isErrorEnabled = false

                val oldGpa = binding.oldGpa.text.toString().toDouble()
                val oldCredit: Double = binding.oldCredit.text.toString().toDouble()
                val oldScore = oldGpa * oldCredit

                Dialog(requireContext()).apply {
                    this.setContentView(R.layout.result_popup_massege)
                    val resultGPA: TextView = this.findViewById(R.id.result)
                    resultGPA.text = viewModel.calculateOverallGpa(oldScore, oldCredit)
                    this.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                }.show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.add_menu, menu )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.add_menu) {
            adapter.differ.submitList(viewModel.addItem())
            binding.overallList.adapter = adapter
        }
        return super.onOptionsItemSelected(item)
    }
}