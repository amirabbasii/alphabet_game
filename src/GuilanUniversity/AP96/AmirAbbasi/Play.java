package GuilanUniversity.AP96.AmirAbbasi;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import org.omg.CORBA.portable.OutputStream;

public class Play {
	
static JFrame frame;
public static void main(String[] args) {
	Player.loadPlayers();//player ha load mishavand

	String[] names=new String[Player.numberOfPlayers()];//araye baraye name player ha
	for(int i=0;i<Player.numberOfPlayers();i++) 
		names[i]=Player.getPlayer(i).nameGetter();
	Draw panel=new Draw(names);//sakhte menu page
	panel.underIt.setVisible(false);
	panel.underIt2.setVisible(false);
	panel.back.setVisible(false);
	panel.delete.setText("Delete");
	panel.delete.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			panel.delPanel();
			panel.del.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					int indexes[]=panel.delAction();
					for(int i=0;i<indexes.length;i++) {
						try {
						File fi=new File("pictures/"+Player.getPlayer(indexes[i]).nameGetter()+".jpg");
						fi.delete();}
						catch (Exception e) {
							// TODO: handle exception
						}
						Player.getPlayer(indexes[i]).nameGetter();
						Player.delPlayer(indexes[i]);}
					Player.writePlayers();
					panel.repaint();
					
					
				}
			});
			
			
		}
	});
panel.setLayout(null);
	frame=new JFrame();
	frame.setLayout(null);
	frame.setBounds(10, 10, 1000, 800);
	frame.add(panel);
	frame.setVisible(true);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	/////////////////////ACtionLIstener baraye menu//////////////////////
	for(int i=0;i<panel.list.size();i++) {
		final int finalBazy=i;
		panel.list.get(i).addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Profile pro=new Profile(Player.getPlayer(finalBazy));
				frame.setVisible(false);
				
			}
		});
	}
	///////////////////////////////////////////////////////////
	
	//////////////////tanzime newO////////////////////////
	panel.newO.setText("New Player");
	panel.newO.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
try {
		String name=JOptionPane.showInputDialog("Please enter name of player");//name gerefte mishavad
		Exception g=new Exception();
			if(name.equals(null))
				throw g;
		while(Player.exist(name)) {
			name=JOptionPane.showInputDialog("It exist!Please enter another name");//name gerefte mishavad
		}
		File file=fileChooser();//FileChooser
		if(file.equals(null))
			throw g;
		try {
	File iop=new File("pictures/"+name+".jpg");

	
		Files.copy(file.toPath(), iop.toPath());//tasvir dar /pictures copy mishavad
		panel.adder(name);//menu page update mihavad
		Player.newPlayer(name);//new
		Player.writePlayers();//players.data update mishavad
		
		Image imgg=ImageIO.read(new File("pictures/"+name+".jpg"));
		panel.images.get(panel.images.size()-1).setIcon(new ImageIcon(imgg.getScaledInstance(150, 150, 150)));
		
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		panel.repaint();
		ActionListener action=new ActionListener() {//action baraye button jadid
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Profile pro=new Profile(Player.getPlayer(Player.numberOfPlayers()-1));
				frame.dispose();
			}
		};
		panel.lastButtonAction(action);
		}
		catch(Exception e) {
			
		}}
	});
	/////////////////////////////////////
	
		
}
/**
 * JFileChooser
 * @return addresse file entekhabi
 */
public static  File fileChooser() {
//////////////////JFileChooser va kar haye tasvir/////////////////////
File picAddress=null;
JFileChooser choose=new JFileChooser();
choose.setAcceptAllFileFilterUsed(false);
choose.setFileFilter(new FileFilter() {
	
	@Override
	public String getDescription() {
		return ".jpg (150*150 pixels)";//mahdoodiat JPG
	
	}
	
	@Override
	public boolean accept(File file) {
		if(file.isDirectory())
			return true;
			else {
			if(file.getName().lastIndexOf(".jpg")!=-1)
			return true;
			}
			return false;
	}
});


choose.setFileSelectionMode(JFileChooser.FILES_ONLY);
if(choose.showOpenDialog(frame)==JFileChooser.APPROVE_OPTION) {
picAddress=choose.getSelectedFile();

}
return picAddress;
}

}
