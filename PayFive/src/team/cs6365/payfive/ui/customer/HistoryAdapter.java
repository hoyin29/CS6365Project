package team.cs6365.payfive.ui.customer;

import java.util.ArrayList;
import java.util.List;

import team.cs6365.payfive.R;
import team.cs6365.payfive.model.MenuItem;
import team.cs6365.payfive.model.Transaction;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A custom ArrayAdapter used by the {@link AppListFragment} to display the
 * device's installed applications.
 */
public class HistoryAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private Context ctx;
	private List<Transaction> items;

	public HistoryAdapter(Context ctx) {
		this.ctx = ctx;
		mInflater = (LayoutInflater) ctx
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		items = new ArrayList<Transaction>();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;

		if (convertView == null) {
			view = mInflater.inflate(R.layout.row_history, null);
		} else {
			view = convertView;
		}

		Transaction current = items.get(position);
		if (current != null) {

			((TextView) view.findViewById(R.id.tv_desc)).setText(current
					.getDesc());
			((TextView) view.findViewById(R.id.tv_amount)).setText("$ "
					+ String.valueOf(String.valueOf(current.getAmount())));
			((TextView) view.findViewById(R.id.tv_date)).setText(current
					.getDate());
			Drawable thumbnail;
			String fromTo = "";

			if (current.isSendType()) {
				// if sending transaction
				thumbnail = ctx.getResources().getDrawable(R.drawable.send);
				// TODO fromTo = current.getRecipient().getName();
			} else {
				// if receiving transaction
				thumbnail = ctx.getResources().getDrawable(R.drawable.recv);
				// TODO fromTo = current.getSender().getName();
			}

			((ImageView) view.findViewById(R.id.iv_thumbnail))
					.setImageDrawable(thumbnail);
			// TODO
			((TextView) view.findViewById(R.id.tv_from_to))
					.setText("George P. Burdell");
		}

		return view;
	}

	public void setItems(List<Transaction> data) {
		if (items != null)
			items.clear();
		if (data != null) {
			items = data;
		}
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.size();
	}

	@Override
	public Transaction getItem(int position) {

		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
}
