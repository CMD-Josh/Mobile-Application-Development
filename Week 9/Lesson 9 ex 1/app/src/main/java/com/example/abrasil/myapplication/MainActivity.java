package com.example.abrasil.myapplication;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = findViewById(R.id.btn1);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        EditText et = findViewById(R.id.et1);
        System.out.println(et.getText().toString());

        ExerciseAsyncTask task = new ExerciseAsyncTask();
        task.execute(et.getText().toString());
    }

    /* -----------------------------------------------------------------------------------------------------------------------------

            Inner class defining an AsyncTask (If defiend as its own seperate class than it wouldn't need to be parsed
            this class as a context for its constructor. also it looks cool.)

       -----------------------------------------------------------------------------------------------------------------------------*/

    // Creation of inner class using the Async Task
    class ExerciseAsyncTask extends AsyncTask<String, Void, String>{ // Generics takes 3 types to specify input types of 3 methods. <doInBackground, onPreExecute, onPostExecute>

        @Override
        public String doInBackground(String... artists) {
            HttpURLConnection connection = null;
            String result = "";
            try {                                                                       // Surrounded in a try/catch block (incase user looses connection to the internet or another error occurs
                URL url = new URL("http://www.free-map.org.uk/course/ws/hits.php?artist=" + artists[0]);
                connection = (HttpURLConnection)url.openConnection();                   // Create a new connection to the URL specified in the line above
                InputStream in = connection.getInputStream();                           // Get data from the connection from an InputStream

                if(connection.getResponseCode() == 200){                                // Check if the response from the web server is "OK" which we get as a 200
                    BufferedReader b = new BufferedReader(new InputStreamReader(in));   // Proceed to decode the binary data from the input stream into readable ascii via Buffered Reader
                    String line;
                    while((line = b.readLine()) != null){                               // Each new line from the stream is set to the 'line' variable and keep going until we've run out of lines
                        result += line;                                                 // Append the new line that was decoded from the InputStream into the results variable which we can return from this function
                    }
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally{
                if(connection != null){
                    connection.disconnect();
                }
            }

            return result;
        }

        @Override
        public void onPostExecute(String result){ // Method takes in the returned values of "doInBackground"
            TextView tv = findViewById(R.id.tv1);
            tv.setText(result);
        }
    }
}
