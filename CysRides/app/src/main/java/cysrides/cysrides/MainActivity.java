package cysrides.cysrides;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //TODO what is going to get put on our main page???
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i;
        switch(item.getItemId()) {
            case R.id.register:
                Toast.makeText(getApplicationContext(), "Register", Toast.LENGTH_SHORT).show();
                break;
                case R.id.login:
                Toast.makeText(getApplicationContext(), "Login", Toast.LENGTH_SHORT).show();
                break;
            case R.id.profile:
                Toast.makeText(getApplicationContext(), "My Profile", Toast.LENGTH_SHORT).show();
                break;
            case R.id.contacts:
                Toast.makeText(getApplicationContext(), "Contacts", Toast.LENGTH_SHORT).show();
                i = new Intent(MainActivity.this, Contacts.class);
                startActivity(i);
                break;
            case R.id.requests:
                Toast.makeText(getApplicationContext(), "Ride Requests", Toast.LENGTH_SHORT).show();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
