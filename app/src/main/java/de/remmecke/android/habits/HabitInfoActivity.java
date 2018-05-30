package de.remmecke.android.habits;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Observer;

import de.remmecke.android.habits.data.Habit;
import de.remmecke.android.habits.data.HabitRepository;
import de.remmecke.android.habits.data.HabitWithInfo;
import de.remmecke.android.habits.data.Occurrence;

public class HabitInfoActivity  extends AppCompatActivity implements View.OnClickListener{

    private EditText etName;
    private Button btSave;
    private Button btDelete;
    private TextView tvLasttime;
    private TextView tvCounter;
    private GraphView graph;

    private HabitInfoViewModel mViewModel;
    private ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_info);

        etName = findViewById(R.id.et_name);
        tvCounter = findViewById(R.id.tv_counter);
        tvLasttime = findViewById(R.id.tv_lasttime);
        graph = findViewById(R.id.graph);
        btSave = findViewById(R.id.button_save);
        btDelete = findViewById(R.id.button_delete);
        constraintLayout = findViewById(R.id.contraintlayout);

        btSave.setOnClickListener(this);
        btDelete.setOnClickListener(this);


        Bundle bundle = getIntent().getExtras();
        int habitId = bundle.getInt("habitId");

        mViewModel = ViewModelProviders.of(this, new ViewModelFactory(this.getApplication(), habitId)).get(HabitInfoViewModel.class);

        setUpGui();
        makeDailyGraph();


//        ConstraintSet set = new ConstraintSet();
//
//        TextView view = new TextView(this);
//        view.setId(View.generateViewId());
//        view.setText("hallllllojhbjhbjhbjhb");
//        constraintLayout.addView(view,0);
//        set.clone(constraintLayout);
//        set.connect(view.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP, 60);
//        set.applyTo(constraintLayout);

    }

    private void setUpGui(){
        etName.setText(mViewModel.getHabitName());
        tvCounter.setText(mViewModel.getHabitCounterString());
        tvLasttime.setText(mViewModel.getHabitLasttimeString());
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.button_delete:
                mViewModel.toggleFrequency();
                makeDailyGraph();
                break;
            case R.id.button_save:
                mViewModel.updateHabit(etName.getText().toString());
                finish();
                Toast.makeText(this,"Änderungen gespeichert",Toast.LENGTH_LONG).show();
                break;
        }

    }



    private void makeDailyGraph(){
        DataPoint[] dataPoints = mViewModel.getGraphDatapoints();
        graph.removeAllSeries();
        PointsGraphSeries<DataPoint> series = new PointsGraphSeries<>(dataPoints);
        graph.addSeries(series);

        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this, mViewModel.getDateFormat()));
        graph.getGridLabelRenderer().setNumHorizontalLabels(3); // only 4 because of the space
        graph.getGridLabelRenderer().setNumVerticalLabels(3);

      //   set manual x bounds to have nice steps
       // graph.getViewport().setMinY(1);
       // graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinX(dataPoints[0].getX());
        graph.getViewport().setMaxX(dataPoints[dataPoints.length-1].getX());
        graph.getViewport().setXAxisBoundsManual(true);

        // as we use dates as labels, the human rounding to nice readable numbers
        // is not necessary
        graph.getGridLabelRenderer().setHumanRounding(false);
       // graph.getViewport().setScrollable(true);
      //  graph.getViewport().setScalable(true);
    }

}