package vnt.dip.rpg_to_do_kurs.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import vnt.dip.rpg_to_do_kurs.HERO_DB_TABLE_NAME
import vnt.dip.rpg_to_do_kurs.TASK_DB_TABLE_NAME
import vnt.dip.rpg_to_do_kurs.models.task.TaskModelDB

@Dao
interface TaskDao {
    @Query("SELECT * FROM $TASK_DB_TABLE_NAME")
    fun getAll(): Flow<List<TaskModelDB>>

    @Insert
    suspend fun insert(user: TaskModelDB)

    @Delete
    suspend fun delete(user: TaskModelDB)

}