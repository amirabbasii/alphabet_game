package GuilanUniversity.AP96.AmirAbbasi;

import java.awt.Image;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.xml.bind.DatatypeConverter;
import javax.xml.ws.handler.MessageContext.Scope;

public class Server {
	DatagramSocket so;

 HashSet<String> firstname=new HashSet<>();//firstname ha
 HashSet<String> lastname=new HashSet<>();//lastname ha
 HashSet<String> country=new HashSet<>();//country ha
 HashSet<String> animal=new HashSet<>();//animal ha
 HashSet<String> fruit=new HashSet<String>();//fruit ha
private Player player;//
private DataInputStream in;
private DataOutputStream out;
private OutputStream output;
private InputStream input;
private boolean flag;
private String hisName;
private JFrame fr;
private String key;
ServerSocket server;
 ArrayList<String> inbox=new ArrayList<String>();//inboxe payam haye daryaft shode

Socket socket=new Socket();
/**
 * Constructor
 * @param player
 * @param fr
 */
public Server (Player player,JFrame fr) {
loadAnswers();
	this.player=player;
	this.fr=fr;
	
}
/**
 * getter name raqib
 * @return
 */
public String getHisName() {
	return hisName;
}
/**
 * setter name raqib
 * @param name
 */
public void setHisName(String name) {
	hisName=name;
}
public void headShot() {
	flag=false;
}
/**
 * ersale payam
 * @param message
 */
public void sendMessage(String message) {
	try {
		out.writeUTF(encryption(key));
	out.writeUTF(encryption(message));}catch (Exception e) {
		// TODO: handle exception
	}
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
 * daryafte object
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
 * daryafte payam
 * @return 
 */
public String getMessage() {
	String message="";
	String checkKey;//kelide daryafti
	boolean on=true;//
	try {
		while(on==true) {
		checkKey=in.readUTF();//kelid gerefte mishavad
       if(checkKey!=null) {
		if(desription(checkKey).equals(key)) {//agar qofle ebarat baz shod va ba key daryafti dar ebtedaye ertebat yeki bood
			on=false;//amne;)
	message=in.readUTF();//payam daryaft mishavad
	message=desription(message);}}
		}
	}catch (Exception e) {
		// TODO: handle exception
	}
	return message;
}
/**
 * tamizkari:close
 */
public void kill() {
	try {
		out.close();
		in.close();
		input.close();
		output.close();
		socket.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
/**
 * be protocol UDP montazer payam mimanad va agar daryaft shode javab midahad
 */
public void turnOnHost() {
	flag=true;//flage ertebate asli
	connect c=new connect(this);//amade boodan baraye ertebate nahayi ba TCP
	c.start();
	while(flag==true) {
	try {
		//////////////daryafte payam//////////
	 so=new DatagramSocket(1234);
	
	byte[] bytes=new byte[1024];
	DatagramPacket packet=new DatagramPacket(bytes, bytes.length);
//	so.setSoTimeout(5000);
	so.receive(packet);

	so.close();
	///////////////////////////////////////////
	
	//////////////////javab dade mishavad//////////
	DatagramSocket son=new DatagramSocket();
	byte[] byes=new String(player.nameGetter()).getBytes();

	DatagramPacket p=new DatagramPacket(byes, byes.length, packet.getAddress(), 123);
	son.send(p);
	so.close();
	//////////////////////////////////////////////////
	
	
	}
	catch (Exception e) {
		// TODO: handle exception
	}}
}
/**
 * amade boodan baraye ertebate asli ba TCP
 * @author Asus
 *
 */
class connect extends Thread{
	public connect(Server ser) {
		this.serv=ser;
	}
	Server serv;
	public void run() {
	try {
		server=new ServerSocket(2029);
		
		socket=server.accept();
		input=socket.getInputStream();
		in=new DataInputStream(input);
	output=socket.getOutputStream();
		out=new DataOutputStream(output);
		key=makePassword();//kelid sakhte mishavad
		MessageDigest md=MessageDigest.getInstance("MD5");
		md.update(key.getBytes());//be MD5 tabdil mishavad
		
		String code=DatatypeConverter.printHexBinary(md.digest());
		out.writeUTF(encryption(key)+"#"+code);//qofl shodeye key ba MD5 e on ersal mishavad
		
		flag=false;

		PlayBoard pl=new PlayBoard(player,serv);//bazi shoroo mishavad
	
		fr.dispose();//frame az beim miravad
		
	}
	catch (Exception e) {
		e.printStackTrace();
		// TODO: handle exception
	}
	so.close();
	}
	
}
/**
 * sakhte ebarati 20 raqami az horoof va adad 
 * @return
 */
public String makePassword() {
	String h="";
	Random rnd=new Random();
	for(int i=0;i<20;i++) {
		int bit=rnd.nextInt(3);
		if(bit==0 || bit==1 ) 
			h+=(char)(rnd.nextInt(25)+97);
		else
			h+=(char)rnd.nextInt(9)+49;
			
	}
	return h;
}
/**
 * getter key
 * @return
 */
public String getKey() {
	return key;
}
/**
 * getter etesal
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
	String out="";
	while(!in.equals("")) {
		int b=Integer.parseInt(String.valueOf(in.charAt(0)));
		String buffer=in.substring(1, 1+b);
		out+=(char)Integer.parseInt(buffer);
		in=in.substring(1+b);
		if(in.equals(""))
			break;
				
	}
	return out;
}

/**
 * load kardane javab ha
 */
public void loadAnswers() {
try {
	BufferedReader r=new BufferedReader(new FileReader("answers/firstNames.txt"));
	String l="";
	while(l!=null) {
		l=r.readLine();
		if(l!=null) {
			l=l.toUpperCase();
		firstname.add(l);
		}
	}
r.close();
 r=new BufferedReader(new FileReader("answers/lastNames.txt"));
 l="";
while(l!=null) {
	l=r.readLine();
	if(l!=null) {
		l=l.toUpperCase();
	lastname.add(l);
	}
}
r.close();
r=new BufferedReader(new FileReader("answers/animals.txt"));
l="";
while(l!=null) {
	l=r.readLine();
	if(l!=null) {
		l=l.toUpperCase();
	animal.add(l);
	}
}
r.close();
r=new BufferedReader(new FileReader("answers/countries.txt"));
l="";
while(l!=null) {
	l=r.readLine();
	if(l!=null) {
		l=l.toUpperCase();
	country.add(l);
	}
}
r.close();
r=new BufferedReader(new FileReader("answers/fruits.txt"));
l="";
while(l!=null) {
	l=r.readLine();
	if(l!=null) {
		l=l.toUpperCase();
	fruit.add(l);
	}
}
r.close();
}catch (IOException e) {
	e.printStackTrace();
	// TODO: handle exception
}
}
}
