package vnt.dip.rpg_to_do_kurs.ui.tasks.subscreens.add

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import vnt.dip.rpg_to_do_kurs.CHARACTER_KEY
import vnt.dip.rpg_to_do_kurs.DB
import vnt.dip.rpg_to_do_kurs.PREFS_NAME
import vnt.dip.rpg_to_do_kurs.models.hero.HeroModelUI
import vnt.dip.rpg_to_do_kurs.models.task.TaskModelDB

class TaskAddViewModel : ViewModel() {
    fun tryInsert(task: TaskModelDB) {
        viewModelScope.launch(Dispatchers.IO) {
            if (!task.title.isBlank() || !task.desc.isBlank()) {
                DB.getTaskDao().insert(task)
            }
        }
    }

    fun tryReplace(oldTask: TaskModelDB, newTask: TaskModelDB){
        viewModelScope.launch(Dispatchers.IO) {
            if (!newTask.title.isBlank() || !newTask.desc.isBlank()) {
                DB.getTaskDao().delete(oldTask)
                DB.getTaskDao().insert(newTask)
            }
        }
    }

}