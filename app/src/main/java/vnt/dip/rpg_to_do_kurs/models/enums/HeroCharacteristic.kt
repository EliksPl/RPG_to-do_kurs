package vnt.dip.rpg_to_do_kurs.models.enums

enum class HeroCharacteristic(val displayName: String, val value: Int) {
    STRENGTH("Strength", 0),
    INTELLIGENCE("Intelligence", 1),
    ENDURANCE("Endurance", 2),
    AGILITY("Agility", 3),
    WISDOM("Wisdom", 4);

    override fun toString(): String {
        return displayName
    }

    companion object {
        fun fromValue(value: Int): HeroCharacteristic? {
            return values().find { it.value == value }
        }
    }
}