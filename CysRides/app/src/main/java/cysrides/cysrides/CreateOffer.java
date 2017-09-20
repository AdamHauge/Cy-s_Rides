package cysrides.cysrides;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

import domain.Offer;
import domain.Request;
import domain.UserInfo;
import domain.UserType;

public class CreateOffer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_offer);

        Button submit = (Button) findViewById(R.id.Submit);
        submit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                EditText temp = (EditText)findViewById(R.id.Cost);
                double cost = Double.parseDouble(temp.getText().toString());

                temp = (EditText)findViewById(R.id.Dest);
                String dest = temp.getText().toString();

                temp = (EditText)findViewById(R.id.Description);
                String description = temp.getText().toString();

                temp = (EditText)findViewById(R.id.LeaveDate);
                String date = temp.getText().toString();

                UserInfo ui = new UserInfo("username", "password", "rcerveny@iastate.edu", UserType.PASSENGER, "Ryan",
                        "Cerveny", "Venmo", (float)5.0 , new ArrayList<Offer>());

                Offer o = new Offer(UserType.DRIVER, cost, ui, dest, description, date);

                o.viewOffer(o, CreateOffer.this);
            }
        });
    }
}
