package hu.bme.aut.android.workouttracker.data
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Entity(tableName = "workoutSets")
data class WorkoutSet(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id: Long? = null,
    @ColumnInfo(name = "name") public var name: String,
    @ColumnInfo(name = "weight") var weight: Int,
    @ColumnInfo(name = "reps") var reps: Int,
    @ColumnInfo(name = "date") var date: String
    ) {



}