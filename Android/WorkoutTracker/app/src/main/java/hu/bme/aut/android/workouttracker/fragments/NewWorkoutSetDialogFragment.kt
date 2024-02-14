package hu.bme.aut.android.workouttracker.fragments

import android.app.Dialog
import androidx.appcompat.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentOnAttachListener
import androidx.fragment.app.setFragmentResult
import hu.bme.aut.android.workouttracker.R
import hu.bme.aut.android.workouttracker.data.WorkoutSet
import hu.bme.aut.android.workouttracker.databinding.DialogNewSetBinding
import java.lang.RuntimeException
import java.time.LocalDateTime

class NewWorkoutSetDialogFragment : DialogFragment() {

    interface NewWorkoutSetDialogListener{
        fun onWorkoutSetCreated(newSet: WorkoutSet)
    }

    private lateinit var binding: DialogNewSetBinding
    private lateinit var listener: NewWorkoutSetDialogListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = parentFragmentManager.fragments.get(0) as? NewWorkoutSetDialogListener
            ?: throw RuntimeException("Activity must implement the NewWorkoutSetDialogListener interface!")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogNewSetBinding.inflate(LayoutInflater.from(context))
        return  AlertDialog.Builder(requireContext())
            .setTitle(R.string.new_set_title)
            .setView(binding.root)
            .setPositiveButton(R.string.button_ok){
                dialogInterface, i ->
                if(isValid()){
                    listener.onWorkoutSetCreated(getWorkoutSet())
                }
            }
            .setNegativeButton(R.string.button_cancel, null)
            .create()
    }
    companion object{
        const val TAG = "NewWorkoutSetDialogFragment"
    }


    private fun isValid(): Boolean{
        if(binding.etname.text.isNotEmpty() && binding.etReps.text.isNotEmpty() && binding.etWeight.text.isNotEmpty()){
            return true
        }
        return false
    }

    private fun getWorkoutSet() = WorkoutSet(
        name = binding.etname.text.toString(),
        weight = binding.etWeight.text.toString().toInt(),
        reps = binding.etWeight.text.toString().toInt(),
        date = ""
    )




}