package team.cs6365.payfive.admin;

import java.util.ArrayList;
import java.util.Random;

import team.cs6365.payfive.R;
import team.cs6365.payfive.database.MenuItemDataSource;
import team.cs6365.payfive.model.Item;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.provider.MediaStore;

public class ItemMenuFragment extends Fragment {

	private static final String TAG = "***ITEMMENU";
	private static final int MENU_ADD = 0;
	private static final int MENU_VIEW = 1;
	private static final int MENU_EDIT = 2;
	private static final int REQUEST_PICTURE = 0;
	private ListView listview;
	private MenuItemArrayAdapter adapter;
	private ArrayList<Item> items;
	private Activity ctx;
	private String picPath;
	private ImageView ivPic;
	private LayoutParams lp;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_item_menu,
				container, false);
		getActivity().setTitle("Item Menu");
		ctx = getActivity();
		picPath = "";
		ivPic = null;

		listview = (ListView) rootView.findViewById(R.id.listview);
		items = new ArrayList<Item>();

		adapter = new MenuItemArrayAdapter(ctx, items);
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

		setHasOptionsMenu(true);

		return rootView;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View view,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, view, menuInfo);
		Log.d(TAG, "context menu created");
		MenuInflater mi = ctx.getMenuInflater();
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
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.action_bar, menu);
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
			refreshListview();
			return true;
		case R.id.iEdit:
			contextMenuEdit(true, position);
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
			ctx.openContextMenu(getActionBarView());
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
		Window window = ctx.getWindow();
		View v = window.getDecorView();
		int resId = getResources().getIdentifier("action_bar_container", "id",
				"android");
		return v.findViewById(resId);
	}

	private void addItemsToDB(int n) {
		MenuItemDataSource ds = new MenuItemDataSource(ctx);
		ds.open();
		ds.drop();
		ds.create();
		Random r = new Random();
		// Bitmap bmap = BitmapFactory.decodeResource(getResources(),
		// R.drawable.placeholder);
		for (int i = 0; i < n; ++i) {
			Log.d(TAG, "adding item" + i);
			ds.addMenuItem("item" + i, r.nextDouble() * 10 + 1, "category" + i,
					"description" + i, "");
		}
		ds.close();
	}

	private void getAllItemsFromDB() {
		Log.d(TAG, "getting all items from DB");
		MenuItemDataSource ds = new MenuItemDataSource(ctx);
		ds.open();
		items = ds.getAllMenuItems();
		Toast.makeText(ctx, "retrieved " + items.size() + " items from DB",
				Toast.LENGTH_LONG).show();
		Log.d(TAG, "closing db");
		ds.close();
		Log.d(TAG, "notify adapter after getting all items from db");
		// adapter.notifyDataSetChanged();
	}

	private void actionBarAdd() {
		contextMenuEdit(false, -1);
	}

	private void actionBarSort() {

	}

	private void actionBarSearch() {

	}

	private void actionBarSettings() {

	}

	private void actionBarCustomerView() {
		Fragment customerViewFragment = new CustomerViewFragment();
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.content_frame, customerViewFragment).commit();
	}

	private void contextMenuDelete(int pos) {
		MenuItemDataSource ds = new MenuItemDataSource(ctx);
		ds.open();
		ds.deleteMenuItem(items.get(pos));
		ds.close();
		items.remove(pos);
	}

	private void refreshListview() {
		getAllItemsFromDB();
		adapter.notifyDataSetChanged();
		adapter = new MenuItemArrayAdapter(ctx, items);
		listview.setAdapter(adapter);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == REQUEST_PICTURE && resultCode == ctx.RESULT_OK
				&& data != null) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = ctx.getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();

			picPath = picturePath;

			Log.d(TAG, "got picture from gallery");
			Log.d(TAG, "pic path: " + picturePath);

			ivPic.setImageBitmap(BitmapFactory.decodeFile(picPath));
			lp = ivPic.getLayoutParams();
			lp.width = 300;
			lp.height = 300;
			ivPic.setLayoutParams(lp);
		}
	}

	private void contextMenuView(final int pos) {
		Log.d(TAG, "inside contextMenuView");
		final Dialog dialog = new Dialog(ctx);
		dialog.setContentView(R.layout.edit_item_dialog);

		Item curr = items.get(pos);

		MenuItemDataSource ds = new MenuItemDataSource(ctx);
		ds.open();
		curr = ds.getMenuItem(curr.getName(), curr.getPrice(),
				curr.getCategory(), curr.getDescription());
		items.set(pos, curr);
		ds.close();

		final EditText etName = (EditText) dialog
				.findViewById(R.id.et_item_name);
		final EditText etCate = (EditText) dialog.findViewById(R.id.etCategory);
		final EditText etDesc = (EditText) dialog
				.findViewById(R.id.etDescription);
		final EditText etPrice = (EditText) dialog.findViewById(R.id.etPrice);
		final CheckBox cbVis = (CheckBox) dialog.findViewById(R.id.cbVisible);
		final Button bPic = (Button) dialog.findViewById(R.id.bPicture);
		final Button bCancel = (Button) dialog.findViewById(R.id.bCancel);
		final Button bSave = (Button) dialog.findViewById(R.id.bSave);
		ivPic = (ImageView) dialog.findViewById(R.id.ivPic);

		dialog.setTitle("View Item");
		etName.setText(curr.getName());
		etCate.setText(curr.getCategory());
		etDesc.setText(curr.getDescription());
		etPrice.setText(String.valueOf(curr.getPrice()));
		cbVis.setChecked(curr.isVisible());

		Log.d(TAG, "picPath: " + curr.getThumbnail());

		if (curr.getThumbnail().equals("")) {
			Log.d(TAG, "picPath is empty - placeholder");
			ivPic.setImageDrawable(getResources().getDrawable(
					R.drawable.placeholder));
		} else {
			Log.d(TAG, "picPath is not empty - thumbnail");
			// ivPic.setImageBitmap(BitmapFactory.decodeFile(curr.getThumbnail()));
			ivPic.setImageURI(Uri.parse(curr.getThumbnail()));
		}

		// ivPic.setImageBitmap(BitmapFactory.decodeFile(picPath));
		lp = ivPic.getLayoutParams();
		lp.width = 300;
		lp.height = 300;
		ivPic.setLayoutParams(lp);

		etName.setEnabled(false);
		etPrice.setEnabled(false);
		etCate.setEnabled(false);
		etDesc.setEnabled(false);
		cbVis.setClickable(false);
		bPic.setVisibility(View.GONE);
		bSave.setVisibility(View.GONE);
		bCancel.setVisibility(View.GONE);

		dialog.show();
	}

	private void contextMenuEdit(final boolean isEdit, final int pos) {
		final Dialog dialog = new Dialog(ctx);
		dialog.setContentView(R.layout.edit_item_dialog);

		final MenuItemDataSource ds = new MenuItemDataSource(ctx);
		ds.open();

		final EditText etName = (EditText) dialog
				.findViewById(R.id.et_item_name);
		final EditText etCate = (EditText) dialog.findViewById(R.id.etCategory);
		final EditText etDesc = (EditText) dialog
				.findViewById(R.id.etDescription);
		final EditText etPrice = (EditText) dialog.findViewById(R.id.etPrice);
		final CheckBox cbVis = (CheckBox) dialog.findViewById(R.id.cbVisible);
		ivPic = (ImageView) dialog.findViewById(R.id.ivPic);

		if (isEdit) {
			dialog.setTitle("Edit Item");
			Item curr = items.get(pos);
			curr = ds.getMenuItem(curr.getName(), curr.getPrice(),
					curr.getCategory(), curr.getDescription());
			items.set(pos, curr);

			etName.setText(curr.getName());
			etCate.setText(curr.getCategory());
			etDesc.setText(curr.getDescription());
			etPrice.setText(String.valueOf(curr.getPrice()));
			cbVis.setChecked(curr.isVisible());
			if (curr.getThumbnail().equals("")) {
				Log.d(TAG, "picPath is empty - placeholder");
				ivPic.setImageDrawable(getResources().getDrawable(
						R.drawable.placeholder));
			} else {
				Log.d(TAG, "picPath is not empty - thumbnail");
				// ivPic.setImageBitmap(BitmapFactory.decodeFile(curr.getThumbnail()));
				ivPic.setImageURI(Uri.parse(curr.getThumbnail()));

				lp = ivPic.getLayoutParams();
				lp.width = 300;
				lp.height = 300;
				ivPic.setLayoutParams(lp);
			}
		} else
			dialog.setTitle("Add Item");

		Button bPic = (Button) dialog.findViewById(R.id.bPicture);
		bPic.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(i, REQUEST_PICTURE);
			}
		});

		Button bCancel = (Button) dialog.findViewById(R.id.bCancel);
		bCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		Button bSave = (Button) dialog.findViewById(R.id.bSave);
		bSave.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();

				Log.d(TAG, "saving picPath (" + picPath + ") to item");
				Item i = new Item(etName.getText().toString(), Double
						.valueOf(etPrice.getText().toString()), etCate
						.getText().toString(), etDesc.getText().toString(),
						picPath, cbVis.isChecked());

				if (isEdit) {
					ds.updateMenuItem(items.get(pos), i);
					items.remove(pos);
				} else {
					ds.addMenuItem(i);
				}

				items.add(i);
				ds.close();

				refreshListview();
			}
		});

		dialog.show();
	}
}
