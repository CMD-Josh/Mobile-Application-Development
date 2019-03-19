package uk.ac.solent.week3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MapView mv;
    private String lastActivity; // Used to store the last activity the user was on. also used to stop the preferences from taking changes when the user goes back to the map activity
    private ItemizedIconOverlay<OverlayItem> items;
    private ItemizedIconOverlay.OnItemGestureListener<OverlayItem> markerListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) { // Runs when the activity has been initialized but not visible yet
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // This line sets the user agent, a requirement to download OSM maps
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));


        mv = findViewById(R.id.map1);
        mv.getController().setCenter(new GeoPoint(50.9396,-1.3391));
        mv.getController().setZoom(16);

        mv.setBuiltInZoomControls(true);
        mv.setClickable(true);

        //Creation of marker listener to do something when the marker is pressed
        markerListener = new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>(){
            @Override
            public boolean onItemSingleTapUp(int index, OverlayItem item) {
                Toast.makeText(MainActivity.this,item.getTitle() + " :\n" + item.getSnippet(),Toast.LENGTH_LONG).show();
                return true;
            }

            public boolean onItemLongPress(int i, OverlayItem item){
                Toast.makeText(MainActivity.this,item.getSnippet(),Toast.LENGTH_LONG).show();
                return true;
            }
        };

        items = new ItemizedIconOverlay<OverlayItem>(this, new ArrayList<OverlayItem>(), markerListener);
        insertOverlayItem("Home", "The only place to be.", new GeoPoint(50.9396,-1.3391));

        ExerciseAsyncTask task = new ExerciseAsyncTask();
        task.execute();
    }

    @Override
    public void onResume() { // Runs when the activity come into view of the user {Needed to be renamed from "onStart" to "onResume" for some reason}
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        double lat = Double.parseDouble(prefs.getString("lat", "50.9396"));
        double lon = Double.parseDouble(prefs.getString("lon", "-1.3391"));
        int zoom = Integer.parseInt(prefs.getString("zoom","16"));
        String mapStyle = prefs.getString("mapStyle","NONE");

        mv.getController().setCenter(new GeoPoint(lat,lon));
        mv.getController().setZoom(zoom);
        if(mapStyle != null && lastActivity == null){
            if(mapStyle.equals("HBV")){
                mv.setTileSource(TileSourceFactory.HIKEBIKEMAP);
            }else{
                mv.setTileSource(TileSourceFactory.MAPNIK);
            }
        }

        lastActivity = null;
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
        }else if(item.getItemId() == R.id.egListActivity){
            intent = new Intent(this, ExampleListActivity.class);
            startActivityForResult(intent,0); // uses requestCode 0 because the code written to handle it is pretty much doesn't need to be changed
            return true;
        }
        return false;
    }

    protected void onActivityResult(int requestCode,int resultCode,Intent intent){ // Function that is called when an activity returns an intent
        if(requestCode==0){ // Result from set map view activity (requestCode = 0)
            if(resultCode==RESULT_OK){
                Bundle extras = intent.getExtras();
                boolean hikebikemap = extras.getBoolean("uk.ac.solent.hikebikemap");

                lastActivity = extras.getString("uk.ac.solent.lastActivity");

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

    private void insertOverlayItem(String name, String desc, GeoPoint location){
        OverlayItem marker = new OverlayItem(name, desc, location);

        items.addItem(marker);

        mv.getOverlays().add(items);
    }

    /* -----------------------------------------------------------------------------------------------------------------------------

            Inner class defining an AsyncTask (If defiend as its own seperate class than it wouldn't need to be parsed
            this class as a context for its constructor. also it looks cool.)

       -----------------------------------------------------------------------------------------------------------------------------*/

    // Creation of inner class using the Async Task
    class ExerciseAsyncTask extends AsyncTask<String, Void, ArrayList<String>> { // Generics takes 3 types to specify input types of 3 methods. <doInBackground, onPreExecute, onPostExecute>

        @Override
        public ArrayList<String> doInBackground(String... artists) {
            HttpURLConnection connection = null;
            ArrayList<String> result = new ArrayList<String>();

            try {                                                                       // Surrounded in a try/catch block (incase user looses connection to the internet or another error occurs)
                URL url = new URL("https://edward2.solent.ac.uk/course/mad/restaurants.txt");
                connection = (HttpURLConnection) url.openConnection();                   // Create a new connection to the URL specified in the line above
                InputStream in = connection.getInputStream();                           // Get data from the connection from an InputStream

                if (connection.getResponseCode() == 200) {                                // Check if the response from the web server is "OK" which we get as a 200
                    BufferedReader b = new BufferedReader(new InputStreamReader(in));   // Proceed to decode the binary data from the input stream into readable ascii via Buffered Reader
                    String line;
                    while ((line = b.readLine()) != null) {                               // Each new line from the stream is set to the 'line' variable and keep going until we've run out of lines
                        result.add(line + "\n");                                                 // Append the new line that was decoded from the InputStream into the results variable which we can return from this function
                    }
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }

            return result;
        }

        @Override
        public void onPostExecute(ArrayList<String> result){
            for(int i = 0; i < result.size(); i++){
                String[] components;
                components = result.get(i).split(",");
                insertOverlayItem(components[0],components[1] + " " + components[2], new GeoPoint(Double.valueOf(components[4]), Double.valueOf(components[3])));
            }
        }
    }
}
