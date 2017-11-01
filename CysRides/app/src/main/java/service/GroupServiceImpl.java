package service;

import android.content.Context;

import domain.Group;
import volley.GroupVolley;
import volley.GroupVolleyImpl;

/**
 * Created by Ryan on 10/18/2017.
 */

public class GroupServiceImpl implements GroupService{

    private GroupVolley groupVolley = new GroupVolleyImpl();


    @Override
    public void createGroup(Context context, Group g) {
        groupVolley.createGroup(context, g);
    }
    @Override
    public void addRider(Context context, Group g, String netid){groupVolley.addRider(context,g,netid);}
}
