package com.live.data.db;

/*
*  ****************************************************************************
*  * Created by : Md. Azizul Islam on 2/14/2018 at 4:02 PM.
*  * Email : azizul@w3engineers.com
*  * 
*  * Last edited by : Md. Azizul Islam on 2/14/2018.
*  * 
*  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>  
*  ****************************************************************************
*/


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.live.data.AppExecutors;
import com.live.data.db.dao.UserDao;
import com.live.data.db.entity.UserEntity;

@Database(entities = {UserEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao getUserDao();

    private static AppDatabase sInstance;
    @VisibleForTesting
    public static final String DATABASE_NAME = "basic-sample-db";
    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();


    public static AppDatabase getInstance(Context context, AppExecutors appExecutors){
        if(sInstance == null){
            synchronized (AppDatabase.class){
                sInstance = buildDatabase(context, appExecutors);
                sInstance.updateDatabaseCreated(context.getApplicationContext());
            }
        }
        return sInstance;
    }

    private static AppDatabase buildDatabase(final Context context, final AppExecutors appExecutors){
        return Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME).addCallback(new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                appExecutors.diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        AppDatabase database = AppDatabase.getInstance(context, appExecutors);
                        UserEntity  userEntity = new UserEntity();
                        userEntity.setName("Azizul Islam");
                        long count = database.getUserDao().insert(userEntity);
                        database.setDatabaseCreated();
                    }
                });
            }

            @Override
            public void onOpen(@NonNull SupportSQLiteDatabase db) {
                super.onOpen(db);
            }
        }).build();
    }

    private void setDatabaseCreated(){
        mIsDatabaseCreated.postValue(true);
    }

    public LiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }
    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

}
