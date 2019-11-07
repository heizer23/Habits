package de.remmecke.android.habits;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jjoe64.graphview.series.DataPoint;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TestActivity extends AppCompatActivity implements View.OnClickListener{

    //HabitInfoViewModel mViewModel;
    TextView tv1;
    TextView tv2;
    Button button;
    Date timeStamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


        tv1 = findViewById(R.id.textView1);
        tv2 = findViewById(R.id.textView2);
        button = findViewById(R.id.button1);
        button.setOnClickListener(this);

    //    mViewModel = ViewModelProviders.of(this, new ViewModelFactory(this.getApplication(), 1)).get(HabitInfoViewModel.class);

      //  timeStamp = mViewModel.getFirstOcc();
        setUp();
    }

    private void setUp(){
        Long fullDate;
        Long timeOfOcc;
        Long dayOfOcc;


        SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm:ss");

        Date jetzt = new Date();

        String time = localDateFormat.format(jetzt);
        fullDate = timeStamp.getTime();
        timeOfOcc = fullDate % (1000*60*60*24);
        dayOfOcc = (long)fullDate / (1000*60*60*24);
        tv1.setText("TimeStamp " + fullDate);
        tv2.setText("time " + time);

    }

    @Override
    public void onClick(View v) {

        Long fullDate;
        Long timeOfOcc;
        Long dayOfOcc;

        fullDate = timeStamp.getTime();
        Calendar c = Calendar.getInstance();
        c.setTime(timeStamp);
        c.set(Calendar.YEAR, 2018);
        c.set(Calendar.MONTH, 1);
        c.set(Calendar.DAY_OF_WEEK, 1);

        Date temp = c.getTime();

        timeOfOcc = fullDate % (1000*60*60*24);
        dayOfOcc = (long)fullDate / (1000*60*60*24);
        tv1.setText("TimeStamp " + temp);
        tv2.setText("Processed " + temp.getTime());
    }
}
