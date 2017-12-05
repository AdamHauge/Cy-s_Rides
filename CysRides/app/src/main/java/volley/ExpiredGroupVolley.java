package volley;

import android.content.Context;

import domain.Group;

public interface ExpiredGroupVolley {
//    void getGroup(final Context context, final int groupID);
    void createExpiredGroup(Group group, Context context);
    void createExpiredGroupByRideId(final int groupId, boolean isOffer, int id, Context context);
}

