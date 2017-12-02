package volley;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import service.Callback;


public class RequestVolleyImpl extends AsyncTask<Void, Void, JSONArray> implements RequestVolley {

    private String createRequestUrl = "http://proj-309-sa-b-5.cs.iastate.edu/createRequest.php";
    private String deleteRequestUrl = "http://proj-309-sa-b-5.cs.iastate.edu/deleteRequest.php";
    private String getRequestsUrl = "http://proj-309-sa-b-5.cs.iastate.edu/getRequest.php";
    private String giveRequestGroupUrl = "http://proj-309-sa-b-5.cs.iastate.edu/giveRequestGroup.php";

    private domain.Request newRequest;
    private Context currentContext;
    private String destinationName;
    private String startName;
    private ArrayList<domain.Request> requests;
    private Callback callback;
    private GroupVolleyImpl groupVolley = new GroupVolleyImpl();


    public RequestVolleyImpl() { }

    /*
     * constructor that stores the caller data
     *
     * Param: Callback data for caller
     */
    public RequestVolleyImpl(Context c, Callback o) {
        currentContext = c;
        callback = o;
    }

    /*
     * Method that adds request to the database
     */
    @Override
    public void createRequest(Context context, domain.Request request, String destination, String start) {
        newRequest = request;
        currentContext = context;
        destinationName = destination;
        startName = start;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, createRequestUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        newRequest.getGroup().setRequestID(Integer.parseInt(response));
                        groupVolley.createGroup(currentContext, newRequest.getGroup());

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
                params.put("destination", destinationName);
                params.put("start", startName);
                params.put("description", newRequest.getDescription());
                params.put("date", String.format("%s '%s'", "DATE", new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(newRequest.getDate())));
                return params;
            }
        };

        /* push request to database */
        MySingleton.getInstance(currentContext).addToRequestQueue(stringRequest);
    }

    @Override
    public void deleteRequest(final Context context, final int id) {
        currentContext = context;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, deleteRequestUrl,
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
                params.put("id", id+"");
                return params;
            }
        };

        /* push request to database */
        MySingleton.getInstance(currentContext).addToRequestQueue(stringRequest);
    }

    /*
     * Method that takes group number that was just created and sets the groupID of the given request to the given group id
     */
    public void giveRequestGroup(Context context, final int requestId, final int groupId){
        currentContext = context;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, giveRequestGroupUrl,
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
                params.put("request_id", Integer.toString(requestId));
                params.put("group_id", Integer.toString(groupId));
                return params;
            }
        };

        MySingleton.getInstance(currentContext).addToRequestQueue(stringRequest);
    }

    /*
     * Method that parses data pulled from database
     *
     * Param: JSONArray of ride request data pulled from database
     */
    @Override
    protected void onPostExecute(JSONArray jsonArray) {
        try{
            /* parse ride request data and place in arraylist */
            requests = new ArrayList<>();
            for(int i=0; i < jsonArray.length();i++){
                Log.d("JSON",jsonArray.toString());
                JSONObject jsonRequest = jsonArray.getJSONObject(i);

                String stringId = jsonRequest.getString("ID");
                int id = Integer.parseInt(stringId);
                String stringCost = jsonRequest.getString("NUM_BAGS");
                int numBags = Integer.parseInt(stringCost);
                String email = jsonRequest.getString("REQUEST_EMAIL");

                String stringDestination = jsonRequest.getString("DESTINATION");
                String destinationName = getLocationName(stringDestination);
                LatLng destLatLng = getLatLngFromDatabase(stringDestination);

                String stringStart = jsonRequest.getString("START");
                String startName = getLocationName(stringStart);
                LatLng startLatLng = getLatLngFromDatabase(stringStart);

                String description = jsonRequest.getString("DESCRIPTION");
                String stringDate = jsonRequest.getString("DATE");
                Date date =  new Date();

                int group_id = jsonRequest.getInt("GROUP_ID");

                try {
                    date = new SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(stringDate);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                domain.Request request = new domain.Request(numBags, id, email, destinationName,
                        destLatLng, startName, startLatLng, description, date, group_id,
                        this.currentContext);
                requests.add(request);
                Log.d("size", requests.size()+"");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        /* send list of ride requests back to caller */
        callback.call(requests);
    }

    /*
     * Method that runs a new thread in the background to pull data from database
     */
    @Override
    protected JSONArray doInBackground(Void... aVoid) {
        HttpURLConnection urlConnection = null;
        StringBuilder result = new StringBuilder();

        try {
            /* pull data from database */
            URL url = new URL(getRequestsUrl);
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
            /* disconnect from server */
            try {
                if(null != urlConnection) {
                    urlConnection.disconnect();
                }
            }catch(NullPointerException e) {
                e.printStackTrace();
            }
        }

        Log.d("String", result.toString());
        JSONArray array = null;
        try {
            array = new JSONArray(result.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        /* return data from database */
        return array;
    }



    /* parse destination from string */
    private String getLocationName(String location) {
        String[] splitDestination = location.split(" lat/lng: ");
        return splitDestination[0];
    }

    /* parse LatLng from string */
    private LatLng getLatLngFromDatabase(String location) {
        String[] splitDestination = location.split(" lat/lng: ");
        String latLong = splitDestination[1];
        latLong = latLong.replace("(","");
        latLong = latLong.replace(")","");
        String[] splitLatLong = latLong.split(",");
        double latitude = Double.parseDouble(splitLatLong[0]);
        double longitude = Double.parseDouble(splitLatLong[1]);
        return new LatLng(latitude, longitude);
    }

}
