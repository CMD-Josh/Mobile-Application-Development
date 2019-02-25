package uk.ac.solent.myapplication;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Clock clock = new Clock();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button BtnConv = findViewById(R.id.btn1);

        BtnConv.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        EditText txthours = findViewById(R.id.et1);
        EditText txtminuets = findViewById(R.id.et2);
        EditText txtseconds = findViewById(R.id.et3);
        TextView time = findViewById(R.id.tv1);

        try {
            clock.setTime(Integer.parseInt(txthours.getText().toString()),
                    Integer.parseInt(txtminuets.getText().toString()),
                    Integer.parseInt(txtseconds.getText().toString()));

            time.setText(clock.toString());

        }catch(TimeException e){
            new AlertDialog.Builder(this).setPositiveButton("OK", null).setMessage(e.message).show();
        }catch(NumberFormatException e){
            new AlertDialog.Builder(this).setPositiveButton("OK", null).setMessage("Input numbers please").show();
        }
    }
}
