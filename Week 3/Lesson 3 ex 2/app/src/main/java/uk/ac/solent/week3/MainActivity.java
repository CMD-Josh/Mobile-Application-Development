package uk.ac.solent.week3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnGo = findViewById(R.id.btnGo);

        btnGo.setOnClickListener(this);

        MapView mv = findViewById(R.id.map1);
        mv.getController().setCenter(new GeoPoint(50.9080,-1.4004));
        mv.getController().setZoom(14);

        mv.setBuiltInZoomControls(true);
        mv.setClickable(true);
    }

    @Override
    public void onClick(View v) { // View is the component sending the click event
        MapView mv = findViewById(R.id.map1);
        EditText lat = findViewById(R.id.txtLat);
        EditText lon = findViewById(R.id.txtLon);

        mv.getController().setCenter(new GeoPoint(Double.parseDouble(lat.getText().toString()),Double.parseDouble(lon.getText().toString())));
    }
}
