package de.remmecke.android.habits;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import com.jjoe64.graphview.series.DataPoint;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import de.remmecke.android.habits.data.Habit;
import de.remmecke.android.habits.data.HabitRepository;
import de.remmecke.android.habits.data.HabitWithInfo;
import de.remmecke.android.habits.data.Occurrence;

public class HabitInfoViewModel extends AndroidViewModel {


    private HabitRepository mRepository;
    private HabitWithInfo habitWithInfo;
    private List<Occurrence> occurrences;
    private String frequency = "month";

    public HabitInfoViewModel(@NonNull Application application, int habitId) {
        super(application);
        mRepository = new HabitRepository(application);
        habitWithInfo = mRepository.getHabitWithInfo(habitId);
        occurrences = mRepository.getOccurences(habitId);
    }

    public String getHabitName(){
        return habitWithInfo.getName();
    }

    public String getHabitCounterString(){
        return habitWithInfo.getCount().toString();
    }

    public String getHabitLasttimeString(){
        Date lastTime = habitWithInfo.getLastTime();
        DateFormat df = DateFormat.getDateTimeInstance();

        return df.format(lastTime);
    }

    public void updateHabit(String newName){
        Habit habit = mRepository.getHabit(habitWithInfo.getHabitId());
        habit.setName(newName);
        mRepository.updateHabit(habit);
    }

    public DataPoint[] getGraphDatapoints(){
        Long[][] sortArray = new Long[occurrences.size()][2];
        Date fullDate;
        Long timeOfOcc;
        int dayOfOcc;
        DataPoint[] dataPoints = new DataPoint[occurrences.size()];


        Calendar c = Calendar.getInstance();

        for (int j = 0; j < occurrences.size(); j++) {
            fullDate = occurrences.get(j).timeStamp;
            sortArray[j] = getDataTupel(c,fullDate);
        }

        Arrays.sort(sortArray, arrayComparator);

        for(int j = 0; j < sortArray.length;j++){
            System.out.println ("j " + j+ " x " + sortArray[j][0]+ " y " + sortArray[j][1] );
            dataPoints[j] = new DataPoint(sortArray[j][0],sortArray[j][1]);
        }

        return dataPoints;
    }


    private Long[] getDataTupel(Calendar c, Date fullDate){
        Long[] dataTupel = new Long[2];
        Long xValue = null;
        Long yValue = null;

        c.setTime(fullDate);

        switch (frequency){
            case "day":
                yValue = Long.valueOf(c.get(Calendar.DAY_OF_WEEK));
                c.set(Calendar.YEAR, 2018);
                c.set(Calendar.MONTH, 0);
                c.set(Calendar.DAY_OF_MONTH, 1);
                xValue = c.getTime().getTime();
                break;
            case "month":
                yValue = Long.valueOf(c.get(Calendar.MONTH) +1);
                c.set(Calendar.YEAR, 2018);
                c.set(Calendar.MONTH, 1);
                xValue = c.getTime().getTime();
                break;
        }

        dataTupel[0] = xValue;
        dataTupel[1] = yValue;
        return dataTupel;
    }

    public SimpleDateFormat getDateFormat(){
        String dateFormat = null;
        switch (frequency){
            case "day":
                dateFormat = "HH:mm:ss";
                break;
            case "month":
                dateFormat = "dd";
                break;
        }
        return new SimpleDateFormat(dateFormat);
    }

    public void toggleFrequency(){
        if(frequency.equals("day")){
            frequency = "month";
        }else{
            frequency = "day";
        }
    }

    final Comparator<Long[]> arrayComparator = new Comparator<Long[]>() {
        @Override
        public int compare(Long[] o1, Long[] o2) {
            return o1[0].compareTo(o2[0]);
        }
    };

}
