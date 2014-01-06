package com.appiari.parkaspharmacy.fragments;

import com.appiari.parkaspharmacy.MainActivity;
import com.appiari.parkaspharmacy.adapters.HorizontalSelectorModel;
import com.appiari.parkaspharmacy.interfaces.Listeners;
import com.appiari.parkaspharmacy.R;

import android.support.v4.app.Fragment;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class Fragment_0 extends Fragment {

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
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_0, null);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		setupTextviews();
		setupHorizontalScrollViews();
		setupHorizontalSelectors();
		setupButtons();
	}
	Typeface robotoTypeface;

	public void setupTextviews() {
		robotoTypeface = Typeface.createFromAsset(getActivity().getAssets(),
				"fonts/Roboto/Roboto-Light.ttf");
		// Color
		TextView textViewLeafIDColor = (TextView) getActivity().findViewById(
				R.id.textview_leafID_color);
		textViewLeafIDColor.setTypeface(robotoTypeface);
		//Shape
		TextView textViewLeafIDShape = (TextView) getActivity().findViewById(
				R.id.textview_leafID_shape);
		textViewLeafIDShape.setTypeface(robotoTypeface);
		//Margin
		TextView textViewLeafIDMargin = (TextView) getActivity().findViewById(
				R.id.textview_leafID_margin);
		textViewLeafIDMargin.setTypeface(robotoTypeface);
		//Arrangement
		TextView textViewLeafIDArrangment = (TextView) getActivity().findViewById(
				R.id.textview_leafID_arrangement);
		textViewLeafIDArrangment.setTypeface(robotoTypeface);
	}

	HorizontalScrollView flowerColorHorizontalScrollView,leafShapeHorizontalScrollView,leafMarginHorizontalScrollView,leafArrangementHorizontalScrollView;
	HorizontalSelectorModel flowerColorHorizontalSelector,leafShapeHorizontalSelector,leafMarginHorizontalSelector,leafArrangementHorizontalSelector;

	public void setupHorizontalScrollViews() {
		flowerColorHorizontalScrollView = (HorizontalScrollView) getActivity()
				.findViewById(R.id.horizontalscroll_leafID_color);
		leafShapeHorizontalScrollView = (HorizontalScrollView) getActivity()
				.findViewById(R.id.horizontalscroll_leafID_images);
		leafMarginHorizontalScrollView = (HorizontalScrollView) getActivity()
				.findViewById(R.id.horizontalscroll_leafID_margin);
		leafArrangementHorizontalScrollView = (HorizontalScrollView) getActivity()
				.findViewById(R.id.horizontalscroll_leafID_arrangement);	
		}
	

	public void setupHorizontalSelectors() {
		Context act = getActivity();
		//Flower Color 
		flowerColorHorizontalSelector = new HorizontalSelectorModel(act,false,"FLOWER_COLOR");
		flowerColorHorizontalSelector.setResourceLocation("color");
		TableRow flowerColorView = flowerColorHorizontalSelector.getView();
		flowerColorHorizontalScrollView.addView(flowerColorView);
		//Leaf Shape
		leafShapeHorizontalSelector = new HorizontalSelectorModel(act,true,"LEAF_SHAPE");
		leafShapeHorizontalSelector.setResourceLocation("drawable");
		TableRow leafShapeView = leafShapeHorizontalSelector.getView();
		leafShapeHorizontalScrollView.addView(leafShapeView);
		//Margin 
		leafMarginHorizontalSelector = new HorizontalSelectorModel(act,true,"LEAF_MARGIN");
		leafMarginHorizontalSelector.setResourceLocation("drawable");
		TableRow leafMarginView = leafMarginHorizontalSelector.getView();
		leafMarginHorizontalScrollView.addView(leafMarginView);
		//Arrangement 
		leafArrangementHorizontalSelector = new HorizontalSelectorModel(act,true,"LEAF_ARRANGEMENT");
		leafArrangementHorizontalSelector.setResourceLocation("drawable");
		TableRow leafArrangementView = leafArrangementHorizontalSelector.getView();
		leafArrangementHorizontalScrollView.addView(leafArrangementView);	
	}

	public void showText(String text) {
		Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
	}
		
	public void setupButtons(){
		Button button = (Button) getActivity().findViewById(R.id.button_search);
		button.setTypeface(robotoTypeface);
		button.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String colorString = flowerColorHorizontalSelector.getSelected();
				String leafShapeString = leafShapeHorizontalSelector.getSelected();
				String leafMarginString = leafMarginHorizontalSelector.getSelected();
				String leafArrangementString = leafArrangementHorizontalSelector.getSelected();
				//Uncomfortable putting this here ////messy
				if (colorString!=null | leafShapeString!=null | leafMarginString!=null | leafArrangementString!=null){
					Cursor cursor = ((MainActivity) getActivity()).getLeaves(colorString, leafShapeString,leafMarginString,leafArrangementString);
			        mListener.fragmentToActivity(2, cursor);
				}else{
					showText("Please select at least one option from one category.");
				}
			}		
		});
	}
}
