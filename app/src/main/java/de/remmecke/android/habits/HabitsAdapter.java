package de.remmecke.android.habits;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import de.remmecke.android.habits.data.Occurrence;


public class HabitsAdapter extends RecyclerView.Adapter<HabitsAdapter.HabitViewHolder> {

    private HabitsAdapterOnClickHandler mClickHandler;

    public interface HabitsAdapterOnClickHandler{
        void onClick(Occurrence clickedOcc, String action);
    }

    HabitsAdapter(Context context, HabitsAdapterOnClickHandler clickHandler){
        mInflater = LayoutInflater.from(context);
        mClickHandler = clickHandler;
    }

    class HabitViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final  TextView habitIdView;
        private final TextView habitItemView;
        private final TextView timePassedView;
        private final ImageView butt_add;
        private final LinearLayout itemContainer;

        public HabitViewHolder(View itemView) {
            super(itemView);
            this.habitItemView = itemView.findViewById(R.id.tv_habit_name);
            this.habitIdView = itemView.findViewById(R.id.tv_habit_info);
            this.timePassedView = itemView.findViewById(R.id.tv_time_passed);
            this.butt_add = itemView.findViewById(R.id.button_add);
            this.itemContainer = itemView.findViewById(R.id.itemcontainer);
            itemContainer.setOnClickListener(this);
            butt_add.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            String action = null;

            switch (v.getId()){
                case R.id.button_add:
                    action = "addOccurrence";
                    break;
                case R.id.itemcontainer:
                    action = "editHabit";
                    break;
            }

            int adapterPosition = getAdapterPosition();
            Occurrence current = mOccurrences.get(adapterPosition);
            mClickHandler.onClick(current, action);
        }
    }

    private final LayoutInflater mInflater;
    private List<Occurrence> mOccurrences;




    @Override
    public HabitViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = mInflater.inflate(R.layout.habit_list_item, parent, false );
        return new HabitViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HabitViewHolder holder, int position) {
        if(mOccurrences != null){
            Occurrence current = mOccurrences.get(position);
            holder.habitItemView.setText(current.getName());
            Integer count = 2424;
            holder.habitIdView.setText(String.valueOf(current.getTargetTime()));
            holder.timePassedView.setText(String.valueOf(current.getTargetTime()));
        }else{
            holder.habitItemView.setText("No Occurrence");
        }
    }

    void setHabits(List<Occurrence> occs){
        mOccurrences = occs;
        notifyDataSetChanged();
    }

    public void updateListView(){
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mOccurrences != null)
            return mOccurrences.size();
        return 0;
    }






}
