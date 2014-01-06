package com.appiari.parkaspharmacy.adapters;
//package com.appiari.parkaspharmacyabs.models;
//
//import com.appiari.parkaspharmacyabs.R;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//
// class LeafListViewCache {
//
//		private View		baseView;
//		private TextView	textNameCity;
//		private TextView	textWikiCity;
//		private ImageView	imageCity;
//
//		public LeafListViewCache ( View baseView ) {
//			
//			this.baseView = baseView;
//		}
//
//		public View getViewBase (  ) {
//			return baseView;
//		}
//
//		public TextView getTextNameCity (int resource) {
//			if ( textNameCity == null ) {
//				textNameCity = ( TextView ) baseView.findViewById(R.id.cityName);
//			}
//			return textNameCity;
//		}
//
//		public TextView getTextWikiCity (int resource) {
//			if ( textWikiCity == null ) {
//				textWikiCity = ( TextView ) baseView.findViewById(R.id.cityLinkWiki);
//			}
//			return textWikiCity;
//		}
//
//		public ImageView getImageView (int resource) {
//			if ( imageCity == null ) {
//				imageCity = ( ImageView ) baseView.findViewById(R.id.ImageCity);
//			}
//			return imageCity;
//		}
//	}