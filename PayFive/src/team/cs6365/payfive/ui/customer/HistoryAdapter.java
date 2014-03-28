package team.cs6365.payfive.ui.customer;

import java.util.ArrayList;
import java.util.List;

import team.cs6365.payfive.PayFive;
import team.cs6365.payfive.R;
import team.cs6365.payfive.model.Item;
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

	private TextView tvDate, tvAmount, tvType, tvPerson;
	private ImageView ivType;
	private Drawable drawableType;

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

			tvDate = (TextView) view.findViewById(R.id.tv_date);
			tvAmount = (TextView) view.findViewById(R.id.tv_amount);
			tvType = (TextView) view.findViewById(R.id.tv_type);
			tvPerson = (TextView) view.findViewById(R.id.tv_person);
			ivType = (ImageView) view.findViewById(R.id.iv_type);

			String fromTo = "";

			tvDate.setText(current.getDate());
			tvAmount.setText(String.valueOf(current.getAmount()));
			if (current.isSendType()) {
				// if sending transaction
				drawableType = PayFive.getContext().getResources()
						.getDrawable(R.drawable.send);
				tvType.setText("sent to");
				tvPerson.setText(current.getRecipient().getName());
			} else {
				// if receiving transaction
				drawableType = PayFive.getContext().getResources()
						.getDrawable(R.drawable.recv);
				tvType.setText("received from");
				tvPerson.setText(current.getSender().getName());
			}
			ivType.setImageDrawable(drawableType);

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
