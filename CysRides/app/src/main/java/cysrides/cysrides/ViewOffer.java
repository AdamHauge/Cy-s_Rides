package cysrides.cysrides;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import domain.Offer;

public class ViewOffer extends RideFragment {

    private Offer offer;

    public ViewOffer() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_view_offer, container, false);
        setTextInfo(v);
        v.findViewById(R.id.join).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                groupService.addRider(context, offer.getGroup(), offer.getEmail());
            }
        });


        return v;
    }

    @Override
    protected <T> void setData(T offer) {
        this.offer = (Offer) offer;
    }

    @Override
    protected void setNonAdminTextInfo(View v) {
        TextView info = v.findViewById(R.id.offer);
        info.setText(offer.toString());
    }

    @Override
    protected void setAdminTextInfo(View v) {
        TextView info = v.findViewById(R.id.offer);
        info.setText(offer.adminOffer());
    }
}