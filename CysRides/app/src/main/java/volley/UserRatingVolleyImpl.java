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

import service.Callback;

public class UserRatingVolleyImpl implements UserRatingVolley {

    private String addRatingUrl = "http://proj-309-sa-b-5.cs.iastate.edu/addRating.php";
    private Context currentContext;
    private Callback callback;

    public UserRatingVolleyImpl(Callback call){
        callback = call;
    }

    @Override
    public void addRating(Context context, final String netID, final float userRating, final float numberRatings){

        currentContext = context;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, addRatingUrl,
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
                params.put("rating", userRating + "");
                params.put("number_ratings", numberRatings + "");
                params.put("netID", netID);
                return params;
            }
        };

        MySingleton.getInstance(currentContext).addToRequestQueue(stringRequest);

    }
}
