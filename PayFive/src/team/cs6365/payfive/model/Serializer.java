package team.cs6365.payfive.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Serializer {
	public Serializer() {

	}

	public static byte[] serialize(Object obj) {
		if (obj == null)
			return null;

		ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
		ObjectOutputStream objOut = null;
		byte[] serialObj = null;
		try {
			objOut = new ObjectOutputStream(byteArrayOS);
			objOut.writeObject(obj);
			serialObj = byteArrayOS.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				objOut.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				byteArrayOS.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return serialObj;
	}

	public static Object deserialize(byte[] arr) {
		if (arr == null || arr.length == 0)
			return null;

		ByteArrayInputStream byteArrayIS = new ByteArrayInputStream(arr);
		ObjectInputStream objIn = null;
		Object obj = null;
		try {
			objIn = new ObjectInputStream(byteArrayIS);
			obj = objIn.readObject();
		} catch (StreamCorruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				objIn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				byteArrayIS.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return obj;
	}

	public static byte[] serializeImage(Context ctx, Object obj) {
		if (obj == null)
			return null;

		Bitmap bmp = (Bitmap) obj;
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
		return stream.toByteArray();
	}

	public static Bitmap deserializeImage(byte[] arr) {
		if (arr == null)
			return null;

		return BitmapFactory.decodeByteArray(arr, 0, arr.length);
	}
}
