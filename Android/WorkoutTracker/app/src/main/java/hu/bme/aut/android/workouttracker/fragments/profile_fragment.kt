package hu.bme.aut.android.workouttracker.fragments

import android.content.Context
import android.os.Binder
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.set
import androidx.navigation.fragment.findNavController
import hu.bme.aut.android.workouttracker.databinding.FragmentMainScreenBinding
import hu.bme.aut.android.workouttracker.databinding.FragmentProfileFragmentBinding


class profile_fragment : Fragment() {

    lateinit var binding: FragmentProfileFragmentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileFragmentBinding.inflate(inflater, container, false)
        val sharedpref = activity?.getPreferences(Context.MODE_PRIVATE)
        val name = sharedpref?.getString("name", "Adja meg") ?: "Error"
        val age = sharedpref?.getString("age", "Adja meg") ?: "Error"
        val height = sharedpref?.getString("weight", "Adja meg") ?: "Error"
        val weight = sharedpref?.getString("height", "Adja meg") ?: "Error"
        binding.nameIn?.setText(name)
        binding.ageIn?.setText(age)
        binding.heightIn?.setText(height)
        binding.weightIn?.setText(weight)


        binding.bSave.setOnClickListener{
            var editor = sharedpref?.edit()
            editor?.putString("name", binding.nameIn.text.toString())
            editor?.putString("age", binding.ageIn.text.toString())
            editor?.putString("weight", binding.weightIn.text.toString())
            editor?.putString("height", binding.heightIn.text.toString())
            editor?.commit()
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {




    }


}