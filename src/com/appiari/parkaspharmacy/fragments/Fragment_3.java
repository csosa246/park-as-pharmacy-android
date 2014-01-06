package com.appiari.parkaspharmacy.fragments;

import com.appiari.parkaspharmacy.R;

import android.support.v4.app.Fragment;
import android.text.Html;
import android.app.Activity;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Fragment_3 extends Fragment {
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_3, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		setupTextViews();
	}
	
	public void setupTextViews(){
		Activity a = getActivity();
		resources = a.getResources();
		pkg = a.getPackageName();
		Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Roboto/Roboto-Light.ttf");
		 profile = (ImageView) a.findViewById(R.id.imageview_profilepic);
		 tvCommonName = (TextView) a.findViewById(R.id.textview_common_name);
		 tvCommonName.setTypeface(typeface);
		 tvDistinguishingFeaturesTitle = (TextView) a.findViewById(R.id.textview_distinguishing_features_title);
		 tvDistinguishingFeaturesTitle.setTypeface(typeface);
		 tvDistinguishingFeatures = (TextView) a.findViewById(R.id.textview_distinguishing_features);
		 tvDistinguishingFeatures.setTypeface(typeface);
		 tvFlowerCharacteristicsTitle = (TextView) a.findViewById(R.id.textview_flower_characteristics_title);
		 tvFlowerCharacteristicsTitle.setTypeface(typeface);
		 tvFlowerCharacteristics = (TextView) a.findViewById(R.id.textview_flower_characteristics);
		 tvFlowerCharacteristics.setTypeface(typeface);
		 tvLeafCharacteristicsTitle = (TextView) a.findViewById(R.id.textview_leaf_characteristics_title);
		 tvLeafCharacteristicsTitle.setTypeface(typeface);
		 tvLeafCharacteristics = (TextView) a.findViewById(R.id.textview_leaf_characteristics);
		 tvLeafCharacteristics.setTypeface(typeface);
		 tvOtherInformationTitle = (TextView) a.findViewById(R.id.textview_otherinfo_title);
		 tvOtherInformationTitle.setTypeface(typeface);
		 tvOtherInformation = (TextView) a.findViewById(R.id.textview_otherinfo);
		 tvOtherInformation.setTypeface(typeface);
	}

	public void showText(String text) {
		Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
	}
	
	Resources resources;
	String pkg;
	ImageView profile;
	TextView tvCommonName,tvDistinguishingFeaturesTitle,tvDistinguishingFeatures,tvFlowerCharacteristicsTitle,tvFlowerCharacteristics,tvLeafCharacteristicsTitle,tvLeafCharacteristics,
	tvOtherInformationTitle,tvOtherInformation;
	public void setDatabaseCursor(Cursor cursor) {
		if (cursor.moveToFirst()) {
			do {
				String commonName = cursor.getString(cursor.getColumnIndex("COMMON_NAME"));
				tvCommonName.setText(commonName);
				
				String image = cursor.getString(cursor.getColumnIndex("IMAGE"));
				profile.setImageResource(resources.getIdentifier(image, "drawable", pkg));

				String distinguishingFeatures = cursor.getString(cursor.getColumnIndex("DISTINGUISHING_FEATURE"));
				distinguishingFeatures = bulletPoint(distinguishingFeatures);
				tvDistinguishingFeatures.setText(Html.fromHtml(distinguishingFeatures));
				
				String flowerCharacteristics="";
				flowerCharacteristics += "Flower Color:<br/>" + bulletPoint(cursor.getString(cursor.getColumnIndex("FLOWER_COLOR")));
				flowerCharacteristics += "<br/><br/>Flower Diameter (inches):<br/>" + bulletPoint(cursor.getString(cursor.getColumnIndex("FLOWER_DIAMETER_IN")));
				tvFlowerCharacteristics.setText(Html.fromHtml(flowerCharacteristics));
				
				String leafCharacteristics="";
				leafCharacteristics += "Petals:<br/>" + bulletPoint(cursor.getString(cursor.getColumnIndex("PETALS")));
				leafCharacteristics += "<br/><br/>Leaf Shape:<br/>" + bulletPoint(cursor.getString(cursor.getColumnIndex("LEAF_SHAPE")));
				leafCharacteristics += "<br/><br/>Leaf Margin:<br/>" + bulletPoint(cursor.getString(cursor.getColumnIndex("LEAF_MARGIN")));
				leafCharacteristics +=  "<br/><br/>Leaf Arrangement:<br/>" + bulletPoint(cursor.getString(cursor.getColumnIndex("LEAF_ARRANGEMENT")));
				tvLeafCharacteristics.setText(Html.fromHtml(leafCharacteristics));
				
				String otherInformation="";
				otherInformation += "Growth:<br/>" + bulletPoint(cursor.getString(cursor.getColumnIndex("GROWTH")));
				otherInformation += "<br/><br/>Blooming Period:<br/>" + bulletPoint(cursor.getString(cursor.getColumnIndex("BLOOMING_PERIOD")));
				otherInformation += "<br/><br/>Common Locations:<br/>" + bulletPoint(cursor.getString(cursor.getColumnIndex("COMMON_LOCATIONS")));
				otherInformation +=  "<br/><br/>Origin:<br/>" + bulletPoint(cursor.getString(cursor.getColumnIndex("ORIGIN")));
				otherInformation +=  "<br/><br/>Parts Used:<br/>" + bulletPoint(cursor.getString(cursor.getColumnIndex("PARTS_USED")));
				otherInformation +=  "<br/><br/>Medicinal Uses:<br/>" + bulletPoint(cursor.getString(cursor.getColumnIndex("MEDICINAL_USES")));
				otherInformation +=  "<br/><br/>Active Compounds:<br/>" + bulletPoint(cursor.getString(cursor.getColumnIndex("ACTIVE_COMPOUNDS")));
				otherInformation +=  "<br/><br/>Research Studies:<br/>" + bulletPoint(cursor.getString(cursor.getColumnIndex("RESEARCH_STUDIES")));
				tvOtherInformation.setText(Html.fromHtml(otherInformation));
				
			} while (cursor.moveToNext());
		}
	}
	
	public String bulletPoint(String text){
		text = "&#8226;  " + text;
		if (text.contains("|")){
			text = text.replaceAll("\\|", "<br/> &#8226;  ");
		}
		return text;
	}
}