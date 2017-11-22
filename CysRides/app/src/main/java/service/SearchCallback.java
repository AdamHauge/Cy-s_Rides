package service;

import com.google.android.gms.location.places.Place;

public interface SearchCallback {
    void call(Place location);
}
