package edu.csulb.cecs.lavilla;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserMenuAdapter extends RecyclerView.Adapter<UserMenuHolder> {

    Context c;
    ArrayList<UserMenuModel> models;

    public UserMenuAdapter(Context c, ArrayList<UserMenuModel> models) {
        this.c = c;
        this.models = models;
    }

    @NonNull
    @Override
    public UserMenuHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_row, null);

        return new UserMenuHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserMenuHolder holder, int position) {

        holder.mTitle.setText(models.get(position).getName());
        holder.mDes.setText(models.get(position).getDescription());

    }

    @Override
    public int getItemCount() {
        return models.size();
    }
}
