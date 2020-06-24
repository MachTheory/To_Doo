package com.machtheory.todoo;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    ArrayAdapter arrayAdapter;

    public InProgress() {
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.menus, menu);
        super.onCreateOptionsMenu(menu, menuInflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id){
            case R.id.clear_text:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Delete All Items in In Progress");
                builder.setMessage("Are you sure you want to clear all items?");
                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        inProgs.clear();
                        arrayAdapter.notifyDataSetChanged();
                        PrefConfig.listInPref(getActivity(), inProgs);
                    }
                });

                builder.setNegativeButton("No, don't clear items", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                builder.show();

                break;
        }

        return super.onOptionsItemSelected(item);
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
