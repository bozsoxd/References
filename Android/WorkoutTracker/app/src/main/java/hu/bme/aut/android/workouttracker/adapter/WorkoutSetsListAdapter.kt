package hu.bme.aut.android.workouttracker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.workouttracker.data.WorkoutSet
import hu.bme.aut.android.workouttracker.databinding.WorkoutSetsListBinding

class WorkoutSetsListAdapter (private val listener: WorkoutSetsItemListener) :
    RecyclerView.Adapter<WorkoutSetsListAdapter.WorkoutSetsViewHolder>() {

    private val items = mutableListOf<WorkoutSet>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = WorkoutSetsViewHolder(
        WorkoutSetsListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: WorkoutSetsViewHolder, position: Int) {
        val workoutSet = items[position]

        holder.binding.gyakName.text = workoutSet.name
        holder.binding.reps.text = workoutSet.reps.toString()
        holder.binding.wight.text = "${workoutSet.weight.toString()} kg"

        holder.binding.ibRemove.setOnClickListener{
            listener.onItemDeleted(workoutSet)
            delete(workoutSet)
        }
    }

    override fun getItemCount(): Int = items.size

    interface WorkoutSetsItemListener {
        fun onItemChanged(item: WorkoutSet)
        fun onItemDeleted(item: WorkoutSet)
    }

    fun addItem(item: WorkoutSet){
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun update(workoutSetItems: List<WorkoutSet>){
        items.clear()
        items.addAll(workoutSetItems)
        notifyDataSetChanged()
    }

    fun delete(set: WorkoutSet){
        items.remove(set)
        notifyItemRemoved(items.indexOf(set))

    }

    inner class WorkoutSetsViewHolder(val binding: WorkoutSetsListBinding) : RecyclerView.ViewHolder(binding.root)
}