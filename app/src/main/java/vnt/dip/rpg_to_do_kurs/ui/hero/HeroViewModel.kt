package vnt.dip.rpg_to_do_kurs.ui.hero

import android.content.Context
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import vnt.dip.rpg_to_do_kurs.CHARACTER_KEY
import vnt.dip.rpg_to_do_kurs.PREFS_NAME
import vnt.dip.rpg_to_do_kurs.models.hero.HeroModelDB
import vnt.dip.rpg_to_do_kurs.models.hero.HeroModelUI

class HeroViewModel : ViewModel() {

    fun getCharacter(context: Context): HeroModelUI {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val gson = Gson()
        val characterJson = prefs.getString(CHARACTER_KEY, null)
        return gson.fromJson(characterJson, HeroModelUI::class.java)
    }

//    fun getCharacterIfExists(context: Context): HeroModelUI {
//        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
//        return if(prefs.contains(CHARACTER_KEY)){
//            getCharacter(context)
//        }else{
//            saveCharacter(context, HeroModelUI(name = "Hero"))
//            getCharacter(context)
//        }
//    }


}