package com.appiari.parkaspharmacy.fragments;

import java.util.ArrayList;

import com.appiari.parkaspharmacy.MainActivity;
import com.appiari.parkaspharmacy.interfaces.Listeners;
import com.appiari.parkaspharmacy.R;

import android.support.v4.app.Fragment;
import android.app.Activity;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Fragment_1 extends Fragment {
	Listeners.CursorTransaction mListener;
	ListView listView;
	ArrayList<String> arrayList = new ArrayList<String>();

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (Listeners.CursorTransaction) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException("implementation error");
		}
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_1, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		setRetainInstance(true);
		setupListView();
		setupTextViews();
	}

	public void setupTextViews() {
		Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(),
				"fonts/Roboto/Roboto-Light.ttf");
		TextView titleMedicinal = (TextView) getActivity().findViewById(
				R.id.textview_medicinal_title);
		titleMedicinal.setTypeface(typeface);
	}

	public void setupListView() {
		// Getting arraylist for medicinal uses
		arrayList = ((MainActivity) getActivity()).getColumnValues(
				"MEDICINAL_USES_RESOURCES", null);
		listView = (ListView) getActivity().findViewById(
				R.id.listview_medicinal);
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_list_item_1, arrayList);
		listView.setAdapter(arrayAdapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				Cursor cursor = ((MainActivity) getActivity())
						.getLeavesByMedicinalProperties(arrayList.get(position));
				mListener.fragmentToActivity(2, cursor);
			}
		});
	}

	public void showText(String text) {
		Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
	}
}
