package volley;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import domain.Offer;

public class OfferVolleyImpl implements OfferVolley {

    private String serverUrl = "http://proj-309-sa-b-5.cs.iastate.edu/createOffer.php";
    private Offer newOffer;
    private Context currentContext;

    @Override
    public void createOffer(Context context, Offer offer) {
        newOffer = offer;
        currentContext = context;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, serverUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(currentContext, "It works",Toast.LENGTH_SHORT).show();
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
                params.put("cost", newOffer.getCost()+"");
                params.put("email", newOffer.getEmail());
                params.put("destination", newOffer.getDestination().getName().toString());
                params.put("description", newOffer.getDescription());
                params.put("date", String.format("%s '%s'", "DATE", new SimpleDateFormat("YYYY-MM-DD").format(newOffer.getDate())));
                return params;
            }
        };

        MySingleton.getInstance(currentContext).addToRequestQueue(stringRequest);
    }

}
