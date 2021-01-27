package GuilanUniversity.AP96.AmirAbbasi;


import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelListener;

import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PlayBoard extends JFrame {

	private JPanel contentPane;
	private JTable table;//table bazi
	private JPanel panel;
	private JLabel label;
	private JLabel timeLbl=new JLabel();
	private Server server;
	private Client client;
	private Timer f;//timere bazi
	private int round=1;//nobate bazi
	private String type;//noo(Server ya Client)
	private int clientScore=0;//emtiaze client
	private int serverScore=0;//emtiaze server
	private String message="";
	private char letter;//harfe bazi
	private char[] letters=new char [5];//araye letter ha
	private 		JLabel scoreLbl=new JLabel();//lable score
//	ArrayList<String> getAnswer=new ArrayList<String>();
private Player player;
/**
 * Constroctro noe Server
 * @param player
 * @param server
 */
	public PlayBoard(Player player,Server server) {
	f=new Timer();
		this.player=player;
		this.server=server;
		type="Server";
		play();}
	/**
	 * Constructor noe Client
	 * @param player
	 * @param client
	 */
	public PlayBoard(Player player,Client client) {
		f=new Timer();
			this.player=player;
			this.client=client;
			type="Client";
			play();}
	/**
	 * bazi shooro mishavad
	 */
	public void play() {
		f.start();
		
		Alive alive=new Alive();//Thread modiriate daryafte payam
		alive.start();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 958, 630);
		contentPane = new JPanel();
		contentPane.setBackground(Color.YELLOW);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.SOUTH);
		
		table = new JTable();
		table.setRowHeight(80);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
			},
			new String[] {
				"first name", "last name", "country", "fruit", "animal", "point", "letter"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class, String.class, String.class, String.class, Object.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				true, true, true, true, true, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});

		scrollPane.setViewportView(table);

		/**
		 * dar rounde aval ast va harfi be tore tasadofi entekhab mishavad
		 */
		if(type.equals("Server")) {//agar servre bashaad bayad in kar ra anjam dahad va dar akhar harf ra baraye client ham befrestad
			Random rnd=new Random();
			letter=(char)(rnd.nextInt(25)+65);
			server.sendMessage(String.valueOf(letter));//harf ersal mishavad
			
			/**
			 * montazaer mimanad ta client name khod ra befrestad
			 */
			while(server.inbox.size()==0) {
				try {
					Thread.sleep(100);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			server.setHisName(server.inbox.get(0));//name raqib setmishavad
			server.inbox.clear();
			}
			else
			{//montazer mishavad ta harfe bazi barayash ersal shavad
				while(client.inbox.size()==0) {
					try {
						Thread.sleep(100);
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				letter=client.inbox.get(0).charAt(0);//harf sabt mishavad
				client.sendMessage(player.nameGetter());//name ferestade mishavad
				client.inbox.clear();//inbox khali mishavad
			}
	
	
letters[0]=letter;
			table.setValueAt(letter, 0, 6);
		panel = new JPanel();
		JPanel p=new JPanel();
		p.setLayout(new BorderLayout());
		panel.setBackground(Color.BLACK);
		panel.setLayout(new BorderLayout());
		contentPane.add(panel, BorderLayout.NORTH);
		
		
		label = new JLabel();
		try {
		Image img=ImageIO.read(new File("icon/timer.png"));
		label.setIcon(new ImageIcon(img.getScaledInstance(100, 100, 100)));}
		catch (IOException e) {
			// TODO: handle exception
		}
		p.add(label,BorderLayout.WEST);
		p.add(timeLbl,BorderLayout.EAST);
		p.setBackground(Color.black);
		timeLbl.setFont(new Font("Hobo Std", Font.PLAIN, 40));
		timeLbl.setForeground(Color.white);
		panel.add(p,BorderLayout.WEST);

		String tmp;
		scoreLbl.setFont(new Font("Hobo Std", Font.PLAIN, 40));
		if(type.equals("Server"))
			tmp=String.valueOf(serverScore);
		else
			tmp=String.valueOf(clientScore);
		scoreLbl.setText("            Score:"+tmp);
		scoreLbl.setForeground(Color.white);
		panel.add(scoreLbl,BorderLayout.CENTER);
	
		JButton stop=new JButton("Click me to Stop!!!");//buttone payane round
		stop.addActionListener(new ActionListener() {//actionListener
			public void actionPerformed(ActionEvent arg0) {
				//ba tavajo be noe anha kalame "next" ferestade mishavad ta baraye round baad amade shavad va khodash ham amade mishavad
				if(type.equals("Server")) {
				server.sendMessage("next");//next ferestade mishavad

nextRound next=new nextRound();
next.start();
				}
				else {
					client.sendMessage("next");//next ferestade mishavad
					nextRound next=new nextRound();
					next.start();
					}
				
				
			}
		});
		stop.setForeground(Color.WHITE);
		stop.setBackground(Color.RED);
		stop.setFont(new Font("Hobo Std", Font.PLAIN, 40));
	
		panel.add(stop,BorderLayout.SOUTH);

	}
	/**
	 * timer
	 * 
	 * @author Asus
	 *
	 */
	class Timer extends Thread{
		int n=0,m=3;//n:sanie //m:daqiqe
	
		boolean flag=true;//flag baraye kill kardane Thread
	public void run() {

		while(flag==true) {
			String minute=String.valueOf(m);
			String second=String.valueOf(n);
			try {
			Thread.sleep(1000);}
			catch (Exception e) {
				
			}
			if(m<10)
				minute="0"+String.valueOf(m);
			if(n==-1) {//tabdil
				n=59;
				m--;
				//////////////////tanzime sefre ghable adad
				if(m<10)
				minute="0"+String.valueOf(m);
				}
			if(n<10)
			second="0"+String.valueOf(n);
			else
				second=String.valueOf(n);
			timeLbl.setText(minute+":"+second);
			///////////////////////////////////////////////
			n--;
			
		if(n==0 && m==0)
		{
			flag=false;
nextRound next=new nextRound();
next.start();
			
		}
		}
	}
	}
	/**
	 * darayefte payam
	 * ta vaqti ertebat barqarar bashad payam migirad va be barname tahvil midahad
	 * albate noei modiriat ham bar round ha darad va agar payam "next" bashad round badi ra miavarad
	 * @author Asus
	 *
	 */

	class Alive extends Thread{
		public void run() {
if(type.equals("Server")) {
	while(server.checkConnection()) {
		message=server.getMessage();
	if(message.equals("next"))//agar next daryaft shode be round baad miravad
		{
		nextRound next=new nextRound();
		next.start();
		}
	else//be inbox ezafe mishavad
		server.inbox.add(message);}}
else {
	while(client.checkConnection()) {
		message=client.getMessage();

	if(message.equals("next")) {//agar next daryaft shode be round baad miravad
		nextRound next=new nextRound();
		next.start();
	}
else//be inbox ezafe mishavad
client.inbox.add(message);}}
	}
		
}
	/**
	 * Thread round baad
	 * 
	 * @author Asus
	 *
	 */
	class nextRound extends Thread{
		public void run() {
			f.flag=false;
			f=new Timer();
			if(type.equals("Server")) {
				int myScore=0;
		int[] inc=new int[5];//tozie emtiaz ba 10
		for(int i=0;i<5;i++)
		inc[i]=10;
		
				/**
				 * montazere daryafte javab ha az client mimanad
				 */
				while(server.inbox.size()==0) {
					try {
					Thread.sleep(100);}catch (Exception e) {
						// TODO: handle exception
					}}
				String input=server.inbox.get(0);
				String[] answers=input.split("#");//javab ha split mishavad
				
				int score=0;
				
				for(int i=0;i<5;i++) {//baraye rafe bug
					if(table.getValueAt(round-1, i)==null || table.getValueAt(round-1, i).equals("")) {
						table.setValueAt(" ", round-1, i);
					
					}
					/**
					 * agar javabi yeki bashad tozie emtiaz nesf mishavad(5)
					 */
				if(answers[i].equals(table.getValueAt(round-1, i).toString().toUpperCase())) {
				inc[i]=5;
				}
				}
				
				
				
				///////////////baresi javab haye client/////////////////////////
				if(server.firstname.contains(answers[0]) && answers[0].charAt(0)==letter) {
					score+=inc[0];
				}
				if(server.lastname.contains(answers[1])  && answers[1].charAt(0)==letter){
					score+=inc[1];
				}
				if(server.country.contains(answers[2]) && answers[2].charAt(0)==letter) {
					score+=inc[2];
				}
				if(server.fruit.contains(answers[3]) && answers[3].charAt(0)==letter) {
					score+=inc[3];
				}
				if(server.animal.contains(answers[4])  && answers[4].charAt(0)==letter) {
					score+=inc[4];
				}
				/////////////////////////////////////////////////////////////////////////
				
				
				//////////////////////////////baresi javab haye khod////////////////////////////////////////////////////////////////////////////////////////////////////
				
				if(server.firstname.contains(table.getValueAt(round-1, 0).toString().toUpperCase()) && String.valueOf(table.getValueAt(round-1, 0)).toUpperCase().charAt(0)==letter) {
					myScore+=inc[0];
				}
				if(server.lastname.contains(table.getValueAt(round-1, 1).toString().toUpperCase()) &&  String.valueOf(table.getValueAt(round-1, 1)).toUpperCase().charAt(0)==letter) {
					myScore+=inc[1];
				}
				if(server.country.contains(table.getValueAt(round-1, 2).toString().toUpperCase()) && String.valueOf(table.getValueAt(round-1, 2)).toUpperCase().charAt(0)==letter) {
					myScore+=inc[2];
				}
				if(server.fruit.contains(table.getValueAt(round-1, 3).toString().toUpperCase()) &&  String.valueOf(table.getValueAt(round-1, 3)).toUpperCase().charAt(0)==letter) {
					myScore+=inc[3];
				}
				if(server.animal.contains(table.getValueAt(round-1, 4).toString().toUpperCase()) &&  String.valueOf(table.getValueAt(round-1, 4)).toUpperCase().charAt(0)==letter) {
					myScore+=inc[4];
				}
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				
				//////update emtiaz////
				clientScore+=score;
				serverScore+=myScore;
				/////////////////////
				
				table.setValueAt(myScore, round-1, 5);//gozashtane emtiaz dar jadval
				server.sendMessage(String.valueOf(score));//emtiaze in marhale be client ferestade mishavad
				round++;
			scoreLbl.setText("            Score:"+String.valueOf(serverScore));//lable score update mishavad
				
			/**
			 * agar bazi tamam shode bashad round=6;
			 * agar Server barande bashad "me" vagarna "you" ferestade mishavad
			 */
				if(round==6) {
				if(serverScore<clientScore) {
					server.sendMessage("you");
					finish(false);
				}	else {
					server.sendMessage("me");
					finish(true);
				}
					server.kill();//tamizkari
				}else {
					server.inbox.clear();//inbox tamiz mishavad
					
					f.start();//timer az noe shoroo mishavad
					boolean ok=false;//flage entekhabe harf
					/**
					 * harfi be soorate tasadofi entekhab mishavad 
					 * 
					 */
					while(ok==false) {
					Random rnd=new Random();
					letter=(char)(rnd.nextInt(25)+65);
					int i=0;
					for( i=0;i<5;i++)//check mishavad ta tekraru nabashad
					{
						if(letter==letters[i]) {
							break;
						}
					}
					if(i==5)//kalame tekrari nist
						ok=true;
					
						}
					letters[round-2]=letter;
					server.sendMessage(String.valueOf(letter));//harf be client ersal mishavad
					table.setValueAt(letter, round-1, 6);
				}
			}
			else {//noe Client
				String msg="";//javab ha
				for(int i=0;i<5;i++) {//javab ha dar msg ba # gozashte mishavad
				if(table.getValueAt(round-1, i)==null || table.getValueAt(round-1, i).equals(""))
					table.setValueAt(" ", round-1, i);
					msg+=table.getValueAt(round-1, i).toString().toUpperCase()+"#";
				}
	
			
			client.sendMessage(msg);//javab be Server ersal mishavad
			/**
			 * montazere payam az server mimanad ta emtiaz ra begirad
			 */
			while(client.inbox.size()==0) {
				try {
				Thread.sleep(100);}
				catch (Exception e) {
					// TODO: handle exception
				}}
			String score=client.inbox.get(0);//emtiaz
			
			clientScore+=Integer.parseInt(score);//update score
			table.setValueAt(score, round-1, 5);
			round++;
			scoreLbl.setText("            Score:"+String.valueOf(clientScore));//update lable score
			/**
			 * agar round=6 bazi tamam shode va montazere vaziat az server mimanad
			 */
			if(round==6)
			{
				while(client.inbox.size()==1)
				{
					try {
						Thread.sleep(100);}
						catch (Exception e) {
							// TODO: handle exception
						}
				}
				/**
				 * you:client barande shode
				 * me:server barande shode
				 */
				if(client.inbox.get(1).equals("you"))
					finish(true);
				else
					finish(false);
				client.kill();//tamizkari
			}else {
			/**
			 * montazere karfe riunde baadi az server mimanad
			 */
				while(client.inbox.size()==1) {
					try {
					Thread.sleep(100);}
					catch (Exception e) {
						// TODO: handle exception
					}}
				letter=client.inbox.get(1).charAt(0);
				client.inbox.clear();//inbox tamiz mishavad
			table.setValueAt(letter, round-1, 6);
		f.start();//timer shoroo mishavad
		
		}
			}
			
		}
	}
	/**
	 * tabe karhaye payane bazi
	 * agar barande bashad flag==true vagarna flag==false
	 * @param flag
	 */
	public void finish(boolean flag) {
		this.dispose();
		Image img=null;
		String hjk=null;//name raqib
		if(type.equals("Server"))
			hjk=server.getHisName();
		else
			hjk=client.getHisName();
		try {
		img=ImageIO.read(new File("icon/fin.png"));}
		catch (IOException e) {
			// TODO: handle exception
		}
		if(flag==true) {//agar barande  ast
			Clip clip=null;
			try {
			 clip=AudioSystem.getClip();
				clip.open(AudioSystem.getAudioInputStream(new File("sounds/win.wav")));
				clip.start();//sedaye barande shodan pakhs mishavad
				}
					catch (Exception e) {
						e.printStackTrace();
					}
			try {
			JOptionPane.showMessageDialog(null, "", "\nWinner is:"+player.nameGetter(), 1, new ImageIcon(img.getScaledInstance(700, 700, 200)));//barande ra elam mikonad
			}
			catch (Exception e) {
				// TODO: handle exception
			}
			player.winPlusPlus();//tedade bord haye bazikon ziad mishavad
			Player.writePlayers();//file players.data update mishavad
		clip.close();
		}
		else {//bazande
			Clip clip=null;
			try {
				 clip=AudioSystem.getClip();
				clip.open(AudioSystem.getAudioInputStream(new File("sounds/gameOver.wav")));
				clip.start();//sedaye bazande shodan pakhsh mishavad
				}
					catch (Exception e) {
						e.printStackTrace();
					}
			try {
		
				
		
				JOptionPane.showMessageDialog(null, "", "\nWinner is:"+hjk, 1, new ImageIcon(img.getScaledInstance(700, 700, 200)));//barande ra elam mikonad
				}
				catch (Exception e) {
					// TODO: handle exception
				}
				player.failPlusPlus();//tedad bakht ha ziad mishavad
				Player.writePlayers();//players.data update mishavad
				clip.close();
				
		}
		if(type.equals("Server")) {
			try {
				server.socket.close();
				server.server.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}}else {
				try {
					client.socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		Profile pr=new Profile(player);//safhre profile
		this.dispose();
	}
	}
