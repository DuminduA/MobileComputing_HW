package com.example.composetutorial.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun getAll(): Flow<List<User>>

    @Query("SELECT * FROM users WHERE id IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): Flow<List<User>>

    @Query("SELECT * FROM users WHERE firstName LIKE :firstName LIMIT 1")
    fun findByName(firstName: String): Flow<User>

    @Query("SELECT * FROM users WHERE id LIKE :id LIMIT 1")
    fun findById(id: Int): Flow<User>

    @Query("SELECT * FROM users WHERE id=1 LIMIT 1")
    fun findFirstUser(): User
    @Query("UPDATE users SET firstName = :firstName where id = :id")
    fun updateUserName(firstName: String, id: Int)

    @Query("UPDATE users SET photo = :pic where id = :id")
    fun updateProPic(pic: String, id: Int)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg users: User)

    @Upsert
    fun upsertUser(user: User)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUser(user: User)

    @Delete
    fun delete(user: User)
}
