package volley;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailVolleyImpl implements EmailVolley {


    private String sendEmailUrl = "http://proj-309-sa-b-5.cs.iastate.edu/sendEmail.php";
    private Context currentContext;
    private String to, from, subject, message;

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
