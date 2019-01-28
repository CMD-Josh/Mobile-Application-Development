package uk.ac.solent.layouttest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b = (Button)findViewById(R.id.btn2);
        b.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        EditText et = (EditText)findViewById(R.id.et1);
        et.setText("");
    }
}
