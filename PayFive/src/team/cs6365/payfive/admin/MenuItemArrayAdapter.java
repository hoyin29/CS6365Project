package team.cs6365.payfive.admin;

import java.util.ArrayList;

import team.cs6365.payfive.R;
import team.cs6365.payfive.database.MenuItemDataSource;
import team.cs6365.payfive.model.Item;
import team.cs6365.payfive.util.ImageConversion;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuItemArrayAdapter extends ArrayAdapter<Item> {

	private static final String TAG = "***MENUADP";
	private final Activity context;
	private final ArrayList<Item> values;
	private boolean isCustomer;
	private LayoutParams lp;

	public MenuItemArrayAdapter(Activity context, ArrayList<Item> values) {
		super(context, R.layout.row_item, values);
		this.context = context;
		this.values = values;
		if (context.getClass().getSimpleName().equals("CustomerViewActivity")) {
			Log.d(TAG, "calling adapter for customer view activity");
			isCustomer = true;
		} else {
			isCustomer = false;
		}
		
		lp = null;
	}

	/*
	 * @Override public View getView(int position, View convertView, ViewGroup
	 * parent) { LayoutInflater inflater = (LayoutInflater) context
	 * .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	 * 
	 * View rowView = inflater.inflate(R.layout.row, parent, false); MenuItem m
	 * = values.get(position);
	 * 
	 * ImageView pic = (ImageView) rowView.findViewById(R.id.ivPic); TextView
	 * name = (TextView) rowView.findViewById(R.id.tvName); TextView price =
	 * (TextView) rowView.findViewById(R.id.tvPrice);
	 * 
	 * //pic.setBackgroundDrawable(new
	 * BitmapDrawable(values[position].getThumbnail()));
	 * pic.setImageBitmap(m.getThumbnail()); name.setText(m.getName());
	 * price.setText("$" + m.getPrice());
	 * 
	 * return rowView; }
	 */

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = null;
		// final Item m = values.get(position);

		// reuse views
		if (convertView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			view = inflater.inflate(R.layout.row_item, null);

			// configure view holder
			final ViewHolder viewHolder = new ViewHolder();
<<<<<<< HEAD
			viewHolder.pic = (ImageView) view.findViewById(R.id.ivThumb);
			viewHolder.visible = (ImageView) view.findViewById(R.id.ivVisible);
			viewHolder.name = (TextView) view.findViewById(R.id.tvName);
			viewHolder.price = (TextView) view.findViewById(R.id.tvPrice);
			
			viewHolder.visible.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Log.d(TAG, "toggle visibility (now - if): " + viewHolder.see);
					
					if(viewHolder.see) {
						viewHolder.see = false;
						viewHolder.visible.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.eye_off));
					}
					else {
						viewHolder.see = true;
						viewHolder.visible.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.eye_on));
					}
					
					//values.get(position).setVisible(viewHolder.see);
					updateVisibility(values.get(position), viewHolder.see);
				}
			});
	
=======
			viewHolder.pic = (ImageView) view.findViewById(R.id.ivPic);
			// viewHolder.visible = (ImageView)
			// view.findViewById(R.id.ivVisible);
			viewHolder.name = (TextView) view.findViewById(R.id.tvName);
			viewHolder.price = (TextView) view.findViewById(R.id.tvPrice);

			// viewHolder.visible.setOnClickListener(new OnClickListener() {
			// @Override
			// public void onClick(View v) {
			// Log.d(TAG, "toggle visibility (now - if): "
			// + viewHolder.see);
			//
			// if (viewHolder.see) {
			// viewHolder.see = false;
			// viewHolder.visible.setImageBitmap(BitmapFactory
			// .decodeResource(context.getResources(),
			// R.drawable.eye_off));
			// } else {
			// viewHolder.see = true;
			// viewHolder.visible.setImageBitmap(BitmapFactory
			// .decodeResource(context.getResources(),
			// R.drawable.eye_on));
			// }
			//
			// values.get(position).setVisible(viewHolder.see);
			// updateVisibility(values.get(position), viewHolder.see);
			// // MenuItemArrayAdapter.this.notifyDataSetChanged();
			// }
			// });

