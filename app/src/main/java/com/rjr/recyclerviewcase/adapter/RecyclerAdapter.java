package com.rjr.recyclerviewcase.adapter;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rjr.recyclerviewcase.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

/**
 * Created by rjr on 2018/4/10.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private static final int TYPE_LINEAR_VERTICAL = 0;
    private static final int TYPE_LINEAR_HORIZONTAL = 1;
    private static final int TYPE_GRID = 2;
    private static final int TYPE_STAGGERED = 3;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({TYPE_LINEAR_VERTICAL, TYPE_LINEAR_HORIZONTAL, TYPE_GRID, TYPE_STAGGERED})
    @interface ManagerType {}

    private Context mContext;
    private List<String> list;
    private RecyclerView rv;

    public RecyclerAdapter(Context context, List<String> list, RecyclerView rv) {
        this.mContext = context;
        this.list = list;
        this.rv = rv;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, @ManagerType int viewType) {
        View view = null;
        switch (viewType) {
            case TYPE_GRID:
                view = LayoutInflater.from(mContext).inflate(R.layout.recycler_item_horizontal, parent, false);
                break;
            case TYPE_LINEAR_HORIZONTAL:
                view = LayoutInflater.from(mContext).inflate(R.layout.recycler_item_horizontal, parent, false);
                break;
            case TYPE_LINEAR_VERTICAL:
                view = LayoutInflater.from(mContext).inflate(R.layout.recycler_item_vertical, parent, false);
                break;
            case TYPE_STAGGERED:
                view = LayoutInflater.from(mContext).inflate(R.layout.recycler_item_horizontal, parent, false);
                break;
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvName.setText("item" + position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        RecyclerView.LayoutManager layoutManager = rv.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) layoutManager).getOrientation() == LinearLayoutManager.VERTICAL ? TYPE_LINEAR_VERTICAL : TYPE_LINEAR_HORIZONTAL;
        } else if (layoutManager instanceof GridLayoutManager) {
            return TYPE_GRID;
        } else {
            return TYPE_STAGGERED;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
        }
    }
}
