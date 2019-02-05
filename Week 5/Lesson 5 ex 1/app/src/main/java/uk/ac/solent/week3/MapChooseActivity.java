package uk.ac.solent.week3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;
import android.view.View;

public class MapChooseActivity extends AppCompatActivity implements View.OnClickListener {

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_choose);

        Button btnRegular = findViewById(R.id.btnRegular);
        Button btnHikeBike = findViewById(R.id.btnHikeBikeMap);
        btnRegular.setOnClickListener(this);
        btnHikeBike.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        boolean hikebike = false;

        if(v.getId() == R.id.btnHikeBikeMap){
            hikebike = true;
        }

        bundle.putBoolean("uk.ac.solent.hikebikemap",hikebike); // bundle is a storage of data that we can parse between activities. Think dictionary with key-value pairs
        intent.putExtras(bundle); // Putting the bundle into the intent
        setResult(RESULT_OK,intent); // return results code (RESULT_OK) along with the intent (which has our data in it)
        finish(); // forcibly finish the activity
    }
}