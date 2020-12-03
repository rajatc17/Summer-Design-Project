package Client;

import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

class ReceiveScreen extends Thread{
	private JPanel SCREEN_PANEL = null;
	private boolean VERBOSE = true;
	InputStream oin = null;
	Image image1 = null;

	public ReceiveScreen(InputStream in,JPanel p){
		oin = in;
		SCREEN_PANEL = p;
		start();
	}

	public void run(){
		try{
			//Read screenshots of the client and then draw them
			while(VERBOSE){
				byte[] bytes = new byte[1024*1024];
				int count = 0;
				do{
					count+=oin.read(bytes,count,bytes.length-count);
				}while(!(count>4 && bytes[count-2]==(byte)-1 && bytes[count-1]==(byte)-39));

				image1 = ImageIO.read(new ByteArrayInputStream(bytes));
				image1 = image1.getScaledInstance(SCREEN_PANEL.getWidth(),SCREEN_PANEL.getHeight(),Image.SCALE_FAST);

				//Draw the received screenshots

				Graphics graphics = SCREEN_PANEL.getGraphics();
				graphics.drawImage(image1, 0, 0, SCREEN_PANEL.getWidth(), SCREEN_PANEL.getHeight(), SCREEN_PANEL);
			}

		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}
}
