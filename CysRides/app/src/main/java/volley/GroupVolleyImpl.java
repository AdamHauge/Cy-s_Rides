package volley;

import android.content.Context;
import android.content.pm.LauncherApps;
import android.os.AsyncTask;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import domain.Group;
import cysrides.cysrides.Callback;

/**
 * Created by Ryan on 10/18/2017.
 */

public class GroupVolleyImpl extends AsyncTask<Void, Void, JSONArray> implements GroupVolley {

    private String createGroupUrl = "http://proj-309-sa-b-5.cs.iastate.edu/createGrouop.php";
    private Group group;
    private Context currentContext;
    private Callback callback;

    public GroupVolleyImpl(Callback c) {
        callback = c;
    }

    @Override
    public void createGroup(Context context, Group g) {
        this.group = g;
        currentContext = context;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, createGroupUrl,
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
                //params.put("cost", group.getCost()+"");
                //params.put("driver", group.getGroup.get(0));
                              return params;
            }
        };

        MySingleton.getInstance(currentContext).addToRequestQueue(stringRequest);
    }


    @Override
    protected JSONArray doInBackground(Void... voids) {
        return null;
    }
}
