package volley;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import cysrides.cysrides.R;
import domain.Offer;

public class OfferVolleyImpl implements OfferVolley {

    private String createOfferUrl = "http://proj-309-sa-b-5.cs.iastate.edu/createOffer.php";
    private String getOffersUrl = "http://proj-309-sa-b-5.cs.iastate.edu/getOffer.php";
    private Offer newOffer;
    private Context currentContext;
    private View currentView;

    @Override
    public void createOffer(Context context, View view, Offer offer) {
        newOffer = offer;
        currentContext = context;
        currentView = view;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, createOfferUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Snackbar.make(currentView, response, Snackbar.LENGTH_INDEFINITE);
//                        Toast.makeText(currentContext, response,Toast.LENGTH_LONG).show();
                        Log.d("Response", response);
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
    public Offer[] GetOffers() {
        Offer[] offers = {};
        JsonObjectRequest jsonRequest = new JsonObjectRequest(getOffersUrl,new JSONObject(),
              new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response){
                    //DO STUFF

                }
              },
              new Response.ErrorListener(){
                 @Override
                  public void onErrorResponse(VolleyError error){
                     VolleyLog.e("Error: ", error.getMessage());
                 }
              });


        MySingleton.getInstance(currentContext).addToRequestQueue(jsonRequest);
        return offers;
    }

}
