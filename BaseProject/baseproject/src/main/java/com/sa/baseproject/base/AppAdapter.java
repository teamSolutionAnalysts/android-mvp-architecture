package com.sa.baseproject.base;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;


public abstract class AppAdapter<holder extends RecyclerView.ViewHolder, T> extends AppHolderAdapter<holder, T> {
    private ArrayList<T> items = new ArrayList<T>();

    public AppAdapter() {
        setHasStableIds(true);
    }


    public void add(T object) {
        items.add(object);
        notifyDataSetChanged();
    }

    public void add(int index, T object) {
        items.add(index, object);
        notifyDataSetChanged();
    }

    public void addAll(Collection<? extends T> collection) {
        if (collection != null) {
            items.addAll(collection);
            notifyDataSetChanged();
        }
    }

    public void append(Collection<? extends T> collection, int startPoint, int size) {
        if (collection != null) {
            items.addAll(collection);
            notifyItemRangeInserted(startPoint, size);
        }
    }

    public void addAll(T... items) {
        addAll(Arrays.asList(items));
    }


    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    public void remove(String object) {
        items.remove(object);
        notifyDataSetChanged();
    }

    public void remove(T object) {
        items.remove(object);
        notifyDataSetChanged();
    }

    public T getItem(int position) {
        return items.get(position);
    }


    @Override
    public long getItemId(int position) {
        return getItem(position).hashCode();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void removeEdit(T object) {
        items.remove(object);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        items.remove(position);
        notifyItemRemoved(position);
    }


    public ArrayList<T> getItems() {
        return items;
    }


}
