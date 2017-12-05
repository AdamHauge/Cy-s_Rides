package volley;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import domain.Group;
import service.Callback;

public class ExpiredGroupVolleyImpl implements ExpiredGroupVolley{

    private String getExpiredGroupUrl = "http://proj-309-sa-b-5.cs.iastate.edu/getExpiredGroups.php";
    private String getGroupByGroupIdUrl = "http://proj-309-sa-b-5.cs.iastate.edu/getGroupByGroupId.php";
    private String createExpiredGroupUrl = "http://proj-309-sa-b-5.cs.iastate.edu/createExpiredGroup.php";

    private Group newGroup;
    private Context currentContext;
    private boolean iO;
    private int rideId;
    private Callback callback;
    private int gId;

    public ExpiredGroupVolleyImpl(){};
    public ExpiredGroupVolleyImpl(Context currentContext, Callback c) {
        this.currentContext = currentContext;
        callback = c;
    }

    @Override
    public void createExpiredGroupByRideId(final int groupId, boolean isOffer, int id, Context context) {
        currentContext = context;
        iO = isOffer;
        rideId = id;
        gId = groupId;
        StringRequest stringRequest = new StringRequest(Request.Method.POST,getGroupByGroupIdUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray=new JSONArray(response);
                            JSONObject jsonObject=jsonArray.getJSONObject(0);
                            Group group = new Group();
                            for(int i=0 ; i<jsonArray.length() ; i++)
                            {
                                ArrayList<String> groupMembers = new ArrayList<>();
                                groupMembers.add(jsonObject.getString("DRIVER"));
                                groupMembers.add(jsonObject.getString("RIDER_1"));
                                groupMembers.add(jsonObject.getString("RIDER_2"));
                                groupMembers.add(jsonObject.getString("RIDER_3"));
                                groupMembers.add(jsonObject.getString("RIDER_4"));
                                groupMembers.add(jsonObject.getString("RIDER_5"));
                                groupMembers.add(jsonObject.getString("RIDER_6"));
                                groupMembers.add(jsonObject.getString("RIDER_7"));
                                group.setGroupMembers(groupMembers);
                            }
                            createExpiredGroup(group, currentContext);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

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
                Map<String,String> params = new HashMap<String,String>();
                params.put("id", gId+"");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(currentContext);
        requestQueue.add(stringRequest);
    }

    @Override
    public void createExpiredGroup(Group group, Context context) {
        newGroup = group;
        currentContext = context;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, createExpiredGroupUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(iO) {
                            deleteOffer(currentContext, rideId);
                        } else {
                            deleteRequest(currentContext, rideId);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(currentContext, "Error...",Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("driver", newGroup.getGroupMembers().get(0));
                params.put("riderOne", newGroup.getGroupMembers().get(1));
                params.put("riderTwo", newGroup.getGroupMembers().get(2));
                params.put("riderThree", newGroup.getGroupMembers().get(3));
                params.put("riderFour", newGroup.getGroupMembers().get(4));
                params.put("riderFive", newGroup.getGroupMembers().get(5));
                params.put("riderSix", newGroup.getGroupMembers().get(6));
                params.put("riderSeven", newGroup.getGroupMembers().get(7));
                return params;
            }
        };

        MySingleton.getInstance(currentContext).addToRequestQueue(stringRequest);
    }

//    @Override
//    public void getGroup(final Context currentContext, final int groupNum) {
//        this.currentContext = currentContext;
//        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, getExpiredGroupUrl,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONArray jarr = new JSONArray(response);
//                            JSONObject jGroup = jarr.getJSONObject(0);
//                            int groupNum = jGroup.getInt("ID");
//
//                            ArrayList<String> members = new ArrayList<>();
//                            String rider = jGroup.getString("DRIVER");
//                            members.add(rider);
//                            rider = jGroup.getString("RIDER_1");
//                            members.add(rider);
//                            rider = jGroup.getString("RIDER_2");
//                            members.add(rider);
//                            rider = jGroup.getString("RIDER_3");
//                            members.add(rider);
//                            rider = jGroup.getString("RIDER_4");
//                            members.add(rider);
//                            rider = jGroup.getString("RIDER_5");
//                            members.add(rider);
//                            rider = jGroup.getString("RIDER_6");
//                            members.add(rider);
//                            rider = jGroup.getString("RIDER_7");
//                            members.add(rider);
//
//                            String  offerNum = jGroup.getString("OFFER_ID");
//                            if(!offerNum.equals("null")){
//                                group = new Group(groupNum, members,Integer.parseInt(offerNum), Integer.MIN_VALUE);
//                            }else {
//                                group = new Group(groupNum, members, Integer.MIN_VALUE, Integer.parseInt(jGroup.getString("REQUEST_ID")));
//                            }
//                            ArrayList<Group> groupList = new ArrayList<>();
//                            groupList.add(group);
//                            callback.call(groupList);
//
//
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(currentContext, "Error...", Toast.LENGTH_SHORT).show();
//                        error.printStackTrace();
//                    }
//                }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("group_id", Integer.toString(groupNum));
//                return params;
//            }
//        };
//
//        MySingleton.getInstance(currentContext).addToRequestQueue(stringRequest);
//    }

    private void deleteOffer(final Context context, final int id) {
        String deleteOfferUrl = "http://proj-309-sa-b-5.cs.iastate.edu/deleteOffer.php";
        currentContext = context;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, deleteOfferUrl,
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

        MySingleton.getInstance(currentContext).addToRequestQueue(stringRequest);
    }

    private void deleteRequest(final Context context, final int id) {
        String deleteRequestUrl = "http://proj-309-sa-b-5.cs.iastate.edu/deleteRequest.php";
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

}
