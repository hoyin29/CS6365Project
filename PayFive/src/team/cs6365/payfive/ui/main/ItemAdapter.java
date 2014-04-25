package team.cs6365.payfive.ui.main;

import java.util.ArrayList;
import java.util.List;

import team.cs6365.payfive.R;
import team.cs6365.payfive.model.Item;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A custom ArrayAdapter used by the {@link AppListFragment} to display the
 * device's installed applications.
 */
public class ItemAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private Context ctx;
	private List<Item> items;

	public ItemAdapter(Context ctx) {
		this.ctx = ctx;
		mInflater = (LayoutInflater) ctx
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		items = new ArrayList<Item>();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;

		if (convertView == null) {
			view = mInflater.inflate(R.layout.row_history, null);
		} else {
			view = convertView;
		}

		Item current = items.get(position);
		if (current != null) {

			Drawable thumbnail = ctx.getResources()
					.getDrawable(R.drawable.recv);
			((ImageView) view.findViewById(R.id.iv_thumbnail))
					.setImageDrawable(thumbnail);
			((TextView) view.findViewById(R.id.tv_desc)).setText(current
					.getName());

			((TextView) view.findViewById(R.id.tv_amount)).setText("$ "
					+ String.valueOf(current.getPrice()));
		}

		// view.findViewById(R.id.icon)).setImageDrawable(item.getThumbnail());

		return view;
	}

	public void setItems(List<Item> data) {
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
	public Item getItem(int position) {

		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
}
