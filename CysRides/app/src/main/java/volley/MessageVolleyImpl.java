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

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;
import java.util.Locale;
import service.Callback;
import java.text.SimpleDateFormat;

import domain.Message;

/**
 * Created by Ryan on 12/1/2017.
 */

public class MessageVolleyImpl extends AsyncTask<Void, Void, JSONArray> implements MessageVolley {

   private String createMessageUrl = "http://proj-309-sa-b-5.cs.iastate.edu/sendMessage.php";
   private String getMessagesUrl = "http://proj-309-sa-b-5.cs.iastate.edu/getGroupsMessages.php";
   private ArrayList<Message> messages;
   private Callback callback;
   private Context currentContext;

   public MessageVolleyImpl() {  }
   public MessageVolleyImpl(Callback o) {callback = o;}
   public MessageVolleyImpl(Context currentContext, Callback o){
     this.currentContext = currentContext;
     callback = o;
   }

   @Override
   public void createMessage(final Context context, final Message message){
     this.currentContext = context;
     StringRequest stringRequest = new StringRequest(Request.Method.POST, createMessageUrl,
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
             params.put("GROUP_ID", Integer.toString(message.getGroupID()));
             params.put("SENDER", message.getSender());
             params.put("MESSAGE", message.getMessage());
             return params;
         }
     };

     MySingleton.getInstance(currentContext).addToRequestQueue(stringRequest);
   }

    @Override
    protected JSONArray doInBackground(Void... voids) {
      HttpURLConnection urlConnection = null;
      StringBuilder result = new StringBuilder();

      try {
          URL url = new URL(getMessagesUrl);
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

    @Override
    protected void onPostExecute(JSONArray jsonArray){
      try {
        messages = new ArrayList<>();
        for(int i = 0; i < jsonArray.length(); i++){
          Log.d("JSON", jsonArray.toString());
          JSONObject jsonMessage = jsonArray.getJSONObject(i);

          String stringID = jsonMessage.getString("ID");
          int id = Integer.parseInt(stringID);

          String stringGroupID = jsonMessage.getString("GROUP_ID");
          int groupID = Integer.parseInt(stringGroupID);

          String sender = jsonMessage.getString("SENDER");

          String message = jsonMessage.getString("MESSAGE");

          String stringDate = jsonMessage.getString("TIME_SENT");
          Date date = new Date();

          try{
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS", Locale.US).parse(stringDate);
          }catch(Exception e){
            e.printStackTrace();
          }

          Message msg = new Message(id, groupID, sender, message, date);
          messages.add(msg);
          Log.d("size", messages.size() + "");
        }

      }catch(Exception e){
        e.printStackTrace();
      }
      callback.call(messages);
    }
}
