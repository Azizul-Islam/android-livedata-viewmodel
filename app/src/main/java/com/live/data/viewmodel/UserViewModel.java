package com.live.data.viewmodel;

/*
*  ****************************************************************************
*  * Created by : Md. Azizul Islam on 2/14/2018 at 5:09 PM.
*  * Email : azizul@w3engineers.com
*  * 
*  * Last edited by : Md. Azizul Islam on 2/14/2018.
*  * 
*  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>  
*  ****************************************************************************
*/


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.live.data.App;
import com.live.data.db.entity.UserEntity;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    private final MediatorLiveData<List<UserEntity>> mObservableProducts;
    public UserViewModel(@NonNull Application application) {
        super(application);
        mObservableProducts = new MediatorLiveData<>();
        LiveData<List<UserEntity>> products = ((App) application).getDbManager().getUsers();
        mObservableProducts.addSource(products, new Observer<List<UserEntity>>() {
            @Override
            public void onChanged(@Nullable List<UserEntity> userEntities) {
                mObservableProducts.postValue(userEntities);
            }
        });
    }
    public LiveData<List<UserEntity>> getUsers() {
        return mObservableProducts;
    }

}
