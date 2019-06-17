package com.apps.smartschoolmanagement.fragments;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.adapters.ListViewAdapter;
import com.apps.smartschoolmanagement.models.ListData;
import com.apps.smartschoolmanagement.utils.ProfileInfo;
import java.util.ArrayList;

public class ListFragment extends Fragment {
    ListView listView;
    public ArrayList<ListData> values = new ArrayList();

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.listview, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.listView = (ListView) view.findViewById(R.id.listview);
        if (ProfileInfo.getInstance().getSelectedTab() == 1) {
            this.values = ProfileInfo.getInstance().getStud();
        } else if (ProfileInfo.getInstance().getSelectedTab() == 2) {
            this.values = ProfileInfo.getInstance().getTeach();
        }
        this.listView.setAdapter(new ListViewAdapter(getActivity(), R.layout.item_layout_appointments, this.values));
    }
}
