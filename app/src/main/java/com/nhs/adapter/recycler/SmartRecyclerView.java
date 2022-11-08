package com.nhs.adapter.recycler;

// created By Abhishek Vadodariya

import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SmartRecyclerView extends RecyclerView {
    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;
    private IPageListener pageListener;
    private EndlessScrollListener endlessScrollListener;
    private RecyclerStateView recyclerStateView;

    public SmartRecyclerView(Context context) {
        super(context);
        init();
    }

    public SmartRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    public SmartRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {

    }

    public void setStateView(RecyclerStateView recyclerStateView) {
        this.recyclerStateView = recyclerStateView;
        setState(RecyclerState.NORMAL);
    }

    public void setState(RecyclerState recyclerState) {
        if (recyclerStateView != null) {
            switch (recyclerState) {
                case EMPTY: {
                    this.setVisibility(GONE);
                    recyclerStateView.setState(RecyclerStateView.States.EMPTY);
                    break;
                }
                case NORMAL: {
                    this.setVisibility(VISIBLE);
                    recyclerStateView.setState(RecyclerStateView.States.NORMAL);
                    break;
                }
            }
        }
    }

    public void setRecyclerViewType(RecyclerViewType recyclerViewType) {
        setRecyclerViewType(recyclerViewType, 2, false);
    }

    public void setRecyclerViewType(RecyclerViewType recyclerViewType, boolean enableReverseLayout) {
        setRecyclerViewType(recyclerViewType, 2, enableReverseLayout);
    }

    public void setRecyclerViewType(RecyclerViewType recyclerViewType, int columnCount, boolean enableReverseLayout) {
        LayoutManager layoutManager;
        switch (recyclerViewType) {
            case VERTICAL: {
                linearLayoutManager = new LinearLayoutManager(getContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                linearLayoutManager.setReverseLayout(enableReverseLayout);

                layoutManager = linearLayoutManager;
                break;
            }
            case HORIZONTAL: {
                linearLayoutManager = new LinearLayoutManager(getContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                linearLayoutManager.setReverseLayout(enableReverseLayout);

                layoutManager = linearLayoutManager;
                break;
            }
            case GRID: {
                gridLayoutManager = new GridLayoutManager(getContext(), columnCount);
                gridLayoutManager.setReverseLayout(enableReverseLayout);
                layoutManager = gridLayoutManager;
                break;
            }
            default: {
                linearLayoutManager = new LinearLayoutManager(getContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                linearLayoutManager.setReverseLayout(enableReverseLayout);

                layoutManager = linearLayoutManager;
                break;
            }
        }
        this.setLayoutManager(layoutManager);
    }

    public void resetScrollListener() {
        if (endlessScrollListener != null) {
            endlessScrollListener.reset(0, true);
        }
    }

    public void setCurrentPage(int pageNo) {
        if (endlessScrollListener != null) {
            endlessScrollListener.setCurrentPageNo(pageNo);
        }
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

    public int getCurrentlyVisibleItemIndex() {
        return getLastVisibleItemPosition();
    }

    public void setPageListener(int pageSize, int startingPageNo, int currentPageNo, final IPageListener pageListener) {
        this.pageListener = pageListener;
        if (endlessScrollListener != null) {
            this.removeOnScrollListener(endlessScrollListener);
        }
        endlessScrollListener = new EndlessScrollListener(linearLayoutManager);
        endlessScrollListener.setStartingPageNo(startingPageNo);
        endlessScrollListener.setCurrentPageNo(currentPageNo);
        endlessScrollListener.setPageSize(pageSize);
        endlessScrollListener.setOnLoadListener(new EndlessScrollListener.IOnLoadListener() {
            @Override
            public void onLoadMore(int currentPage) {
                if (pageListener != null) {
                    pageListener.loadMore(currentPage);
                }
            }
        });
        this.addOnScrollListener(endlessScrollListener);
    }

    public void setPageListener(int pageSize, int startingPageNo, final IPageListener pageListener) {
        this.pageListener = pageListener;

        if (endlessScrollListener != null) {
            this.removeOnScrollListener(endlessScrollListener);
        }
        endlessScrollListener = new EndlessScrollListener(linearLayoutManager);
        endlessScrollListener.setCurrentPageNo(startingPageNo);
        endlessScrollListener.setStartingPageNo(startingPageNo);
        endlessScrollListener.setPageSize(pageSize);
        endlessScrollListener.setOnLoadListener(new EndlessScrollListener.IOnLoadListener() {
            @Override
            public void onLoadMore(int currentPage) {
                if (pageListener != null) {
                    pageListener.loadMore(currentPage);
                }
            }
        });

        this.addOnScrollListener(endlessScrollListener);
    }

    public enum RecyclerViewType {
        VERTICAL,
        HORIZONTAL,
        GRID
    }

    public enum RecyclerState {
        NORMAL,
        EMPTY
    }

    public interface IPageListener {
        void loadMore(int currentPage);
    }
}
