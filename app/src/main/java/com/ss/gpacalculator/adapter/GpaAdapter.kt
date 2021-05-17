package com.ss.gpacalculator.adapter

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

class GpaAdapter : RecyclerView.Adapter<GpaAdapter.ViewHolder>() {

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
            if (subject.credit.toString() != "0")
                binding.credit.setText(subject.credit.toString())
            if (subject.grade != 0)
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
                    val selectedItem = binding.grade.selectedItem
                            if (selectedItem != 0)
                                subject.grade = position
                }

                override fun onNothingSelected(parentView: AdapterView<*>?) {
                    // your code here
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(inflate(from(parent.context), parent, false))

    override fun onBindViewHolder(holder: GpaAdapter.ViewHolder, position: Int) {
        differ.currentList[position]?.let { holder.bind(it) }
    }

    override fun getItemCount() = differ.currentList.size
}