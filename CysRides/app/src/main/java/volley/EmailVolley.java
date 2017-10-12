package volley;

import android.content.Context;
import android.view.View;

public interface EmailVolley {
    void sendEmail(Context context, String to, String from, String subject, String body);
}
