package com.nhs.adapter.recycler;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.nhs.IClickListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public abstract class SmartRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected List<T> dataList = new ArrayList<>();

    protected IClickListener<T> clickListener;
    private IChangeListener<T> changeListener;

    public abstract int getRowLayoutId(int viewType);

    public abstract void bind(RecyclerView.ViewHolder viewHolder, int position);

    public abstract RecyclerView.ViewHolder getViewHolder(View view, int viewType);

    public void setChangeListener(IChangeListener<T> changeListener) {
        this.changeListener = changeListener;
    }

    @Override
    public void registerAdapterDataObserver(final RecyclerView.AdapterDataObserver observer) {
        super.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                observer.onChanged();
                if (changeListener != null) {
                    changeListener.onDataChange(SmartRecyclerAdapter.this);
                }
            }

            @Override
            public void onItemRangeChanged(int positionStart, int itemCount) {
                super.onItemRangeChanged(positionStart, itemCount);
                observer.onItemRangeChanged(positionStart, itemCount);
                if (changeListener != null) {
                    changeListener.onDataChange(SmartRecyclerAdapter.this);
                }
            }

            @Override
            public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
                super.onItemRangeChanged(positionStart, itemCount, payload);
                observer.onItemRangeChanged(positionStart, itemCount, payload);
                if (changeListener != null) {
                    changeListener.onDataChange(SmartRecyclerAdapter.this);
                }
            }

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                observer.onItemRangeInserted(positionStart, itemCount);
                if (changeListener != null) {
                    changeListener.onDataChange(SmartRecyclerAdapter.this);
                }
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                super.onItemRangeRemoved(positionStart, itemCount);
                observer.onItemRangeRemoved(positionStart, itemCount);
                if (changeListener != null) {
                    changeListener.onDataChange(SmartRecyclerAdapter.this);
                }
            }

            @Override
            public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
                super.onItemRangeMoved(fromPosition, toPosition, itemCount);
                observer.onItemRangeMoved(fromPosition, toPosition, itemCount);
                if (changeListener != null) {
                    changeListener.onDataChange(SmartRecyclerAdapter.this);
                }
            }
        });
    }

    public void setClickListener(IClickListener<T> clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getRowLayoutId(viewType), parent, false);
        RecyclerView.ViewHolder viewHolder = getViewHolder(view, viewType);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        bind(holder, position);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void clearAll() {
        dataList.clear();
        notifyDataSetChanged();
    }

    public List<T> getDataList() {
        return dataList;
    }

    /***
     * Additional Methods to handle Data
     */
    public T getItem(int position) {
        return dataList.get(position);
    }

    public void addItem(T item) {
        addItem(item, false);
    }

    public void addItem(T item, boolean withExistCheck) {
        if (withExistCheck) {
            if (!this.dataList.contains(item)) {
                dataList.add(item);
                notifyItemInserted(dataList.indexOf(item));
            }
        } else {
            dataList.add(item);
            notifyItemInserted(dataList.indexOf(item));
        }
    }

    public void addItems(Collection<T> items) {
        if (!items.isEmpty()) {
            int previousSize = getItemCount();
            if (dataList.addAll(items)) {
                notifyItemRangeInserted(previousSize, items.size());
            }
        }
    }

    public void addItems(List<T> dataList, boolean withExistCheck) {
        if (dataList.size() > 0) {
            if (withExistCheck) {
                for (int dataIndex = 0; dataIndex < dataList.size(); dataIndex++) {
                    T dataItem = dataList.get(dataIndex);
                    addItem(dataItem, true);
                }
            } else {
                addItems(dataList);
            }
        }
    }

    public void removeItem(int position) {
        T item = this.dataList.get(position);
        removeItem(item);
    }

    public void removeItem(T item) {
        int index = dataList.indexOf(item);
        if (index >= 0) {
            dataList.remove(index);
            notifyItemRemoved(index);
            notifyItemRangeChanged(index, dataList.size());
        }
    }

    public boolean removeItems(Collection<? extends T> items) {
        if (dataList.removeAll(items)) {
            notifyDataSetChanged();
            return true;
        }
        return false;
    }

    public void updateItem(int position) {
        notifyItemChanged(position);
    }

    public void updateItem(T item, int position) {
        dataList.set(position, item);
        notifyItemChanged(position);
    }

    public interface IChangeListener<T> {
        void onDataChange(SmartRecyclerAdapter<T> smartRecyclerAdapter);
    }
}
