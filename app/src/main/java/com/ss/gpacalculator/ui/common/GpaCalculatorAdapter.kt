package com.ss.gpacalculator.ui.common

import android.view.LayoutInflater.from
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ss.gpacalculator.databinding.ItemSubjectRowBinding
import com.ss.gpacalculator.databinding.ItemSubjectRowBinding.inflate
import com.ss.gpacalculator.model.SubjectModel

class GpaCalculatorAdapter : RecyclerView.Adapter<GpaCalculatorAdapter.ViewHolder>() {

    val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<SubjectModel>() {
        override fun areItemsTheSame(oldItem: SubjectModel, newItem: SubjectModel): Boolean {
            return oldItem.number == newItem.number
        }

        override fun areContentsTheSame(oldItem: SubjectModel, newItem: SubjectModel): Boolean {
            return oldItem == newItem
        }
    })

    inner class ViewHolder(private val binding: ItemSubjectRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(subject: SubjectModel) {
            binding.itemNumber.text = subject.number.toString()
            binding.credit.setText(subject.credit.toString())
            binding.grade.setSelection(subject.grade)

            binding.credit.addTextChangedListener {
                it?.let {
                    subject.credit = if (it.isEmpty()) 0 else {
                        binding.credit.clearFocus()
                        it.toString().toInt()
                    }
                }
            }

            binding.grade.onItemSelectedListener = object : OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    if (position != 0) subject.grade = position
                }

                override fun onNothingSelected(parentView: AdapterView<*>?) {}
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(inflate(from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount() = differ.currentList.size
}