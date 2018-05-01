package de.remmecke.android.habits;


import android.app.Application;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import de.remmecke.android.habits.data.Habit;
import de.remmecke.android.habits.data.HabitWithInfo;

/**
 * Created by Linse on 21.04.2018.
 */

public class HabitsAdapter extends RecyclerView.Adapter<HabitsAdapter.HabitViewHolder> {

    private HabitsAdapterOnClickHandler mClickHandler;

    public interface HabitsAdapterOnClickHandler{
        void onClick(HabitWithInfo clickedHabit);
    }

    HabitsAdapter(Context context, HabitsAdapterOnClickHandler clickHandler){
        mInflater = LayoutInflater.from(context);
        mClickHandler = clickHandler;
    }

    class HabitViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final  TextView habitIdView;
        private final TextView habitItemView;
        private final TextView timePassedView;

        public HabitViewHolder(View itemView) {
            super(itemView);
            this.habitItemView = itemView.findViewById(R.id.tv_habit_name);
            this.habitIdView = itemView.findViewById(R.id.tv_habit_info);
            this.timePassedView = itemView.findViewById(R.id.tv_time_passed);
            habitItemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            HabitWithInfo current = mHabits.get(adapterPosition);
            mClickHandler.onClick(current);
        }
    }

    private final LayoutInflater mInflater;
    private List<HabitWithInfo> mHabits;




    @Override
    public HabitViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = mInflater.inflate(R.layout.habit_list_item, parent, false );
        return new HabitViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HabitViewHolder holder, int position) {
        if(mHabits != null){
            HabitWithInfo current = mHabits.get(position);
            holder.habitItemView.setText(current.getName());
            Integer count = current.getCount();
            holder.habitIdView.setText(String.valueOf(count));
            holder.timePassedView.setText(current.getTimePassed());
        }else{
            holder.habitItemView.setText("No Habit");
        }
    }

    void setHabits(List<HabitWithInfo> habits){
        mHabits = habits;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mHabits!= null)
            return mHabits.size();
        return 0;
    }






}
