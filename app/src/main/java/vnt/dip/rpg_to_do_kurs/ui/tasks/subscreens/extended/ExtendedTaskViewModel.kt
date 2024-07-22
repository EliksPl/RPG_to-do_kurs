package vnt.dip.rpg_to_do_kurs.ui.tasks.subscreens.extended

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import vnt.dip.rpg_to_do_kurs.CHARACTER_KEY
import vnt.dip.rpg_to_do_kurs.DB
import vnt.dip.rpg_to_do_kurs.MAIN
import vnt.dip.rpg_to_do_kurs.PREFS_NAME
import vnt.dip.rpg_to_do_kurs.models.hero.HeroModelUI
import vnt.dip.rpg_to_do_kurs.models.task.TaskModelDB

class ExtendedTaskViewModel : ViewModel() {
    fun tryDelete(task: TaskModelDB) {
        viewModelScope.launch(Dispatchers.IO) {
            if (!task.title.isBlank() || !task.desc.isBlank()) {
                DB.getTaskDao().delete(task)
            }
        }
    }

    fun tryComplete(task: TaskModelDB) {
        viewModelScope.launch(Dispatchers.IO) {
            val hero = getCharacter(MAIN)
            hero.exp += task.rewardExp
            hero.currency += task.rewardCurrency

            if (hero.exp>=hero.expNeeded){
                hero.lvlUp()
            }

            hero.lvlUpStat(task.statNum, task.rewardExp)

            saveCharacter(MAIN, hero)

            if (!task.title.isBlank() || !task.desc.isBlank()) {
                DB.getTaskDao().delete(task)
            }
        }
    }

    fun getCharacter(context: Context): HeroModelUI {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val gson = Gson()
        val characterJson = prefs.getString(CHARACTER_KEY, null)
        return gson.fromJson(characterJson, HeroModelUI::class.java)
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