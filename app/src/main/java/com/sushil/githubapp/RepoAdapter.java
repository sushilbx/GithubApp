package com.sushil.githubapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.MyViewHolder> {
    public List<RepoModel.Item> faqList;
    public Context context;


    public RepoAdapter(List<RepoModel.Item> list, Context context) {
        this.faqList = list;
        this.context = context;
    }


    @Override
    public RepoAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item, parent, false);
        return new RepoAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RepoAdapter.MyViewHolder holder, final int position) {

        holder.name.setText(faqList.get(position).full_name);
        holder.type.setText(faqList.get(position).owner.type);
        holder.description.setText(faqList.get(position).description);
        holder.size.setText(faqList.get(position).size);
        holder.watcher.setText(faqList.get(position).visibility);
    }


    @Override
    public int getItemCount() {
        return this.faqList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, size, description, type, watcher;


        MyViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            size = itemView.findViewById(R.id.size);
            watcher = itemView.findViewById(R.id.watcher);
            description = itemView.findViewById(R.id.description);
            type = itemView.findViewById(R.id.type);


        }

    }
}


