package cysrides.cysrides;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class ViewProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onBackPressed() {
        Intent intent = getIntent();
        String previous = intent.getStringExtra("caller");
        finish();
        if(previous.equals("Create Offer") || previous.equals("Create Request")) {
            intent = new Intent(ViewProfile.this, MainActivity.class);
            startActivity(intent);
        }
        else {
            super.onBackPressed();
        }
    }
}
