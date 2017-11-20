package cysrides.cysrides;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import domain.Offer;
import domain.UserInfo;
import domain.UserType;
import service.GroupService;
import service.GroupServiceImpl;

public class ViewOffer extends Fragment {

    private GroupService groupService = new GroupServiceImpl();

    private Offer offer;
    private Context context;
    private UserInfo userInfo;
    public ViewOffer() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_view_offer, container, false);
        setTextInfo(v);
        v.findViewById(R.id.join).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                groupService.addRider(context, offer.getGroup(), offer.getEmail());
            }
        });


        return v;
    }

    public void setData(Offer offer) {
        this.offer = offer;
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
        TextView info = v.findViewById(R.id.offer);
        info.setText(offer.toString());
    }

    private void setAdminTextInfo(View v) {
        TextView info = v.findViewById(R.id.offer);
        info.setText(adminOffer(offer));
    }

    private String adminOffer(Offer offer) {
        return  "id=" + offer.getId() +
                "\ndestination=" + offer.getDestination() +
                "\nstart=" + offer.getStart() +
                "\ncost=$" + offer.getCost() +
                "\nemail=" + offer.getEmail() +
                "\ndescription=" + offer.getDescription() +
                "\ndate=" + offer.getDate();
    }
}