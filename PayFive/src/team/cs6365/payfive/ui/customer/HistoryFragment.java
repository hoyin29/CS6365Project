package team.cs6365.payfive.ui.customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import team.cs6365.payfive.R;
import team.cs6365.payfive.database.TransactionDataSource;
import team.cs6365.payfive.model.Item;
import team.cs6365.payfive.model.Transaction;
import team.cs6365.payfive.model.User;
import android.app.Activity;
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
public class HistoryFragment extends Fragment {

	private static final String TAG = "PayFive! - HistoryFragment";
	private static final boolean DEBUG = true;
	private HistoryAdapter mAdapter;
	private ListView lvHistory;
	private Activity act;
	private List<Transaction> list;
	private String[] desc = {
		"Pizza Hut cost split",
		"public grocery cost split",
		"sushi at lunch with friends",
		"bar bill",
		"Bento food truck bill",
		"chinese food night!!!",
		"new korean restaurant dinner!!"
	};
	
	private String[] send = {
		"Sehoon",
		"Hoyin",
		"Kelly",
		"Emily"
	};
	
	private String[] recv = {
		"Jin",	
		"David",
		"Bryan",
		"Rachel"
	};
	
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

		list = new ArrayList<Transaction>();
		/*
		 * Transaction t = new Transaction(); User u = new User("JK",
		 * "jk@test.com"); t.setRecipient(u); t.setSendType(true);
		 * t.setAmount(11.99); t.setDate("1/1/2014"); t.setDesc(
		 * "We ordered pizza.\nDave paid.\nI'm paying him back for my portion\nK.Thx.Bye."
		 * );
		 * 
		 * Transaction t1 = new Transaction(); t1.setDate("2/2/2012");
		 * t1.setAmount(12.59);
		 * t1.setDesc("Split pizza with George P. Burdell"); t1.setRecipient(new
		 * User("Jin", "jin@gatech")); t1.setSendType(true);
		 * 
		 * Transaction t2 = new Transaction(); t2.setDate("3/24/2014");
		 * t2.setAmount(3); t2.setDesc("Lunch money return"); t2.setSender(new
		 * User("SG", "a@b.c")); t2.setSendType(false);
		 * 
		 * list.add(t); list.add(t1); list.add(t2);
		 */

		act = getActivity();
		createTransactions(7); // for real code, just load history db to list

		mAdapter.setItems(list);
		lvHistory.setAdapter(mAdapter);

		return rootView;
	}

	private void createTransactions(int n) {
		TransactionDataSource tds = new TransactionDataSource(act);
		tds.open();
		tds.drop();
		tds.create();
		Random r = new Random();
		for (int i = 0; i < n; ++i) {
			List<Item> l = new ArrayList<Item>();
			l.add(new Item("item" + i, r.nextDouble() * 10 + 1, "category" + i,
					"description" + i, ""));
			// Log.d(TAG, "size: " + l.size());
			// Log.d(TAG, "before: " + l.get(0).getName());
			Transaction t = new Transaction(i, l, new User(recv[r.nextInt(recv.length)], "123"),
					new User(send[r.nextInt(send.length)], "456"), r.nextDouble() * 10 + 10, "",
					desc[r.nextInt(desc.length)], r.nextBoolean());
			// Log.d(TAG, "after: " + t.getItems().get(0).getName());
			tds.addTransaction(t);

		}

		list = tds.getAllTransactions();
		tds.close();
	}

}