package com.appiari.parkaspharmacy;

import java.io.IOException;
import java.util.ArrayList;

import android.content.res.Configuration;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.appiari.parkaspharmacy.adapters.About;
import com.appiari.parkaspharmacy.database.DatabaseHelper;
import com.appiari.parkaspharmacy.fragments.Fragment_0;
import com.appiari.parkaspharmacy.fragments.Fragment_1;
import com.appiari.parkaspharmacy.fragments.Fragment_2;
import com.appiari.parkaspharmacy.fragments.Fragment_3;
import com.appiari.parkaspharmacy.interfaces.Listeners;
import com.appiari.parkaspharmacy.R;

public class MainActivity extends SherlockFragmentActivity implements
		Listeners.CursorTransaction {
	private DrawerLayout drawerLayout;
	private ListView drawerList;
	private ActionBarDrawerToggle drawerToggle;
	private DatabaseHelper papDatabase;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(null);
		setContentView(R.layout.activity_main);
		// Setup
		willSetupDrawer();
		willSetupActionBar();
		willSetupDatabase();
		willSetUpFragments();
	}

	public void willSetupDrawer() {
		String[] fragmentTitles = getResources().getStringArray(
				R.array.fragments);
		drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
		drawerList = (ListView) findViewById(R.id.drawer_list);
		drawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);
		drawerList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, fragmentTitles));
		drawerList.setOnItemClickListener(new DrawerItemClickListener());
		drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {
			public void onDrawerClosed(View v) {
				supportInvalidateOptionsMenu();
			}

			public void onDrawerOpened(View v) {
				supportInvalidateOptionsMenu();
			}
		};
		drawerLayout.setDrawerListener(drawerToggle);
	}

	public void willSetupActionBar() {
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
	}

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View v, int position,
				long id) {
			switchFragment(position, false);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			openCloseDrawer();
		}
		return super.onOptionsItemSelected(item);
	}

	public void openCloseDrawer() {
		if (drawerLayout.isDrawerOpen(drawerList)) {
			drawerLayout.closeDrawer(drawerList);
		} else {
			drawerLayout.openDrawer(drawerList);
		}
	}

	public void willSetupDatabase() {
		// Database setup
		papDatabase = new DatabaseHelper(this);
		try {
			papDatabase.createDataBase();
		} catch (IOException ioe) {
			throw new Error("Unable to create database");
		}

		try {
			papDatabase.openDataBase();
		} catch (SQLException sqle) {
			throw sqle;
		}
	}

	public Cursor getLeaves(String flowerColor, String leafShape,
			String leafMargin, String leafArrangement) {
		return papDatabase.getLeaves(flowerColor, leafShape, leafMargin,
				leafArrangement);
	}

	public Cursor getLeavesByMedicinalProperties(String medicinalProperty) {
		return papDatabase.getLeavesByMedicinalProperties(medicinalProperty);
	}

	public Cursor getByName(String name) {
		return papDatabase.getByName(name);
	}

	public ArrayList<String> getColumnValues(String column,
			String optionalResource) {
		ArrayList<String> values = new ArrayList<String>();
		Cursor ti = papDatabase.getColumnValues(column, optionalResource);
		if (ti.moveToFirst()) {
			do {
				String columnName = ti.getString(ti.getColumnIndex(column));
				values.add(columnName);
			} while (ti.moveToNext());
		}
		return values;
	}

	FragmentManager fragmentManager;
	Fragment_0 frag0;
	Fragment_1 frag1;
	Fragment_2 frag2;
	Fragment_3 frag3;

	// Setting up the fragments
	public void willSetUpFragments() {
		fragmentManager = getSupportFragmentManager();
		frag0 = new Fragment_0();
		frag1 = new Fragment_1();
		frag2 = new Fragment_2();
		frag3 = new Fragment_3();

		fragmentManager.beginTransaction().add(R.id.content_frame, frag0)
				.show(frag0).commit();
		fragmentManager.beginTransaction().add(R.id.content_frame, frag1)
				.hide(frag1).commit();
		fragmentManager.beginTransaction().add(R.id.content_frame, frag2)
				.hide(frag2).commit();
		fragmentManager.beginTransaction().add(R.id.content_frame, frag3)
				.hide(frag3).commit();

		fragmentArray.add(frag0);
		fragmentArray.add(frag1);
		fragmentArray.add(frag2);
		fragmentArray.add(frag3);
		// This is for positioning of fragment back stack
		setPosition(0);
	}

	ArrayList<Fragment> fragmentArray = new ArrayList<Fragment>();

	public void switchFragment(int position, boolean isBack) {

		if (position == 4) {
			About about = new About(this);
			about.getAlert();
			return;
		}

		if (!isBack) {
			setPosition(position);
		}

		Fragment frag = fragmentArray.get(position);
		if (frag.isHidden()) {
			for (int i = 0; i < fragmentArray.size(); i++) {
				if (i == position) {
					fragmentManager
							.beginTransaction()
							.setCustomAnimations(R.anim.slide_in_left,
									R.anim.slide_in_right)
							.show(fragmentArray.get(i)).commit();
				} else {
					fragmentManager
							.beginTransaction()
							.setCustomAnimations(R.anim.slide_in_left,
									R.anim.slide_in_right)
							.hide(fragmentArray.get(i)).commit();
				}
			}
		}
		drawerLayout.closeDrawer(drawerList);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		drawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		drawerToggle.onConfigurationChanged(newConfig);
	}

	// FRAGMENT COMMUNICATORS
	@Override
	public void fragmentToActivity(int position, Cursor cursor) {
		// TODO Auto-generated method stub
		if (position == 2) {
			frag2.setDatabaseCursor(cursor);
		} else if (position == 3) {
			frag3.setDatabaseCursor(cursor);
		}
		switchFragment(position, false);
	}

	// Controls fragment back
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (position.size() > 1) {
				switchFragment(position.get(position.size() - 2), true);
				position.remove(position.size() - 1);
			}
			return true;
		} else if (keyCode == KeyEvent.KEYCODE_MENU) {
			openCloseDrawer();
		}
		return super.onKeyDown(keyCode, event);
	}

	ArrayList<Integer> position = new ArrayList<Integer>();

	public void setPosition(int currentPosition) {
		position.add(currentPosition);
	}
}