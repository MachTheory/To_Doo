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


/**
 * A simple {@link Fragment} subclass.
 */
public class InProgress extends Fragment implements SharedPreferences.OnSharedPreferenceChangeListener{

    ListView inProgressList;
    ArrayList<String> inProgs;
    ArrayAdapter arrayAdapter;

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

        arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1,inProgs);

        inProgressList.setAdapter(arrayAdapter);

        Bundle bundle = this.getArguments();
        if(bundle != null){
            String s =bundle.getString("Key", "received");
            Log.d("Fragment Prog", "Received data!");
            inProgs.add(s);
            PrefConfig.progressListInPref(getContext(), inProgs);
            arrayAdapter.notifyDataSetChanged();
        }

        inProgressList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                //send data to next fragment
                Bundle bundle = new Bundle();
                Done fragC = new Done();
                bundle.putString("Key2", inProgs.get(i));
                fragC.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.frame_done, fragC).commit();
                Toast.makeText(getActivity(), "You Finished! 💥", Toast.LENGTH_SHORT).show();

                inProgs.remove(i);
                arrayAdapter.notifyDataSetChanged();
                PrefConfig.progressListInPref(getActivity(),inProgs);

                return true;
            }
        });

        if(inProgs.size() == 0){
            inProgs.clear();
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
        Log.d("In Progress", "Paused");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("In Progress", "Destroyed");
    }
}
