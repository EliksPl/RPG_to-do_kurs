package vnt.dip.rpg_to_do_kurs.ui.tasks.subscreens.extended

import android.graphics.Color
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import vnt.dip.rpg_to_do_kurs.*
import vnt.dip.rpg_to_do_kurs.databinding.FragmentExtendedTaskBinding
import vnt.dip.rpg_to_do_kurs.models.enums.HeroCharacteristic
import vnt.dip.rpg_to_do_kurs.models.task.TaskModelDB
import vnt.dip.rpg_to_do_kurs.ui.tasks.subscreens.add.TaskAddFragment
import java.io.Serializable
import java.text.DateFormat

class ExtendedTaskFragment : DialogFragment() {

    private lateinit var viewModel: ExtendedTaskViewModel
    private lateinit var binding: FragmentExtendedTaskBinding
    private lateinit var taskData: TaskModelDB

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExtendedTaskBinding.inflate(inflater,container,false)
        taskData = arguments?.customGetSerializable(BUNDLE_KEY_TASK_TO_DESC)!!

        init()
        return binding.root
    }

    private fun init() {
        initDataAndInterface()
        initListeners()
    }

    private fun initDataAndInterface() {
        binding.fragmentExtendedTaskTitle.text = taskData.title
        binding.fragmentExtendedTaskDescription.text = taskData.desc
        binding.fragmentExtendedTaskTvDateTo.text = DateFormat.getInstance().format(taskData.dateEnd)
        binding.fragmentExtendedTaskTvExpReward.text = taskData.rewardExp.toString()
        binding.fragmentExtendedTaskTvCurrencyReward.text = taskData.rewardCurrency.toString()

        updateStatImage(taskData.statNum)


    }

    private fun initListeners() {
        binding.fragmentExtendedTaskBtnComplete.setOnClickListener(){
            if (ADMIN_MODE) {
                viewModel.tryComplete(taskData)
                navigateToTaskFragment()
            }else{
                Toast.makeText(MAIN,R.string.admin_mode_required,Toast.LENGTH_LONG).show()
            }
        }

        binding.fragmentExtendedTaskBtnDelete.setOnClickListener(){
            if (ADMIN_MODE) {
                viewModel.tryDelete(taskData)
                navigateToTaskFragment()
            }else{
                Toast.makeText(MAIN,R.string.admin_mode_required,Toast.LENGTH_LONG).show()
            }
        }

        binding.fragmentExtendedTaskBtnEdit.setOnClickListener(){
            val bundle = Bundle()
            bundle.putSerializable(BUNDLE_KEY_DESC_TO_EDIT, taskData)
//            MAIN.navController.navigate(R.id.action_extendedTaskFragment_to_taskAddFragment, bundle)
            val addTaskFragmentDialog = TaskAddFragment()
            addTaskFragmentDialog.arguments = bundle
            addTaskFragmentDialog.show(parentFragmentManager, "addTaskFragment")

            dismiss()
        }

    }

    private fun navigateToTaskFragment(){
        //MAIN.navController.navigate(R.id.action_extendedTaskFragment_to_navigation_tasks)
        dismiss()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ExtendedTaskViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    private fun updateStatImage(statNum: Int){
        if (statNum == HeroCharacteristic.STRENGTH.value){
            binding.fragmentExtendedTaskStatImage.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_strength))
        }
        if (statNum == HeroCharacteristic.INTELLIGENCE.value){
            binding.fragmentExtendedTaskStatImage.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_intelligence))
        }
        if (statNum == HeroCharacteristic.ENDURANCE.value){
            binding.fragmentExtendedTaskStatImage.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_endurance))
        }
        if (statNum == HeroCharacteristic.AGILITY.value){
            binding.fragmentExtendedTaskStatImage.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_agility))
        }
        if (statNum == HeroCharacteristic.WISDOM.value){
            binding.fragmentExtendedTaskStatImage.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_wisdom))
        }

    }

    private inline fun <reified T : Serializable> Bundle.customGetSerializable(key: String): T? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            getSerializable(key, T::class.java)
        } else {
            @Suppress("DEPRECATION")
            getSerializable(key) as? T
        }
    }



}