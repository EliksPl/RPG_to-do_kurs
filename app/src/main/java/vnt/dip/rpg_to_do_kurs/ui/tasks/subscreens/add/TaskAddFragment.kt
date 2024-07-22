package vnt.dip.rpg_to_do_kurs.ui.tasks.subscreens.add

import android.graphics.Color
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.simpleNotes.simplenotelist.screens.DateTimePickerDialog.DateTimePickerDialogFragment
import vnt.dip.rpg_to_do_kurs.BUNDLE_KEY_DESC_TO_EDIT
import vnt.dip.rpg_to_do_kurs.MAIN
import vnt.dip.rpg_to_do_kurs.R
import vnt.dip.rpg_to_do_kurs.databinding.FragmentTaskAddBinding
import vnt.dip.rpg_to_do_kurs.models.enums.HeroCharacteristic
import vnt.dip.rpg_to_do_kurs.models.task.TaskModelDB
import java.io.Serializable
import java.text.DateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class TaskAddFragment : BottomSheetDialogFragment(), DateTimePickerDialogFragment.DataTransferListener {

    private lateinit var binding: FragmentTaskAddBinding
    private lateinit var viewModel: TaskAddViewModel
    private lateinit var taskData: TaskModelDB
    var selectedDateTimeTimestamp : Long = -1
    private var selectedCharacteristicValue: Int = 0  // Зберігає обрану характеристику як число
    var currDifficulty = 1


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTaskAddBinding.inflate(inflater, container, false)
        if (arguments != null) {
            taskData = arguments?.customGetSerializable(BUNDLE_KEY_DESC_TO_EDIT)!!
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            ViewCompat.setOnApplyWindowInsetsListener(requireDialog().window?.decorView!!) { _, insets ->
                val imeHeight = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom
                val navigationBarHeight =
                    insets.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom
                binding.root.setPadding(0, 0, 0, imeHeight - navigationBarHeight)
                insets
            }
        } else {
            @Suppress("DEPRECATION")
            requireDialog().window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(TaskAddViewModel::class.java)

        init()

    }

    private fun init(){
        initListeners()
        initDataAndInterface()

    }

    private fun initDataAndInterface(){
        if (arguments != null) {
            binding.AddTaskFragmentEtTitle.setText(taskData.title)
            binding.AddTaskFragmentEtDesc.setText(taskData.desc)
            currDifficulty = getDifficulty()
            selectedDateTimeTimestamp = taskData.dateEnd
        }

        updateDateButton()
        updateStatImage()
        updateDifficultyView()

    }

    private fun updateDateButton(){
        if (selectedDateTimeTimestamp > 0){
            binding.fragmentAddTaskBtnClearDate.visibility = View.VISIBLE
            binding.fragmentAddTaskBtnSetDate.text = DateFormat.getInstance().format(selectedDateTimeTimestamp)
        }else{
            binding.fragmentAddTaskBtnClearDate.visibility = View.GONE
            binding.fragmentAddTaskBtnSetDate.setText(R.string.default_task_date_button_text)
        }
    }

    private fun initListeners(){
        binding.fragmentAddTaskBackBtn.setOnClickListener(){
            returnToTaskScreen()
        }

        binding.fragmentAddTaskDoneBtn.setOnClickListener(){
            val newTask = getNewTaskData()

            if (arguments != null){
                if (checkTaskForSimilarity(newTask)){
                    return@setOnClickListener
                }
                viewModel.tryReplace(taskData, newTask)
            }else{
                viewModel.tryInsert(newTask)
            }

            returnToTaskScreen()
        }

        binding.fragmentAddTaskBtnSetDate.setOnClickListener(){
            val dateTimePickerDialog = DateTimePickerDialogFragment()
            dateTimePickerDialog.setDataTransferListener(this)
            dateTimePickerDialog.show(parentFragmentManager, "dateTimePicker")
        }

        binding.fragmentAddTaskBtnHigherDifficulty.setOnClickListener(){
            if (currDifficulty < 3){
                ++currDifficulty
                updateDifficultyView()
            }
        }

        binding.fragmentAddTaskBtnLowerDifficulty.setOnClickListener(){
            if (currDifficulty > 1){
                --currDifficulty
                updateDifficultyView()
            }
        }

        binding.fragmentAddTaskBtnClearDate.setOnClickListener(){
            selectedDateTimeTimestamp = -1
            updateDateButton()
        }

        binding.addTaskFragmentStatIv.setOnClickListener(){
            showCharacteristicDialog()
        }

    }

    private fun getDifficulty() : Int{
        return (taskData.rewardExp)/(10)
    }

    private fun updateDifficultyView(){
        binding.fragmentAddTaskTvDifficulty.text = resources.getStringArray(R.array.task_difficulty).get(currDifficulty-1)
    }

    private fun checkTaskForSimilarity(tempNewTask: TaskModelDB): Boolean {
        if (taskData.title != tempNewTask.title){
            return false
        }

        if (taskData.desc != tempNewTask.desc){
            return false
        }

        if (taskData.dateEnd != tempNewTask.dateEnd){
            return false
        }

        if (taskData.rewardExp != tempNewTask.rewardExp){
            return false
        }

        if (taskData.rewardCurrency != tempNewTask.rewardCurrency){
            return false
        }

        return true
    }

    private fun getNewTaskData(): TaskModelDB{
        val newTitle = binding.AddTaskFragmentEtTitle.text.toString()
        val newDesc = binding.AddTaskFragmentEtDesc.text.toString()
        val newCurrentDate = Instant.now().toEpochMilli()
        val newDeadlineDate = selectedDateTimeTimestamp
        val newStatNumber = selectedCharacteristicValue

        val newExp = currDifficulty*10
        val newCurrency = currDifficulty*5


        val tempNewTask = TaskModelDB(
            title = newTitle,
            desc = newDesc,
            dateCreated = newCurrentDate,
            dateEnd = newDeadlineDate,
            rewardExp = newExp,
            rewardCurrency = newCurrency,
            statNum = newStatNumber
        )

        return tempNewTask

    }

    //прийом даних(LocalDateTime) з кастомного DateTimePicker, зміна тексту кнопки, збереження у вигляді TimeStamp
    override fun onDataTransfer(data: LocalDateTime) {
        binding.fragmentAddTaskBtnSetDate.text = data.format(DateTimeFormatter.ofLocalizedDateTime(
            FormatStyle.SHORT))
        selectedDateTimeTimestamp = data.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        updateDateButton()

    }

    private inline fun <reified T : Serializable> Bundle.customGetSerializable(key: String): T? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            getSerializable(key, T::class.java)
        } else {
            @Suppress("DEPRECATION")
            getSerializable(key) as? T
        }
    }

    private fun returnToTaskScreen(){
//        MAIN.navController.navigate(R.id.action_taskAddFragment_to_navigation_tasks)
        dismiss()
    }

    private fun showCharacteristicDialog() {
        val characteristics = HeroCharacteristic.values()
        val characteristicNames = characteristics.map { it.displayName }.toTypedArray()

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Choose characteristic to upgrade")
            .setItems(characteristicNames) { dialog, which ->
                val selectedCharacteristic = characteristics[which]
                selectedCharacteristicValue = selectedCharacteristic.value  // Зберегти числове значення
                updateStatImage()
                // Виконати дію при виборі характеристики
                // Наприклад, оновити зображення або інший інтерфейс
            }
        builder.create().show()
    }

    private fun updateStatImage(){
        if (selectedCharacteristicValue == HeroCharacteristic.STRENGTH.value){
            binding.addTaskFragmentStatIv.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_strength))
        }

        if (selectedCharacteristicValue == HeroCharacteristic.INTELLIGENCE.value){
            binding.addTaskFragmentStatIv.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_intelligence))
        }
        if (selectedCharacteristicValue == HeroCharacteristic.ENDURANCE.value){
            binding.addTaskFragmentStatIv.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_endurance))
        }
        if (selectedCharacteristicValue == HeroCharacteristic.AGILITY.value){
            binding.addTaskFragmentStatIv.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_agility))
        }
        if (selectedCharacteristicValue == HeroCharacteristic.WISDOM.value){
            binding.addTaskFragmentStatIv.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_wisdom))
        }

    }




}