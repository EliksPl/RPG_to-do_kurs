package vnt.dip.rpg_to_do_kurs.ui.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.simpleNotes.simplenotelist.screens.DateTimePickerDialog.DateTimePickerDialogFragment
import kotlinx.coroutines.launch
import vnt.dip.rpg_to_do_kurs.BUNDLE_KEY_TASK_TO_DESC
import vnt.dip.rpg_to_do_kurs.MAIN
import vnt.dip.rpg_to_do_kurs.R
import vnt.dip.rpg_to_do_kurs.adapters.task.TaskAdapter
import vnt.dip.rpg_to_do_kurs.databinding.FragmentTasksBinding
import vnt.dip.rpg_to_do_kurs.models.task.TaskModelDB
import vnt.dip.rpg_to_do_kurs.ui.tasks.subscreens.add.TaskAddFragment
import vnt.dip.rpg_to_do_kurs.ui.tasks.subscreens.extended.ExtendedTaskFragment

class TasksFragment : Fragment() {

    private var _binding: FragmentTasksBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var tasksViewModel : TasksViewModel
    private lateinit var rvTaskList: RecyclerView
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        tasksViewModel = ViewModelProvider(this).get(TasksViewModel::class.java)

        _binding = FragmentTasksBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun init(){
        initData()
        initButtonListeners()

    }

    fun initButtonListeners(){
        binding.btnAddTask.setOnClickListener(){
            //MAIN.navController.navigate(R.id.action_navigation_tasks_to_taskAddFragment)
            val addTaskFragmentDialog = TaskAddFragment()
            addTaskFragmentDialog.show(parentFragmentManager, "addTaskFragment")
        }
    }

    fun updateInterface(){

    }

    fun initData(){
        tasksViewModel.initDatabase()
        rvTaskList = binding.recyclerTaskList
        taskAdapter = TaskAdapter()
        rvTaskList.adapter = taskAdapter

        viewLifecycleOwner.lifecycleScope.launch{
            tasksViewModel.allTasks().collect { tasks ->
                taskAdapter.setList(tasks)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        fun itemClicked(itemModel: TaskModelDB){
            val bundle = Bundle()
            bundle.putSerializable(BUNDLE_KEY_TASK_TO_DESC, itemModel)
            val extendedTaskDialog = ExtendedTaskFragment()
            extendedTaskDialog.arguments = bundle
            extendedTaskDialog.show(MAIN.supportFragmentManager, "taskDescriptionFragment")
//            MAIN.navController.navigate(R.id.action_navigation_tasks_to_extendedTaskFragment, bundle)
        }
    }
}