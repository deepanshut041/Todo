package com.silive.deepanshu.todoapp;

import android.database.Cursor;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.silive.deepanshu.todoapp.Adapter.TodoListAdapter;
import com.silive.deepanshu.todoapp.data.DbContract;
import com.silive.deepanshu.todoapp.fragments.AddTodo;
import com.silive.deepanshu.todoapp.models.TodoModel;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerViewMoreList;
    private FloatingActionButton floatingActionButton;
    private AddTodo addEditFragment;
    private TodoListAdapter todoListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Open New Birthday Fragment
                showAddToDoFragment();
            }
        });

        recyclerViewMoreList = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManagerMovieList = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewMoreList.setLayoutManager(layoutManagerMovieList);
        recyclerViewMoreList.setItemAnimator(new DefaultItemAnimator());
        GetFilterData getFilterData = new GetFilterData();
        getFilterData.execute();
    }


    public void showAddToDoFragment() {
        // Create an instance of the dialog fragment and show it
        addEditFragment = AddTodo.newInstance();

        // Create bundle for storing mode information
        Bundle bundle = new Bundle();
        // Pass mode parameter onto Fragment
        bundle.putInt(AddTodo.MODE_KEY, AddTodo.MODE_ADD);
        addEditFragment.setArguments(bundle);
        // Pass bundle to Dialog, get FragmentManager and show
        addEditFragment.show(getSupportFragmentManager(), "AddEditBirthdayFragment");
    }

    public class GetFilterData extends AsyncTask<Void,Void,Cursor> {

        @Override
        protected Cursor doInBackground(Void... params) {
            String WHERE = null;
            String args[] = null;
            return getContentResolver().query(DbContract.ApiData.CONTENT_URI,
                    null,
                    WHERE,
                    args,
                    null);
        }
        protected void onPostExecute(Cursor cursor) {
            ArrayList<TodoModel> todoModels = new ArrayList<>();
            while (cursor.moveToNext()){
                TodoModel listDataModel = new TodoModel(cursor.getInt(cursor.getColumnIndex(DbContract.ApiData._ID)),cursor.getString(cursor.getColumnIndex(DbContract.ApiData.COLUMN_NAME))
                        ,cursor.getString(cursor.getColumnIndex(DbContract.ApiData.COLUMN_KEYWORD)),
                        Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(DbContract.ApiData.COLUMN_NOTIFICATION))),
                        cursor.getString(cursor.getColumnIndex(DbContract.ApiData.COLUMN_DATE)));
                todoModels.add(listDataModel);
            }

//            cursor.close();
            todoListAdapter = new TodoListAdapter(todoModels, MainActivity.this);
            recyclerViewMoreList.setAdapter(todoListAdapter);
        }
    }
}
