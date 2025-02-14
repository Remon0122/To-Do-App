package com.example.todo.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.todo.database.models.entity.Task
import com.example.todo.ui.home.fragments.addtask.AddTaskFragment
import com.example.todo.ui.home.fragments.settings.SettingsFragment
import com.example.todo.ui.home.fragments.taskslist.TasksFragment
import com.route.todo.R
import com.route.todo.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setNavigation()
        setOnFabClick()
    }

    private fun setNavigation() {
        binding.bottomNavigationView.setOnItemSelectedListener { menuItem->
            if (menuItem.itemId ==R.id.tasks){
                showFragment(TasksFragment())
                binding.title.text = getString(R.string.to_do_list)
            }else if (menuItem.itemId ==R.id.settings){
                showFragment(SettingsFragment())
                binding.title.text = getString(R.string.settings)
            }
            true
        }
        binding.bottomNavigationView.selectedItemId = R.id.tasks
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container,fragment)
            .setCustomAnimations(R.anim.fade_in,R.anim.fade_out)
            .commit()
    }


    private fun setOnFabClick() {
        binding.fabAddTask.setOnClickListener {
            val bottomSheet = AddTaskFragment()
            bottomSheet.show(supportFragmentManager,"")
            bottomSheet.onTaskAdded = AddTaskFragment.OnTaskAdded { task: Task ->
                //reload data in recyclerview in TasksFragment
            }
        }

    }
}