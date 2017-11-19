package service;

import android.content.Context;

import domain.Group;

public interface GroupService {

    void createGroup(Context context, Group g);
    void addRider(Context context, Group g, String netid);
    void addDriver(Context context, Group g, String netid);
    void getGroup(Context context, int groupNum);

}
