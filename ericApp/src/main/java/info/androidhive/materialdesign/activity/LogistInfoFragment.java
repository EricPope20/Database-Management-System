package info.androidhive.materialdesign.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import info.androidhive.materialdesign.R;

/**
 * Created by new on 6/20/2016.
 */
public class LogistInfoFragment extends Fragment {

    private HomeConnectionWithFrag homeCon;
    private RelativeLayout lineItemsLayout,lineItemMenu;

    private ArrayList<String> gotData;

    private TextView hwName,serialNumber,manufacturer,model,status,powerStatus,budgetOwner,
            servicer,auditDate;

    private String serverName = "";

    public void getServerName(String name){
        serverName = name;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.logist_info_layout,container,false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();

        lineItemsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lineItemMenu.getVisibility() == View.VISIBLE)
                    lineItemMenu.setVisibility(View.GONE);
                else
                    lineItemMenu.setVisibility(View.VISIBLE);
            }
        });

        try {
            Sqlitehelper ob = new Sqlitehelper(getActivity());
            ob.open();

            gotData.addAll(ob.retrieveData(serverName));

            //Toast.makeText(getActivity(),gotData.size()+"",Toast.LENGTH_SHORT).show();

            hwName.setText(gotData.get(0));
            serialNumber.setText(gotData.get(1));
            manufacturer.setText(gotData.get(2));
            model.setText("Model :");
            status.setText(gotData.get(3));
            powerStatus.setText(gotData.get(4));
            budgetOwner.setText(gotData.get(5));
            servicer.setText(gotData.get(6));
            auditDate.setText(gotData.get(7));

            ob.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void init() {
        homeCon = (HomeConnectionWithFrag)getActivity();
        homeCon.setExactTitle("Logist Info");

        lineItemsLayout = (RelativeLayout) getActivity().findViewById(R.id.lineItemsLayout);
        lineItemMenu = (RelativeLayout) getActivity().findViewById(R.id.lineItemMenu);

        gotData = new ArrayList<>();

        hwName = (TextView)getActivity().findViewById(R.id.hwName);
        serialNumber = (TextView)getActivity().findViewById(R.id.serialNumber);
        manufacturer = (TextView)getActivity().findViewById(R.id.manufacturer);
        model = (TextView)getActivity().findViewById(R.id.model);
        status = (TextView)getActivity().findViewById(R.id.status);
        powerStatus = (TextView)getActivity().findViewById(R.id.powerStatus);
        budgetOwner = (TextView)getActivity().findViewById(R.id.budgetOwner);
        servicer = (TextView)getActivity().findViewById(R.id.servicer);
        auditDate = (TextView)getActivity().findViewById(R.id.auditDate);
    }
}
