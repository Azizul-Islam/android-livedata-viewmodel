package com.live.data;

/*
*  ****************************************************************************
*  * Created by : Md. Azizul Islam on 2/14/2018 at 5:10 PM.
*  * Email : azizul@w3engineers.com
*  * 
*  * Last edited by : Md. Azizul Islam on 2/14/2018.
*  * 
*  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>  
*  ****************************************************************************
*/


import android.app.Application;
import android.content.Context;

import com.live.data.db.AppDatabase;
import com.live.data.db.DataManager;

public class App extends Application {
    private static AppExecutors mAppExecutors;
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        mAppExecutors = new AppExecutors();
        context = getApplicationContext();
    }
    public static AppDatabase getDatabase() {
        return AppDatabase.getInstance(context, mAppExecutors);
    }

    public DataManager getRepository() {
        return DataManager.getInstance(getDatabase());
    }

    public static AppExecutors getmAppExecutors(){
        return mAppExecutors;
    }

}
