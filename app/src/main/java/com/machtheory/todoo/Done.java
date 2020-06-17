package com.machtheory.todoo;

import android.content.SharedPreferences;
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
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Done extends Fragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    ListView doneList;
    ArrayList<String> done;

    public Done() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_done, container, false);

        doneList = view.findViewById(R.id.doneList);
        done = PrefConfig.readDoneFromPref(getActivity());

        if(done == null) {
            done = new ArrayList<>();
        }

        final ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, done);

        doneList.setAdapter(arrayAdapter);

       Bundle bundle = this.getArguments();
        if (bundle != null) {
            String s = bundle.getString("Key2", "received");
            Log.d("Fragment Done", "Received data!");
            done.add(s);
            PrefConfig.doneListInPref(getContext(), done);
            arrayAdapter.notifyDataSetChanged();
        }



        doneList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Toast.makeText(getActivity(), done.get(i), Toast.LENGTH_SHORT).show();
                    done.remove(i);
                    arrayAdapter.notifyDataSetChanged();
                    PrefConfig.doneListInPref(getActivity(), done);
                }
                });

        if(done.size() == 0){
            done.clear();
            arrayAdapter.notifyDataSetChanged();
        }
            return view;
        }



    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("Done", "Paused");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("Done", "Destroyed");
    }
}


