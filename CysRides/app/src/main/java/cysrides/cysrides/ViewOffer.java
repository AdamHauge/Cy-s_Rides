package cysrides.cysrides;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import domain.Offer;
import service.GroupServiceImpl;

public class ViewOffer extends Fragment {

    private Offer offer;
    private Context context;
    private GroupServiceImpl g = new GroupServiceImpl();
    private String email;
    public ViewOffer() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_view_offer, container, false);
        setTextInfo(v);
        v.findViewById(R.id.join).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                g.addRider(context, offer.getGroup(), email);

            }
        });


        return v;
    }

    public void setData(Offer offer) {
        this.offer = offer;
    }

    public void setEmail(String email){ this.email = email; }

    public void setContext(Context context){this.context = context;}

    public void setTextInfo(View v) {
        TextView info = v.findViewById(R.id.offer);
        info.setText(offer.toString());
    }
}