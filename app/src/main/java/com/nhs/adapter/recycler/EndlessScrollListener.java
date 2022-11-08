package com.nhs.adapter.recycler;


import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class EndlessScrollListener extends RecyclerView.OnScrollListener {
    public static String TAG = EndlessScrollListener.class.getSimpleName();
    int firstVisibleItem, visibleItemCount, totalItemCount;
    private int previousTotal = 0; // The total number of items in the dataset after the last load
    private boolean loading = true; // True if we are still waiting for the last set of data to load.
    private int visibleThreshold = 5; // The minimum amount of items to have below your current scroll position before loading more.
    private int startingPageNo = 0;
    private int currentPage = 0;

    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;
    private IOnLoadListener onLoadListener;

    public EndlessScrollListener(GridLayoutManager linearLayoutManager) {
        this.gridLayoutManager = linearLayoutManager;
    }

    public EndlessScrollListener(LinearLayoutManager linearLayoutManager) {
        this.linearLayoutManager = linearLayoutManager;
    }

    public void setOnLoadListener(IOnLoadListener onLoadListener) {
        this.onLoadListener = onLoadListener;
    }

    private int getLastVisibleItemPosition() {
        if (linearLayoutManager != null) {
            return linearLayoutManager.findLastCompletelyVisibleItemPosition();
        } else if (gridLayoutManager != null) {
            return gridLayoutManager.findLastCompletelyVisibleItemPosition();
        } else {
            return 0;
        }
    }

    private int getFirstVisibleItemPosition() {
        if (linearLayoutManager != null) {
            return linearLayoutManager.findFirstVisibleItemPosition();
        } else if (gridLayoutManager != null) {
            return gridLayoutManager.findFirstVisibleItemPosition();
        } else {
            return 0;
        }
    }

    private int getTotalItemCount() {
        if (linearLayoutManager != null) {
            return linearLayoutManager.getItemCount();
        } else if (gridLayoutManager != null) {
            return gridLayoutManager.getItemCount();
        } else {
            return 0;
        }
    }

    public void setCurrentPageNo(int currentPage) {
        this.currentPage = currentPage;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = getTotalItemCount();
        firstVisibleItem = getFirstVisibleItemPosition();
        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }
        synchronized (this) {
            if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
                currentPage++;
                if (onLoadListener != null) {
                    onLoadListener.onLoadMore(currentPage);
                }
                loading = true;
            }
        }
    }

    public void reset(int previousTotal, boolean loading) {
        currentPage = startingPageNo;
        this.previousTotal = previousTotal;
        this.loading = loading;
    }

    public void restore(int previousTotal, boolean loading) {
        this.previousTotal = previousTotal;
        this.loading = loading;
    }

    public int getStartingPageNo() {
        return startingPageNo;
    }

    public void setStartingPageNo(int startingPageNo) {
        this.startingPageNo = startingPageNo;
        currentPage = startingPageNo;
    }

    public void setPageSize(int pageSize) {
        this.visibleThreshold = pageSize;
    }

    public interface IOnLoadListener {
        void onLoadMore(int currentPage);
    }
}
