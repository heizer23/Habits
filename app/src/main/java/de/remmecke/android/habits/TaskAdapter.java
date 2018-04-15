package de.remmecke.android.habits;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Linse on 15.04.2018.
 *
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskAdapterViewHolder> {

    private String[] mTaskData;

    public TaskAdapter(){

    }

    public class TaskAdapterViewHolder extends RecyclerView.ViewHolder{
        public final TextView mTaskNameTextView;

        public TaskAdapterViewHolder(View view) {
            super(view);
            mTaskNameTextView = (TextView) view.findViewById(R.id.tv_task_name);
        }


    }

    @NonNull
    @Override
    public TaskAdapter.TaskAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.task_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediatly = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediatly);
        return new TaskAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.TaskAdapterViewHolder taskAdapterViewHolder, int position) {
        String taskName = mTaskData[position];
        taskAdapterViewHolder.mTaskNameTextView.setText(taskName);
    }

    @Override
    public int getItemCount() {
        if(null == mTaskData) return 0;
        return mTaskData.length;
    }

    public void setTaskData(String[] taskData){
        mTaskData = taskData;
        notifyDataSetChanged();
    }


}
