package team.cs6365.payfive.util;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageConversion {

	public static byte[] compressBitmap(Bitmap bmp) {
		/* compress image */
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
		byte[] bytes = stream.toByteArray();
		/* return the result bytes */
		return bytes;
	}

	public static Bitmap extractBitmap(byte[] bytes) {
		Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
		return bmp;
	}

}
