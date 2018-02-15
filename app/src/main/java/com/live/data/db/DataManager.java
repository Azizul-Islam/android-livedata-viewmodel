package com.live.data.db;

/*
*  ****************************************************************************
*  * Created by : Md. Azizul Islam on 2/14/2018 at 5:34 PM.
*  * Email : azizul@w3engineers.com
*  * 
*  * Last edited by : Md. Azizul Islam on 2/14/2018.
*  * 
*  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>  
*  ****************************************************************************
*/


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import com.live.data.db.entity.UserEntity;

import java.util.List;

public class DataManager {
    private static DataManager dataManager;
    private final AppDatabase mDatabase;
    private DataManager(AppDatabase appDatabase){
        mDatabase = appDatabase;
    }

    public static DataManager getInstance(AppDatabase appDatabase){
        if (dataManager == null) {
            synchronized (DataManager.class) {
                if (dataManager == null) {
                    dataManager = new DataManager(appDatabase);
                }
            }
        }
        return dataManager;
    }
    public LiveData<List<UserEntity>> getUsers() {
        return mDatabase.getUserDao().getAllUsers();
    }
}
