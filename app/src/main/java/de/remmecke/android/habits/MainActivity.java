package de.remmecke.android.habits;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import de.remmecke.android.habits.data.Habit;
import de.remmecke.android.habits.data.HabitViewModel;
import de.remmecke.android.habits.data.HabitWithInfo;

public class MainActivity extends AppCompatActivity implements HabitsAdapter.HabitsAdapterOnClickHandler {

    public static final int NEW_HABIT_ACTIVITY_REQUEST_CODE = 1;

    private HabitViewModel mHabitViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerview_tasks);
        final HabitsAdapter adapter = new HabitsAdapter(this, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mHabitViewModel = ViewModelProviders.of(this).get(HabitViewModel.class);

        mHabitViewModel.getmAllHabits().observe(this, new Observer<List<HabitWithInfo>>() {
            @Override
            public void onChanged(@Nullable List<HabitWithInfo> habits) {
                adapter.setHabits(habits);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        mHabitViewModel.setOccurenceNr();
        String occNr = String.valueOf(mHabitViewModel.occurenceNr.get(0).habitId);

        Toast.makeText(this,occNr,Toast.LENGTH_LONG).show();


        return super.onOptionsItemSelected(item);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

//        if (requestCode == NEW_HABIT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
//            Habit habit = new Habit(data.getStringExtra(NewHabitActivity.EXTRA_REPLY));
//            mHabitViewModel.insert(habit);
//        }else{
//            Toast.makeText(getApplicationContext(),
//                    R.string.empty_not_saved,
//                    Toast.LENGTH_LONG).show();
//        }
    }

    @Override
    public void onClick(HabitWithInfo clickedHabit) {
        mHabitViewModel.insertOccurrence(clickedHabit);
        Toast.makeText(this,clickedHabit.getName(),Toast.LENGTH_LONG).show();
    }
}
