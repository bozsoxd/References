package hu.bme.aut.android.workouttracker.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [WorkoutSet::class], version = 1)
abstract class WorkoutSetsDatabase : RoomDatabase() {
    abstract fun WorkoutSetsDao():WorkoutSetsDao

    companion object{
        fun getDatabase(applicationContext: Context): WorkoutSetsDatabase{
            return Room.databaseBuilder(
                applicationContext,
                WorkoutSetsDatabase::class.java,
                "workout-list"
            ).build();
        }
    }
}