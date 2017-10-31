package volley;

import android.content.Context;

import domain.Group;

/**
 * Created by Ryan on 10/18/2017.
 */

public interface GroupVolley {
    void createGroup(Context context, Group g);
    void addRider(Context context, Group group, String netID);
}
