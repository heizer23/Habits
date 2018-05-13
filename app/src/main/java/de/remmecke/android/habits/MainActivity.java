package de.remmecke.android.habits;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

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
        Intent intent;
        switch (item.getItemId()){

            case R.id.action_new_habit:
                intent = new Intent(MainActivity.this, NewHabitActivity.class);
                startActivity(intent);
                break;
            case R.id.action_refresh:

                break;
            default:
                Toast.makeText(this,"Default",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(HabitWithInfo clickedHabit, String action) {
        switch (action){
            case "addOccurrence":
                mHabitViewModel.insertOccurrence(clickedHabit);
                break;
            case "editHabit":
                int habitId = clickedHabit.getHabitId();
                Intent intent = new Intent(MainActivity.this, HabitInfoActivity.class);
                intent.putExtra("habitId", habitId);
                startActivity(intent);
                break;
        }
    }
}
