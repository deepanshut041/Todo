package com.silive.deepanshu.todoapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.silive.deepanshu.todoapp.EventActivity;
import com.silive.deepanshu.todoapp.R;
import com.silive.deepanshu.todoapp.models.TodoModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by deepanshu on 11/4/18.
 */
public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.todoListViewHolder> {
    public ArrayList<TodoModel> moreListArrayList;
    public Context context;
    public String type;

    public static class todoListViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView keywordTextView;
        TextView dateTextView;
        TextView monthTextView;
        TextView daysLeft;
        TextView click;

        public todoListViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.name);
            dateTextView = (TextView) itemView.findViewById(R.id.dateDay);
            monthTextView = (TextView) itemView.findViewById(R.id.dateMonth);
            daysLeft = (TextView) itemView.findViewById(R.id.days_remaining);
            click = (TextView) itemView.findViewById(R.id.textViewAge);
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
        holder.nameTextView.setText(todoModel.getTitle());
        Calendar date = todoModel.getCreated_at();
        holder.dateTextView.setText(5+"");
        holder.monthTextView.setText("Apr");
//        holder.daysLeft.setText("10");
        holder.click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EventActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return moreListArrayList.size();
    }
}