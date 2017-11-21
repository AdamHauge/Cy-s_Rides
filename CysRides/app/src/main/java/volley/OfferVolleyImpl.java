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
import domain.Offer;

public class OfferVolleyImpl extends AsyncTask<Void, Void, JSONArray> implements OfferVolley {

    private String createOfferUrl = "http://proj-309-sa-b-5.cs.iastate.edu/createOffer.php";
    private String getOffersUrl = "http://proj-309-sa-b-5.cs.iastate.edu/getOffer.php";
    private String giveOfferGroupUrl = "http://proj-309-sa-b-5.cs.iastate.edu/giveOfferGroup.php";
    private Offer newOffer;
    private Context currentContext;
    private ArrayList<Offer> offers;
    private String destinationName;
    private String startName;
    private Callback callback;
    private GroupVolleyImpl groupVolley = new GroupVolleyImpl();
    public OfferVolleyImpl() { }

    /*
     * constructor that stores the caller data
     *
     * Param: Callback data for caller
     */
    public OfferVolleyImpl(Callback o) {
        callback = o;
    }

    public OfferVolleyImpl(Context currentContext, Callback o) {
        this.currentContext = currentContext;
        callback = o;
    }

    //add offer to the database
    @Override
    public void createOffer(final Context context, final Offer offer, String destination, String start) {
        newOffer = offer;
        currentContext = context;
        destinationName = destination;
        startName = start;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, createOfferUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        newOffer.getGroup().setOfferID(Integer.parseInt(response));
                        groupVolley.createGroup(currentContext, newOffer.getGroup());
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
                params.put("destination", destinationName);
                params.put("start", startName);
                params.put("description", newOffer.getDescription());
                params.put("date", String.format("%s '%s'", "DATE", new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(newOffer.getDate())));
                return params;
            }
        };

        MySingleton.getInstance(currentContext).addToRequestQueue(stringRequest);
    }

    //gives specified offer the given groupID
    public void giveOfferGroup(Context context, final int offerId, final int groupId){
        currentContext = context;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, giveOfferGroupUrl,
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
                params.put("offer_id", Integer.toString(offerId));
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
            offers = new ArrayList<>();
            for(int i=0; i < jsonArray.length();i++){
                Log.d("JSON",jsonArray.toString());
                JSONObject jsonOffer = jsonArray.getJSONObject(i);

                String stringId = jsonOffer.getString("ID");
                int id = Integer.parseInt(stringId);
                String stringCost = jsonOffer.getString("COST");
                double cost = Double.parseDouble(stringCost);
                String email = jsonOffer.getString("EMAIL");

                String stringDestination = jsonOffer.getString("DESTINATION");
                String destinationName = getLocationName(stringDestination);
                LatLng destLatLng = getLatLngFromDatabase(stringDestination);

                String stringStart = jsonOffer.getString("START");
                String startName = getLocationName(stringStart);
                LatLng startLatLng = getLatLngFromDatabase(stringStart);

                String description = jsonOffer.getString("DESCRIPTION");
                String stringDate = jsonOffer.getString("DATE");
                Date date =  new Date();

                int groupID = jsonOffer.getInt("GROUP_ID");

                try {
                    date = new SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(stringDate);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Offer offer = new Offer(cost, id, email, destinationName, destLatLng, startName,
                        startLatLng, description, date, groupID, this.currentContext);
                //Log.d("offer", offer.toString());
                offers.add(offer);
                Log.d("size", offers.size()+"");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        callback.call(offers);
    }

    @Override
    protected JSONArray doInBackground(Void... aVoid) {
        HttpURLConnection urlConnection = null;
        StringBuilder result = new StringBuilder();

        try {
            URL url = new URL(getOffersUrl);
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
        JSONArray array = null;
        try {
            array = new JSONArray(result.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return array;
    }



    private String getLocationName(String location) {
        String[] splitDestination = location.split(" lat/lng: ");
        return splitDestination[0];
    }

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
