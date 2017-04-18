package com.sa.baseproject.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sa.baseproject.R;
import com.sa.baseproject.base.AppAdapter;
import com.sa.baseproject.model.ContactModel;


public class CardAdapter extends AppAdapter<CardAdapter.ViewHolder, ContactModel.Contact> {

    public CardAdapter() {
        super();
    }

    @Override
    protected int getViewId() {
        return R.layout.row_recycler_view;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        ContactModel.Contact github = getItem(i);
        viewHolder.login.setText(github.getEmail());
        viewHolder.repos.setText("repos: " + github.getName());
        viewHolder.blog.setText("blog: " + github.getAddress());
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView login;
        private TextView repos;
        private TextView blog;

        public ViewHolder(View itemView) {
            super(itemView);
            login = (TextView) itemView.findViewById(R.id.login);
            repos = (TextView) itemView.findViewById(R.id.repos);
            blog = (TextView) itemView.findViewById(R.id.blog);
        }
    }
}