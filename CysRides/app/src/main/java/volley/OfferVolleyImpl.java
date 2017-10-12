package volley;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.location.places.Place;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domain.Offer;

public class OfferVolleyImpl implements OfferVolley {

    private String createOfferUrl = "http://proj-309-sa-b-5.cs.iastate.edu/createOffer.php";
    private String getOffersUrl = "http://proj-309-sa-b-5.cs.iastate.edu/getOffer.php";
    private Offer newOffer;
    private Context currentContext;
    private List<Offer> offers;

    @Override
    public void createOffer(Context context, Offer offer) {
        newOffer = offer;
        currentContext = context;
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
                params.put("destination", newOffer.getDestination().getName().toString());
                params.put("description", newOffer.getDescription());
                params.put("date", String.format("%s '%s'", "DATE", new SimpleDateFormat("yyyy-MM-dd").format(newOffer.getDate())));
                return params;
            }
        };

        MySingleton.getInstance(currentContext).addToRequestQueue(stringRequest);
    }

    @Override
    public List<Offer> getOffers(Context context) {
        offers = new ArrayList<>();

        currentContext = context;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, getOffersUrl, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try{
                            for(int i=0;i<response.length();i++){
                                Log.d("JOSN",response.toString());
                                JSONObject jsonOffer = response.getJSONObject(i);

                                String id = jsonOffer.getString("ID");
                                String stringCost = jsonOffer.getString("COST");
                                double cost = Double.parseDouble(stringCost);
                                String email = jsonOffer.getString("EMAIL");
                                String stringDestination = jsonOffer.getString("DESTINATION");
                                Place destination = null;
                                String description = jsonOffer.getString("DESCRIPTION");
                                String stringDate = jsonOffer.getString("DATE");
                                Date date =  new Date();
                                try {
                                    date = new SimpleDateFormat("yyyy-MM-dd").parse(stringDate);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                Offer offer = new Offer(cost, email, destination, description, date);
                                offers.add(offer);
                                Log.d("size", offers.size()+"");
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        VolleyLog.e("Error: ", error.getMessage());
                        error.printStackTrace();
                    }
                }
        );

        MySingleton.getInstance(currentContext).addToRequestQueue(jsonArrayRequest);
        Log.d("offers2.0",offers.size()+"");
        return offers;
    }

}
