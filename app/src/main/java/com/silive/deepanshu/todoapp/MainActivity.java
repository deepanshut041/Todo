package com.silive.deepanshu.todoapp;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.silive.deepanshu.todoapp.fragments.AddTodo;
import com.silive.deepanshu.todoapp.models.TodoModel;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton floatingActionButton;
    private AddTodo addEditFragment;

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
}
