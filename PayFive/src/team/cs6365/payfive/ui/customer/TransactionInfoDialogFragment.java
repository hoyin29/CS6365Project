package team.cs6365.payfive.ui.customer;

import team.cs6365.payfive.PayFive;
import team.cs6365.payfive.R;
import team.cs6365.payfive.model.Transaction;
import team.cs6365.payfive.model.User;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class TransactionInfoDialogFragment extends DialogFragment {

	private TextView tvDate, tvAmount, tvType, tvPerson, tvDesc;
	private Drawable drawableType;
	private ImageView ivType;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frag_transaction_info_dialog,
				null);

		/* grab transaction object being passed in bundle */
		Bundle b = getArguments();
		Transaction current = (Transaction) b.getSerializable("Transaction");

		getDialog().setTitle("Transaction Info");

		tvDate = (TextView) view.findViewById(R.id.tv_date);
		tvAmount = (TextView) view.findViewById(R.id.tv_amount);
		tvType = (TextView) view.findViewById(R.id.tv_type);
		tvPerson = (TextView) view.findViewById(R.id.tv_person);
		tvDesc = (TextView) view.findViewById(R.id.tv_desc);
		ivType = (ImageView) view.findViewById(R.id.iv_type);

		// if object is type of Transaction

		/* test object */
		Transaction t = new Transaction();
		User u = new User("JK");
		t.setRecipient(u);
		t.setSendType(true);
		t.setAmount(11.99);
		t.setDate("1/1/2014");
		t.setDesc("We ordered pizza.\nDave paid.\nI'm paying him back for my portion\nK.Thx.Bye.");

		t = current;

		/* set the view elements with values */
		tvDate.setText(t.getDate());
		tvAmount.setText(String.valueOf(t.getAmount()));
		if (t.isSendType()) {
			tvType.setText("Sent to");
			tvPerson.setText(t.getRecipient().getName());
			drawableType = PayFive.getContext().getResources()
					.getDrawable(R.drawable.send);
		} else {
			tvType.setText("Received from");
			tvPerson.setText(t.getSender().getName());
			drawableType = PayFive.getContext().getResources()
					.getDrawable(R.drawable.recv);
		}
		Context c = PayFive.getContext();
		tvDesc.setText(t.getDesc());
		ivType.setImageDrawable(drawableType);

		return view;
	}

}
