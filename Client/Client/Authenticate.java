package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

class Authenticate extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	private Socket host_socket = null;
	private	DataOutputStream psswrchk = null;
	private	DataInputStream verification = null;
	private	String verify ="";
	private	JButton SUBMIT;
	private	JPanel panel;
	private	JLabel label, label1;
	private	String width="",height="";
	private	final JTextField text1;

	Authenticate(Socket host_socket){
		label1=new JLabel();
		label1.setText("Password");
		text1 = new JTextField(15);
		this.host_socket = host_socket;
	
		label=new JLabel();
		label.setText("");
		this.setLayout(new BorderLayout());
	
		SUBMIT = new JButton("SUBMIT");

		panel=new JPanel(new GridLayout(2,1));
		panel.add(label1);
		panel.add(text1);
		panel.add(label);
		panel.add(SUBMIT);
		add(panel,BorderLayout.CENTER);
		SUBMIT.addActionListener(this);
		setTitle("LOGIN FORM");
	}


	public void actionPerformed(ActionEvent ae){


		String value1=text1.getText();
				
		try{
			psswrchk= new DataOutputStream(host_socket.getOutputStream());
			verification= new DataInputStream(host_socket.getInputStream());
			psswrchk.writeUTF(value1);
			verify=verification.readUTF();
	
		}catch (IOException e){
			e.printStackTrace();
		}

		if(verify.equals("valid")){
			try{
				width = verification.readUTF();
				height = verification.readUTF();
		
			}catch (IOException e){
				e.printStackTrace();		
			}
			new CreateFrame(host_socket,width,height);
			dispose();
			}
				
			else{
				System.out.println("PLEASE ENTER THE PASSWORD");
				JOptionPane.showMessageDialog(this, "INCORRECT PASSWORD!", "Error", JOptionPane.ERROR_MESSAGE);
				dispose();
				}

			}
			
	}

