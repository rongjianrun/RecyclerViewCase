package com.rjr.recyclerviewcase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.rjr.recyclerviewcase.adapter.RecyclerAdapter;
import com.rjr.recyclerviewcase.decoration.GridItemDecoration;
import com.rjr.recyclerviewcase.decoration.LinearItemDecoration;
import com.rjr.recyclerviewcase.decoration.StaggeredGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<String> list;
    private RecyclerAdapter adapter;
    private Menu menu;
    private int lastIndex;
    private RecyclerView.ItemDecoration[] itemDecorations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recycler_view);

        initData();
        initItemDecoration();
        adapter = new RecyclerAdapter(this, list, mRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(itemDecorations[0]);
        mRecyclerView.setAdapter(adapter);

    }

    private void initData() {
        list = new ArrayList<>();
        for (int i = 0; i < 41; i++) {
            list.add("hello_" + i);
        }
    }

    private void initItemDecoration() {
        itemDecorations = new RecyclerView.ItemDecoration[]{
                new LinearItemDecoration(this, LinearItemDecoration.VERTICAL, R.drawable.divider),
                new LinearItemDecoration(this, LinearItemDecoration.HORIZONTAL, R.drawable.divider),
                new GridItemDecoration(this, R.drawable.divider, GridItemDecoration.HORIZONTAL,3),
                new StaggeredGridItemDecoration()
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.isChecked()) {
            return true;
        }
        menu.getItem(lastIndex).setChecked(false);
        mRecyclerView.removeItemDecoration(itemDecorations[lastIndex]);
        switch (item.getItemId()) {
            case R.id.menu_vertical:
                lastIndex = 0;
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                break;
            case R.id.menu_horizontal:
                lastIndex = 1;
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
                break;
            case R.id.menu_grid:
                lastIndex = 2;
                mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3, GridLayoutManager.HORIZONTAL, false));
                break;
            case R.id.menu_staggered_grid:
                lastIndex = 3;
                mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.HORIZONTAL));
                break;
        }
        mRecyclerView.addItemDecoration(itemDecorations[lastIndex]);
        item.setChecked(true);
        return true;
    }
}
