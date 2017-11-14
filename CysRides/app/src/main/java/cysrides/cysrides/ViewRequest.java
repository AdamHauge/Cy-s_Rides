package cysrides.cysrides;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import domain.Request;
import service.GroupServiceImpl;

public class ViewRequest extends Fragment {

    private Request request;
    private GroupServiceImpl g = new GroupServiceImpl();
    private Context context;

    public ViewRequest() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_view_request, container, false);
        setTextInfo(v);
        v.findViewById(R.id.join).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                g.addRider(context, request.getGroup(), request.getEmail());

            }
        });
        v.findViewById(R.id.JoinAsDriverButton).setOnClickListener(new View.OnClickListener(){

           @Override
           public void onClick(View view) {
                g.addDriver(context, request.getGroup(), request.getEmail());
           }
       }

        );
        return v;
    }

    public void setData(Request request) {
        this.request = request;
    }

    public void setContext(Context context){this.context = context;}

    public void setTextInfo(View v) {
        TextView info = v.findViewById(R.id.request);
        info.setText(request.toString());
    }
}
