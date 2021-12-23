package com.ss.gpacalculator.ui.semester

import android.app.Dialog
import android.content.Context.INPUT_METHOD_SERVICE
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
import com.ss.gpacalculator.databinding.FragmentSemesterCalculateBinding
import com.ss.gpacalculator.ui.common.GpaCalculatorViewModel
import com.ss.gpacalculator.ui.common.GpaCalculatorAdapter
import com.ss.gpacalculator.utils.showSnackBar
import com.ss.gpacalculator.utils.viewBinding

class SemesterCalculateFragment : Fragment(R.layout.fragment_semester_calculate) {

    private val binding by viewBinding(FragmentSemesterCalculateBinding::bind)
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
        binding.semesterList.adapter = adapter
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
                binding.semesterList.adapter = adapter
                requireView().showSnackBar("Subject Successfully Deleted")
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binding.semesterList)
    }

    private fun calculateGrade() = binding.calculate.setOnClickListener {
        val imm = requireActivity().getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)

        Dialog(requireContext()).apply {
            this.setContentView(R.layout.result_popup_massege)
            val resultGPA: TextView = this.findViewById(R.id.result)
            resultGPA.text = viewModel.calculateSemesterGpa()
            this.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }.show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.add_menu, menu )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.add_menu) {
            adapter.differ.submitList(viewModel.addItem())
            binding.semesterList.adapter = adapter
        }
        return super.onOptionsItemSelected(item)
    }
}