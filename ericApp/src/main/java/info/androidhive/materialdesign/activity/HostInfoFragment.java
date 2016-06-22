package info.androidhive.materialdesign.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import info.androidhive.materialdesign.R;


/**
 * Created by new on 6/19/2016.
 */
public class HostInfoFragment extends Fragment {

    private ListView infoList;
    private String[] values;
    private HomeConnectionWithFrag homeCon;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.host_info_layout,container,false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        infoList.setAdapter(adapter);
    }

    private void init() {
        infoList = (ListView) getActivity().findViewById(R.id.listHostInfo);

        values = new String[] { "Console Interface",
                "Mgmt Interface",
                "Build part Interface Primary a",
                "Build part Interface Primary b",
                "Build part Interface 10GB",
                "Build part Interface 1GB",
                "Build part Interface 10GB",
                "More data to be added",
                "More data to be added",
                "More data to be added",
                "More data to be added"
        };

        homeCon = (HomeConnectionWithFrag)getActivity();
        homeCon.setExactTitle("Host Info");
    }
}
