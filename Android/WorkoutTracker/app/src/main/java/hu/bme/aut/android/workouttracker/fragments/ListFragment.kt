package hu.bme.aut.android.workouttracker.fragments

import android.util.Log
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.navArgument
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.android.workouttracker.adapter.WorkoutSetsListAdapter
import hu.bme.aut.android.workouttracker.data.WorkoutSet
import hu.bme.aut.android.workouttracker.data.WorkoutSetsDatabase
import hu.bme.aut.android.workouttracker.databinding.FragmentListBinding
import java.time.LocalDateTime
import kotlin.concurrent.thread

class ListFragment : Fragment(), WorkoutSetsListAdapter.WorkoutSetsItemListener, NewWorkoutSetDialogFragment.NewWorkoutSetDialogListener {


    private lateinit var year : String
    private lateinit var month : String
    private lateinit var day : String




    private lateinit var binding: FragmentListBinding
    private lateinit var database: WorkoutSetsDatabase
    private lateinit var adapter: WorkoutSetsListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentListBinding.inflate(inflater, container, false)

        year = arguments?.getInt("YEAR").toString()
        month = arguments?.getInt("MONTH").toString()
        day = arguments?.getInt("DAY").toString()

        Log.d("DATE", arguments?.getInt("DAY").toString())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = WorkoutSetsDatabase.getDatabase(requireContext().applicationContext)
        binding.fab.setOnClickListener{
            val newFragment = NewWorkoutSetDialogFragment()
            newFragment.show(
                parentFragmentManager,
                NewWorkoutSetDialogFragment.TAG
            )
        }
        initRecycleView()


    }


    private fun initRecycleView(){
        adapter = WorkoutSetsListAdapter(this)
        binding.rvMain.layoutManager = LinearLayoutManager(requireContext().applicationContext)
        binding.rvMain.adapter = adapter
        loadItemsInBackground()

    }

    private fun loadItemsInBackground(){
        thread {
            val items = database.WorkoutSetsDao().getAllOnSpecDate(year+month+day)
            activity?.runOnUiThread{
            adapter.update(items)
            }
        }
    }

    override fun onItemChanged(item: WorkoutSet) {
        thread {
            database.WorkoutSetsDao().update(item)
        }
    }


    override fun onWorkoutSetCreated(newSet: WorkoutSet){
        thread {
            newSet.date=year+month+day
            val insertedId = database.WorkoutSetsDao().insert(newSet)
            newSet.id = insertedId
            activity?.runOnUiThread{
                adapter.addItem(newSet)
            }
        }
    }

    override fun onItemDeleted(item: WorkoutSet) {
        thread {
            database.WorkoutSetsDao().deleteItem(item)
            activity?.runOnUiThread{
                adapter.delete(item)
            }
        }
    }




}

