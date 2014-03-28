package team.cs6365.payfive.ui.customer;

import java.util.ArrayList;
import java.util.List;
import team.cs6365.payfive.R;
import team.cs6365.payfive.model.Item;
import team.cs6365.payfive.model.Transaction;
import team.cs6365.payfive.model.User;
import team.cs6365.payfive.ui.loader.ItemLoader;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

/**
 * MenuListFragment shows a list of available menu at the vendor.
 * 
 * @author Jin
 */
public class HistoryFragment extends Fragment implements
		LoaderManager.LoaderCallbacks<List<Item>> {

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
		lvHistory.setEmptyView(rootView.findViewById(android.R.id.empty));

		mAdapter = new HistoryAdapter(getActivity());

		lvHistory.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// new transaction info dialog fragment

				TransactionInfoDialogFragment dFrag = new TransactionInfoDialogFragment();

				// attach the selected Transaction object in bundle
				Bundle b = new Bundle();
				Transaction selected = mAdapter.getItem(position);
				b.putSerializable("Transaction", selected);
				dFrag.setArguments(b);

				/* Search for dialog that exists from before */
				FragmentManager fragmentManager = getFragmentManager();
				FragmentTransaction fragmentTransaction = fragmentManager
						.beginTransaction();
				TransactionInfoDialogFragment tPrev = (TransactionInfoDialogFragment) fragmentManager
						.findFragmentByTag("transaction_info_dialog");
				if (tPrev != null) // if exists, remove it first
					fragmentTransaction.remove(tPrev);
				// show dialog
				dFrag.show(fragmentTransaction, "transaction_info_dialog");
			}
		});

		List<Transaction> list = new ArrayList<Transaction>();

		Transaction t = new Transaction();
		User u = new User("JK", "jk@test.com");
		t.setRecipient(u);
		t.setSendType(true);
		t.setAmount(11.99);
		t.setDate("1/1/2014");
		t.setDesc("We ordered pizza.\nDave paid.\nI'm paying him back for my portion\nK.Thx.Bye.");

		Transaction t1 = new Transaction();
		t1.setDate("2/2/2012");
		t1.setAmount(12.59);
		t1.setDesc("Split pizza with George P. Burdell");
		t1.setRecipient(new User("Jin", "jin@gatech"));
		t1.setSendType(true);

		Transaction t2 = new Transaction();
		t2.setDate("3/24/2014");
		t2.setAmount(3);
		t2.setDesc("Lunch money return");
		t2.setSender(new User("SG", "a@b.c"));
		t2.setSendType(false);

		list.add(t);
		list.add(t1);
		list.add(t2);

		mAdapter.setItems(list);
		lvHistory.setAdapter(mAdapter);

		return rootView;
	}

	/**********************/
	/** LOADER CALLBACKS **/
	/**********************/

	@Override
	public Loader<List<Item>> onCreateLoader(int arg0, Bundle arg1) {
		if (DEBUG)
			Log.i(TAG, "#_#_#_# onCreateLoader() called! #_#_#_#");
		return new ItemLoader(getActivity());
	}

	@Override
	public void onLoadFinished(Loader<List<Item>> arg0, List<Item> arg1) {
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
	public void onLoaderReset(Loader<List<Item>> arg0) {
		if (DEBUG)
			Log.i(TAG, "#_#_#_# onLoaderReset() called! #_#_#_#");
		/*
		 * mAdapter.setData(null);
		 */

	}
}