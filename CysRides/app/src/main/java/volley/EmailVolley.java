package volley;

import android.content.Context;

public interface EmailVolley {
    void sendEmail(String toData, String fromData, String subjectData, String messageData, Context context);
}
