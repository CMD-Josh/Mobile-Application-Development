package uk.ac.solent.lesson2;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.TextView;
import android.widget.EditText;


public class FeetToMetersActivity extends AppCompatActivity implements OnClickListener {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feet_to_meters);
        Button b = (Button)findViewById(R.id.btn1);
        b.setOnClickListener(this);
    }

    public void onClick(View view)
    {
        TextView tv = (TextView)findViewById(R.id.tv1);
        EditText et = (EditText)findViewById(R.id.et1);
        try{ // Try catch statement to see if the text entered into ET is a number or string
            double feet = Double.parseDouble(et.getText().toString());
            double metres = feet*0.305;
            tv.setText("In metres that is: " + metres);
        }catch(NumberFormatException e){
            new AlertDialog.Builder(this).setPositiveButton("OK",null).setMessage("Please Insert a Number").show();
        }
    }
}