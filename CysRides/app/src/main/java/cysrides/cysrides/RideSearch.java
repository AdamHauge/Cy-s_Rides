package cysrides.cysrides;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.location.places.ui.SupportPlaceAutocompleteFragment;

import service.FragmentImpl;
import service.SearchCallback;

public class RideSearch extends FragmentImpl {

    private SearchCallback callback;

    public RideSearch() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ride_search, container, false);

        SupportPlaceAutocompleteFragment placeAutoComplete = new SupportPlaceAutocompleteFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_ride_search, placeAutoComplete);
        fragmentTransaction.commit();

        AutocompleteFilter autocompleteFilter = new AutocompleteFilter.Builder()
                .setTypeFilter(Place.TYPE_COUNTRY)
                .setCountry("US")
                .build();

        placeAutoComplete.setFilter(autocompleteFilter);
        placeAutoComplete.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                callback.call(place);
            }

            @Override
            public void onError(Status status) {
                Log.d("Maps", "An error occurred: " + status);
            }
        });
        return v;
    }

    /**
     * Sets callback to return user's search information
     * @param callback - determines where to send data back to
     */
    public void setCallback(SearchCallback callback) {
        this.callback = callback;
    }
}
