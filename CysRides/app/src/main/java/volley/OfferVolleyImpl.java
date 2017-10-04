package volley;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import cysrides.cysrides.CreateOffer;

public class OfferVolleyImpl {

    private String serverUrl = "http://ineedtheip/createOffer.php";

    public void createOffer(final AlertDialog.Builder builder) {
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
                //Add all params to this map
                params.put("key","value");
                return params;
            }
        };

//        MySingleton.getInstance(CreateOffer.this).addToRequestQueue(stringRequest);
    }

}
