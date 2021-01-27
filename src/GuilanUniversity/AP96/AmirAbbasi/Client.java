package GuilanUniversity.AP96.AmirAbbasi;

import java.awt.Image;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.xml.bind.DatatypeConverter;

import java.io.DataOutputStream;
import java.io.OutputStream;

public class Client {
Socket socket;
private DataInputStream in;
private DataOutputStream out;
private InputStream input;
private OutputStream output;
DatagramSocket so;
private boolean flag;
private JFrame fr;
private String key;//passwrode ertebat
private String hisName;//name raqib
JoinPanel joinPanel;//panele search
ArrayList<InetAddress> listOfIP=new ArrayList<InetAddress>();//liste ip server haye montazer
ArrayList<String> inbox=new ArrayList<String>();//inboxe payam ha
ArrayList<String> listOfNames=new ArrayList<>();//liste name server haye montazer
private Player player;//bazikon
/**
 * Constructor
 * @param player
 * @param fr
 * @param joinPanel
 */
public Client(Player player,JFrame fr,JoinPanel joinPanel) {
	this.player = player;
	this.fr=fr;
	this.joinPanel=joinPanel;
	
}
/**
 * ersale payam
 * @param message
 */
public void sendMessage(String message) {
	try {
		out.writeUTF(encryption(key));//ebtedad hey ersal mishavad
	out.writeUTF(encryption(message));//payam ersal mishavad
	}catch (IOException e) {
		// TODO: handle exception
	}
}
/**
 * daryafte payam
 * ebteda hoviate ersal konande check mishavad va agar dorost bood payam daryaft mishavad
 * @return
 */
public String getMessage() {
	String message="";//payam
	boolean flag=true;//falg baraye inke payami az servere morede nazar daryaft shode
	while(flag==true) {
	try {
		String checkKey=in.readUTF();//key gerefte mishavad
		if(checkKey!=null) {
			if(desription(checkKey).equals(key)) {//yani agar key gerfete shode ba key gerfete shode dar ebtedaye ersal yeki ast
				flag=false;
	message=in.readUTF();//payam gerefte mishavad(ramz negari shode)
	message=desription(message);//ramz baz mishavad
	}}	}catch (Exception e) {
		// TODO: handle exception
	}
	}

	return message;
}
/**
 * ersale object
 * @param obj
 */
public void sendObject(Object obj) {
	try {
		ObjectOutputStream ou=new ObjectOutputStream(output);
		ou.writeObject(obj);
		
	}catch (IOException e) {
		// TODO: handle exception
	}
}
/**
 * gereftane object
 * @return
 */
public Object getObject() {
	Object obj=null;
	try {
		ObjectInputStream inr=new ObjectInputStream(input);
		obj=inr.readObject();
	}catch (IOException e) {
		// TODO: handle exception
	}
	catch (ClassNotFoundException e) {
		// TODO: handle exception
	}
	return obj;
}
/**
 * tamizkari:close 
 * 
 */
public void kill() {
	try {
		socket.close();
		out.close();
		in.close();
		output.close();
		input.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
/**
 * getter name raqib
 * @return
 */
public String getHisName() {
	return hisName;
}
/**
 * ebteda be tamame ip haye shabake az tariqe protocole UDP baste hayi miferestad ta server haye montazer ra peyda konad
 * bade entekhabe server ba on ertebate TCP barqar mikonad va yek payam be shekle code#MD5 daryaft mikonad.code ebarati ramz negari shode ast va MD5 hashe ebrate khame code ast.
 * az in tariq hoviat barname moqabel check mishavad ta az taqalob jelogiri shavad(agar natavanad ramz negari konad in barname nist)
 */
public void turnOnJoin() {
	findHost listener=new findHost();//listener baraye daryafte payam
	listener.start();
	flag=true;
boolean found=false;//flage peyda va entekhabe server
	while(found==false) {
		Object obj2= JOptionPane.showInputDialog(null, "Please choose on of players to start the game", "", 1, null, new String[] {"127.0.0.1","networks on 192.168.*.*"}, null);
		if(String.valueOf(obj2).equals("127.0.0.1")) {
			try {
				DatagramSocket socket=new DatagramSocket();
				byte[] bytes=new String("connect").getBytes();
				DatagramPacket packet;
			packet=new DatagramPacket(bytes, bytes.length, InetAddress.getByName("127.0.0.1"), 1234);
			socket.send(packet);socket.send(packet);socket.send(packet);
		socket.send(packet);}
				catch (Exception e) {
					// TODO: handle exception
				}try {
		socket.close();}
				catch (Exception e) {
					// TODO: handle exception
				}
		}
		else {
		/////ersale payam be kole shabake/////
		for(int i=1;i<255;i++) {
			flag=true;
			for(int j=1;j<255;j++) {
				try {
				DatagramSocket socket=new DatagramSocket();
				byte[] bytes=new String("connect").getBytes();
				DatagramPacket packet;
			packet=new DatagramPacket(bytes, bytes.length, InetAddress.getByName("192.168."+String.valueOf(i)+"."+String.valueOf(j)), 1234);
		socket.send(packet);}
				catch (Exception e) {
					// TODO: handle exception
				}try {
		socket.close();}
				catch (Exception e) {
					// TODO: handle exception
				}
	}}
		}	
	
		///////////////////////////////////////////
		

	try {
	Thread.sleep(1000);}
	catch (Exception e) {
		// TODO: handle exception
	}
	
	String[] names=listOfNames.toArray(new String[listOfNames.size()]);///araye az name server haye peyda shode
	if(names.length!=0) {//agar chizi peyda shode
		flag=false;
	Object obj= JOptionPane.showInputDialog(null, "Please choose on of players to start the game", "", 1, null, names, null);
	boolean safe=false;//ehraze hoviat
	Exception g=new Exception();
	
	try {	
	if(obj==null) {
		flag=false;
		found=true;

		
			Profile pro=new Profile(player);
			fr.dispose();
	throw g;
	}
	socket=new Socket(listOfIP.get(listOfNames.indexOf(obj)), 2029);

hisName=listOfNames.get(listOfNames.indexOf(obj));

	input=socket.getInputStream();
	in=new DataInputStream(input);
	output=socket.getOutputStream();
	out=new DataOutputStream(output);

					//////payami az server daryaft mishavad ta hoviat baresi shavad//////////
				String[] ha=in.readUTF().split("#");
				MessageDigest md=MessageDigest.getInstance("MD5");
				md.update(desription(ha[0]).getBytes());//ha[0] ramzash baz mishavad va be MD5 tabdil mishavad
				
				String code=DatatypeConverter.printHexBinary(md.digest());
				
				if(ha[1].equals(code))//agar MD5 gerefte shode ba MD5 ramznegari shodeye ha[0] barabar bashad
				{
				
					key=desription(ha[0]);//key set mishavad
					safe=true;//amn;)
				}
	
		
	}
	catch (Exception e) {
		safe=false;
		e.printStackTrace();
		// TODO: handle exception
	}
	if(safe==true) {//bazi shoroo mishavad
PlayBoard pl=new PlayBoard(player,this);

fr.dispose();
found=true;
flag=false;}
}
	else {
		int bit=JOptionPane.showOptionDialog(null, "Cant'n find any friend", "Not found!", 0, 0, null, new String[] {"Try again","Ok!"}, null);
	if(bit==0) 
		found=false;
		else {
			flag=false;
		found=true;
			fr.dispose();
			Profile pro=new Profile(player);}
	
	}}
	so.close();
}
/**
 * daryafte baste az serrverhayi ke payam be onha ersal shode va javab dade and
 * @author Asus
 *
 */
class findHost extends Thread{
	public void run() {
	
		
		while(flag==true) {//ta vaqti barname dar hale gashtane serverast
			try {
			
			 so=new DatagramSocket(123);
		byte[] bytes=new byte[1024];
			DatagramPacket packet=new DatagramPacket(bytes,bytes.length);
//			so.setSoTimeout(5000);
			
			so.receive(packet);
			
			joinPanel.found();
			listOfNames.add(new String(packet.getData()));
			listOfIP.add(packet.getAddress());
			so.close();}catch (Exception e) {
				// TODO: handle exception
			}
			
		}
		
		
	}
}

/**
 * getter key
 * @return
 */
public String getKey() {
	return key;
}
/**
 * check kardane ertebat
 * @return
 */
public boolean checkConnection() {
	return socket.isConnected();
}
/**
 * ebarat ramz negrai mishavad va return mishavad
 * algoritme ramz negari:baraye har harf ebteda be int cast mishavad va tedade raqam on hamrahe ebarate cast shode
 * @param in
 * @return
 */
public String encryption(String in) {
	String out="";
	for(int i=0;i<in.length();i++) {
		int intCode=(int)in.charAt(i);
		out+=String.valueOf(String.valueOf(intCode).length())+String.valueOf(intCode);
			
	}
	return out;
}
/**
 * erbarat ramzash baz mishavad va return mishavad
 * algorite baz kardane ramz:adade ebteda khande mishavad be be andaze on raqam mikhanad va sepas raqam hara be char tabdil mikonad va in mojmooe az ebarat hazf mishavand va dobare ...
 * 
 * @param in
 * @return
 * @throws NumberFormatException(momken ast baraye be moshkel endakhtane barname kasi ebarate namonaseb ersal konad
 */
public String desription(String in) throws NumberFormatException {
	String out="";//return mishavad
	while(!in.equals("")) {
		int b=Integer.parseInt(String.valueOf(in.charAt(0)));//adade tedad
		String buffer=in.substring(1, 1+b);//raqam ha
		out+=(char)Integer.parseInt(buffer);//be char tabdil mishavad
		in=in.substring(1+b);//ebarat shekaste mishavad
		if(in.equals(""))
			break;
				
	}
	return out;
}
}

