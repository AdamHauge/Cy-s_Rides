package cysrides.cysrides;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import domain.Request;

public class ViewRequest extends RideFragment {

    private Request request;

    public ViewRequest() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_view_ride, container, false);
        setTextInfo(v);
        v.findViewById(R.id.join).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                groupService.addRider(context, request.getGroup(), request.getEmail());

            }
        });
        v.findViewById(R.id.JoinAsDriverButton).setOnClickListener(new View.OnClickListener(){

           @Override
           public void onClick(View view) {
               groupService.addDriver(context, request.getGroup(), request.getEmail());
           }
       }

        );
        return v;
    }

    @Override
    protected <T> void setData(T request) {
        this.request = (Request) request;
    }

    @Override
    protected void setNonAdminTextInfo(View v) {
        TextView info = v.findViewById(R.id.ride);
        info.setText(request.toString());
    }

    @Override
    protected void setAdminTextInfo(View v) {
        TextView info = v.findViewById(R.id.ride);
        info.setText(request.adminRequest());
    }
}
