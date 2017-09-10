package cysrides.cysrides;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Contacts extends AppCompatActivity {

    private ListView listView;
    private ArrayList list = new ArrayList();
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        listView = (ListView)findViewById(R.id.contacts_list);

        for(int i = 0; i < 20; i++) //TODO the number needs to match the amount of contacts
        {
            list.add("Item #" + (i + 1)); //TODO this needs to be changed to match the contact names
        }

        adapter = new ArrayAdapter(Contacts.this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), list.get(position).toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
