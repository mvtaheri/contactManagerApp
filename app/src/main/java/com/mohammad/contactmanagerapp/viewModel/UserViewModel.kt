package com.mohammad.contactmanagerapp.viewModel

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohammad.contactmanagerapp.room.User
import com.mohammad.contactmanagerapp.room.UserRepository
import kotlinx.coroutines.launch

class UserViewModel( private  val repository: UserRepository):ViewModel(), Observable {

    val users=repository.users
    private var isUpdateOrDelete =false
    private lateinit var userToUpdateOrDelete :User

    @Bindable
    var inputName=MutableLiveData<String?>()

    @Bindable
    var inputEmail=MutableLiveData<String?>()

    @Bindable
    var saveOrUpdateButtonText=MutableLiveData<String>()

    @Bindable
    var clearAllOrDeleteButtonText=MutableLiveData<String>()

    init {
        saveOrUpdateButtonText.value="save"
        clearAllOrDeleteButtonText.value="Clear All"
    }

    fun saveOrUpdate(){
        if (isUpdateOrDelete){
            userToUpdateOrDelete.name =inputName.value!!
            userToUpdateOrDelete.email=inputEmail.value!!
            update(userToUpdateOrDelete)
        }else {
            val name = inputName.value!!
            val email = inputEmail.value!!
            insert(User(0, name, email))
            inputEmail.value = null
            inputName.value = null
        }
    }
    fun insert(user:User)=viewModelScope.launch {
       repository.insert(user)
    }
    fun clearAllOrDelete(){
        if (isUpdateOrDelete)
            delete(userToUpdateOrDelete)
        else
            clearAll()
    }
    fun update(user:User)=viewModelScope.launch {
        repository.update(user)
        //Reseting All
        inputName.value=null
        inputEmail.value=null
        isUpdateOrDelete=false
        saveOrUpdateButtonText.value="Save"
        clearAllOrDeleteButtonText.value="Clear All"
    }
    fun clearAll()=viewModelScope.launch {
        repository.deleteAll()
    }
    fun delete(user:User)=viewModelScope.launch {
        repository.delete(user)
        //Reseting
        inputName.value=null
        inputEmail.value=null
        isUpdateOrDelete=false
        saveOrUpdateButtonText.value="Save"
        clearAllOrDeleteButtonText.value="Clear All"
    }
    fun initUpdateAndDelete(user: User){
        inputName.value=user.name
        inputEmail.value=user.email
        isUpdateOrDelete=true
        userToUpdateOrDelete=user
        saveOrUpdateButtonText.value="Update"
        clearAllOrDeleteButtonText.value="Delete"
    }
    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}