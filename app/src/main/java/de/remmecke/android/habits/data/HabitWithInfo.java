package de.remmecke.android.habits.data;

import android.arch.persistence.room.TypeConverters;

import java.util.Date;

@TypeConverters(DateConverter.class)
public class HabitWithInfo {

    private Integer habitId;
    private String name;
    private Integer count;
    private Date lastTime;
    private String timePassed;

    public HabitWithInfo(Integer habitId, String name, Integer count, Date lastTime){
        this.habitId = habitId;
        this.name = name;
        this.count = count;
        this.lastTime = lastTime;
        setTimePassed();
    };

    public void setTimePassed(String timePassed) {
        this.timePassed = timePassed;
    }

    public String getName(){
        return name;
    }

    public Integer getHabitId() {
        return habitId;
    }

    public Integer getCount(){return count;}

    public String getTimePassed() {
        return timePassed;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setTimePassed() {
        String result = "";
        if (lastTime != null){
            //milliseconds
            Date now = new Date();
            long different = now.getTime() - lastTime.getTime();

            long secondsInMilli = 1000;
            long minutesInMilli = secondsInMilli * 60;
            long hoursInMilli = minutesInMilli * 60;
            long daysInMilli = hoursInMilli * 24;

            long elapsedDays = different / daysInMilli;
            different = different % daysInMilli;
            long elapsedHours = different / hoursInMilli;
            different = different % hoursInMilli;
            long elapsedMinutes = different / minutesInMilli;
            different = different % minutesInMilli;
            long elapsedSeconds = different / secondsInMilli;
            if (elapsedDays > 0) {
                result = String.format("%d d", elapsedDays);
            }else if (elapsedHours > 0) {
                result = String.format("%d h", elapsedHours);
            }else if (elapsedMinutes > 0) {
                result = String.format("%d m", elapsedMinutes);
            }else if (elapsedSeconds > 0) {
                result = String.format("%d s", elapsedSeconds);
            }
        }
        timePassed = result;
    }

}
