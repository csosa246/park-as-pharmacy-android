package com.appiari.parkaspharmacy.adapters;

import java.util.ArrayList;
import java.util.List;

import com.appiari.parkaspharmacy.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LeafListAdapterWithCache extends BaseAdapter {
	private LayoutInflater l_Inflater;
	List<Leaf> leafArray = new ArrayList<Leaf>();
	Resources resources;
	String pkg;
	int resID;
	Typeface typeface;

	public LeafListAdapterWithCache(Context context, int resID,
			List<Leaf> listLeaf) {
		this.leafArray = listLeaf;
		this.resID = resID;
		this.resources = context.getResources();
		this.pkg = context.getPackageName();
		l_Inflater = LayoutInflater.from(context);
		typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto/Roboto-Light.ttf");
//		getLeafs();
	}

	public int getCount() {
		return leafArray.size();
	}

	public Object getItem(int position) {
		return leafArray.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = l_Inflater.inflate(resID, null);
			holder = new ViewHolder();
			holder.txt_itemName = (TextView) convertView
					.findViewById(R.id.cityName);
			holder.itemImage = (ImageView) convertView
					.findViewById(R.id.ImageCity);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		
		
		//Find some way to cache the images....fuck 
		holder.txt_itemName.setTypeface(typeface);
		holder.txt_itemName.setText(leafArray.get(position).getName());
		holder.itemImage.setBackgroundResource(resources.getIdentifier(leafArray.get(position).getImage(), "drawable", pkg));
		holder.itemImage.setImageResource(resources.getIdentifier(leafArray.get(position).getImage(), "drawable", pkg));
		holder.itemImage.getDrawingCache();
		return convertView;
	}
	
//	List<Integer> leafs = new ArrayList<Integer>();
//	
//	public List<Integer> getLeafs(){
//		
//		leafs.add(R.drawable.mock_strawberry);
//		leafs.add(R.drawable.canadian_goldenrod);
//		leafs.add(R.drawable.chickwee_maidenwort);
//		leafs.add(R.drawable.evening_primrose);
//		leafs.add(R.drawable.hyacinth);
//		leafs.add(R.drawable.rome);
//		leafs.add(R.drawable.knotgrass_doorweed);
//		leafs.add(R.drawable.lanceolate);
//		leafs.add(R.drawable.common_burdock);
//		leafs.add(R.drawable.stjohns_wort);
//		return leafs;
//		
//	}

	static class ViewHolder {
		TextView txt_itemName;
		ImageView itemImage;
	}
}