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

import domain.Offer;
import domain.UserInfo;
import domain.UserType;
import service.OfferService;
import service.OfferServiceImpl;

public class RideRequests extends AppCompatActivity {

    OfferService offerService = new OfferServiceImpl();

    private ListView listView;
    private ArrayAdapter adapter;
    private List<Offer> list = offerService.getOfferRequests(new UserInfo("userName", "password", "email", UserType.DRIVER, "firstName", "lastName", "venmoName", 4, null));
    private List<String> destinationAndDescriptionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        listView = (ListView)findViewById(R.id.contacts_list);

        for(int i=0 ; i<list.size() ; i++) {
            String destinationAndDescription = "";
            destinationAndDescription += list.get(i).getDescription() + " " + list.get(i).getDestination();
            destinationAndDescriptionList.add(destinationAndDescription);
        }

        adapter = new ArrayAdapter(RideRequests.this, android.R.layout.simple_list_item_1, destinationAndDescriptionList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), destinationAndDescriptionList.get(position).toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
