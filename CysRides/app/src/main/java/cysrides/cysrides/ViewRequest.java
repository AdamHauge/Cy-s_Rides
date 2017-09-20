package cysrides.cysrides;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import domain.Request;

public class ViewRequest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_request);

        //Bundle b = getIntent().getBundleExtra("Bundle");

        EditText temp = (EditText)findViewById(R.id.driverName);
        temp.setText(getIntent().getExtras().getString("UserName"));

        temp = (EditText)findViewById(R.id.destination);
        temp.setText(getIntent().getExtras().getString("Dest"));

        temp = (EditText)findViewById(R.id.numBags);
        temp.setText(getIntent().getStringExtra("NumBags"));

        temp = (EditText)findViewById(R.id.date);
        temp.setText(getIntent().getStringExtra("Date"));

        temp = (EditText)findViewById(R.id.description);
        temp.setText(getIntent().getStringExtra("Description"));
    }

}
