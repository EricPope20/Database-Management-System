package info.androidhive.materialdesign.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import info.androidhive.materialdesign.R;


public class DeatilsInfoFragment extends Fragment {

    private ArrayList<String> gotData;
    private TextView displayName,itemName,refTemplate,txtType,typeState,serialNumber,manufacturer,
            model,status,powerStatus,updatedOn,updatedBy;

    private String serverName = "";

    public void getServerName(String name){
        serverName = name;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.details_info_layout,container,false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();

        try {
            Sqlitehelper ob = new Sqlitehelper(getActivity());
            ob.open();

            gotData.addAll(ob.retrieveData(serverName));

           // Toast.makeText(getActivity(),gotData.size()+"",Toast.LENGTH_SHORT).show();

            displayName.setText(gotData.get(0));
            itemName.setText(gotData.get(1));
            refTemplate.setText(gotData.get(2));
            txtType.setText(gotData.get(3));
            typeState.setText(gotData.get(4));
            serialNumber.setText(gotData.get(5));
            manufacturer.setText(gotData.get(6));
            model.setText(gotData.get(7));
            status.setText(gotData.get(8));
            powerStatus.setText(gotData.get(9));
            updatedOn.setText(gotData.get(10));
            updatedBy.setText(gotData.get(11));


            ob.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void init() {
        HomeConnectionWithFrag homeCon = (HomeConnectionWithFrag)getActivity();
        homeCon.setExactTitle("Details Info");
        gotData = new ArrayList<>();

        displayName = (TextView) getActivity().findViewById(R.id.displayName);
        itemName = (TextView) getActivity().findViewById(R.id.itemName);
        refTemplate = (TextView) getActivity().findViewById(R.id.refTemplate);
        txtType = (TextView) getActivity().findViewById(R.id.txtType);
        typeState = (TextView) getActivity().findViewById(R.id.typeState);
        serialNumber = (TextView) getActivity().findViewById(R.id.serialNumber);
        manufacturer = (TextView) getActivity().findViewById(R.id.manufacturer);
        model = (TextView) getActivity().findViewById(R.id.model);
        status = (TextView) getActivity().findViewById(R.id.status);
        powerStatus = (TextView) getActivity().findViewById(R.id.powerStatus);
        updatedOn = (TextView) getActivity().findViewById(R.id.updatedOn);
        updatedBy = (TextView) getActivity().findViewById(R.id.updatedBy);
    }
}
