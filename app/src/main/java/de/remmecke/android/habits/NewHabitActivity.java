package de.remmecke.android.habits;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import de.remmecke.android.habits.data.Habit;
import de.remmecke.android.habits.data.HabitRepository;
import de.remmecke.android.habits.data.HabitViewModel;
import de.remmecke.android.habits.data.HabitWithInfo;

public class NewHabitActivity  extends AppCompatActivity implements View.OnClickListener{

    EditText etName;
    Button btSave;
    HabitRepository mRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_habit);


        etName = findViewById(R.id.et_name);
        btSave = findViewById(R.id.button_save);

        btSave.setOnClickListener(this);
        mRepository   = new HabitRepository(this.getApplication());
    }


    @Override
    public void onClick(View v) {
        String habitname = etName.getText().toString();
        Habit habit = new Habit(habitname);

        mRepository.insertHabit(habit);
        String toast = "Habit " + habitname + " wurde gespeichert";
        Toast.makeText(this,toast,Toast.LENGTH_LONG).show();
        finish();
    }
}
