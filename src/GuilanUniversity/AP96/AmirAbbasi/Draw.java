package GuilanUniversity.AP96.AmirAbbasi;



import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

/*
 * GUI be soorate daste kod zade shode ast
 * 
 */
public class Draw extends JPanel{
	int n=0;
protected JButton del=new JButton();//dokme hazfe nahayi
protected JButton cancel=new JButton("Cancel");
	ArrayList<JButton > list=new ArrayList<>();//JButoon ha
	ArrayList<JButton> images=new ArrayList<>();//Label ha(baraye tasvir)
	protected JButton delete = new JButton();//dokme hazf
	protected JButton newO = new JButton();//dokme new
	protected JButton underIt = new JButton();//dokme zirshahke 1
	protected JButton underIt2=new JButton();//dokme zirshahke 2
	protected JButton back=new JButton();//dokme bargasht
	protected JScrollBar bar=new JScrollBar();
	private ArrayList<JCheckBox> check=new ArrayList<>();
	private int menuN;

		

	/**
	 * sakhte panel baraye:list ke mitavanad aks dashte bashad ya na
	 * flag==true --> ba aks
	 * flag==false --> bi aks
	 * addressbook ha bare geft
	 * @param names
	 * @param flag
	 * @param addressBook
	 */
	
		public Draw(String[] names) {
		

		addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
			menuN=-1;
				
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
			this.setBounds(0, 0, 1000, 800);
			this.setBackground(new Color(230,230,0));//pas zamine panel
				for (int i = 0; i < names.length; i++) {

					JButton tmp = new JButton();
					tmp.setFont(new Font("Hobo Std", Font.PLAIN, 70));
					tmp.setText(names[i]);//text
					
						tmp.setBounds(10, 60 + 150 * i, 790, 150);
						if (tmp.getY() >= 660)
							tmp.setVisible(false);
				
					tmp.setBackground(Color.WHITE);//range button
					
					list.add(tmp);//button be list ezafe mishavad
				this.add(list.get(i));//button be panel ezafe mishavad
			}
				addMouseWheelListener(new MouseWheelListener() {
					
					@Override
					public void mouseWheelMoved(MouseWheelEvent arg0) {
						if(arg0.getWheelRotation()==1 && bar.getValue()<bar.getMaximum()/3) {
						
						
						bar.setValue(bar.getValue()+arg0.getWheelRotation());
					mvObj(-arg0.getWheelRotation());}
						if(arg0.getWheelRotation()==-1 && bar.getValue()>=1) {
						
							bar.setValue(bar.getValue()+arg0.getWheelRotation());
							mvObj(-arg0.getWheelRotation());
						}
						
					}
				});
					File file;
					for(int i=0;i<list.size();i++) {
						try {
						Image im=ImageIO.read(new File("pictures/"+names[i]+".jpg"));
							ImageIcon	tmpImg = new ImageIcon(im.getScaledInstance(150, 150, 150));
						JButton tmpLable = new JButton();//label baraye aks
						
						
						tmpLable.setIcon(tmpImg);//aks be tmpLable afzoode mishavad
						
						tmpLable.setBounds(800, 60 + 150 * i, 150, 150);
						if (tmpLable.getY() >= 660)//agar Y>660 az mahoode namayesh kharej shode ast
							tmpLable.setVisible(false);
						images.add(tmpLable);//be list ezafe mishavad
						this.add(images.get(i));//be panel ezafe mishavad
						}catch (IOException e) {
							// TODO: handle exception
						}
images.get(i).addMouseListener(new mouse(i));
list.get(i).addMouseListener(new mouse(i));
						}
				
				///////////tanzime dokme haye pish farz////////////
//				this.add(searchBox);
				newO.setBounds(10, 680, 140, 70);
				
				newO.setFont(new Font("Hobo Std", Font.PLAIN, 15));
				this.add(newO);
				delete.setBounds(150, 680, 140, 70);
				delete.setFont(new Font("Hobo Std", Font.PLAIN, 15));
				this.add(delete);
				underIt.setBounds(290, 680, 140, 70);
				underIt.setFont(new Font("Hobo Std", Font.PLAIN, 15));
				this.add(underIt);
				underIt2.setBounds(430, 680, 120, 70);
				underIt2.setFont(new Font("Hobo Std", Font.PLAIN, 15));
				this.add(underIt2);
				bar.setBounds(963, 0, 20, 750);
				bar.setValues(0, 4, 0, list.size());
				
				this.add(bar);
				/////////////////////////////////////////////////
				
				
				
				//////tanzime bar////////////////////////////
				bar.addAdjustmentListener(new AdjustmentListener() {
					int defaultValue = 0;

					@Override
					public void adjustmentValueChanged(AdjustmentEvent action) {
						if (bar.getValue() > defaultValue) {
							mvObj(-1);
							defaultValue = bar.getValue();
							
						} else {
							mvObj(1);
							defaultValue = bar.getValue();
						}
					}
					
				});
				//////////////////////////////////////////////////
		}
	
