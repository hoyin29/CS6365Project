<<<<<<< HEAD
//package team.cs6365.payfive.admin;
//
//import team.cs6365.payfive.database.MenuItemDataSource;
//import team.cs6365.payfive.model.Item;
//
//import team.cs6365.payfive.R;
//import android.app.Activity;
//import android.app.ActionBar;
//import android.app.Fragment;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//import android.os.Build;
//
//public class AddItemActivity extends Activity {
//
//	private static final String TAG = "***ADD";
//	private EditText name, cate, desc, price;
//	private Button pic, cancel, save;
//	private Bitmap thumbnail;
//	private Item selected, updated;
//	private boolean isEdit;
//	private Intent result;
//	
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_add_item);
//		
//		/*
//		if (savedInstanceState == null) {
//			getFragmentManager().beginTransaction()
//					.add(R.id.container, new PlaceholderFragment()).commit();
//		}
//		*/
//		
//		name = (EditText) findViewById(R.id.etName);
//		cate = (EditText) findViewById(R.id.etCategory);
//		desc = (EditText) findViewById(R.id.etDescription);
//		price = (EditText) findViewById(R.id.etPrice);
//		thumbnail = BitmapFactory.decodeResource(getResources(), R.drawable.placeholder);
//		
//		pic = (Button) findViewById(R.id.bPicture);
//		pic.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				Toast.makeText(v.getContext(), "select pic from gallery", Toast.LENGTH_LONG).show();
//			}
//		});
//		
//		cancel = (Button) findViewById(R.id.bCancel);
//		cancel.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				Toast.makeText(v.getContext(), "no new item added", Toast.LENGTH_LONG).show();
//				finish();
//			}
//		});
//		
//		save = (Button) findViewById(R.id.bSave);
//		save.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				Toast.makeText(v.getContext(), "saving this new item", Toast.LENGTH_LONG).show();
//				save();
//				setResult(Activity.RESULT_OK, result);
//				Log.d(TAG, "sending result back to main activity");
//				finish();
//				Log.d(TAG, "finish AddItemActivitiy");
//			}
//		});
//		
//		Intent intent = getIntent();
//		int menuType = intent.getIntExtra("MENU", 1);
//		if(menuType == 2 || menuType == 1) {
//			Log.d(TAG, "this activity was triggered by edit");
//			isEdit = true;
//			name.setText(intent.getStringExtra("NAME"));
//			price.setText(intent.getStringExtra("PRICE"));
//			cate.setText(intent.getStringExtra("CATEGORY"));
//			desc.setText(intent.getStringExtra("DESCRIPTION"));
//			thumbnail = intent.getParcelableExtra("THUMBNAIL");
//			
//			selected = new Item(name.getText().toString(),
//					Double.valueOf(price.getText().toString()),
//					cate.getText().toString(),
//					desc.getText().toString(),
//					thumbnail);
//			
//			if(menuType == 1) {
//				Log.d(TAG, "this activity was triggered by view");
//				name.setEnabled(false);
//				price.setEnabled(false);
//				cate.setEnabled(false);
//				desc.setEnabled(false);
//				pic.setVisibility(View.GONE);
//				save.setVisibility(View.GONE);
//				cancel.setVisibility(View.GONE);
//			}
//		} else if(menuType == 0){
//			Log.d(TAG, "this activity was triggered by add");
//		} 
//	}
//
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.add_item, menu);
//		return true;
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		// Handle action bar item clicks here. The action bar will
//		// automatically handle clicks on the Home/Up button, so long
//		// as you specify a parent activity in AndroidManifest.xml.
//		int id = item.getItemId();
//		if (id == R.id.action_settings) {
//			return true;
//		}
//		return super.onOptionsItemSelected(item);
//	}
//
//	/**
//	 * A placeholder fragment containing a simple view.
//	 */
//	/*
//	public static class PlaceholderFragment extends Fragment {
//
//		public PlaceholderFragment() {
//		}
//
//		@Override
//		public View onCreateView(LayoutInflater inflater, ViewGroup container,
//				Bundle savedInstanceState) {
//			View rootView = inflater.inflate(R.layout.fragment_add_item,
//					container, false);
//			return rootView;
//		}
//	}	
//	*/
//
//	private void save() {
//		MenuItemDataSource ds = new MenuItemDataSource(this);
//		ds.open();
//		
//		if(isEdit) {
//			Log.d(TAG, "saving edit");
//			updated = new Item(name.getText().toString(),
//					Double.valueOf(price.getText().toString()),
//					cate.getText().toString(),
//					desc.getText().toString(),
//					thumbnail);
//			ds.updateMenuItem(selected, updated);
//			
//			result = new Intent();
//			result.putExtra("NAME", updated.getName());
//			result.putExtra("PRICE", String.valueOf(updated.getPrice()));
//			result.putExtra("CATEGORY", updated.getCategory());
//			result.putExtra("DESCRIPTION",updated.getDescription());
//			result.putExtra("THUMBNAIL", updated.getThumbnail());
//		} else {
//			Log.d(TAG, "saving add");
//			ds.addMenuItem(name.getText().toString(),
//					Double.valueOf(price.getText().toString()),
//					cate.getText().toString(),
//					desc.getText().toString(),
//					thumbnail);
//			
//			result = new Intent();
//			result.putExtra("NAME", name.getText().toString());
//			result.putExtra("PRICE", price.getText().toString());
//			result.putExtra("CATEGORY", cate.getText().toString());
//			result.putExtra("DESCRIPTION",desc.getText().toString());
//			result.putExtra("THUMBNAIL", thumbnail);
//		}
//		
//		ds.close();
//	}
//}
=======
package team.cs6365.payfive.admin;

