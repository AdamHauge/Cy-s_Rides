package volley;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

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

    private Group group;
    private Context currentContext;
    private Callback callback;

    public ExpiredGroupVolleyImpl(){};
    public ExpiredGroupVolleyImpl(Context currentContext, Callback c) {
        this.currentContext = currentContext;
        callback = c;
    }

    @Override
    public void getGroup(final Context currentContext, final int groupNum) {
        this.currentContext = currentContext;
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, getExpiredGroupUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
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

                            String  offerNum = jGroup.getString("OFFER_ID");
                            if(!offerNum.equals("null")){
                                group = new Group(groupNum, members,Integer.parseInt(offerNum), Integer.MIN_VALUE);
                            }else {
                                group = new Group(groupNum, members, Integer.MIN_VALUE, Integer.parseInt(jGroup.getString("REQUEST_ID")));
                            }
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

}
