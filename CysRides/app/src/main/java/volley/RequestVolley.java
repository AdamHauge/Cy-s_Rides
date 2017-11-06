package volley;

import android.content.Context;

import domain.Request;

public interface RequestVolley {
    void createRequest(Context context, domain.Request request, String latLongName);
    void giveRequestGroup(Context context,final int requestId, final int groupId);
}
