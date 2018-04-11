package com.silive.deepanshu.todoapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.silive.deepanshu.todoapp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddTodo extends Fragment {

    private EditText name, keyword;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private CheckBox notification;
    private Button button;

    public AddTodo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_add_todo, container, false);
        name = (EditText) rootView.findViewById(R.id.editTextName);
        keyword = (EditText) rootView.findViewById(R.id.editText);
        datePicker = (DatePicker) rootView.findViewById(R.id.datePicker);
        timePicker = (TimePicker) rootView.findViewById(R.id.timePicker);
        notification = (CheckBox) rootView.findViewById(R.id.checkboxShowYear);
        button = (Button) rootView.findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });

        return rootView;
    }

}
