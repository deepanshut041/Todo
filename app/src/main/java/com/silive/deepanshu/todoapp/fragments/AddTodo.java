package com.silive.deepanshu.todoapp.fragments;


import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.CalendarContract;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.silive.deepanshu.todoapp.MainActivity;
import com.silive.deepanshu.todoapp.R;
import com.silive.deepanshu.todoapp.data.DbContract;
import com.silive.deepanshu.todoapp.data.DbHelper;
import com.silive.deepanshu.todoapp.firebase.FirebaseCrud;
import com.silive.deepanshu.todoapp.models.TodoModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddTodo extends DialogFragment {

    public final static String MODE_KEY = "key_mode";
    public final static String DATE_KEY = "key_date";
    public final static String MONTH_KEY = "key_month";
    public final static String SHOW_YEAR_KEY = "key_show_year";
    public final static String YEAR_KEY = "key_year";
    public final static String UID_KEY = "key_uid";
    public final static String NAME_KEY = "key_position";
    public final static String KEYWORD_KEY = "key_keyword";
    public final static String HOUR_KEY = "key_hour";
    public final static String MINUTE_KEY = "key_minute";
    private int ADD_OR_EDIT_MODE;
    public final static int MODE_ADD = 0;
    public final static int MODE_EDIT = 1;

    private Bundle bundle;


    private View view;


    private CheckBox checkYearToggle;

    private int todoKey;

    public AddTodo() {

    }

    public static AddTodo newInstance() {
        return new AddTodo();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Detect which state mode Dialog should be in
        bundle = this.getArguments();

        // Null check, then set mode reference
        if (bundle != null) {
            ADD_OR_EDIT_MODE = bundle.getInt(MODE_KEY, MODE_ADD);

        } else { // Fallback to default mode
            ADD_OR_EDIT_MODE = MODE_ADD;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(
                new ContextThemeWrapper(getActivity(), R.style.DialogFragmentTheme));
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.fragment_add_todo, null);

        // Detect which state mode Dialog should be in
        bundle = this.getArguments();

        // Null check, then set mode reference
        if (bundle != null) {
            ADD_OR_EDIT_MODE = bundle.getInt(MODE_KEY, MODE_ADD);

        } else { // Fallback to default mode
            ADD_OR_EDIT_MODE = MODE_ADD;
        }


        final DatePicker datePicker = (DatePicker) view.findViewById(R.id.datePicker);
        setUpDatePicker(datePicker);

        final TimePicker timePicker = (TimePicker) view.findViewById(R.id.timePicker);

        checkYearToggle = (CheckBox) view.findViewById(R.id.checkboxShowYear);
        checkYearToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setYearFieldVisibility(isChecked, datePicker);
            }
        });


        if (ADD_OR_EDIT_MODE == MODE_EDIT) {

            boolean showYear = bundle.getBoolean(SHOW_YEAR_KEY, true);
            checkYearToggle.setChecked(showYear);
            setYearFieldVisibility(showYear, datePicker);

            todoKey = bundle.getInt(UID_KEY);

            EditText editText = (EditText) view.findViewById(R.id.editTextName);
            EditText editText1 = (EditText) view.findViewById(R.id.editText);
            editText.setText(bundle.getString(NAME_KEY));
            editText1.setText(bundle.getString(KEYWORD_KEY));

            // Move cursor to end of text
            editText.setSelection(editText.getText().length());
            editText1.setSelection(editText1.getText().length());

            // Set DatePicker
            int spinnerYear =  bundle.getInt(YEAR_KEY);
            int spinnerMonth =  bundle.getInt(MONTH_KEY);
            int spinnerDate =  bundle.getInt(DATE_KEY);
            int spinnerHour = bundle.getInt(HOUR_KEY);
            int spinnerMinute = bundle.getInt(MINUTE_KEY);
            datePicker.updateDate(spinnerYear, spinnerMonth, spinnerDate);
            timePicker.setHour(spinnerHour);
            timePicker.setMinute(spinnerMinute);
        }

        // Set view, then add buttons and title
        builder.setView(view)
                // Add action buttons
                .setPositiveButton("Add", null)
                .setNegativeButton("Cancel", null)
                .setTitle(getDialogTitle());

        return builder.create();
    }

    private void setUpDatePicker(final DatePicker datePicker) {
        Calendar today = Calendar.getInstance();
        setYearFieldVisibility(false, datePicker);
        datePicker.init(2018, today.get(Calendar.MONTH), today.get(Calendar.DATE), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(datePicker.getWindowToken(), 0);
            }
        });
    }

    private void setYearFieldVisibility(boolean isChecked, DatePicker datePicker) {
        if (isChecked) {
            datePicker.findViewById(Resources.getSystem().getIdentifier("year", "id", "android")).setVisibility(View.VISIBLE);
        } else {
            datePicker.findViewById(Resources.getSystem().getIdentifier("year", "id", "android")).setVisibility(View.GONE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        setRetainInstance(true);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        if (getDialog() != null && getRetainInstance())
            getDialog().setDismissMessage(null);
        super.onDestroyView();
    }

    @Override
    public void onStart() {
        super.onStart();    //super.onStart() is where dialog.show() is actually called on the underlying dialog, so we have to do it after this point

        // References need to be final as they're used in inner class
        final AlertDialog dialog = (AlertDialog) getDialog();
        final EditText editText = (EditText) view.findViewById(R.id.editTextName);
        final EditText editText1 = (EditText) view.findViewById(R.id.editText);
        final DatePicker datePicker = (DatePicker) view.findViewById(R.id.datePicker);
        final TimePicker timePicker = (TimePicker) view.findViewById(R.id.timePicker);
        // Use my custom onFocusChange function.
        View.OnFocusChangeListener onFocusChangeListener = new MyFocusChangeListener();
        editText.setOnFocusChangeListener(onFocusChangeListener);

        // Set background depending on theme
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());

        // Null check
        if (dialog != null) {
            // This is it! The done button listener which we override onStart to use.
            final Button positiveButton = dialog.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {

                    // Only allow birthday to be saved and dialog dismissed if String isn't empty
                    if (editText.getText().toString().length() != 0) {

                        // Get date and month from datepicker
                        int dateOfMonth = datePicker.getDayOfMonth();
                        int month = datePicker.getMonth();
                        int year = datePicker.getYear();
                        int hour = timePicker.getHour();
                        int minute = timePicker.getMinute();
                        boolean includeYear = checkYearToggle.isChecked();

                        // Build date object which will be used by new Birthday
                        Calendar datetime = Calendar.getInstance();
                        datetime.set(Calendar.YEAR, year);
                        datetime.set(Calendar.MONTH, month);
                        datetime.set(Calendar.DAY_OF_MONTH, dateOfMonth);
                        datetime.set(Calendar.HOUR, hour);
                        datetime.set(Calendar.MINUTE, minute);
                        // Send the positive button event back to BirthdayListActivity
                        TodoModel todoModel = new TodoModel();
                        todoModel.setTitle(editText.getText().toString());
                        todoModel.setKeyword(editText1.getText().toString());
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                        String date = sdf.format(datetime.getTime());
                        Log.e("date ---------", datetime.getTime().toString());
                        Log.e("date ---------", date);
                        todoModel.setCreated_at(date);
                        todoModel.setNotification(true);

                        FirebaseCrud firebaseCrud = new FirebaseCrud(getActivity());
                        if (ADD_OR_EDIT_MODE == MODE_EDIT) {
                            todoModel.setId(todoKey);
                            databaseUpdate(todoModel);
                            firebaseCrud.addTodoListModel(todoModel);

                        } else if (ADD_OR_EDIT_MODE == MODE_ADD) {
                            Uri uri = databaseInsert(todoModel);
                            int id = Integer.valueOf(uri.getLastPathSegment());
                            todoModel.setId(id);
                            firebaseCrud.addTodoListModel(todoModel);
                        }
                        ((MainActivity) getActivity()).updateData();
                        // Finally close the dialog, and breath a sign of relief
                        dialog.dismiss();

                    } else {
                        Toast.makeText(getActivity(), "Enter Details", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private class MyFocusChangeListener implements View.OnFocusChangeListener {

        public void onFocusChange(View view, boolean hasFocus) {

            if ((view.getId() == R.id.editTextName || view.getId() == R.id.datePicker) && !hasFocus) {
                // Get input manager and hide keyboard
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0); // no flags

            }
        }
    }


    private String getDialogTitle() {
        switch (ADD_OR_EDIT_MODE) {
            case MODE_EDIT:
                return "Edit Event";
            default:
                return "Add Event";
        }
    }

    private Uri databaseInsert(TodoModel dataModel) {
        ContentValues values = new ContentValues();
            if(dataModel.getId() != 0){
                values.put(DbContract.ApiData._ID, dataModel.getId());
            }
            values.put(DbContract.ApiData.COLUMN_NAME, dataModel.getTitle());
            values.put(DbContract.ApiData.COLUMN_KEYWORD, dataModel.getKeyword());
            values.put(DbContract.ApiData.COLUMN_DATE, dataModel.getCreated_at().toString());
            values.put(DbContract.ApiData.COLUMN_NOTIFICATION, dataModel.getKeyword());
            Uri uri = getActivity().getContentResolver().insert(DbContract.ApiData.CONTENT_URI, values);
            return uri;
    }

    private  void databaseUpdate(TodoModel dataModel){
        ContentValues values = new ContentValues();
        values.put(DbContract.ApiData._ID, dataModel.getId());
        values.put(DbContract.ApiData.COLUMN_NAME, dataModel.getTitle());
        values.put(DbContract.ApiData.COLUMN_KEYWORD, dataModel.getKeyword());
        values.put(DbContract.ApiData.COLUMN_DATE, dataModel.getCreated_at().toString());
        values.put(DbContract.ApiData.COLUMN_NOTIFICATION, dataModel.getKeyword());
        int count = getActivity().getContentResolver().update(DbContract.ApiData.CONTENT_URI, values, DbContract.ApiData._ID+" = ?", new String[] {String.valueOf(dataModel.getId())});
    }

}
