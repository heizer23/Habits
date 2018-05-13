package de.remmecke.android.habits;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import de.remmecke.android.habits.data.Habit;
import de.remmecke.android.habits.data.HabitRepository;

public class HabitInfoActivity  extends AppCompatActivity implements View.OnClickListener{

    EditText etName;
    Button btSave;
    Button btDelete;
    HabitRepository mRepository;
    Habit habit;
    GraphView graph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mod_habit);


        etName = findViewById(R.id.et_name);
        btSave = findViewById(R.id.button_save);
        btDelete = findViewById(R.id.button_delete);


        btSave.setOnClickListener(this);
        btDelete.setOnClickListener(this);
        mRepository   = new HabitRepository(this.getApplication());

        Bundle bundle = getIntent().getExtras();
        int habitId = bundle.getInt("habitId");

        habit = mRepository.getHabit(habitId);
        etName.setText(habit.getName());

        graph = findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph.addSeries(series);


    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.button_delete:
                Toast.makeText(this,"Delete",Toast.LENGTH_LONG).show();
                break;
            case R.id.button_save:
                updateHabit();
                finish();
                Toast.makeText(this,"Änderungen gespeichert",Toast.LENGTH_LONG).show();
                break;
        }

    }

    private void updateHabit(){
        habit.setName(etName.getText().toString());
        mRepository.updateHabit(habit);
    }

}