	/**
	 * update kardan
	 * yek khane be list ezafe mikonad
	 * flag==false --> bedoone tasvir
	 * flag==true --> ba tasvir
	 * @param name
	 * @param flag
	 */
	public void adder(String name) {
		
	for(int i=0;i<list.size();i++)
		list.get(i).setBounds(10, 60 + 150 * i, 790, 150);

		for(int i=0;i<images.size();i++)
			images.get(i).setBounds(800, 60 + 150 * i, 150, 150);
		
		ImageIcon tmpImg = new ImageIcon(name+".png");
		JButton tmpLable = new JButton();

		tmpLable.setIcon(tmpImg);
		tmpLable.setBounds(800, 60 + 150 * images.size(), 150, 150);
		if (tmpLable.getY() >= 660)//agar y>660 yani az mahdoode namayesh kharej shode
			tmpLable.setVisible(false);
		images.add(tmpLable);
		this.add(images.get(images.size()-1));
	
	if(list.size()>5)//rafe buge ajib
		list.get(5).setVisible(true);
		JButton tmp = new JButton();
		tmp.setFont(new Font("Hobo Std", Font.PLAIN, 70));
		tmp.setText(name);
			tmp.setBounds(10, 60 + 150 * list.size(), 790, 150);
		if (tmp.getY() >= 660)//kharej az mahdoode
			tmp.setVisible(false);
		tmp.setBackground(Color.WHITE);
		list.add(tmp);
	this.add(list.get(list.size()-1));


			
		}

	/**
	 * hazf JButton haye gofte shodwe az list va menu
	 * baraye location va group
	 * @param names
	 */
	/**
	 * hazf JButton haye gofte shodwe az list va menu
	 * baraye addressbook va contact
	 * @param names
	 * @param flag
	 */
	public void remover(String names[]) {
		for(int i=0;i<list.size();i++) 
			list.get(i).setBounds(10, 60 + 150 * i, 790, 150);
		int w;
			w=440;
			if(names!=null) {
		for(int i=0;i<names.length;i++) {
			this.remove(list.get(Integer.parseInt(names[i])));
			list.remove(Integer.parseInt(names[i]));
				this.remove(images.get(Integer.parseInt(names[i])));
				images.remove(Integer.parseInt(names[i]));
			}}
		for(int i=0;i<list.size();i++) {
			list.get(i).removeAll();
			list.get(i).setBounds(10, 60 + 150 * i, 790, 150);
			if(list.get(i).getY()>=660)
				list.get(i).setVisible(false);
			else
				list.get(i).setVisible(true);
				images.get(i).setBounds(800, 60 + 150 * i, 150, 150);
				if(images.get(i).getY()>=660)
					images.get(i).setVisible(false);
				else
					images.get(i).setVisible(true);
			}
		check.clear();
		
		for(int i=0;i<list.size();i++) {
			MouseListener[] tmp=list.get(i).getMouseListeners();
			System.out.println(tmp.length);
			
			list.get(i).removeMouseListener(tmp[1]);
			
			images.get(i).removeMouseListener(tmp[1]);
			list.get(i).addMouseListener(new mouse(i));
			images.get(i).addMouseListener(new mouse(i));
			}
		
	}
	/**
	 * tanzime value bar
	 * @param a
	 * @param b
	 */
	public void barValues(int a,int b) {
		bar.setValues(0, a, 0, b);
	}
	/**
	 * afzoode action be dokme akhar dar list
	 * @param action
	 */
	public void lastButtonAction(ActionListener action) {
		list.get(list.size()-1).addActionListener(action);
		images.get(images.size()-1).addActionListener(action);
		images.get(images.size()-1).addMouseListener(new mouse(images.size()-1));
		list.get(list.size()-1).addMouseListener(new mouse(list.size()-1));
	
	}
	/**
	 * "name anjast?
	 * dar vaghe chon texte dokme ha name aza hastand tarfandi baraye search ast
	 * @param name
	 * @return boolean
	 */
	public boolean isThereBt(String name) {
		boolean flag=false;
		for(int i=0;i<list.size();i++)
		{
			if(list.get(i).getText().equals(name)) {
				flag=true;
				break;
			}
		}
		return flag;
	}
	/**
	 * jabejayi menu
	 * dar bar estefade shode ast
	 * @param type  :noe panel(1,2,3)
	 * 
	 * @param l :ya -1 ya 1 ast va baraye bala payin raftan naghsh darad
	 */
	public  void mvObj(int l) {
		for (int i = 0; i < list.size(); i++) {
			int y=list.get(i).getY();
			
				list.get(i).setBounds(10, y + 150*l, 790, 150);
				images.get(i).setBounds(800, images.get(i).getY() + 150*l, 150, 150);
				if(list.get(i).getY()>=660 || list.get(i).getY()<60) {//check kardane mahdoode
					list.get(i).setVisible(false);
					images.get(i).setVisible(false);
				}
				else {
					list.get(i).setVisible(true);
				images.get(i).setVisible(true);
			}
				
			
				repaint();

			}
		
		
		
	}
	/**
	 * taghire halate panel be hazf kardan
	 * @param type
	 */
	public void delPanel() {
		n=0;//tedade entekhab shode ha
		
		del.setText("Delete");
		/////////////dokme haye ezafi makhfi mishavand
			delete.setVisible(false);
			newO.setVisible(false);
			underIt.setVisible(false);
			underIt2.setVisible(false);
			back.setVisible(false);
			del.setBounds(250, 680, 200, 70);
			cancel.setBounds(100, 680, 150, 70);
			cancel.setFont(new Font("Hobo Std", Font.PLAIN, 30));

			del.setFont(new Font("Hobo Std", Font.PLAIN, 30));
cancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
					delete.setVisible(true);
					newO.setVisible(true);
					
				
				
				for(int i=0;i<list.size();i++)
					list.get(i).removeAll();//pak shodane checkbox ha
				check.clear();//tamiz shodan arraye check box
		
	remove(del);
	remove(cancel);
	repaint();
				
			}
		});
		add(del);
		add(cancel);
		/////////be tedade button ha JCheckBox sakhte mishavad///////////////
		for(int i=0;i<this.list.size();i++) {
			JCheckBox chTmp=new JCheckBox();
			chTmp.setBounds(0, 0, 20, 20);
			
			chTmp.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {//ActionListener
					if(chTmp.isSelected())
						n++;//tedad ziad mishavad
					else
						n--;///tedad kam mishavad
					del.setText("Delete "+String.valueOf(n)+" choices");//update texte dokme hazf
				}
			});
			this.list.get(i).add(chTmp);
			check.add(chTmp);
		}
		//////////////////////////////////////////////////
		
		}
	/**
	 * vazife  hazf az panel va bargardandane  gozine haye entekhabi ra bar ohde darad
	 * @param type
	 * @return
	 */
