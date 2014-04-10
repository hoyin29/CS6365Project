package team.cs6365.payfive.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Resizer {
	
	public static final int WIDTH = 100;
	public static final int HEIGHT = 100;
	
	public Resizer() {
		
	}
	
	public static Bitmap resizeImageDimension(String path, int width, int height) {
		Bitmap orig = BitmapFactory.decodeFile(path);
		Bitmap resized = Bitmap.createScaledBitmap(orig, width, height, true);
		return resized;
	}
	
	public static Bitmap resizeImage(String path) {
        Bitmap bitmap=null;
        BitmapFactory.Options bfOptions=new BitmapFactory.Options();
        bfOptions.inDither=false;                     //Disable Dithering mode
        bfOptions.inPurgeable=true;                   //Tell to gc that whether it needs free memory, the Bitmap can be cleared
        bfOptions.inInputShareable=true;              //Which kind of reference will be used to recover the Bitmap data after being clear, when it will be used in the future
        bfOptions.inTempStorage=new byte[32 * 1024]; 
 
        File file=new File(path);
        FileInputStream fs=null;
        
        try {
            fs = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
 
        try {
            if(fs != null) {
                bitmap=BitmapFactory.decodeFileDescriptor(fs.getFD(), null, bfOptions);
            } 
        } catch (IOException e) {
            	e.printStackTrace();
        } finally {  
            if(fs!=null) {
                try {
                    fs.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
 
        return bitmap;
    }
}
