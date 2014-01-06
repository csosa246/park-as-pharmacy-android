package com.appiari.parkaspharmacy.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class About {
	Context context;

	public About(Context context) {
		this.context = context;
	}

	public void getAlert() {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);
		alertDialogBuilder.setTitle("About");
		alertDialogBuilder
				.setMessage(
						"Information Here:\n\n" + "Project Leader: Rena Lee\n"
								+ "Android Programmer: Crae Sosa")
				.setCancelable(false)
				.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {

					}
				});
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}
}