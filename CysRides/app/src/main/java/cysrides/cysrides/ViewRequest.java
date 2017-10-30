package cysrides.cysrides;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import domain.Request;

public class ViewRequest extends Fragment {

    private Request request;

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
                Toast.makeText(getActivity(), "NEED TO JOIN TRIP", Toast.LENGTH_LONG).show();
                //offers.get(position).getGroup().addUser(ME);
            }
        });
        return v;
    }

    public void setData(Request request) {
        this.request = request;
    }

    public void setTextInfo(View v) {
        TextView info = v.findViewById(R.id.request);
        info.setText(request.toString());
    }
}
