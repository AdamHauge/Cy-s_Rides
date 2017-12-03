package cysrides.cysrides;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import domain.Offer;

public class ViewOffer extends RideFragment {

    private Offer offer;

    /**
     * Required empty public constructor
     */
    public ViewOffer() {
        // Required empty public constructor
    }

    /**
     * initializes data to be displayed
     * @param inflater inflates the fragment
     * @param container fragment view
     * @param savedInstanceState app info
     * @return fragment view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_view_ride, container, false);
        setTextInfo(v);
        v.findViewById(R.id.join).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                groupService.addRider(context, offer.getGroup(), offer.getEmail());
            }
        });

        v.findViewById(R.id.JoinAsDriverButton).setVisibility(View.GONE);

        return v;
    }

    /**
     * Sets fragment data
     * @param offer data
     * @param <T> offer
     */
    @Override
    protected <T> void setData(T offer) {
        this.offer = (Offer) offer;
    }

    /**
     * sets text for non-admins
     * @param v view to be set
     */
    @Override
    protected void setNonAdminTextInfo(View v) {
        TextView info = v.findViewById(R.id.ride);
        info.setText(offer.toString());
    }

    /**
     * sets text for admins
     * @param v view to be set
     */
    @Override
    protected void setAdminTextInfo(View v) {
        TextView info = v.findViewById(R.id.ride);
        info.setText(offer.adminOffer());
    }
}