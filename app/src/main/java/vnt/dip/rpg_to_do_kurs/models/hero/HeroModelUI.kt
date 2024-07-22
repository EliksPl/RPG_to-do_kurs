package vnt.dip.rpg_to_do_kurs.models.hero

import android.graphics.Color
import vnt.dip.rpg_to_do_kurs.models.enums.HeroCharacteristic

data class HeroModelUI(
    var name: String,
    var lvl: Int = 1,
    var expNeeded: Int = 100,
    var freePoints: Int = 0,
    var exp: Int = 0,
    var currency: Int = 0,
    var strength: Int = 1,
    var strengthExp: Int = 0,

    var intelligence: Int = 1,
    var intelligenceExp: Int = 0,

    var endurance: Int = 1,
    var enduranceExp: Int = 0,

    var agility: Int = 1,
    var agilityExp: Int = 0,

    var wisdom: Int = 1,
    var wisdomExp: Int = 0,
) {
    fun toHash(): Int{
        return name.hashCode()+
                lvl.hashCode()+
                exp.hashCode()+
                strength.hashCode()+
                intelligence.hashCode()+
                endurance.hashCode()+
                agility.hashCode()+
                wisdom.hashCode()
    }

    fun lvlUp() {
        ++lvl
        exp -= expNeeded
        expNeeded += 5
        freePoints += 2

        strength += 1
        intelligence += 1
        endurance += 1
        agility += 1
        wisdom += 1

    }

    fun lvlUpStat(statNum: Int, rewardExp: Int) {
        if (statNum == HeroCharacteristic.STRENGTH.value){
            strengthExp += rewardExp
        }
        if (statNum == HeroCharacteristic.INTELLIGENCE.value){
            intelligenceExp += rewardExp
        }
        if (statNum == HeroCharacteristic.ENDURANCE.value){
            enduranceExp += rewardExp
        }
        if (statNum == HeroCharacteristic.AGILITY.value){
            agilityExp += rewardExp
        }
        if (statNum == HeroCharacteristic.WISDOM.value){
            wisdomExp += rewardExp
        }
        checkForStatLvlUp()
    }

    private fun checkForStatLvlUp() {
        if (strengthExp >= 100){
            strengthExp -= 100
            strength++
        }
        if (intelligenceExp >= 100){
            intelligenceExp -= 100
            intelligence++
        }
        if (enduranceExp >= 100){
            enduranceExp -= 100
            endurance++
        }
        if (agilityExp >= 100){
            agilityExp -= 100
            agility++
        }
        if (wisdomExp >= 100){
            wisdomExp -= 100
            wisdom++
        }
    }
}