package volley;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class EmailVolleyImpl implements EmailVolley {


    private String sendEmailUrl = "http://proj-309-sa-b-5.cs.iastate.edu/sendEmail.php";
    private Context currentContext;
    private String to, from, subject, message;

    /*
    Method to send the user a confirmation email. Takes parameters for the email fields like to, from,
    subject, and the message. Also needs current context. Makes volley request to sendEmail.php. Sends
    given parameters over as a map.
     */
    public void sendEmail(String toData, String fromData, String subjectData, String messageData, Context context)
    {
        currentContext = context;
        to = toData;
        from = fromData;
        subject = subjectData;
        message = messageData;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, sendEmailUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(currentContext, "Error...",Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("to", to);
                params.put("from", from);
                params.put("subject", subject);
                params.put("message", message);
                return params;
            }
        };

        MySingleton.getInstance(currentContext).addToRequestQueue(stringRequest);

    }
}
