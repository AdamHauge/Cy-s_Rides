package cysrides.cysrides;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList list = new ArrayList();
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //TODO what is going to get put on our main page???
        listView = (ListView) findViewById(R.id.main_list);

        for (int i = 0; i < 20; i++) { //TODO the number needs to match the amount of contacts
            list.add("Item #" + (i + 1)); //TODO this needs to be changed to match the contact names
        }

        adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_2, android.R.id.text1, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), list.get(position).toString(), Toast.LENGTH_SHORT).show();
            }
        });
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
                i = new Intent(MainActivity.this, CreateProfile.class);
                startActivity(i);
                break;
                case R.id.login:
                Toast.makeText(getApplicationContext(), "Login", Toast.LENGTH_SHORT).show();
                i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
                break;
            case R.id.profile:
                Toast.makeText(getApplicationContext(), "My Profile", Toast.LENGTH_SHORT).show();
                i = new Intent(MainActivity.this, ViewProfile.class);
                startActivity(i);
                break;
            case R.id.contacts:
                Toast.makeText(getApplicationContext(), "Contacts", Toast.LENGTH_SHORT).show();
                i = new Intent(MainActivity.this, Contacts.class);
                startActivity(i);
                break;
            case R.id.requests:
                Toast.makeText(getApplicationContext(), "Ride Requests", Toast.LENGTH_SHORT).show();
                i = new Intent(MainActivity.this, RideRequests.class);
                startActivity(i);
                break;
            case R.id.createProfile:
                i = new Intent(MainActivity.this, CreateProfile.class);
                startActivity(i);
                break;
            case R.id.createOffer:
                Toast.makeText(getApplicationContext(), "Create New Offer", Toast.LENGTH_SHORT).show();

                i = new Intent(MainActivity.this, CreateOffer.class);
                startActivity(i);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
