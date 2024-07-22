package vnt.dip.rpg_to_do_kurs.ui.tasks

import android.content.Context
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import vnt.dip.rpg_to_do_kurs.CHARACTER_KEY
import vnt.dip.rpg_to_do_kurs.DB
import vnt.dip.rpg_to_do_kurs.MAIN
import vnt.dip.rpg_to_do_kurs.PREFS_NAME
import vnt.dip.rpg_to_do_kurs.db.AppDatabase
import vnt.dip.rpg_to_do_kurs.models.hero.HeroModelUI
import vnt.dip.rpg_to_do_kurs.models.task.TaskModelDB

class TasksViewModel : ViewModel() {

    fun initDatabase(){
        DB = AppDatabase.getInstance(MAIN)
        createCharacterIfNotExists(MAIN)
    }

    fun allTasks() : Flow<List<TaskModelDB>> = DB.getTaskDao().getAll()

    private fun createCharacterIfNotExists(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        if (!prefs.contains(CHARACTER_KEY)) {
            saveCharacter(context, HeroModelUI(name = "Hero"))
        }
    }

    private fun saveCharacter(context: Context, character: HeroModelUI) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        val gson = Gson()
        val characterJson = gson.toJson(character)
        editor.putString(CHARACTER_KEY, characterJson)
        editor.apply()
    }

}