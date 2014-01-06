package com.appiari.parkaspharmacy.adapters;

import java.util.ArrayList;

import com.appiari.parkaspharmacy.MainActivity;
import com.appiari.parkaspharmacy.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

public class HorizontalSelectorModel {
	Context context;
	TableRow tableRow;
	Typeface typeface;
	Resources resources;
	String pkg;
	String resourceLocation;
	boolean showText=false;

	public void setResourceLocation(String resourceLocation) {
		this.resourceLocation = resourceLocation;
	}
	// Optional
	ArrayList<String> textArray = new ArrayList<String>();
	ArrayList<String> resourceArray = new ArrayList<String>();
	String columnName; 
	
	public HorizontalSelectorModel(Context context,boolean showText,String columnName) {
		this.context = context;
		this.showText = showText;
		this.columnName = columnName;
		tableRow = new TableRow(this.context);
		resources = context.getResources();
		pkg = context.getPackageName();
		if(showText){
			typeface = Typeface.createFromAsset(this.context.getAssets(),"fonts/Roboto/Roboto-Light.ttf");
		}
		
		//SETTING DATA HERE
		textArray = ((MainActivity) this.context).getColumnValues(columnName,null);
		resourceArray = ((MainActivity) this.context).getColumnValues(columnName+"_RESOURCES",null);
	}

	public void setTextArray(ArrayList<String> textArray) {
		this.textArray = textArray;
	}

	public void setResourceArray(ArrayList<String> resourceArray) {
		this.resourceArray = resourceArray;
	}

	ArrayList<View> list = new ArrayList<View>();
	int selected=0;
	ArrayList<Integer> selectedArray = new ArrayList<Integer>();

	public TableRow getView() {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// TODO Auto-generated method stub
		for (int i = 0; i < resourceArray.size(); i++) {
			final View convertView;
			ViewHolder holder;
			convertView = inflater.inflate(R.layout.gridinflater, null);
			holder = new ViewHolder();
			holder.ImgThumb = (ImageView) convertView.findViewById(R.id.imgThumb);
			holder.ImhText = (TextView) convertView.findViewById(R.id.imgText);
			holder.ImhText.setTypeface(typeface);
			if(showText){
				holder.ImhText.setText(textArray.get(i));
			}else{
				holder.ImhText.setHeight(0);
			}
			holder.ImgThumb.setBackgroundResource(resources.getIdentifier(resourceArray.get(i), resourceLocation, pkg));
			
			convertView.setTag(String.valueOf(i));
			convertView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					selected = Integer
							.parseInt(convertView.getTag().toString());
					if (selected==0){
						selectedArray.clear();
					}else if(selectedArray.contains(selected)){
						selectedArray.remove(selectedArray.indexOf(selected));
					}else{
						selectedArray.add(selected);
					}
					updateView();
				}

			});
			list.add(convertView);
			tableRow.addView(convertView);
			//updateView();
		}
		return tableRow;
	}
	
	public void updateView() {
		for (int i = 0; i < list.size(); i++) {
			View v = list.get(i);
			v.setBackgroundColor(Color.TRANSPARENT);
			if (selectedArray.contains(i)) {
				v.setBackgroundColor(Color.BLACK);
			}
		}
	}	
	public String getSelected(){
		String toBeSearched = "";
		for(int i = 0; i < selectedArray.size() ; i++){
			int sel = selectedArray.get(i);
			String get = textArray.get(sel);
			toBeSearched += " OR " + columnName + " LIKE " + "'%" + get + "%'";
		}
		toBeSearched = "(" + toBeSearched.replaceFirst(" OR ", "") + ")";
		if(selectedArray.isEmpty()){
			return null;
		}
		return toBeSearched;
	}
	
	public class ViewHolder {
		ImageView ImgThumb;
		TextView ImhText;
	}

}