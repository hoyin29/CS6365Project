/**
 * @author Jinhyun Kim
 * 
 * MainActivity is the starting point of PayFive application and holds
 * MenuListFragment, MenuItemFragment, ViewCartFragment, UpdateCartFragment,
 * ManageMenuFragment, EditMenuItemFragment, ManageAdminAcctFragment.
 * 
 *  MainActivity uses navigation drawer sample projects for the menu and
 *  switching fragments. The below is the license for the sample project.
 */

/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package team.cs6365.payfive.ui.main;

import java.util.Locale;

import team.cs6365.payfive.PayFive;
import team.cs6365.payfive.R;
import team.cs6365.payfive.R.array;
import team.cs6365.payfive.R.drawable;
import team.cs6365.payfive.R.id;
import team.cs6365.payfive.R.layout;
import team.cs6365.payfive.R.menu;
import team.cs6365.payfive.R.string;
import team.cs6365.payfive.ui.transaction.NewTransactionFragment;
import team.cs6365.payfive.ui.transaction.ScanToPayFragment;
import team.cs6365.payfive.ui.vendormenu.CustomerViewFragment;
import team.cs6365.payfive.ui.vendormenu.ItemMenuFragment;
import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

/**
 * This example illustrates a common usage of the DrawerLayout widget in the
 * Android support library.
 * <p/>
 * <p>
 * When a navigation (left) drawer is present, the host activity should detect
 * presses of the action bar's Up affordance as a signal to open and close the
 * navigation drawer. The ActionBarDrawerToggle facilitates this behavior. Items
 * within the drawer should fall into one of two categories:
 * </p>
 * <p/>
 * <ul>
 * <li><strong>View switches</strong>. A view switch follows the same basic
 * policies as list or tab navigation in that a view switch does not create
 * navigation history. This pattern should only be used at the root activity of
 * a task, leaving some form of Up navigation active for activities further down
 * the navigation hierarchy.</li>
 * <li><strong>Selective Up</strong>. The drawer allows the user to choose an
 * alternate parent for Up navigation. This allows a user to jump across an
 * app's navigation hierarchy at will. The application should treat this as it
 * treats Up navigation from a different task, replacing the current task stack
 * using TaskStackBuilder or similar. This is the only form of navigation drawer
 * that should be used outside of the root activity of a task.</li>
 * </ul>
 * <p/>
 * <p>
 * Right side drawers should be used for actions, not navigation. This follows
 * the pattern established by the Action Bar that navigation should be to the
 * left and actions to the right. An action should be an operation performed on
 * the current contents of the window, for example enabling or disabling a data
 * overlay on top of the current content.
 * </p>
 */
public class MainActivity extends Activity {
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private String[] mDrawerMenus;

	// drawer menus
	// private final int DRAWER_MENU_LIST = 0;
	// private final int DRAWER_VIEW_CART = 1;
	// private final int DRAWER_ADMIN_PANEL = 2;
	private final int NEW_TRANSACTION = 0;
	private final int SCAN_TO_PAY = 1;
	private final int HISTORY = 2;
	private final int DRAWER_ABOUT = 3;
	private final int ADMIN_ITEM_MENU = 4;
	private final int ADMIN_MENU = 4;
	private final int CUSTOMER_MENU = 5;

	protected PayFive payfive;

	public static String paypalUserEmail = "";
	public static String paypalUserName = "";
	public static String paypalUserPhone = "";

	public static boolean isLoggedIn = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		payfive = (PayFive) this.getApplication();

		mTitle = mDrawerTitle = getTitle();
		mDrawerMenus = getResources().getStringArray(R.array.drawer_menu);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		// set a custom shadow that overlays the main content when the drawer
		// opens
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);
		// set up the drawer's list view with items and click listener
		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, mDrawerMenus));
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		// enable ActionBar app icon to behave as action to toggle nav drawer
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		// ActionBarDrawerToggle ties together the the proper interactions
		// between the sliding drawer and the action bar app icon
		mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		mDrawerLayout, /* DrawerLayout object */
		R.drawable.ic_drawer, /* nav drawer image to replace 'Up' caret */
		R.string.drawer_open, /* "open drawer" description for accessibility */
		R.string.drawer_close /* "close drawer" description for accessibility */
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			selectItem(0);
		}
	}

	/* Called whenever we call invalidateOptionsMenu() */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If the nav drawer is open, hide action items related to the content
		// view
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		// menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	/* The click listener for ListView in the navigation drawer */
	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectItem(position);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		return super.onOptionsItemSelected(item);

	}

	private void selectItem(int position) {

		FragmentManager fragmentManager = getFragmentManager();
		Bundle args = new Bundle();

		switch (position) {

		case NEW_TRANSACTION:
			Fragment newTransactionFragment = new NewTransactionFragment();

			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, newTransactionFragment)
					.commit();
			break;

		case SCAN_TO_PAY:
			Fragment scanToPayFragment = new ScanToPayFragment();
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, scanToPayFragment).commit();
			break;

		case HISTORY:
			Fragment historyFragment = new HistoryFragment();
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, historyFragment).commit();
			break;

		case DRAWER_ABOUT:
			Fragment aboutFragment = new AboutFragment();
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, aboutFragment).commit();
			break;

		case ADMIN_MENU:
			Fragment itemMenuFragment = new ItemMenuFragment();
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, itemMenuFragment).commit();
			break;
		case CUSTOMER_MENU:
			Fragment customerViewFragment = new CustomerViewFragment();
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, customerViewFragment).commit();
			break;
		default:
			Fragment defaultFragment = new NewTransactionFragment();
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, defaultFragment).commit();
			break;

		}

		// update selected item and title, then close the drawer
		mDrawerList.setItemChecked(position, true);
		if (position != NEW_TRANSACTION)
			setTitle(mDrawerMenus[position]);
		mDrawerLayout.closeDrawer(mDrawerList);
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	// return true if paypalUserEmail is set (after logging in) else false
	public static Boolean isLoggedIn() {
		return (!paypalUserEmail.equals(""));
	}
}