package Client;

import java.awt.BorderLayout;
import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.net.Socket;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import java.io.IOException;

class CreateFrame extends Thread {
	String width="", height="";
	private JFrame frame = new JFrame();

	//JDesktopPane represents the main container that will contain all connected clients' screens

	private JDesktopPane desktop = new JDesktopPane();
	private Socket host_socket = null;
	private JInternalFrame interFrame = new JInternalFrame("Server Screen", true, true, true);
	private JPanel SCREEN_PANEL = new JPanel();

	public CreateFrame(Socket host_socket, String width, String height) {

		this.width = width;
		this.height = height;
		this.host_socket = host_socket;
		start();
	}
	
	//Draw GUI per each connected client

	public void drawGUI() {
		frame.add(desktop, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Show the frame in maximized state
	
		frame.setExtendedState(frame.getExtendedState()|JFrame.MAXIMIZED_BOTH);		//CHECK THIS LINE
		frame.setVisible(true);
		interFrame.setLayout(new BorderLayout());
		interFrame.getContentPane().add(SCREEN_PANEL, BorderLayout.CENTER);
		interFrame.setSize(100,100);
		desktop.add(interFrame);

		try {
			//Initially show the internal frame maximized
			interFrame.setMaximum(true);
			}catch (PropertyVetoException ex) { 
				ex.printStackTrace();
		}

		//This allows to handle KeyListener events
		SCREEN_PANEL.setFocusable(true);
		interFrame.setVisible(true);
		
	}

	public void run() { 
		//Used to read screenshots
		InputStream in = null;
		//start drawing GUI
		drawGUI();

		try{
			in = host_socket.getInputStream();
			}catch (IOException ex){
			ex.printStackTrace();
		}

		//Start receiving screenshots
		new ReceiveScreen(in,SCREEN_PANEL);
		//Start sending events to the client
		new SendEvents(host_socket,SCREEN_PANEL,width,height);
	}
}
