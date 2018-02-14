package com.live.data;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.live.data.db.entity.UserEntity;
import com.live.data.viewmodel.NameViewModel;
import com.live.data.viewmodel.UserViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private NameViewModel mModel;

    private Button button;
    private RecyclerView recyclerView;
    private MainAdapter mainAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button_submit);
        recyclerView = findViewById(R.id.recycleList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mainAdapter = new MainAdapter(this);
        recyclerView.setAdapter(mainAdapter);

        button.setOnClickListener(this);
        UserViewModel userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        subscribe(userViewModel);

    }

    private void subscribe(UserViewModel userViewModel) {
        userViewModel.getUsers().observe(this, userEntities -> {
            Toast.makeText(MainActivity.this, "User found ", Toast.LENGTH_LONG).show();
            mainAdapter.clear();
            mainAdapter.addItems(userEntities);
        });
    }

    @Override
    public void onClick(View v) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName("Item =" + System.currentTimeMillis());
        new Thread(() -> {
            App.getDatabase().getUserDao().insert(userEntity);
        }).start();
    }

}
