package cysrides.cysrides;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import domain.Offer;

public class ViewOffer extends Fragment {

    private Offer offer;

    public ViewOffer() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_view_offer, container, false);
        setTextInfo(v);
        return v;
    }

    public void setData(Offer offer) {
        this.offer = offer;
    }

    public void setTextInfo(View v) {
        TextView info = v.findViewById(R.id.offer);
        info.setText(offer.toString());
    }
}