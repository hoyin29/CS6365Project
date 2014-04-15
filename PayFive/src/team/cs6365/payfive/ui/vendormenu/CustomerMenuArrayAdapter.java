package team.cs6365.payfive.ui.vendormenu;

import java.util.ArrayList;
import java.util.List;

import team.cs6365.payfive.R;
import team.cs6365.payfive.database.MenuItemDataSource;
import team.cs6365.payfive.model.Formatter;
import team.cs6365.payfive.model.Item;
import team.cs6365.payfive.model.Resizer;
import team.cs6365.payfive.ui.main.MainActivity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomerMenuArrayAdapter extends ArrayAdapter<Item> {

	private static final String TAG = "***MENUADP";
	private final Activity context;
	private final ArrayList<Item> values;
	private LayoutParams lp;

	public CustomerMenuArrayAdapter(Activity context, ArrayList<Item> values) {
		super(context, R.layout.row_item, values);
		this.context = context;
		this.values = values;
		lp = null;
	}

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
			viewHolder.pic = (ImageView) view.findViewById(R.id.ivPic);
			viewHolder.visible = (ImageView) view.findViewById(R.id.ivVisible);
			viewHolder.name = (TextView) view.findViewById(R.id.tvName);
			viewHolder.price = (TextView) view.findViewById(R.id.tvPrice);

			view.setTag(viewHolder);
			viewHolder.visible.setTag(values.get(position));
		} else {
			view = convertView;
			((ViewHolder) view.getTag()).visible.setTag(values.get(position));
		}

		final ViewHolder vh = (ViewHolder) view.getTag();
		Item i = values.get(position);

		if (i.getThumbnail().equals("")) {
			Log.d(TAG, "picPath is empty");

			if (context == null)
				Log.d(TAG, "context is null");
			if (context.getResources() == null)
				Log.d(TAG, "context resources is null");
			if (context.getResources().getDrawable(R.drawable.placeholder) == null)
				Log.d(TAG, "context resources drawable is null");

			if (vh.pic == null)
				Log.d(TAG, "vh pic is null");

			vh.pic.setImageDrawable(context.getResources().getDrawable(
					R.drawable.placeholder));
		} else {
			Log.d(TAG, "picPath is not empty");

			Log.d(TAG, "picPath: " + i.getThumbnail());
			
			if(i.getThumbnail().contains("@drawable")) {
				int imageResource = context.getResources().getIdentifier(i.getThumbnail(), null, context.getPackageName());
				Drawable res = context.getResources().getDrawable(imageResource);
				vh.pic.setImageDrawable(res);
			} else {
				vh.pic.setImageBitmap(Resizer.resizeImage(i.getThumbnail()));
			}
		}

		lp = vh.pic.getLayoutParams();
		lp.width = Resizer.WIDTH;
		lp.height = Resizer.HEIGHT;
		vh.pic.setLayoutParams(lp);

		Log.d(TAG, "after setting thumbnail");

		vh.name.setText(Formatter.formatName(i.getName()));
		vh.price.setText("$" + Formatter.formatPrice(i.getPrice()));
		vh.see = i.isVisible();
		vh.visible.setVisibility(View.GONE);
		return view;
	}

	static class ViewHolder {
		public ImageView pic, visible;
		public TextView name, price;
		public boolean see;
	}
}
