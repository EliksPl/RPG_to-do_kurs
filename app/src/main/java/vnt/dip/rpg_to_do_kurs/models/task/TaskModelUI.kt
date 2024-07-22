package vnt.dip.rpg_to_do_kurs.models.task

data class TaskModelUI(
    var id: Int,
    var title: String,
    var desc: String,
    var date: Long,
    var rewardCurrency: Int,
    var rewardExp: Int
) {

    fun toHash(): Int{
        return title.hashCode()+
                desc.hashCode()+
                date.hashCode()+
                rewardCurrency.hashCode()+
                rewardExp.hashCode()
    }
}