package vnt.dip.rpg_to_do_kurs.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import vnt.dip.rpg_to_do_kurs.DB_NAME
import vnt.dip.rpg_to_do_kurs.db.dao.TaskDao
import vnt.dip.rpg_to_do_kurs.models.hero.HeroModelDB
import vnt.dip.rpg_to_do_kurs.models.task.TaskModelDB

@Database(
    entities = [TaskModelDB::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getTaskDao():TaskDao

    companion object{
        private var database: AppDatabase ?= null

        @Synchronized
        fun getInstance(context: Context):AppDatabase{
            return if(database == null){
                database = Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME).build()

                database as AppDatabase
            }else{
                database as AppDatabase
            }
        }
    }

}