import java.io.ByteArrayOutputStream;

import team.cs6365.payfive.database.MenuItemDataSource;
import team.cs6365.payfive.model.Item;
import team.cs6365.payfive.util.ImageConversion;

import team.cs6365.payfive.R;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Build;

public class AddItemActivity extends Activity {

	private static final String TAG = "***ADD";
	private EditText name, cate, desc, price;
	private Button pic, cancel, save;
	private byte[] thumbnailBytes;
	private Item selected, updated;
	private boolean isEdit;
	private Intent result;

	private Double valPrice = 0.0d;
	private String valName = "", valDesc = "", valCategory = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_item);

		/*
		 * if (savedInstanceState == null) {
		 * getFragmentManager().beginTransaction() .add(R.id.container, new
		 * PlaceholderFragment()).commit(); }
		 */

		name = (EditText) findViewById(R.id.et_item_name);
		cate = (EditText) findViewById(R.id.etCategory);
		desc = (EditText) findViewById(R.id.etDescription);
		price = (EditText) findViewById(R.id.etPrice);
		Bitmap bmp = BitmapFactory.decodeResource(getResources(),
				R.drawable.placeholder);
		thumbnailBytes = ImageConversion.compressBitmap(bmp);

		pic = (Button) findViewById(R.id.bPicture);
		pic.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(v.getContext(), "select pic from gallery",
						Toast.LENGTH_LONG).show();
			}
		});

		cancel = (Button) findViewById(R.id.bCancel);
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(v.getContext(), "no new item added",
						Toast.LENGTH_LONG).show();
				finish();
			}
		});

		save = (Button) findViewById(R.id.bSave);
		save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(v.getContext(), "saving this new item",
						Toast.LENGTH_LONG).show();
				save();
				setResult(Activity.RESULT_OK, result);
				Log.d(TAG, "sending result back to main activity");
				finish();
				Log.d(TAG, "finish AddItemActivitiy");
			}
		});

		Intent intent = getIntent();
		int menuType = intent.getIntExtra("MENU", 1);
		if (menuType == 2 || menuType == 1) {
			Log.d(TAG, "this activity was triggered by edit");
			this.getActionBar().setTitle("Edit Item");
			isEdit = true;
			name.setText(intent.getStringExtra("NAME"));
			price.setText(intent.getStringExtra("PRICE"));
			cate.setText(intent.getStringExtra("CATEGORY"));
			desc.setText(intent.getStringExtra("DESCRIPTION"));

			// thumbnail = intent.getParcelableExtra("THUMBNAIL");

			selected = new Item(name.getText().toString(), Double.valueOf(price
					.getText().toString()), cate.getText().toString(), desc
					.getText().toString(), thumbnailBytes);

			if (menuType == 1) {
				Log.d(TAG, "this activity was triggered by view");
				this.getActionBar().setTitle("View Item");
				name.setEnabled(false);
				price.setEnabled(false);
				cate.setEnabled(false);
				desc.setEnabled(false);
				pic.setVisibility(View.GONE);
				save.setVisibility(View.GONE);
				cancel.setVisibility(View.GONE);
			}
		} else if (menuType == 0) {
			Log.d(TAG, "this activity was triggered by add");
			this.getActionBar().setTitle("Add Item");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_item, menu);
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
	 * inflater.inflate(R.layout.fragment_add_item, container, false); return
	 * rootView; } }
	 */

	private void save() {
		MenuItemDataSource ds = new MenuItemDataSource(this);
		ds.open();

		if (isEdit) {
			Log.d(TAG, "saving edit");
			updated = new Item(name.getText().toString(), Double.valueOf(price
					.getText().toString()), cate.getText().toString(), desc
					.getText().toString(), thumbnailBytes);
			ds.updateMenuItem(selected, updated);

			result = new Intent();
			result.putExtra("NAME", updated.getName());
			result.putExtra("PRICE", String.valueOf(updated.getPrice()));
			result.putExtra("CATEGORY", updated.getCategory());
			result.putExtra("DESCRIPTION", updated.getDescription());
			result.putExtra("THUMBNAIL_BYTES", updated.getThumbnailBytes());
		} else {
			Log.d(TAG, "saving add");

			/* grab values from view */
			if (!price.getText().toString().equals("")) {
				valPrice = Double.valueOf(price.getText().toString());
			}
			valName = name.getText().toString();
			valCategory = cate.getText().toString();
			valDesc = desc.getText().toString();

			/* save */
			ds.addMenuItem(valName, valPrice, valCategory, valDesc,
					thumbnailBytes);

			result = new Intent();
			result.putExtra("NAME", name.getText().toString());
			result.putExtra("PRICE", price.getText().toString());
			result.putExtra("CATEGORY", cate.getText().toString());
			result.putExtra("DESCRIPTION", desc.getText().toString());

			result.putExtra("THUMBNAIL_BYTES", thumbnailBytes);
		}

		ds.close();
	}
}
>>>>>>> 706c59efba5c68aa10a162d2e08dd9cba5f78a09
