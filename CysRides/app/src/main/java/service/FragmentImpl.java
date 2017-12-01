package service;

import android.content.Context;
import android.support.v4.app.Fragment;


public class FragmentImpl extends Fragment {

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((DrawerLock)getActivity()).lockDrawer(true);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ((DrawerLock)getActivity()).lockDrawer(false);
    }
}
