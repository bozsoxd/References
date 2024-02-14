package hu.bme.aut.android.workouttracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.ListFragment
import hu.bme.aut.android.workouttracker.data.WorkoutSet
import hu.bme.aut.android.workouttracker.fragments.NewWorkoutSetDialogFragment
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }


}