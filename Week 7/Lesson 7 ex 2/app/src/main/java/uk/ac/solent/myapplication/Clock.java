package uk.ac.solent.myapplication;

public class Clock {
    private int hours;
    private int minuets;
    private int seconds;

    public Clock(){
        hours = 0;
        minuets = 0;
        seconds = 0;
    }

    public String toString(){
        return hours + ":" + minuets + ":" + seconds;
    }

    public void setTime(int hoursIn, int minuetsIn, int secondsIn)throws TimeException{
        if(hoursIn > 0 && hoursIn < 24 &&
        minuetsIn > 0 && minuetsIn < 60 &&
        secondsIn > 0 && secondsIn < 60){
            hours = hoursIn;
            minuets = minuetsIn;
            seconds = secondsIn;
        }else{
            throw new TimeException("Parameters must be a valid time");
        }
    }

}
