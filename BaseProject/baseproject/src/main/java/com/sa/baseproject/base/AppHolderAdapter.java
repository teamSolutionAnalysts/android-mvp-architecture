package com.sa.baseproject.base;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by harsh.parikh on 08-02-2016.
 */
abstract public class AppHolderAdapter<VH extends RecyclerView.ViewHolder, T> extends RecyclerView.Adapter<VH> {

    @Override
    abstract public void onBindViewHolder(VH holder, int position);

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(getViewId(), parent, false);
        RecyclerView.ViewHolder vh = new RecyclerView.ViewHolder(view) {
        };

        return (VH) vh;
    }


    protected abstract int getViewId();


}
