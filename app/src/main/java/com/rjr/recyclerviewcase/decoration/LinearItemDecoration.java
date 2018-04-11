package com.rjr.recyclerviewcase.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by rjr on 2018/4/10.
 * 线性分割线
 */

public class LinearItemDecoration extends RecyclerView.ItemDecoration {

    private static final String TAG = "LinearItemDecoration";

    public static final int VERTICAL = LinearLayout.VERTICAL;
    public static final int HORIZONTAL = LinearLayout.HORIZONTAL;

    private int orientation;
    private Drawable mDivider;
    private Rect outBounds = new Rect();

    public LinearItemDecoration(Context context) {
        this(context, VERTICAL);
    }

    public LinearItemDecoration(Context context, int orientation) {
        this(context, orientation, 0);
    }

    public LinearItemDecoration(Context context, int orientation, @DrawableRes int dividerResId) {
        this.orientation = orientation;
        if (dividerResId != 0) {
            mDivider = context.getResources().getDrawable(dividerResId);
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (parent.getLayoutManager() == null || mDivider == null) {
            return;
        }
        if (orientation == VERTICAL) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }
    }

    private void drawVertical(Canvas c, RecyclerView parent) {
        c.save();
        final int left;
        final int right;
        if (parent.getClipToPadding()) {
            left = parent.getPaddingLeft();
            right = parent.getWidth() - parent.getPaddingRight();
            // 剪去padding部分矩形
            c.clipRect(left, parent.getPaddingTop(), right, parent.getHeight() - parent.getPaddingBottom());
        } else {
            left = 0;
            right = parent.getWidth();
        }
        for (int i = 0, childCount = parent.getChildCount(); i < childCount; i++) {
            View child = parent.getChildAt(i);
            // 获得recyclerView测量到的item的大小
            parent.getDecoratedBoundsWithMargins(child, outBounds);
            int bottom = (int) (outBounds.bottom + child.getTranslationY());
            int top = bottom - mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
            Log.d(TAG, "left: " + left + ", top: " + top + ", right: " + right + ", bottom: " + bottom);
        }
        c.restore();
    }

    private void drawHorizontal(Canvas c, RecyclerView parent) {
        c.save();
        final int top;
        final int bottom;
        if (parent.getClipToPadding()) {
            top = parent.getPaddingTop();
            bottom = parent.getHeight() - parent.getPaddingBottom();
            c.clipRect(parent.getPaddingLeft(), top, parent.getWidth() - parent.getPaddingRight(), bottom);
        } else {
            top = 0;
            bottom = parent.getHeight();
        }
        for (int i = 0, childCount = parent.getChildCount(); i < childCount; i++) {
            View child = parent.getChildAt(i);
            parent.getDecoratedBoundsWithMargins(child, outBounds);
            int right = outBounds.right + Math.round(child.getTranslationX());
            int left = right - mDivider.getIntrinsicWidth();
            mDivider.setBounds(left, top, right, bottom);
            // 划分割线
            mDivider.draw(c);
        }
        c.restore();
    }

    @Override
    public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
        Log.d(TAG, "getItemOffsets: " + itemPosition + ", count = " + parent.getAdapter().getItemCount());
        if (mDivider == null || itemPosition == parent.getAdapter().getItemCount() - 1) {
            super.getItemOffsets(outRect, itemPosition, parent);
        } else if (orientation == VERTICAL) {
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
        } else {
            outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
        }
    }
}
