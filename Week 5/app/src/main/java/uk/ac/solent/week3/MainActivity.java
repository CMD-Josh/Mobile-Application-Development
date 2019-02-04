package uk.ac.solent.week3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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
    protected void onCreate(Bundle savedInstanceState) { // Runs when the activity has been initialized but not visible yet
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mv = findViewById(R.id.map1);
        mv.getController().setCenter(new GeoPoint(51.05,-0.72));
        mv.getController().setZoom(16);

        mv.setBuiltInZoomControls(true);
        mv.setClickable(true);
    }

    @Override
    public void onStart() { // Runs when the activity come into view of the user

        // TODO Make the preference persistance between destroyed states

        super.onStart();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        double lat = Double.parseDouble(prefs.getString("lat", "50.9"));
        double lon = Double.parseDouble(prefs.getString("lon", "-1.4"));
        int zoom = Integer.parseInt(prefs.getString("zoom","16"));
        String mapStyle = prefs.getString("mapStyle","NONE");

        mv.getController().setCenter(new GeoPoint(lat,lon));
        mv.getController().setZoom(zoom);
        if(mapStyle != null){
            if(mapStyle.equals("HBV")){
                mv.setTileSource(TileSourceFactory.HIKEBIKEMAP);
            }else{
                mv.setTileSource(TileSourceFactory.MAPNIK);
            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) // Used to instantiate the options menu
    {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) // Function that is called when an option from the menu is selected
    {
        Intent intent;
        if(item.getItemId() == R.id.choosemap) // Item being the option that was selected
        {
            // react to the menu item being selected...
            intent = new Intent(this,MapChooseActivity.class); // Intent is what sends data between Activities, is given this class and the class to start the activity
            startActivityForResult(intent,0); // starts the activity that was defined in the line above and assigns it the request code of 0
            return true;
        }else if(item.getItemId() == R.id.setloc){
            intent = new Intent(this,SetLocationActivity.class);
            startActivityForResult(intent,1);
            return true;
        }else if(item.getItemId() == R.id.prefs){
            intent = new Intent(this,MyPrefsActivity.class);
            startActivity(intent);
            return true;
        }
        return false;
    }

    protected void onActivityResult(int requestCode,int resultCode,Intent intent){ // Function that is called when an activity returns an intent
        if(requestCode==0){ // Result from set map view activity (requestCode = 0)
            if(resultCode==RESULT_OK){
                Bundle extras = intent.getExtras();
                boolean hikebikemap = extras.getBoolean("uk.ac.solent.hikebikemap");
                if(hikebikemap == true){
                    mv.setTileSource(TileSourceFactory.HIKEBIKEMAP);
                }else{
                    mv.setTileSource(TileSourceFactory.MAPNIK);
                }
            }
        }else if(requestCode == 1){ // Result from set location activity (requestCode = 1)
            if(resultCode==RESULT_OK){
                Bundle extras = intent.getExtras();
                mv.getController().setCenter(new GeoPoint(extras.getDouble("uk.ac.solent.txtLat"),extras.getDouble("uk.ac.solent.txtLon")));
            }
        }
    }

    @Override
    public void onClick(View v) { // View is the component sending the click event // Function called when a component is clicked

    }
}
