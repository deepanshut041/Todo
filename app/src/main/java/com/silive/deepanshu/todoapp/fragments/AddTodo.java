package com.silive.deepanshu.todoapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.silive.deepanshu.todoapp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddTodo extends Fragment {


    public AddTodo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_todo, container, false);
    }

}
