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

    /**
     * Sets the data to be displayed
     * @param data information to be displayed
     * @param <T> ride offer or ride request
     */
    protected abstract <T> void setData(T data);

    /**
     * Sets text for non-admins
     * @param v view to be set
     */
    protected abstract void setNonAdminTextInfo(View v);

    /**
     * Sets text for non-admins
     * @param v view to be set
     */
    protected abstract void setAdminTextInfo(View v);

    /**
     * Sets the context to display fragment on
     * @param context to be displayed on
     */
    public void setContext(Context context){
        this.context = context;
    }

    /**
     * Sets the user type
     * @param userInfo of user
     */
    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    /**
     * sets text to be displayed
     * @param v view to be set
     */
    public void setTextInfo(View v) {
        if(userInfo.getUserType() == UserType.ADMIN) {
            setAdminTextInfo(v);
        } else {
            setNonAdminTextInfo(v);
        }
    }
}
