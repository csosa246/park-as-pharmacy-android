package com.appiari.parkaspharmacy.interfaces;

import android.database.Cursor;

public class Listeners {
	public interface CursorTransaction {
		void fragmentToActivity(int position,Cursor cursor);
	}
}
