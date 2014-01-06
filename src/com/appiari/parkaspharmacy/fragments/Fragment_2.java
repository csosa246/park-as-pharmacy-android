package com.appiari.parkaspharmacy.fragments;

import java.util.ArrayList;
import java.util.List;

import com.appiari.parkaspharmacy.MainActivity;
import com.appiari.parkaspharmacy.adapters.Leaf;
import com.appiari.parkaspharmacy.adapters.LeafListAdapterWithCache;
import com.appiari.parkaspharmacy.interfaces.Listeners;
import com.appiari.parkaspharmacy.R;

import android.support.v4.app.Fragment;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class Fragment_2 extends Fragment{
    private ListView listViewCity;
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		return inflater.inflate(R.layout.fragment_2, null);
	}
	
    Listeners.CursorTransaction mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (Listeners.CursorTransaction) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement Listeners");
        }
    }
	
    RelativeLayout relativeViewEmpty;
	List<Leaf> listLeaf= new ArrayList<Leaf>();

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		Activity activity = getActivity();
        listViewCity = (ListView) activity.findViewById( R.id.leaf_list);
        relativeViewEmpty = (RelativeLayout) activity.findViewById(R.id.relativelayout_emptylist);
        listViewCity.setAdapter(new LeafListAdapterWithCache(getActivity(), R.layout.list_row, listLeaf ));
	}
	
	public void showText(String text){
		Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
	}
	
	String databaseString;
	public void setDatabaseCursor(Cursor cursor){
		listLeaf.clear();
		
		if (cursor.moveToFirst()) {
			relativeViewEmpty.setVisibility(View.GONE);
			do {
				String commonName = cursor.getString(cursor.getColumnIndex("COMMON_NAME"));
				String imageName = cursor.getString(cursor.getColumnIndex("IMAGE"));
				listLeaf.add(new Leaf(commonName,null,imageName,0));
			} while (cursor.moveToNext());
		}else{
			relativeViewEmpty.setVisibility(View.VISIBLE);
		}
		
        listViewCity.setAdapter(new LeafListAdapterWithCache(getActivity(), R.layout.list_row, listLeaf ));
        listViewCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				Cursor cursor = ((MainActivity) getActivity()).getByName(listLeaf.get(position).getName());
				mListener.fragmentToActivity(3, cursor);
			}
		});
	}
}