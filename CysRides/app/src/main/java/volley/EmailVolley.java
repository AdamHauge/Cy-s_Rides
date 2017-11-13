package volley;

import android.content.Context;

public interface EmailVolley {
    public void sendEmail(String toData, String fromData, String subjectData, String messageData, Context context);
}
