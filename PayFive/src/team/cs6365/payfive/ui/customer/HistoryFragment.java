package team.cs6365.payfive.ui.customer;

import java.util.ArrayList;
import java.util.List;
import team.cs6365.payfive.R;
import team.cs6365.payfive.model.MenuItem;
import team.cs6365.payfive.model.Transaction;
import team.cs6365.payfive.model.User;
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
public class HistoryFragment extends Fragment implements
		LoaderManager.LoaderCallbacks<List<MenuItem>> {

	private static final String TAG = "PayFive! - HistoryFragment";
	private static final boolean DEBUG = true;
	private HistoryAdapter mAdapter;
	private ListView lvHistory;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.frag_history, container,
				false);

		getActivity().setTitle("History");

		lvHistory = (ListView) rootView.findViewById(R.id.lv_menu_items);

		mAdapter = new HistoryAdapter(getActivity());

		lvHistory.setAdapter(mAdapter);

		List<Transaction> list = new ArrayList<Transaction>();

		Transaction t1 = new Transaction();
		t1.setAmount(12.50);
		t1.setDesc("Split pizza with George P. Burdell");
		// t1.setSender(new User("JK"));
		t1.setSendType(true);

		Transaction t2 = new Transaction();
		t2.setAmount(8.50);
		t2.setDesc("Lunch money return");
		// t1.setSender(new User("JK"));
		t2.setSendType(false);

		list.add(t1);
		list.add(t2);

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