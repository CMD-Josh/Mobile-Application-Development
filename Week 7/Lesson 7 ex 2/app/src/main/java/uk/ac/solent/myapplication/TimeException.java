package uk.ac.solent.myapplication;

public class TimeException extends Exception {
    String message;

    public TimeException(String m){
        message = m;
    }

    public String toString(){
        return message;
    }
}
