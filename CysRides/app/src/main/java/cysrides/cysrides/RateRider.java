package cysrides.cysrides;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import domain.UserInfo;
import service.UserIntentService;
import service.UserIntentServiceImpl;
import service.UserRatingService;
import service.UserRatingServiceImpl;
import volley.UserRatingVolley;

public class RateRider extends AppCompatActivity {

    private String riderName;

    private UserIntentService userIntentService = new UserIntentServiceImpl();
    private UserRatingService userRatingService = new UserRatingServiceImpl();

    private EditText resultView;
    private Button goButton;
    private TextView rateRiderTextView;

    private float rating;
    private UserInfo user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_rider);

        riderName = (this.getIntent().getExtras().getString("Rider name"));

        resultView =  (EditText) findViewById(R.id.resultText);
        goButton = (Button) findViewById(R.id.goButton);
        rateRiderTextView = (TextView) findViewById(R.id.rateRiderTextView);

        rateRiderTextView.append(riderName);
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rating = Float.parseFloat(resultView.getText().toString());

                user = userIntentService.getUserFromIntent(getIntent());

                //userRatingService.updateRating(this, , rating, , );

                Intent i = userIntentService.createIntent(RateRider.this, LoginActivity.class, user);
                startActivity(i);
            }
        });
    }
}
