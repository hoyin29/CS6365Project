package team.cs6365.payfive.admin;

import java.util.ArrayList;

import team.cs6365.payfive.R;
import team.cs6365.payfive.database.MenuItemDataSource;
import team.cs6365.payfive.model.Item;
import team.cs6365.payfive.model.Serializer;
import team.cs6365.payfive.ui.customer.NewTransactionFragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class CustomerViewFragment extends Fragment {

	private static final String TAG = "***CUSTOMER";
	private ListView listview;
	private MenuItemArrayAdapter adapter;
	private ArrayList<Item> items;
	private Activity ctx;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_item_menu,
				container, false);

		ctx = getActivity();
		ctx.setTitle("Customer View");

		listview = (ListView) rootView.findViewById(R.id.listview);
		listview.setEmptyView(rootView.findViewById(android.R.id.empty));
		items = new ArrayList<Item>();

		adapter = new MenuItemArrayAdapter(ctx, items);
		listview.setAdapter(adapter);

		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, final View view,
					int position, long id) {
				final Item item = (Item) parent.getItemAtPosition(position);
				Log.d(TAG, "clicked on item" + position);
				Bundle bundle = new Bundle();
				bundle.putByteArray("ITEM",
						Serializer.serialize(items.get(position)));
				Fragment newTransactionFragment = new NewTransactionFragment();
				newTransactionFragment.setArguments(bundle);
				FragmentManager fragmentManager = getFragmentManager();
				fragmentManager.beginTransaction()
						.replace(R.id.content_frame, newTransactionFragment)
						.commit();
			}
		});

		refreshListview();
		return rootView;
	}

	private void getAllVisibleItemsFromDB() {
		Log.d(TAG, "getting all visible items from DB");
		MenuItemDataSource ds = new MenuItemDataSource(ctx);
		ds.open();
		items = ds.getAllVisibleMenuItemsOnly();
		Toast.makeText(ctx, "retrieved " + items.size() + " items from DB",
				Toast.LENGTH_LONG).show();
		Log.d(TAG, "closing db");
		ds.close();
		Log.d(TAG, "notify adapter after getting all items from db");
		// adapter.notifyDataSetChanged();
	}

	private void refreshListview() {
		getAllVisibleItemsFromDB();
		adapter.notifyDataSetChanged();
		adapter = new MenuItemArrayAdapter(ctx, items);
		listview.setAdapter(adapter);
	}
}
