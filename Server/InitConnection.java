package Server;

import java.awt.*;
import java.io.*;
import java.net.*;

public class InitConnection{
		
	ServerSocket socket = null;
	DataInputStream password = null;
	DataOutputStream verify = null;
	String width="";
	String height="";
			
	InitConnection(int port,String value1){
		Robot robot = null;
		Rectangle rectangle = null;
		try{
			System.out.println("AWAITING CONNECTION FROM CLIENT");
			socket=new ServerSocket(port);
			
			GraphicsEnvironment gEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
			GraphicsDevice gDev = gEnv.getDefaultScreenDevice();
	
			Dimension dim=Toolkit.getDefaultToolkit().getScreenSize();
			String width=""+dim.getWidth();
			String height=""+dim.getHeight();
			rectangle=new Rectangle(dim);
			robot=new Robot(gDev);

			drawGUI();

			while(true){
				Socket sc=socket.accept();
				password=new DataInputStream(sc.getInputStream());
				verify=new DataOutputStream(sc.getOutputStream());
				String pssword=password.readUTF();
				
				if(pssword.equals(value1)){
					verify.writeUTF("valid");
					verify.writeUTF(width);
					verify.writeUTF(height);
					new SendScreen(sc,robot,rectangle);
					new ReceiveEvents(sc,robot);}
				else{
					verify.writeUTF("Invalid");
				}
			}
		}catch (Exception ex){
			ex.printStackTrace();
		}
	}
			
	private void drawGUI(){
	}

}
