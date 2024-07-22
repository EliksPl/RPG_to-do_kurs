package vnt.dip.rpg_to_do_kurs.models.hero

import java.io.Serializable

data class HeroModelDB(
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

): Serializable