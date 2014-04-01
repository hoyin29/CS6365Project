package team.cs6365.payfive.admin;

import java.util.ArrayList;
import java.util.Random;

import team.cs6365.payfive.R;
import team.cs6365.payfive.database.MenuItemDataSource;
import team.cs6365.payfive.model.Item;
import team.cs6365.payfive.util.ImageConversion;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.Toast;

public class ItemMenuActivity extends Activity {

	private static final String TAG = "***MAIN";
	private static final int MENU_ADD = 0;
	private static final int MENU_VIEW = 1;
	private static final int MENU_EDIT = 2;
	private ListView listview;
	private MenuItemArrayAdapter adapter;
	private ArrayList<Item> items;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_menu);

		/*
		 * if (savedInstanceState == null) {
		 * getSupportFragmentManager().beginTransaction() .add(R.id.container,
		 * new PlaceholderFragment()).commit(); }
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

		addItemsToDB(20);
		refreshListview();
		registerForContextMenu(listview);
		Log.d(TAG, "done with onCreate()");
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View view,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, view, menuInfo);
		Log.d(TAG, "context menu created");
		MenuInflater mi = getMenuInflater();
		// mi.inflate(R.menu.main, menu);

		Log.d(TAG, "view id: " + view.getId() + " - (sort: " + (R.id.iASort)
				+ ")");
		if (view.getId() == R.id.iASort) {
			Log.d(TAG, "action bar sort context menu pressed");
			mi.inflate(R.menu.actionbar_sort_menu, menu);
		} else {
			Log.d(TAG, "listview item context menu pressed");
			mi.inflate(R.menu.listview_menu, menu);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Log.d(TAG, "option menu created");
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.action_bar, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		Log.d(TAG, "context item selected");
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		int position = (int) info.id;

		switch (item.getItemId()) {
		case R.id.iDelete:
			contextMenuDelete(position);
			// adapter.notifyDataSetChanged();
			refreshListview();
			return true;
		case R.id.iEdit:
			contextMenuEdit(position);
			// adapter.notifyDataSetChanged();
			refreshListview();
			return true;
		case R.id.iView:
			contextMenuView(position);
			return true;
		}

		return super.onContextItemSelected(item);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.iAAdd:
			actionBarAdd();
			return true;
		case R.id.iASort:
			this.openContextMenu(getActionBarView());
			return true;
		case R.id.iASearch:
			actionBarSearch();
			return true;
		case R.id.iASettings:
			actionBarSettings();
			return true;
		case R.id.iACustomerView:
			actionBarCustomerView();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public View getActionBarView() {
		Window window = getWindow();
		View v = window.getDecorView();
		int resId = getResources().getIdentifier("action_bar_container", "id",
				"android");
		return v.findViewById(resId);
	}

	/*
	 * @Override public void onStart() { super.onStart(); ActionBar actionBar =
	 * getSupportActionBar(); actionBar.show(); }
	 */

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
	 * inflater.inflate(R.layout.fragment_main, container, false); return
	 * rootView; } }
	 */

	private void addItemsToDB(int n) {
		MenuItemDataSource ds = new MenuItemDataSource(this);
		ds.open();
		ds.drop();
		ds.create();
		Random r = new Random();
		Bitmap bmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.placeholder);
		for (int i = 0; i < n; ++i) {
			Log.d(TAG, "adding item" + i);
			ds.addMenuItem("item" + i, r.nextDouble() * 10 + 1, "category" + i,
					"description" + i, ImageConversion.compressBitmap(bmap));
		}
		ds.close();
	}

	private void getAllItemsFromDB() {
		Log.d(TAG, "getting all items from DB");
		MenuItemDataSource ds = new MenuItemDataSource(this);
		ds.open();
		items = ds.getAllMenuItems();
		Toast.makeText(this, "retrieved " + items.size() + " items from DB",
				Toast.LENGTH_LONG).show();
		Log.d(TAG, "closing db");
		ds.close();
		Log.d(TAG, "notify adapter after getting all items from db");
		// adapter.notifyDataSetChanged();
	}

	private void actionBarAdd() {
		Intent intent = new Intent(this, AddItemActivity.class);
		intent.putExtra("MENU", MENU_ADD);
		startActivityForResult(intent, MENU_ADD);
	}

	private void actionBarSort() {

	}

	private void actionBarSearch() {

	}

	private void actionBarSettings() {

	}

	private void actionBarCustomerView() {
		startActivity(new Intent(this, CustomerViewActivity.class));
	}

	private void contextMenuView(int pos) {
		startActivityForResult(prepareIntent(pos, MENU_VIEW), MENU_VIEW);
	}

	private void contextMenuEdit(int pos) {
		Intent intent = prepareIntent(pos, MENU_EDIT);
		items.remove(pos);
		startActivityForResult(intent, MENU_EDIT);
	}

	private Intent prepareIntent(int pos, int menuType) {
		Intent intent = new Intent(this, AddItemActivity.class);
		Item item = items.get(pos);
		intent.putExtra("MENU", menuType);
		intent.putExtra("NAME", item.getName());
		intent.putExtra("PRICE", String.valueOf(item.getPrice()));
		intent.putExtra("CATEGORY", item.getCategory());
		intent.putExtra("DESCRIPTION", item.getDescription());
		intent.putExtra("THUMBNAIL_BYTES", item.getThumbnailBytes());

		return intent;
	}

	private void contextMenuDelete(int pos) {
		MenuItemDataSource ds = new MenuItemDataSource(this);
		ds.open();
		ds.deleteMenuItem(items.get(pos));
		ds.close();
		items.remove(pos);
	}

	private void refreshListview() {
		getAllItemsFromDB();
		adapter.notifyDataSetChanged();
		adapter = new MenuItemArrayAdapter(this, items);
		listview.setAdapter(adapter);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);

		if (requestCode == MENU_ADD || requestCode == MENU_EDIT) {
			if (resultCode == RESULT_OK) {
				Log.d(TAG, "return from AddItemActivity with result");
				double valPrice = 0.0d;
				if (!intent.getStringExtra("PRICE").equals("")) {
					valPrice = Double.valueOf(intent.getStringExtra("PRICE"));
				}
				Item res = new Item(intent.getStringExtra("NAME"), valPrice,
						intent.getStringExtra("CATEGORY"),
						intent.getStringExtra("DESCRIPTION"),
						intent.getByteArrayExtra("THUMBNAIL_BYTES"));

				items.add(res);
			} else {
				Log.d(TAG, "return from AddItemActivity without result");
			}
		}
		/*
		 * switch (requestCode) { case MENU_ADD: if (resultCode == RESULT_OK) {
		 * Log.d(TAG, "return from AddItemActivity with result"); Item res = new
		 * Item(intent.getStringExtra("NAME"),
		 * Double.valueOf(intent.getStringExtra("PRICE")),
		 * intent.getStringExtra("CATEGORY"),
		 * intent.getStringExtra("DESCRIPTION"), (Bitmap)
		 * intent.getParcelableExtra("THUMBNAIL"));
		 * 
		 * items.add(res); } else { Log.d(TAG,
		 * "return from AddItemActivity without result"); } }
		 */
		refreshListview();
	}

}