public int[] delAction() {
	ArrayList<Integer> indexes=new ArrayList<>();

			int j=0;//chon ba hazf index ha yeki yeki kam mishavad
			if(n==0)//yani hich entekhabi nashode
				JOptionPane.showMessageDialog(null, "You didn't choose any thing", "Delete", 0, null);
			else {
			int bit=JOptionPane.showOptionDialog(null, "Are you sure?", "Delete groups", 0, 1, null, new String[] {"Yes","No"}, null);//motmaeni?
		String temp="";//liste index addressbook haye entekhab shode
			if(bit==0) {//Are mikham hazf konam!
				
				for(int i=0;i<check.size();i++) {
					if(check.get(i).isSelected()) {
						temp+=String.valueOf(i-j)+"#";//index sabt mishavad(string)
						indexes.add(i-j);//index sabt mishavad(araye)
					File file=new File("pictures/"+list.get(i).getText()+".jpg");
					file.delete();
						j++;
					}
				}
			}
		
			String arr[]=temp.split("#");//araye index ha
			if(temp.equals(""))
				arr=null;
			remove(del);
			remove(cancel);
			barValues(6,list.size()-j );//update bar
	
				delete.setVisible(true);
				newO.setVisible(true);
				

				
				remover(arr);//update panel
		
	
remove(del);
remove(cancel);
repaint();
			}

	
int [] index=new int[indexes.size()];//tabdile ArrayList<Integer> be int[]
for(int i=0;i<index.length;i++)
	index[i]=indexes.get(i);
check.clear();//reset check
return index;
}
/**
 * Adapter baraye MouseListener
 * @author Asus
 *
 */
class mouse implements MouseListener{
	int i;
public mouse(int i) {
	this.i=i;
}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		if(menuN!=i)
			sound();
				list.get(i).setSize(800, 160);
				list.get(i).setBackground(Color.cyan);
				images.get(i).setSize(160,160);
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		list.get(i).setSize(790, 150);
		images.get(i).setSize(150, 150);
		list.get(i).setBackground(Color.white);
		menuN=i;
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
public void sound() {
	sounder s=new sounder();
	s.start();

}
class sounder extends Thread{
	public void run() {
		try {
			Clip clip=AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(new File("sounds/menu.wav")));
			clip.start();
			Thread.sleep(500);
			clip.close();
			}
			catch (Exception e) {
				// TODO: handle exception
			}
	}
}

}
