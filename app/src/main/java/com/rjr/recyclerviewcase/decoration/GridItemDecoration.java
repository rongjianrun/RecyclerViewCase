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
 * 网格分割线
 */

public class GridItemDecoration extends RecyclerView.ItemDecoration {

    public static final int VERTICAL = LinearLayout.VERTICAL;
    public static final int HORIZONTAL = LinearLayout.HORIZONTAL;
    private final int spanCount;
    private int orientation;
    private int mDividerWidth;
    private int mDividerHeight;
    private Drawable mDivider;
    private Rect outBounds = new Rect();

    public GridItemDecoration(Context context, @DrawableRes int dividerResId, int orientation, int spanCount) {
        this.orientation = orientation;
        this.spanCount = spanCount;
        if (dividerResId != 0) {
            mDivider = context.getResources().getDrawable(dividerResId);
            mDividerWidth = mDivider.getIntrinsicWidth();
            mDividerHeight = mDivider.getIntrinsicHeight();
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (parent.getLayoutManager() == null || mDivider == null || spanCount <= 0) {
            return;
        }
        drawHorizontal(c, parent);
        drawVertical(c, parent);
    }

    private void drawHorizontal(Canvas c, RecyclerView parent) {
        c.save();
        int top;
        int bottom;
        if (parent.getClipToPadding()) {
            top = parent.getPaddingTop();
            bottom = parent.getHeight() - parent.getPaddingBottom();
            c.clipRect(parent.getPaddingLeft(), top, parent.getWidth() - parent.getPaddingRight(), bottom);
        }
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            parent.getDecoratedBoundsWithMargins(child, outBounds);
            int right = outBounds.right + Math.round(child.getTranslationX());
            int left = right - mDivider.getIntrinsicWidth();
            RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) child.getLayoutParams();
            top = child.getTop() - lp.topMargin - mDividerHeight;
            bottom = child.getBottom() + lp.bottomMargin + mDividerHeight;
            // 画右边分割线
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
            if (orientation == VERTICAL) {
                if (i % spanCount == 0) {
                    // 画左边分割线
                    left = child.getLeft() - lp.leftMargin - mDividerWidth;
                    right = child.getLeft() - lp.leftMargin;
                    mDivider.setBounds(left, top, right, bottom);
                    mDivider.draw(c);
                }
            } else {
                if (i < spanCount) {
                    // 画左边分割线
                    left = child.getLeft() - lp.leftMargin - mDividerWidth;
                    right = child.getLeft() - lp.leftMargin;
                    mDivider.setBounds(left, top, right, bottom);
                    mDivider.draw(c);
                }
            }
        }
        c.restore();
    }

    private void drawVertical(Canvas c, RecyclerView parent) {
        c.save();
        int left;
        int right;
        if (parent.getClipToPadding()) {
            left = parent.getPaddingLeft();
            right = parent.getWidth() - parent.getPaddingRight();
            c.clipRect(left, parent.getPaddingTop(), right, parent.getHeight() - parent.getPaddingBottom());
        }
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            // 获得child在适配器中的位置
            // 拿到child相对于父控件的边界，包括分割线跟margin
            parent.getDecoratedBoundsWithMargins(child, outBounds);
            RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) child.getLayoutParams();
            // 根据child单独设置边界
            left = child.getLeft() - lp.leftMargin - mDividerWidth;
            right = child.getRight() + lp.rightMargin + mDividerWidth;
            int bottom = outBounds.bottom + Math.round(child.getTranslationY());
            // child.getTranslationY() 在纵向的偏移量 = child.getY() - child.getTop()
            // 滑动并不会改变getTranslationY的值，都等于0，只有当发生平移时才会改变
            int top = bottom - mDividerHeight;
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
            if (orientation == VERTICAL) {
                if (i < spanCount) {
                    // 画上面的分割线
                    top = child.getTop() - lp.topMargin - mDividerHeight;
                    bottom = child.getTop() - lp.topMargin;
                    mDivider.setBounds(left, top, right, bottom);
                    mDivider.draw(c);
                }
            } else {
                if (i % spanCount == 0) {
                    // 画上面的分割线
                    top = child.getTop() - lp.topMargin - mDividerHeight;
                    bottom = child.getTop() - lp.topMargin;
                    mDivider.setBounds(left, top, right, bottom);
                    mDivider.draw(c);
                }
            }
        }
        c.restore();
    }

    @Override
    public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
        int left = 0;
        int top = 0;
        int right = mDividerWidth;
        int bottom = mDividerHeight;
        if (mDivider == null) {
            super.getItemOffsets(outRect, itemPosition, parent);
            return;
        }
        if (orientation == VERTICAL) {
            if (itemPosition % spanCount == 0) {
                // 第一列，需要画出左边分割线
                left = mDividerWidth;
            }
            if (itemPosition % spanCount == spanCount - 1) {
//                right = 0;
            }
            if (itemPosition < spanCount) {
                top = mDividerHeight;
            }
        } else {
            if (itemPosition % spanCount == 0) {
                // 第一行，上面需要画分割线
                // TODO 设置了这个，最后一行的显示出问题
//                top = mDividerHeight;
            }
            if (itemPosition % spanCount == spanCount - 1) {
//                bottom = 0;
            }
            if (itemPosition < spanCount) {
                left = mDividerWidth;
            }
        }
        Log.d("rong", "getItemOffsets: position = " + itemPosition + ", top = " + top);
        outRect.set(left, top, right, bottom);
    }
}
