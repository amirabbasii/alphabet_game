
package GuilanUniversity.AP96.AmirAbbasi;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

import javax.imageio.ImageIO;
/**
 * 
 * @author Asus
 *
 */
public class Player {
private String name;//name player
private int wins;//tedad bord
private int fail;//tedade bakht
private Image picture;//tasvire karbari
private static ArrayList<Player> list=new ArrayList<Player>();//player ha
/**
 * Constructor
 * @param name
 * @param wins
 * @param fail
 */
public Player(String name,int wins,int fail) {
	this.name=name;
	this.wins=wins;
	this.fail=fail;
	try {
		this.picture=ImageIO.read(new File("pictures/"+name+".jpg"));
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
/**
 * getter name
 * @return
 */
public String nameGetter() {
	return name;
}
/**
 * getter win
 * @return
 */
public int winGetter() {
	return wins;
}
/**
 * ziad shodane win baraye bord
 */
public void winPlusPlus() {
	wins++;
}
/**
 * ziad shodane fail baraye bakht
 */
public void failPlusPlus() {
	fail++;
}
/**
 * getter fail
 * @return
 */
public int failGetter() {
	return fail;
}
/**
 * getter picture
 * @return
 */
public Image getPicture() {
	return picture;
}
/**
 * setter picture
 * @param picture
 */
public void setPicture(Image picture) {
	this.picture = picture;
}
/**
 * update file: players.data
 */
public static void writePlayers() {
	FileOutputStream writer;
	try {
		
	writer=new FileOutputStream(new File("players.data"));
	for(int i=0;i<list.size();i++)
		writer.write(new String(list.get(i).toString()).getBytes());
	writer.close();
	}
	catch (IOException e) {
		// TODO: handle exception
	}
	
	
}
/**
 * load kardane  player ha az players.data
 */
public static void loadPlayers() {
	try {
		FileInputStream reader=new FileInputStream(new File("players.data"));
		String ans="";
		String buffer="";
		int charCode=reader.read();
		while(charCode!=-1) {
		
			buffer+=(char)charCode;
			charCode=reader.read();}
		reader.close();
		if(buffer.length()!=0) {
			String[] playerss=buffer.split("#");
			for(int i=0;i<playerss.length;i++) {
				String[] tmp=playerss[i].split(":");
				list.add(new Player(tmp[0], Integer.parseInt(tmp[1]),Integer.parseInt(tmp[2])));
			}
		}
		
	}catch (IOException e) {
		
	}
}
/**
 * player jadid
 * @param name
 */
public static void newPlayer(String name) {
	list.add(new Player(name, 0, 0));
}
/*
 * hazfe player
 */
public static void delPlayer(int i) {
	list.remove(i);
}
/**
 * getter player
 * @param i
 * @return
 */
public static Player getPlayer(int i) {
	return list.get(i);
}
/**
 * getter tedade player
 * @return
 */
public static int numberOfPlayers() {
	return list.size();
}
public String toString() {
	return name+":"+String.valueOf(wins)+":"+String.valueOf(fail)+"#";
}
public static boolean exist(String name) {
	boolean flag=false;
	for(int i=0;i<list.size();i++) {
		if(list.get(i).name.equals(name)) {
			flag=true;
			break;
			
		}
	}
	return flag;
}
}

