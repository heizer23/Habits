package de.remmecke.android.habits;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import de.remmecke.android.habits.data.Occurrence;
import de.remmecke.android.habits.data.HabitRepository;

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
        Occurrence occ = new Occurrence(habitname);
        mRepository.insertOccurrence(occ);
        String toast = "Occurrence " + habitname + " wurde gespeichert";
        Toast.makeText(this,toast,Toast.LENGTH_LONG).show();
        finish();
    }
}
