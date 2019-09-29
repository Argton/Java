import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;  
import java.time.*;
import javax.swing.Timer;
import static javax.swing.JOptionPane.*;

public class Memory extends JFrame implements ActionListener
{
  
  private static int rows,columns;
  private String indata;
  int wins = 0;
  int gamesPlayed = 0;
  int delay = 1000; // How long to wait before turning over card
  int i = 0; // Used to keep track of clicked card
  int points1;
  int points2;
  
  // Layout
  JButton newGame = new JButton("Nytt");
  JButton quitGame = new JButton("Avsluta");
  JButton player1 = new JButton("Player1");
  JButton player2 = new JButton("Player2");
  JPanel p1 = new JPanel();      
  JPanel p2 = new JPanel();
  JPanel p3 = new JPanel();
  JLabel result = new JLabel("Player 1: " +points1 +". \n Player 2: " +points2 +".", JLabel.CENTER); 

  // Cards
  Card[] temp = null; 
  String[] name = null;
  Card[] playingCards = null;
  Card[] playingCards2 = null;
  public Card.Status status = Card.Status.HIDDEN; 
  
  Memory()
  {     
    // Get the pictures
    File folder = new File("myPictures"); 
    File[] pictures = folder.listFiles();
    temp = new Card[pictures.length];
    name = new String[pictures.length];
    
    for(int i=0; i<pictures.length; i++)
    { 
      name[i] = pictures[i].getPath();    
      ImageIcon image = new ImageIcon(name[i]);
      Card card = new Card(image);
      temp[i]=card;          
    }
    
    // Layout
    this.newGame();
    p1.setLayout(new GridLayout(1,2)); 
    p1.add(newGame);     
    p1.add(quitGame); 
    p2.setLayout(new GridLayout(2,1));
    p2.add(player1);
    p2.add(player2);
    p2.add(result);
    p3.setLayout(new GridLayout(rows,columns));
    setBackground(Color.white);
    setPreferredSize(new Dimension(1156,860));
    p2.setPreferredSize(new Dimension(300,100));
    setLayout(new BorderLayout());
    add("South", p1);   
    add("West", p2);
    add("East", p3);    
    newGame.addActionListener(this);
    quitGame.addActionListener(this);
    player1.addActionListener(this);
    player2.addActionListener(this);
    player1.setBackground(Color.ORANGE);   
    this.setSize(1156,860);
    pack(); 
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    repaint();
    revalidate();
    setVisible(true);           
  }
  
  public void newGame()
  {
    points1=0;
    points2=0;
    result.setText("Player 1: " +points1 +". \n Player 2: " +points2 +"."); 
    indata = JOptionPane.showInputDialog("Number of rows?");
    rows = Integer.parseInt(indata);
    indata = JOptionPane.showInputDialog("Number of columns?");
    columns = Integer.parseInt(indata);
    int n = rows * columns;
    playingCards = new Card[n]; 
    playingCards2 = new Card[n];
    Card card2 = new Card(null);
    
    for (int m=0; m < playingCards.length; m++)
    {
      card2.setIcon(null);
      playingCards2[m] = card2;
    }
    
    for(int j=0; j < n/2; j++)
    {      
      playingCards[j] = temp[j];
      playingCards2[j+n/2] = playingCards[j].copy();
      playingCards[j+n/2] = playingCards[j].copy();  
      playingCards2[j] = playingCards[j].copy();
    }
    Tools.randomOrder(playingCards);
    
    for(int k=0; k<playingCards.length; k++)
    {
      playingCards2[k].setIcon(null);
      playingCards2[k].setPressedIcon(playingCards[0].getIcon());
      playingCards2[k].addActionListener(this);
      int wi=(1156-320) / columns;
      int he=860 / rows;
      playingCards2[k].setPreferredSize( new Dimension(wi,he) );
      p3.add(playingCards2[k]);
      playingCards2[k].addMouseListener(l);
      p3.setLayout(new GridLayout( rows,columns) );
      this.revalidate();      
    }
  }
    
  // Event handling
  MouseListener l= new MouseAdapter() 
  { 
    int clicked=0;
    public void mousePressed(MouseEvent e) 
    {
      clicked+=e.getClickCount();
      if(clicked%2==0)
      {
        i=0;
        if(player1.getBackground()==Color.ORANGE)
        {
          player1.setBackground(Color.WHITE);
          player2.setBackground(Color.ORANGE);
        }
        else
        {
          player2.setBackground(Color.WHITE);
          player1.setBackground(Color.ORANGE);
        }
        tim2.start();        
      }      
    }    
  }; 
  
  // Event handling
  ActionListener taskPerformer = new ActionListener() 
  {
    public void actionPerformed(ActionEvent evt) 
    {
      i=0;
      for(int h=0; h<playingCards2.length; h++)
      {
        playingCards2[h].setIcon(null); 
      }
      Card[] card3 = new Card[2];
      int[] index = new int[2];
      for(int u=0; u<playingCards2.length; u++)
      {        
        if(playingCards2[u].getStatus()==status.VISIBLE)
        {
          Icon icon = playingCards[u].getIcon();
          card3[i] = playingCards[u].copy();
          index[i] = u;
          i += 1;
        }
      }
      if(card3[0].getIcon() == card3[1].getIcon())
      {       
        playingCards2[index[0]].setStatus(status.MISSING); 
        playingCards2[index[1]].setStatus(status.MISSING);    
        playingCards2[index[0]].setEnabled(false);
        playingCards2[index[1]].setEnabled(false);
        
        if(player1.getBackground() == Color.ORANGE)
        {
          points1=points1+1;
        }
        else
        {
          points2=points2+1;
        }
        
        result.setText("Player 1: " +points1 +". \n Player 2: " +points2 +"."); 
      }      
      else
      {
        playingCards2[index[0]].setStatus(status.HIDDEN); 
        playingCards2[index[1]].setStatus(status.HIDDEN);  
      }
      int nbrOfMissing=0;
      
     for(int i=0; i<playingCards2.length; i++)
     {
        if(playingCards2[i].getStatus() != status.MISSING)
        {
          nbrOfMissing += 1;
        }
      }
     if(nbrOfMissing == 0)
     {
       if(points1 > points2)
       {
        result.setText("Player 1 wins!"); 
       }
       else if(points2 > points1)
       {
        result.setText("Player 2 wins!"); 
       }
       else
       {
         result.setText("It's a tie!"); 
       }
     }
      tim2.stop();
      i=0;
    }
    
  };
  
  
  Timer tim2=new Timer(delay, taskPerformer);
  
  // Event handling
  public void actionPerformed(ActionEvent e) 
  {    
    if (e.getSource() == quitGame)
    {       
      System.exit(0);
    }        
    if (e.getSource() == newGame)
    {      
      for(int i=0; i<playingCards.length; i++)
      {
        p3.remove(playingCards2[i]);                
      }
      this.revalidate();
      newGame();
    }   
    
    for (int o=0; o<playingCards2.length; o++)
    {
      if (e.getSource() == playingCards2[o])
      {          
        playingCards2[o].setIcon(playingCards[o].getIcon());             
        playingCards2[o].setStatus(status.VISIBLE);           
      }
    }
  }
    
    
    public static void main (String[] arg)
    {
      Memory b = new Memory();
    }
    
}
