package cysrides.cysrides;

import java.util.ArrayList;

import domain.Offer;

public interface Callback {
    public void call(ArrayList<Offer> result);
}
