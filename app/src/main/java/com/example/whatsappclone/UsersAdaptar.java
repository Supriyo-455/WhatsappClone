package com.example.whatsappclone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.whatsappclone.databinding.RowconversationBinding;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class UsersAdaptar extends RecyclerView.Adapter<UsersAdaptar.userViewHolder>{

    Context context;
    ArrayList<User> users;

    public UsersAdaptar(Context context,ArrayList<User> users){
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public userViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rowconversation,parent,false);
        return new userViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull userViewHolder holder, int position) {
        User user = users.get(position);
        holder.binding.tvUserName.setText(user.getName());

        Glide.with(context).load(user.getProfileImage())
                .placeholder(R.drawable.avatar)
                .into(holder.binding.imgUser);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class userViewHolder extends RecyclerView.ViewHolder{

        RowconversationBinding binding;

        public userViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = RowconversationBinding.bind(itemView);
        }
    }
}
