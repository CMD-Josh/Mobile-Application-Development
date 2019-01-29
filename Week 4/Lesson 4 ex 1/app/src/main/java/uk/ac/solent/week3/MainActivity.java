package uk.ac.solent.week3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MapView mv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnGo = findViewById(R.id.btnGo);

        btnGo.setOnClickListener(this);

        mv = findViewById(R.id.map1);
        mv.getController().setCenter(new GeoPoint(51.05,-0.72));
        mv.getController().setZoom(16);

        mv.setBuiltInZoomControls(true);
        mv.setClickable(true);
    }

    public boolean onCreateOptionsMenu(Menu menu) // Used to instantiate the options menu
    {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) // Function that is called when an option from the menu is selected
    {
        if(item.getItemId() == R.id.choosemap) // Item being the option that was selected
        {
            // react to the menu item being selected...
            Intent intent = new Intent(this,MapChooseActivity.class); // Intent is what sends data between Activities, is given this class and the class to start the activity
            startActivityForResult(intent,0); // starts the activity that was defined in the line above and assigns it the request code of 0
            return true;
        }
        return false;
    }

    protected void onActivityResult(int requestCode,int resultCode,Intent intent){ // Function that is called when an activity returns an intent
        if(requestCode==0){
            if(resultCode==RESULT_OK){
                Bundle extras = intent.getExtras();
                boolean hikebikemap = extras.getBoolean("uk.ac.solent.hikebikemap");
                if(hikebikemap == true){
                    mv.setTileSource(TileSourceFactory.HIKEBIKEMAP);
                }else{
                    mv.setTileSource(TileSourceFactory.MAPNIK);
                }
            }
        }
    }

    @Override
    public void onClick(View v) { // View is the component sending the click event // Function called when a component is clicked
        MapView mv = findViewById(R.id.map1);
        EditText lat = findViewById(R.id.txtLat);
        EditText lon = findViewById(R.id.txtLon);

        mv.getController().setCenter(new GeoPoint(Double.parseDouble(lat.getText().toString()),Double.parseDouble(lon.getText().toString())));
    }
}