>>>>>>> 706c59efba5c68aa10a162d2e08dd9cba5f78a09
			view.setTag(viewHolder);
			// viewHolder.visible.setTag(values.get(position));
		} else {
			view = convertView;
			// ((ViewHolder)
			// view.getTag()).visible.setTag(values.get(position));
		}

		final ViewHolder vh = (ViewHolder) view.getTag();
		Item i = values.get(position);
<<<<<<< HEAD
		
		if(i.getThumbnail().equals("")) {
			Log.d(TAG, "picPath is empty");
			
			if(context == null)
				Log.d(TAG, "context is null");
			if(context.getResources() == null)
				Log.d(TAG, "context resources is null");
			if(context.getResources().getDrawable(R.drawable.placeholder) == null)
				Log.d(TAG, "context resources drawable is null");
			
			if(vh.pic == null)
				Log.d(TAG, "vh pic is null");
			
			vh.pic.setImageDrawable(context.getResources().getDrawable(R.drawable.placeholder));
		} else {
			Log.d(TAG, "picPath is not empty");
			
			//vh.pic.setImageBitmap(BitmapFactory.decodeFile(i.getThumbnail()));	
			vh.pic.setImageURI(Uri.parse(i.getThumbnail()));
		}
		
		lp = vh.pic.getLayoutParams();
		lp.width = 100;
		lp.height = 100;
		vh.pic.setLayoutParams(lp);
		
		Log.d(TAG, "after setting thumbnail");
		
		vh.name.setText(i.getName());
		vh.price.setText("$" + i.getPrice());
		vh.see = i.isVisible();
		
		if(vh.see) {
			vh.visible.setImageResource(R.drawable.eye_on);
		} else {
			vh.visible.setImageResource(R.drawable.eye_off);
		}

		if(isCustomer)
			vh.visible.setVisibility(View.GONE);
	
=======
		vh.pic.setImageBitmap(ImageConversion.extractBitmap(i.getThumbnailBytes()));
		vh.name.setText(i.getName());
		vh.price.setText("$" + i.getPrice());
		// vh.see = i.isVisible();

		// if (vh.see) {
		// vh.visible.setImageResource(R.drawable.eye_on);
		// } else {
		// vh.visible.setImageResource(R.drawable.eye_off);
		// }

		/*
		 * vh.visible.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { Log.d(TAG, position +
		 * " toggle visibility (now - else): " + vh.see); if(vh.see) { vh.see =
		 * false; vh.visible.setImageResource(R.drawable.eye_off); } else {
		 * vh.see = true; vh.visible.setImageResource(R.drawable.eye_on); }
		 * updateVisibility(values.get(position), vh.see);
		 * //MenuItemArrayAdapter.this.notifyDataSetChanged(); } });
		 */
		// if (isCustomer) {
		// vh.visible.setVisibility(View.GONE);
		// }

>>>>>>> 706c59efba5c68aa10a162d2e08dd9cba5f78a09
		return view;
	}

	static class ViewHolder {
		public ImageView pic; // , visible;
		public TextView name, price;
		public boolean see;
	}
<<<<<<< HEAD
	
	private void updateVisibility(Item old, boolean v) {
		MenuItemDataSource ds = new MenuItemDataSource(context);
		ds.open();
		Item temp = new Item(old.getName(),
				old.getPrice(), 
				old.getCategory(), 
				old.getDescription(), 
				old.getThumbnail(), 
				v);
		ds.updateMenuItem(old, temp);
	
		ds.close();
	}
}

=======
>>>>>>> 706c59efba5c68aa10a162d2e08dd9cba5f78a09

//	private void updateVisibility(Item old, boolean v) {
//		MenuItemDataSource ds = new MenuItemDataSource(context);
//		ds.open();
//		Item temp = new Item(old.getName(), old.getPrice(), old.getCategory(),
//				old.getDescription(), old.getThumbnail(), v);
//		ds.updateMenuItem(old, temp);
//		ds.close();
//	}
}
