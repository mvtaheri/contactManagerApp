package com.mohammad.contactmanagerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mohammad.contactmanagerapp.databinding.ActivityMainBinding
import com.mohammad.contactmanagerapp.room.User
import com.mohammad.contactmanagerapp.room.UserDatabase
import com.mohammad.contactmanagerapp.room.UserRepository
import com.mohammad.contactmanagerapp.viewModel.UserViewModel
import com.mohammad.contactmanagerapp.viewModel.UserViewModelFactory
import com.mohammad.contactmanagerapp.viewUI.MyRecyclerViewAdapter
import java.net.UnknownServiceException

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    lateinit var userViewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_main)
        //room
        val dao=UserDatabase.getInstance(application).userDAO
        val repository=UserRepository(dao)
        val factory=UserViewModelFactory(repository)
        userViewModel=ViewModelProvider(this,factory).get(UserViewModel::class.java)
        binding.userViewModel=userViewModel
        binding.lifecycleOwner=this
        initRecycleView()
    }
    private fun initRecycleView(){
        binding.recyclerView.layoutManager=LinearLayoutManager(this)
        displayUserList()
    }
    private fun displayUserList(){
        userViewModel.users.observe(
            this,
            {
                binding.recyclerView.adapter=MyRecyclerViewAdapter(
                    it,{selectedItem:User-> listItemClicked(selectedItem)}
                )
            }
        )
    }
    private fun listItemClicked(selectedItem:User){
        Toast.makeText(this,
        "selected name is ${selectedItem.name}",
            Toast.LENGTH_LONG
            ).show()
        userViewModel.initUpdateAndDelete(selectedItem)
    }
}