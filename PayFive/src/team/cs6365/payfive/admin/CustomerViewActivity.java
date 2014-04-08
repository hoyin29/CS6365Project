package team.cs6365.payfive.admin;

import java.util.ArrayList;

import team.cs6365.payfive.R;
import team.cs6365.payfive.database.MenuItemDataSource;
import team.cs6365.payfive.model.Item;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.os.Build;

public class CustomerViewActivity extends Activity {

	private static final String TAG = "***CUSTOMER";
	private ListView listview;
	private MenuItemArrayAdapter adapter;
	private ArrayList<Item> items;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_menu);

		/*
		 * if (savedInstanceState == null) {
		 * getFragmentManager().beginTransaction() .add(R.id.container, new
		 * PlaceholderFragment()).commit(); }
		 */

		listview = (ListView) findViewById(R.id.lv_item_menu);
		items = new ArrayList<Item>();

		adapter = new MenuItemArrayAdapter(this, items);
		listview.setAdapter(adapter);

		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, final View view,
					int position, long id) {
				final Item item = (Item) parent.getItemAtPosition(position);
				Log.d(TAG, "clicked on item" + position);
				view.showContextMenu();
			}
		});

		refreshListview();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.customer_view, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	/*
	 * public static class PlaceholderFragment extends Fragment {
	 * 
	 * public PlaceholderFragment() { }
	 * 
	 * @Override public View onCreateView(LayoutInflater inflater, ViewGroup
	 * container, Bundle savedInstanceState) { View rootView =
	 * inflater.inflate(R.layout.fragment_customer_view, container, false);
	 * return rootView; } }
	 */

	private void getAllVisibleItemsFromDB() {
		Log.d(TAG, "getting all visible items from DB");
		MenuItemDataSource ds = new MenuItemDataSource(this);
		ds.open();
		items = ds.getAllVisibleMenuItemsOnly();
		Toast.makeText(this, "retrieved " + items.size() + " items from DB",
				Toast.LENGTH_LONG).show();
		Log.d(TAG, "closing db");
		ds.close();
		Log.d(TAG, "notify adapter after getting all items from db");
		// adapter.notifyDataSetChanged();
	}

	private void refreshListview() {
		getAllVisibleItemsFromDB();
		adapter.notifyDataSetChanged();
		adapter = new MenuItemArrayAdapter(this, items);
		listview.setAdapter(adapter);
	}
}
