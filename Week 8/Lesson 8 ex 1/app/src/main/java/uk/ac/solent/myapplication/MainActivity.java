package uk.ac.solent.myapplication;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class MainActivity extends AppCompatActivity {

    String fileName = "textedit.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onResume(){
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        fileName = prefs.getString("fileName","textedit.txt");

    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        String directory = Environment.getExternalStorageDirectory().getAbsolutePath();
        EditText et1 = findViewById(R.id.et1);

        if(item.getItemId() == R.id.Save){
            // Code to write whatever is in the Edit text to a file in the absolute path (Which is where the program is running (which also happens to be /storage/sdcard))
            try{
                PrintWriter pw = new PrintWriter(new FileWriter(directory + "/" + fileName));
                pw.print(et1.getText().toString());

                pw.close();
            }catch(IOException e){
                System.out.println(e);
            }

            return true;
        }else if(item.getItemId() == R.id.Load){
            String s1 = "";
            try{
                et1.setText("");
                BufferedReader reader = new BufferedReader(new FileReader(directory + "/" + fileName));
                String line = "";
                while((line = reader.readLine()) != null){
                    s1=s1+line + "\n";
                    System.out.println(line);
                }
            }catch(IOException e){
                new AlertDialog.Builder(this).setPositiveButton("OK", null).setMessage("ERROR: " + e).show();
            }
            s1 = s1.substring(0,s1.length() - 1);
            et1.setText(s1);

            return true;
        }else if(item.getItemId() == R.id.Prefs){
            Intent intent = new Intent(this,PreferenceActivity.class);
            startActivity(intent);
        }
        return false;
    }
}
