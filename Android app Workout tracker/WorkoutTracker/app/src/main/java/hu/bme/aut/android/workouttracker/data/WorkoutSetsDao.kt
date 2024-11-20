package hu.bme.aut.android.workouttracker.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import java.time.LocalDateTime

@Dao
interface WorkoutSetsDao {
    @Query("SELECT * FROM workoutSets WHERE date = :givenDate")
    fun getAllOnSpecDate(givenDate: String): List<WorkoutSet>

    @Query("SELECT * FROM workoutSets")
    fun getAll(): List<WorkoutSet>

    @Insert
    fun insert(shoppingItems: WorkoutSet): Long

    @Update
    fun update(shoppingItem: WorkoutSet)

    @Delete
    fun deleteItem(shoppingItem: WorkoutSet)
}