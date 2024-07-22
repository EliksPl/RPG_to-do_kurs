package vnt.dip.rpg_to_do_kurs.models.task

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import vnt.dip.rpg_to_do_kurs.TASK_DB_TABLE_NAME
import java.io.Serializable

@Entity(tableName = TASK_DB_TABLE_NAME)
data class TaskModelDB (
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo var title: String = "",
    @ColumnInfo var desc: String = "",
    @ColumnInfo(name = "creation_date") var dateCreated: Long = -1,
    @ColumnInfo(name = "expiration_date") var dateEnd: Long = -1,
    @ColumnInfo(name = "currency_reward") var rewardCurrency: Int = 0,
    @ColumnInfo(name = "exp_reward") var rewardExp: Int = 0,
    @ColumnInfo(name = "stat_number") var statNum: Int = 0
        ) : Serializable