package volley;

import android.app.AlertDialog;
import android.content.DialogInterface;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import domain.Offer;

public class OfferVolleyImpl {

    private String serverUrl = "http://proj-309-sa-b-5.cs.iastate.edu/createOffer.php";
    private Offer newOffer;

    public void createOffer(final AlertDialog.Builder builder, Offer offer) {
        newOffer = offer;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, serverUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        builder.setTitle("Server Response");
                        builder.setMessage("Response : " + response);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //He clears the text here
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(CreateOffer.this, "Error...",Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userType", newOffer.getUserType().name());
                params.put("cost", newOffer.getCost()+"");
                params.put("email", newOffer.getEmail());
                params.put("destination", newOffer.getDestination());
                params.put("description", newOffer.getDescription());
                params.put("date", String.format("%s '%s'", "TIMESTAMP", new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(newOffer.getDate())));
                return params;
            }
        };

//        MySingleton.getInstance(CreateOffer.this).addToRequestQueue(stringRequest);
    }

}
