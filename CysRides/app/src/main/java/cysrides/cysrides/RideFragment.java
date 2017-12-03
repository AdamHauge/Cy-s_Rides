package cysrides.cysrides;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

import domain.UserInfo;
import domain.UserType;
import service.FragmentImpl;
import service.GroupService;
import service.GroupServiceImpl;

public abstract class RideFragment extends FragmentImpl {

    protected GroupService groupService = new GroupServiceImpl();
    protected Context context;
    protected UserInfo userInfo;

    protected TextView type;
    protected TextView id;
    protected TextView destination;
    protected TextView start;
    protected TextView cost;
    protected TextView numBags;
    protected TextView email;
    protected TextView description;
    protected TextView date;

    /**
     * Required empty public constructor
     */
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

    protected String getDate(Date d) {
        Calendar date = Calendar.getInstance();
        date.setTime(d);
        return (date.get(Calendar.MONTH) + 1) + "/" + date.get(Calendar.DAY_OF_MONTH) + "/" + date.get(Calendar.YEAR);
    }
}
