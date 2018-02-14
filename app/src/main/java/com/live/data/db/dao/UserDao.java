package com.live.data.db.dao;

/*
*  ****************************************************************************
*  * Created by : Md. Azizul Islam on 2/14/2018 at 4:46 PM.
*  * Email : azizul@w3engineers.com
*  * 
*  * Last edited by : Md. Azizul Islam on 2/14/2018.
*  * 
*  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>  
*  ****************************************************************************
*/

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.live.data.db.entity.UserEntity;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    long insert(UserEntity userEntity);

    @Query("Select * from  user")
    LiveData<List<UserEntity>> getAllUsers();

    @Delete
    void deleteItem(UserEntity userEntity);
}
