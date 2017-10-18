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

import cysrides.cysrides.Callback;
import domain.Offer;

public class OfferVolleyImpl extends AsyncTask<Void, Void, JSONArray> implements OfferVolley {

    private String createOfferUrl = "http://proj-309-sa-b-5.cs.iastate.edu/createOffer.php";
    private String getOffersUrl = "http://proj-309-sa-b-5.cs.iastate.edu/getOffer.php";
    private Offer newOffer;
    private Context currentContext;
    private ArrayList<Offer> offers;
    private String latitudeLongitudeName;
    private Callback callback;

    public OfferVolleyImpl() { }

    public OfferVolleyImpl(Callback o) {
        callback = o;
    }

    @Override
    public void createOffer(Context context, Offer offer, String latLongName) {
        newOffer = offer;
        currentContext = context;
        latitudeLongitudeName = latLongName;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, createOfferUrl,
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
                params.put("cost", newOffer.getCost()+"");
                params.put("email", newOffer.getEmail());
                params.put("destination", latitudeLongitudeName);
                params.put("description", newOffer.getDescription());
                params.put("date", String.format("%s '%s'", "DATE", new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(newOffer.getDate())));
                return params;
            }
        };

        MySingleton.getInstance(currentContext).addToRequestQueue(stringRequest);
    }

    @Override
    protected void onPostExecute(JSONArray jsonArray) {
        try{
            offers = new ArrayList<>();
            for(int i=0; i < jsonArray.length();i++){
                Log.d("JSON",jsonArray.toString());
                JSONObject jsonOffer = jsonArray.getJSONObject(i);

                String id = jsonOffer.getString("ID");
                String stringCost = jsonOffer.getString("COST");
                double cost = Double.parseDouble(stringCost);
                String email = jsonOffer.getString("EMAIL");

                String stringDestination = jsonOffer.getString("DESTINATION");
                String destinationName = getDestinationName(stringDestination);
                LatLng latitudeLongitude = getLatLngFromDatabase(stringDestination);

                String description = jsonOffer.getString("DESCRIPTION");
                String stringDate = jsonOffer.getString("DATE");
                Date date =  new Date();

                try {
                    date = new SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(stringDate);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Offer offer = new Offer(cost, email, destinationName, latitudeLongitude, description, date);
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



    private String getDestinationName(String stringDestination) {
        String[] splitDestination = stringDestination.split(" lat/lng: ");
        return splitDestination[0];
    }

    private LatLng getLatLngFromDatabase(String stringDestination) {
        String[] splitDestination = stringDestination.split(" lat/lng: ");
        String latLong = splitDestination[1];
        latLong = latLong.replace("(","");
        latLong = latLong.replace(")","");
        String[] splitLatLong = latLong.split(",");
        double latitude = Double.parseDouble(splitLatLong[0]);
        double longitude = Double.parseDouble(splitLatLong[1]);
        return new LatLng(latitude, longitude);
    }

}
