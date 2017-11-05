package volley;

import android.content.Context;
import android.content.pm.LauncherApps;
import android.os.AsyncTask;
import android.telecom.Call;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import domain.Group;
import cysrides.cysrides.Callback;

/**
 * Created by Ryan on 10/18/2017.
 */

public class GroupVolleyImpl extends AsyncTask<Void, Void, JSONArray> implements GroupVolley {

    private String createGroupUrl = "http://proj-309-sa-b-5.cs.iastate.edu/createGroup.php";
    private String addRiderUrl =    "http://proj-309-sa-b-5.cs.iastate.edu/addRider.php";
    private String getGroupUrl =    "http://proj-309-sa-b-5.cs.iastate.edu/getGroup.php";
    private Group group;
    private Context currentContext;
    private Callback callback;
    private OfferVolleyImpl ovi;
    private int groupNum;

    public GroupVolleyImpl(){};
    public GroupVolleyImpl(Callback c) {callback = c;}

    @Override
    public void createGroup(Context context, Group g) {
        this.group = g;
        this.ovi = new OfferVolleyImpl();
        currentContext = context;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, createGroupUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //return group number of group we just created
                        //add group number to offer table
                        //0 is Groupid and 1 is Offerid
                        String[] splitResponse = response.split(" ");
                        Log.d("GroupID, OfferID: ", splitResponse[0] + splitResponse[1]);
                        ovi.giveOfferGroup(currentContext, Integer.parseInt(splitResponse[1]), Integer.parseInt(splitResponse[0]));
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
                params.put("driver", group.getGroupMembers().get(0));
                params.put("id", Integer.toString(group.getOfferId()));
                return params;
            }
        };

        MySingleton.getInstance(currentContext).addToRequestQueue(stringRequest);
    }
    public void getGroup(final Context currentContext, final int groupNum) {
        this.currentContext = currentContext;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, getGroupUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(currentContext, response, Toast.LENGTH_SHORT).show();
                        try {
                            JSONArray jarr = new JSONArray(response);
                            JSONObject jGroup = jarr.getJSONObject(0);
                            int groupNum = jGroup.getInt("ID");

                            ArrayList<String> members = new ArrayList<>();
                            String rider = jGroup.getString("DRIVER");
                            members.add(rider);
                            rider = jGroup.getString("RIDER_1");
                            members.add(rider);
                            rider = jGroup.getString("RIDER_2");
                            members.add(rider);
                            rider = jGroup.getString("RIDER_3");
                            members.add(rider);
                            rider = jGroup.getString("RIDER_4");
                            members.add(rider);
                            rider = jGroup.getString("RIDER_5");
                            members.add(rider);
                            rider = jGroup.getString("RIDER_6");
                            members.add(rider);
                            rider = jGroup.getString("RIDER_7");
                            members.add(rider);

                            int offerNum = jGroup.getInt("OFFER_ID");

                            group = new Group(groupNum, members, offerNum);
                            ArrayList<Group> groupList = new ArrayList<>();
                            groupList.add(group);
                            callback.call(groupList);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(currentContext, "Error...", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("group_id", Integer.toString(groupNum));
                return params;
            }
        };

        MySingleton.getInstance(currentContext).addToRequestQueue(stringRequest);
    }


    @Override
    public void addRider(Context context, final Group g, final String netID) {
        currentContext = context;
        this.group = g;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, addRiderUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(currentContext, response, Toast.LENGTH_SHORT).show();

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
                params.put("rider", netID);
                params.put("rider_num", Integer.toString(group.getSize()));
                params.put("offer_id", Integer.toString(group.getOfferId()));
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
