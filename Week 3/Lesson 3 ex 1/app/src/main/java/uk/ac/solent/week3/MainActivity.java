package uk.ac.solent.week3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MapView mv = findViewById(R.id.map1);
        mv.getController().setCenter(new GeoPoint(50.9080,-1.4004));
        mv.getController().setZoom(14);

        mv.setBuiltInZoomControls(true);
        mv.setClickable(true);
    }
}
