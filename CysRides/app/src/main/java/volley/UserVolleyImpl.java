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

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domain.Offer;
import domain.UserInfo;
import domain.UserType;

public class UserVolleyImpl implements UserVolley {
    private String createUserUrl = "http://proj-309-sa-b-5.cs.iastate.edu/createUser.php";
    private String getUserUrl = "http://proj-309-sa-b-5.cs.iastate.edu/getUser.php";
    private UserInfo currentUser;
    private Context currentContext;

    @Override
    public void createUser(Context context, final UserInfo user) {
        currentUser = user;
        currentContext = context;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, createUserUrl,
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
                params.put("netID", currentUser.getNetID());
                params.put("userPassword", currentUser.getPassword());
                params.put("confirmationCode", currentUser.getConfirmationCode());
                params.put("firstName", currentUser.getFirstName());
                params.put("lastName", currentUser.getLastName());
                params.put("venmo", currentUser.getVenmoName());
                params.put("profileDescription", currentUser.getProfileDescription());
                params.put("userType", currentUser.getUserType().toString());
                params.put("userRating", currentUser.ratingToString(currentUser.getUserRating()));
                return params;
            }
        };

        MySingleton.getInstance(currentContext).addToRequestQueue(stringRequest);
    }

    public void onPostExecute(JSONArray jsonArray) {
        try{
            Log.d("JSON",jsonArray.toString());
            JSONObject jsonUser = jsonArray.getJSONObject(0);

            String netID = jsonUser.getString("NETID");
            String userPassword = jsonUser.getString("PASSWORD");
            String confirmationCode = jsonUser.getString("CONFIRMATION_CODE");
            String firstName = jsonUser.getString("FIRST_NAME");
            String lastName = jsonUser.getString("LAST_NAME");
            String venmo = jsonUser.getString("VENMO");
            String profileDescription = jsonUser.getString("PROFILE_DESCRIPTION");
            String userType = jsonUser.getString("USER_TYPE");
            UserType type = UserType.valueOf(userType);
            float userRating = (float) jsonUser.getDouble("USER_RATING");

            List<Offer> offers = new ArrayList<Offer>();
            List<domain.Request> requests = new ArrayList<domain.Request>();

            UserInfo user = new UserInfo(netID, userPassword, confirmationCode, firstName, lastName,
                            venmo, profileDescription, type, userRating, offers, requests);

        }catch (Exception e){
            e.printStackTrace();
        }
        //callback.call(user);
    }

    public JSONObject doInBackground(Void... aVoid) {
        HttpURLConnection urlConnection = null;
        StringBuilder result = new StringBuilder();

        try {
            URL url = new URL(getUserUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

        }catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if(null != urlConnection) {
                    urlConnection.disconnect();
                }
            }catch(NullPointerException e) {
                e.printStackTrace();
            }
        }

        Log.d("String", result.toString());
        JSONObject obj = null;
        try {
            obj = new JSONObject(result.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return obj;
    }


}
