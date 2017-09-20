package cysrides.cysrides;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;



import domain.Offer;
import domain.Request;
import domain.UserInfo;
import domain.UserType;

public class CreateRequest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_request);

        Button submit = (Button) findViewById(R.id.CreateRequestButton);
        submit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                EditText temp = (EditText)findViewById(R.id.numBagsInput);
                int numBags = Integer.parseInt(temp.getText().toString());

                temp = (EditText)findViewById(R.id.destInput);
                String dest = temp.getText().toString();

                temp = (EditText)findViewById(R.id.discriptionInput);
                String description = temp.getText().toString();

                temp = (EditText)findViewById(R.id.dateInput);
                String date = temp.getText().toString();

                UserInfo ui = new UserInfo("username", "password", "rcerveny@iastate.edu", UserType.DRIVER, "Ryan",
                        "Cerveny", "Venmo", (float)5.0 , new ArrayList<Offer>());

                Request r = new Request(numBags, ui, dest, description, date);

                r.viewRequest(r, CreateRequest.this);
            }
        });
    }
}
