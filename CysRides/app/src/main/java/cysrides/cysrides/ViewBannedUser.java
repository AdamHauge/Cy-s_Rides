package cysrides.cysrides;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import domain.Ban;
import service.FragmentImpl;

public class ViewBannedUser extends FragmentImpl {

    private Ban ban;
    public ViewBannedUser() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_view_banned_user, container, false);
        setTextInfo(v);

        return v;
    }

    public void setData(Ban ban) {
        this.ban = ban;
    }

    public void setTextInfo(View v) {
        TextView info = v.findViewById(R.id.ban);
        info.setText(ban.toString());
    }
}