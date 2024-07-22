package vnt.dip.rpg_to_do_kurs.ui.admin

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import vnt.dip.rpg_to_do_kurs.ADMIN_MODE
import vnt.dip.rpg_to_do_kurs.CHARACTER_KEY
import vnt.dip.rpg_to_do_kurs.PREFS_NAME
import vnt.dip.rpg_to_do_kurs.models.hero.HeroModelUI

class AdminViewModel : ViewModel() {

    fun withdrawCurrency(context : Context, amount : String) {
            if (ADMIN_MODE) {
                val hero = getCharacter(context)
                val amountInNumber = amount.toInt()
                checkForCurrencyAndWithdrawIfEnough(context, hero, amountInNumber)
                saveCharacter(context, hero)
            } else {
                Toast.makeText(context, "Admin mode required", Toast.LENGTH_LONG).show()
            }
    }

    private fun checkForCurrencyAndWithdrawIfEnough(context: Context, hero: HeroModelUI, amount: Int){
        if (hero.currency >= amount){
            hero.currency -= amount
        }else{
            Toast.makeText(context, "Not enough currency!", Toast.LENGTH_LONG).show()
        }

    }

    private fun getCharacter(context: Context): HeroModelUI {
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

    fun getCurrencyAmount(context: Context): String {
        return getCharacter(context).currency.toString()
    }


}