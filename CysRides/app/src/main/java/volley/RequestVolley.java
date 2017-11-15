package volley;

import android.content.Context;

public interface RequestVolley {
    void createRequest(Context context, domain.Request request, String destName, String startName);
    void giveRequestGroup(Context context,final int requestId, final int groupId);
}
