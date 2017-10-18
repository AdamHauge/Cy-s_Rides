package volley;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class RequestVolleyImpl implements RequestVolley{

    private String createRequestUrl = "http://proj-309-sa-b-5.cs.iastate.edu/createRequest.php";
    private domain.Request newRequest;
    private Context currentContext;
    private String latitudeLongitudeName;

    @Override
    public void createRequest(Context context, domain.Request request, String latLongName) {
        newRequest = request;
        currentContext = context;
        latitudeLongitudeName = latLongName;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, createRequestUrl,
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
                params.put("numBags", newRequest.getNumBags()+"");
                params.put("email", newRequest.getEmail());
                params.put("destination", latitudeLongitudeName);
                params.put("description", newRequest.getDescription());
                params.put("date", String.format("%s '%s'", "DATE", new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(newRequest.getDate())));
                return params;
            }
        };

        MySingleton.getInstance(currentContext).addToRequestQueue(stringRequest);
    }

}
