package com.machtheory.todoo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class InProgress extends Fragment {

    ListView inProgressList;
    ArrayList<String> inProgs;

    public InProgress() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_in_progress, container, false);

        inProgressList = view.findViewById(R.id.inProgressList);
        inProgs = PrefConfig.readProgFromPref(getActivity());

        if(inProgs == null) {
            inProgs = new ArrayList<>();
        }


        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1,inProgs);

        inProgressList.setAdapter(arrayAdapter);

        Bundle bundle = this.getArguments();
        if(bundle != null){
            String s =bundle.getString("Key", "recived");
            Log.d("Fragment", "Received data!");
            inProgs.add(s);
            PrefConfig.progressListInPref(getContext(), inProgs);
            arrayAdapter.notifyDataSetChanged();
        }




        inProgressList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), "Moved to Done 💥", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        return view;

    }
}
