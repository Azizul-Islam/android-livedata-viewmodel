package com.live.data;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.live.data.db.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * *************************************************************
 * * Created by Md. Azizul Islam on 2/14/2018 at 11:27 PM.
 * * Email: imazizul@gmail.com
 * *
 * * Company: W3engineers Ltd
 * * Last edited by : Md. Azizul Islam on 2/14/2018.
 * *
 * * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>
 * *************************************************************
 */


public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<UserEntity> userEntityList;
    private Context context;
    public MainAdapter(Context context){
        this.context = context;
        userEntityList = new ArrayList<>();
    }

    public void addItems(List<UserEntity> userEntities){
        userEntityList.addAll(userEntities);
        notifyDataSetChanged();
    }

    public void clear(){
        userEntityList.clear();
        notifyDataSetChanged();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);
        return new UserHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        UserHolder userHolder = (UserHolder)holder;
        userHolder.bind(userEntityList.get(position));
    }

    @Override
    public int getItemCount() {
        return userEntityList.size();
    }

    private class UserHolder extends RecyclerView.ViewHolder{
        private TextView textView;
        private Button button;
        public UserHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.nameEt);
            button = itemView.findViewById(R.id.button_id);
        }

        public void bind(UserEntity userEntity){
            textView.setText(userEntity.getName());
            button.setOnClickListener(v->{
                deleteItem(userEntity);
            });
        }
    }

    private void deleteItem(UserEntity userEntity){
        new Thread(()->App.getDatabase().getUserDao().deleteItem(userEntity)).start();

    }
}
