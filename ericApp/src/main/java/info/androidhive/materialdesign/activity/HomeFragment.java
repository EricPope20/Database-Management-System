package info.androidhive.materialdesign.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import android.widget.RelativeLayout;


import info.androidhive.materialdesign.R;


public class HomeFragment extends Fragment {


    private RelativeLayout chassisNameLay,HostInfoLayout;
    private HomeConnectionWithFrag homeCon;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.chassis_detail_info,container,false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();

        chassisNameLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeCon.triggerOnCreateOptionMenu();
            }
        });

        chassisNameLay.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ShowDialogMenu();
                return true;
            }
        });

        HostInfoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeCon.addFragmentHostInfo();
            }
        });
    }

    private void init() {
        chassisNameLay = (RelativeLayout) getActivity().findViewById(R.id.chassisNameLay);
        HostInfoLayout = (RelativeLayout) getActivity().findViewById(R.id.HostInfoLayout);

        homeCon = (HomeConnectionWithFrag)getActivity();
        homeCon.setExactTitle("Eric Database System");
    }

    private void ShowDialogMenu(){
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popup_menu);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        dialog.getWindow().setAttributes(lp);
        dialog.show();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
