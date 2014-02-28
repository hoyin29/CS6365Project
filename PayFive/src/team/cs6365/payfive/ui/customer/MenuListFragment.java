package team.cs6365.payfive.ui.customer;

import java.util.ArrayList;
import java.util.List;
import team.cs6365.payfive.R;
import team.cs6365.payfive.model.MenuItem;
import team.cs6365.payfive.ui.loader.MenuItemLoader;
import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

/**
 * MenuListFragment shows a list of available menu at the vendor.
 * 
 * @author Jin
 */
public class MenuListFragment extends Fragment implements
		LoaderManager.LoaderCallbacks<List<MenuItem>> {

	private static final String TAG = "PayFive! - MenuListFragment";
	private static final boolean DEBUG = true;
	private MenuItemAdapter mAdapter;
	private ListView lvMenuItems;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.frag_menu_list, container,
				false);

		getActivity().setTitle("Menu List");

		lvMenuItems = (ListView) rootView.findViewById(R.id.lv_menu_items);

		mAdapter = new MenuItemAdapter(getActivity());

		lvMenuItems.setAdapter(mAdapter);

		MenuItem m1 = new MenuItem();
		m1.setName("Hot Dog");
		m1.setPrice(1.99);

		MenuItem m2 = new MenuItem();
		m2.setName("Pretzel");
		m2.setPrice(0.99);

		List<MenuItem> list = new ArrayList<MenuItem>();
		list.add(m1);
		list.add(m2);

		mAdapter.setItems(list);

		return rootView;
	}

	/**********************/
	/** LOADER CALLBACKS **/
	/**********************/

	@Override
	public Loader<List<MenuItem>> onCreateLoader(int arg0, Bundle arg1) {
		if (DEBUG)
			Log.i(TAG, "#_#_#_# onCreateLoader() called! #_#_#_#");
		return new MenuItemLoader(getActivity());
	}

	@Override
	public void onLoadFinished(Loader<List<MenuItem>> arg0, List<MenuItem> arg1) {
		if (DEBUG)
			Log.i(TAG, "#_#_#_# onLoadFinished() called! #_#_#_#");
		/*
		 * mAdapter.setData(data);
		 * 
		 * if (isResumed()) { setListShown(true); } else {
		 * setListShownNoAnimation(true); } }
		 */

	}

	@Override
	public void onLoaderReset(Loader<List<MenuItem>> arg0) {
		if (DEBUG)
			Log.i(TAG, "#_#_#_# onLoaderReset() called! #_#_#_#");
		/*
		 * mAdapter.setData(null);
		 */

	}
}