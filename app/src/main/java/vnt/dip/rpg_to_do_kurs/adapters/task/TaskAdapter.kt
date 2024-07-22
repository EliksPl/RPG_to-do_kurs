package vnt.dip.rpg_to_do_kurs.adapters.task

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import vnt.dip.rpg_to_do_kurs.MAIN
import vnt.dip.rpg_to_do_kurs.R
import vnt.dip.rpg_to_do_kurs.databinding.ItemTaskLayoutBinding
import vnt.dip.rpg_to_do_kurs.models.task.TaskModelDB
import vnt.dip.rpg_to_do_kurs.ui.tasks.TasksFragment
import java.text.DateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit

class TaskAdapter(
//    private val showMenuDelete: (Boolean) -> Unit
): RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    private var itemList = ArrayList<TaskModelDB>()
    //    var isSelectionEnabled = false
    private var selectedItemList = mutableListOf<Int>()


    class TaskViewHolder(val binding: ItemTaskLayoutBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(ItemTaskLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val item = itemList[position]

        holder.binding.taskTitle.text = item.title
        holder.binding.taskDesc.text = item.desc
        holder.binding.rvitemTaskTvExpReward.text = "exp: " + item.rewardExp.toString()
        holder.binding.rvitemTaskTvCurrencyReward.text = "coins: " + item.rewardCurrency.toString()
        holder.binding.taskDate.text = DateFormat.getInstance().format(item.dateEnd)

        val currentTime = System.currentTimeMillis()
        val timeDifference = item.dateEnd - currentTime
        Log.d("MyAdapter", "Timestamp: ${item.dateEnd}, CurrentTime: $currentTime, TimeDifference: $timeDifference")


            if(timeDifference < 0){
                // Дата вже пройшла
                holder.binding.taskDate.setTextColor(Color.RED)
            }
            if(timeDifference < TimeUnit.HOURS.toMillis(24) && timeDifference > 0) {
                // Менше 24 годин до дати
                holder.binding.taskDate.setTextColor(Color.YELLOW)
            }
//            else -> {
//                // Дата у майбутньому, більше ніж 24 години
//                holder.itemView.setBackgroundColor(ContextCompat.getColor(MAIN, R.color.main_color)) // або будь-який інший колір за замовчуванням
//            }

//        if (selectedItemList.contains(position)){
//            holder.binding.checkboxSelectedItem.visibility = View.VISIBLE
//        }else{
//            holder.binding.checkboxSelectedItem.visibility = View.GONE
//        }

        holder.itemView.setOnClickListener {
            TasksFragment.itemClicked(itemList[position])
        }

    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(newList: List<TaskModelDB>){
        itemList.clear()
        for (task in newList){
            this.itemList.add(task)
        }

        itemList.reverse()
        notifyDataSetChanged()
    }


}