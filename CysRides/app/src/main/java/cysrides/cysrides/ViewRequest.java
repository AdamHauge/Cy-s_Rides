package cysrides.cysrides;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import domain.Offer;
import domain.Request;
import domain.UserInfo;
import domain.UserType;
import service.GroupService;
import service.GroupServiceImpl;

public class ViewRequest extends Fragment {

    private GroupService groupService = new GroupServiceImpl();

    private Request request;
    private Context context;
    private UserInfo userInfo;

    public ViewRequest() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_view_request, container, false);
        setTextInfo(v);
        v.findViewById(R.id.join).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                groupService.addRider(context, request.getGroup(), request.getEmail());

            }
        });
        v.findViewById(R.id.JoinAsDriverButton).setOnClickListener(new View.OnClickListener(){

           @Override
           public void onClick(View view) {
               groupService.addDriver(context, request.getGroup(), request.getEmail());
           }
       }

        );
        return v;
    }

    public void setData(Request request) {
        this.request = request;
    }

    public void setContext(Context context){this.context = context;}

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public void setTextInfo(View v) {
        if(userInfo.getUserType() == UserType.ADMIN) {
            setAdminTextInfo(v);
        } else {
            setNonAdminTextInfo(v);
        }
    }

    private void setNonAdminTextInfo(View v) {
        TextView info = v.findViewById(R.id.request);
        info.setText(request.toString());
    }

    private void setAdminTextInfo(View v) {
        TextView info = v.findViewById(R.id.request);
        info.setText(adminRequest(request));
    }

    private String adminRequest(Request request ) {
        return  "id=" + request.getId() +
                "\nnum bags=" + request.getNumBags() +
                "\nemail=" + request.getEmail() +
                "\ndescription=" + request.getDescription() +
                "\ndate=" + request.getDate();
    }
}
