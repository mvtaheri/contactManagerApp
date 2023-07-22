package com.mohammad.contactmanagerapp.room

class UserRepository(private var userDAO: UserDAO) {

    var users=userDAO.getAllUserInDB()

    suspend fun insert(user:User):Long{
        return userDAO.insertUser(user)
    }
    suspend fun delete(user: User){
        return userDAO.deleteUser(user)
    }
    suspend fun update(user: User){
        return userDAO.updateUser(user)
    }
    suspend fun deleteAll(){
        return userDAO.deleteAll()
    }
}