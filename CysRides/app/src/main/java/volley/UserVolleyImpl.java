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
import domain.UserInfo;

public class UserVolleyImpl implements UserVolley {
    private String serverUrl = "http://proj-309-sa-b-5.cs.iastate.edu/createUser.php";
    private String serverUrl2 = "http://proj-309-sa-b-5.cs.iastate.edu/getUser.php";
    private UserInfo user;
    private Context currentContext;

    @Override
    public void createUser(Context context, final UserInfo user) {
        this.user = user;
        currentContext = context;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, serverUrl,
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
                params.put("net-ID", user.getNetID()+"");
                params.put("password", user.getPassword());
                params.put("confirmation code", user.confirmationCodeToString(user.getConfirmationCode()));
                params.put("first name", user.getFirstName());
                params.put("last name", user.getLastName());
                params.put("venmo name", user.getVenmoName());
                params.put("profile description", user.getProfileDescription());
                params.put("user type", user.getUserType().toString());
                params.put("user rating", user.ratingToString(user.getUserRating()));
                return params;
            }
        };

        MySingleton.getInstance(currentContext).addToRequestQueue(stringRequest);
    }

    @Override
    public void getUser(Context context, final UserInfo user) {


    }


}
