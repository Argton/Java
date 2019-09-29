import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import static javax.swing.JOptionPane.*;

public class Card extends JButton{
  private Status status = Status.HIDDEN; // Default
  
  public enum Status 
  {
    HIDDEN, VISIBLE, MISSING 
  }
  
  public Card()
  {
  }
  
  public Card(Icon icon)
  {
    status = Status.HIDDEN;
    this.setBackground(Color.BLUE);
    this.setOpaque(true);
    this.setIcon(icon);
  }
  
  public Card(Icon icon, Status status)
  {
    this.status = status;
    if(this.status==Status.MISSING)
    {
      this.setBackground(Color.WHITE);
    }
    else
    {
      this.setBackground(Color.BLUE);
    }
    this.setOpaque(true);
  }
  
  public void setStatus(Status stat)
  {
    this.status=stat;
    if(this.status==Status.MISSING)
    {
     this.setBackground(Color.WHITE);
    }
    else
    {
      this.setBackground(Color.BLUE);
    }
    this.setOpaque(true);
    repaint();
  }
  
  public Status getStatus()
  {
    return this.status;
  }
  
  public Card copy() 
  {
    Card card1 = new Card();
    card1.status = this.status;
    Icon icon = this.getIcon();
    card1.setBackground(this.getBackground());
    card1.setIcon(icon);
    card1.setOpaque(true);
    return card1;    
  }
  
  public boolean equalIcon(Card a)
  {
    return a.getIcon()==this.getIcon();
  }
}