package cysrides.cysrides;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;

import domain.UserInfo;
import domain.UserType;
import service.FragmentImpl;
import service.GroupService;
import service.GroupServiceImpl;

public abstract class RideFragment extends FragmentImpl {

    protected GroupService groupService = new GroupServiceImpl();
    protected Context context;
    protected UserInfo userInfo;

    public RideFragment() {
        // Required empty public constructor
    }

    protected abstract <T> void setData(T data);
    protected abstract void setNonAdminTextInfo(View v);
    protected abstract void setAdminTextInfo(View v);

    public void setContext(Context context){
        this.context = context;
    }

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
}
