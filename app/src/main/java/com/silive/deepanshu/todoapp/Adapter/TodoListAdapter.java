package com.silive.deepanshu.todoapp.Adapter;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.silive.deepanshu.todoapp.EventActivity;
import com.silive.deepanshu.todoapp.MainActivity;
import com.silive.deepanshu.todoapp.R;
import com.silive.deepanshu.todoapp.fragments.AddTodo;
import com.silive.deepanshu.todoapp.models.TodoModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by deepanshu on 11/4/18.
 */
public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.todoListViewHolder> {
    public ArrayList<TodoModel> moreListArrayList;
    public Context context;
    public String type;
    private AddTodo addEditFragment;
    public String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};


    public static class todoListViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView keywordTextView;
        TextView dateTextView;
        TextView monthTextView;
        TextView daysLeft;
        ImageView gift;
        ImageView edit;

        public todoListViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.name);
            dateTextView = (TextView) itemView.findViewById(R.id.dateDay);
            monthTextView = (TextView) itemView.findViewById(R.id.dateMonth);
            daysLeft = (TextView) itemView.findViewById(R.id.days_remaining);
            edit = (ImageView) itemView.findViewById(R.id.edit);
            gift = (ImageView) itemView.findViewById(R.id.gift);
            keywordTextView = (TextView) itemView.findViewById(R.id.keyword);
        }
    }

    public TodoListAdapter(ArrayList<TodoModel> arrayList, Context context) {
        this.moreListArrayList = arrayList;
        this.context = context;
    }

    @Override
    public todoListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_list_item, parent, false);
        return new todoListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final todoListViewHolder holder, final int position) {
        final TodoModel todoModel = moreListArrayList.get(position);
        final Calendar datetime = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        try {
            datetime.setTime(sdf.parse(todoModel.getCreated_at()));
            holder.monthTextView.setText(months[datetime.get(Calendar.MONTH)]);
            holder.dateTextView.setText(datetime.get(Calendar.DATE) + "");

        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.nameTextView.setText(todoModel.getTitle());
        holder.keywordTextView.setText(todoModel.getKeyword());
        holder.gift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EventActivity.class);
                context.startActivity(intent);
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an instance of the dialog fragment and show it
                addEditFragment = AddTodo.newInstance();

                // Create bundle for storing mode information
                Bundle bundle = new Bundle();
                // Pass mode parameter onto Fragment
                bundle.putInt(AddTodo.MODE_KEY, AddTodo.MODE_EDIT);
                bundle.putInt(AddTodo.YEAR_KEY, datetime.get(Calendar.YEAR));
                bundle.putInt(AddTodo.MONTH_KEY, datetime.get(Calendar.MONTH));
                bundle.putInt(AddTodo.DATE_KEY, datetime.get(Calendar.DATE));
                bundle.putInt(AddTodo.HOUR_KEY, datetime.get(Calendar.HOUR));
                bundle.putInt(AddTodo.MINUTE_KEY, datetime.get(Calendar.MINUTE));
                bundle.putString(AddTodo.NAME_KEY, todoModel.getTitle());
                bundle.putString(AddTodo.KEYWORD_KEY, todoModel.getKeyword());
                addEditFragment.setArguments(bundle);
                addEditFragment.show(((MainActivity)context).getSupportFragmentManager(), "AddEditBirthdayFragment");
            }
        });
    }

    @Override
    public int getItemCount() {
        return moreListArrayList.size();
    }
}