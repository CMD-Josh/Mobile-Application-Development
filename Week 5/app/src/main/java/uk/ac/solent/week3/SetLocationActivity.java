package uk.ac.solent.week3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SetLocationActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_location);

        Button btnGo = findViewById(R.id.btnGo);

        btnGo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        EditText lat = findViewById(R.id.txtLat);
        EditText lon = findViewById(R.id.txtLon);

        if(v.getId() == R.id.btnGo){
            bundle.putDouble("uk.ac.solent.txtLat",Double.parseDouble(lat.getText().toString()));
            bundle.putDouble("uk.ac.solent.txtLon",Double.parseDouble(lon.getText().toString()));

            intent.putExtras(bundle);
            setResult(RESULT_OK,intent);
            finish();
        }
    }
}
