package GuilanUniversity.AP96.AmirAbbasi;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.sound.sampled.Line;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Profile extends JFrame {

	private Panel contentPane;
	private HostPanel waiting=new HostPanel();
	private Hoster hoster;
	private Graphics g;
	 JoinPanel joinPanel=new JoinPanel();
	 private static Profile fr;//profile
	 Client clinet;
private Player player;
	
	public Profile(Player player) {
		this.player=player;
		fr=this;
	
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1001, 620);
		contentPane = new Panel();
		contentPane.setBackground(Color.YELLOW);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JButton backer=new JButton("back");
		backer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setContentPane(contentPane);
				hoster.server.headShot();
			}
		});
		backer.setFont(new Font("Hobo Std", Font.PLAIN, 41));
		backer.setBackground(Color.red);
		backer.setForeground(Color.white);
		waiting.add(backer);
		JButton btnHost = new JButton("Host");
		btnHost.setBackground(Color.blue);
		btnHost.setForeground(Color.white);
		btnHost.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			waiting.setBackground(Color.yellow);
			setContentPane(waiting);
			waiting.setBorder(new EmptyBorder(5, 5, 5, 5));
			waiting.setLayout(new BorderLayout(0,0));
			waiting.add(backer,BorderLayout.WEST);
	
			Hoster hoster=new Hoster();//thread daryafte payam
			hoster.start();
			
			repaint();
				
			}
		});
		btnHost.setFont(new Font("Hobo Std", Font.PLAIN, 41));
		btnHost.setBounds(461, 448, 239, 95);
		JButton back=new JButton("Back");
		back.setFont(new Font("Hobo Std", Font.PLAIN, 41));
		back.setBounds(100, 448, 239, 95);
		back.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFrame frty=Play.frame;
				frty.setVisible(true);
				fr.dispose();
			}
		});
		contentPane.add(back);
		contentPane.add(btnHost);
		back.setBackground(Color.red);
		back.setForeground(Color.white);
		JButton btnJoin = new JButton("Join");
		btnJoin.setBackground(Color.blue);
		btnJoin.setForeground(Color.white);
		btnJoin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setContentPane(joinPanel);
				joinPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
				
				repaint();
				Clienter doo=new Clienter();//join
				doo.start();
			}
		});
		btnJoin.setFont(new Font("Hobo Std", Font.PLAIN, 41));
		btnJoin.setBounds(724, 446, 213, 99);
		contentPane.add(btnJoin);
		
		JLabel lblNewLabel = new JLabel();
		if(player.getPicture()!=null) 
		lblNewLabel.setIcon(new ImageIcon(player.getPicture().getScaledInstance(300, 340, 50)));
		lblNewLabel.setBounds(60, 40, 400, 400);
		contentPane.add(lblNewLabel);
		
		JLabel lblName = new JLabel("Name:"+player.nameGetter());
		lblName.setForeground(Color.CYAN);
		lblName.setFont(new Font("Hobo Std", Font.PLAIN, 40));
		lblName.setBounds(488, 57, 426, 95);
		contentPane.add(lblName);
		
		JLabel label = new JLabel("Wins:"+player.winGetter());
		label.setForeground(Color.CYAN);
		label.setFont(new Font("Hobo Std", Font.PLAIN, 40));
		label.setBounds(488, 164, 426, 95);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("fail:"+player.failGetter());
		label_1.setForeground(Color.CYAN);
		label_1.setFont(new Font("Hobo Std", Font.PLAIN, 40));
		label_1.setBounds(488, 310, 426, 95);
		contentPane.add(label_1);
		contentPane.repaint();
	}
/**
 * ijad host
 * @author Asus
 *
 */
	class Hoster extends Thread{
		Server server;
		public void run() {
			Server server=new Server( player,fr);
			server.turnOnHost();
		}
	}
	/**
	 * ijad join
	 * @author Asus
	 *
	 */
	class Clienter extends Thread{
		public void run() {
		 clinet=new Client( player,fr,joinPanel);
		 
			clinet.turnOnJoin();
		
		}
		}
}
/**
 * JPanel baraye proflie
 * @author Asus
 *
 */
class Panel extends JPanel{
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		try {
		Image img=ImageIO.read(new File("icon/background.png"));
		g.drawImage(img, 0, 0, this);
		}
		catch (Exception e) {
		
		}
		
	}
	
}
/**
 * Jpanel baraye host
 * @author Asus
 *
 */
class HostPanel extends JPanel{
	
	 
	@Override
	protected void paintComponent(Graphics arg0) {
		
		super.paintComponent(arg0);
		try {
		Image img=ImageIO.read(new File("icon/Host.png"));
	arg0.drawImage(img,0,0,this);}
		catch (IOException e) {
			// TODO: handle exception
		}
		
	}
}
/**
 * JPanel baraye join
 * @author Asus
 *
 */
class JoinPanel extends JPanel{
	JLabel lbl;
	public JoinPanel() {
	lbl=new JLabel();
		setLayout(new BorderLayout());
lbl.setLayout(null);
		lbl.setIcon(new ImageIcon("icon/radar.gif"));
		lbl.setLayout(null);


		add(lbl,BorderLayout.CENTER);}
	/**
	 * tasviri be soorate random dar gif rasm mishavad
	 */
	public void found() {
		JLabel l=new JLabel();
		
		try {
		Image img=ImageIO.read(new File("icon/dish.png"));//tasvir
		l.setIcon(new ImageIcon(img.getScaledInstance(25, 25, 25)));
		
		Random rnd=new Random();
		int x=0;
		int y=0;
		boolean flag=false;
		while(flag==false) {
		 x=rnd.nextInt(450)+25;
	 y=rnd.nextInt(450)+40;
	if(Math.pow((x-240),2)+Math.pow((y-250),2)<Math.pow(225,2))//agar noqte dakhele gif ast
		flag=true;
		}
		l.setBounds(x, y, 50, 50);
		lbl.add(l);
		}
		catch (IOException e) {
			// TODO: handle exception
		}
	}
	@Override
	public void setBackground(Color arg0) {
		// TODO Auto-generated method stub
		super.setBackground(Color.yellow);
	}
	@Override
	protected void paintComponent(Graphics arg0) {
		// TODO Auto-generated method stub
		super.paintComponent(arg0);
	}
	
}



