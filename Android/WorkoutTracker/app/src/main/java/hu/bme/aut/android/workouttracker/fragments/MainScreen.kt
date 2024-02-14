package hu.bme.aut.android.workouttracker.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import hu.bme.aut.android.workouttracker.R
import hu.bme.aut.android.workouttracker.databinding.FragmentMainScreenBinding

class MainScreen : Fragment() {

   lateinit var binding: FragmentMainScreenBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.toolbar.inflateMenu(R.menu.toolbar)
        binding.toolbar.setOnMenuItemClickListener{
            when(it.itemId){
                R.id.menu_profile ->{
                    findNavController().navigate(R.id.action_mainScreen_to_profile_fragment)
                    true
                }
                else -> false
            }

        }
        binding.button.setOnClickListener{

            val year = binding.datePicker.year
            val month = binding.datePicker.month
            val day = binding.datePicker.dayOfMonth
            val b = bundleOf(
                Pair("YEAR", year),
                Pair("MONTH", month),
                Pair("DAY", day)
            )

            findNavController().navigate(R.id.action_mainScreen_to_listFragment2, b)
        }



    }




}