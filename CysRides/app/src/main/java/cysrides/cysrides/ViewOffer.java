package cysrides.cysrides;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class ViewOffer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_offer);

        EditText temp = (EditText)findViewById(R.id.UserName);
        temp.setText(getIntent().getExtras().getString("UserName"));

        temp = (EditText)findViewById(R.id.Destination);
        temp.setText(getIntent().getExtras().getString("Dest"));

        temp = (EditText)findViewById(R.id.Cost);
        temp.setText(getIntent().getStringExtra("Cost"));

        temp = (EditText)findViewById(R.id.LeaveDate);
        temp.setText(getIntent().getStringExtra("Date"));

        temp = (EditText)findViewById(R.id.Description);
        temp.setText(getIntent().getStringExtra("Description"));
    }
}
