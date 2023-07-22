package com.mohammad.contactmanagerapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mohammad.contactmanagerapp.room.UserRepository

class UserViewModelFactory(private var userRepository: UserRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java))
            return UserViewModel(userRepository) as T
        throw IllegalArgumentException("unknown view model")
    }
}