package com.example.todo.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.todo.database.models.entity.Task
import com.example.todo.ui.home.fragments.addtask.AddTaskFragment
import com.example.todo.ui.home.fragments.settings.SettingsFragment
import com.example.todo.ui.home.fragments.taskslist.TasksFragment
import com.route.todo.R
import com.route.todo.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    private var currentFragmentTag : String ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setNavigation()
        setOnFabClick()


        if (savedInstanceState!=null){
            currentFragmentTag = savedInstanceState.getString("CURRENT_FRAGMENT")
            if (currentFragmentTag != null){
                val  fragment = supportFragmentManager.findFragmentByTag(currentFragmentTag)
                if (fragment != null){
                    supportFragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.fade_in,R.anim.fade_out)
                        .replace(R.id.fragment_container,fragment,currentFragmentTag)
                        .commit()
                }
            }else{
                binding.bottomNavigationView.selectedItemId = R.id.tasks
            }
        }
    }

    private fun setNavigation() {
        binding.bottomNavigationView.setOnItemSelectedListener { menuItem->
            if (menuItem.itemId == R.id.tasks){
                showFragment(TasksFragment(), "Tasks_Fragment")
                binding.title.text = getString(R.string.to_do_list)
            }else if (menuItem.itemId == R.id.settings){
                showFragment(SettingsFragment(),"Settings_Fragment")
                binding.title.text = getString(R.string.settings)
            }
            true
        }
        binding.bottomNavigationView.selectedItemId = R.id.tasks
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("CURRENT_FRAGMENT",currentFragmentTag)
    }
    private fun showFragment(fragment: Fragment,tag: String) {
        currentFragmentTag = tag
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.fade_in,R.anim.fade_out)
            .replace(R.id.fragment_container,fragment,tag)
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