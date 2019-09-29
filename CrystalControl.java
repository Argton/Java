import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer.*;


public class CrystalControl extends JPanel 
{
  private Timer timer;
  private CrystalView view;
  private CrystalModel model;
  private int speed = 10;
  
  
  public CrystalControl (CrystalModel model, CrystalView model2) 
  {
    this.model = model;
    view = model2;
    
    JButton runButton = new JButton("Run"); 
    JButton stopButton = new JButton("Stop");
    JButton changeSpeedButton = new JButton ("Change Speed");
    
    runButton.addActionListener(new RunListener()); 
    stopButton.addActionListener(new StopListener());
    changeSpeedButton.addActionListener(new ChangeSpeedListener());
    
    setLayout(new BorderLayout()); 
    add(view,BorderLayout.NORTH);
    view.add(changeSpeedButton,BorderLayout.WEST);
    view.add(runButton,BorderLayout.CENTER);
    view.add(stopButton,BorderLayout.EAST);
    TimerListener klocka = new TimerListener();    
    timer = new Timer(0, klocka);     
  }
  
  private class TimerListener implements ActionListener 
  {
    public void actionPerformed (ActionEvent e)
    {
      if(!model.runSomeSteps( 10*speed ))
      {
        timer.stop();
      }
      view.repaint();      
    }
  }
  
  private class RunListener implements ActionListener 
  {
    public void actionPerformed (ActionEvent e) 
    {       
      model.reset();
      timer.start();      
    }
  }    
  
  private class StopListener implements ActionListener 
  {
    public void actionPerformed (ActionEvent e) 
    {
      timer.stop();
    }
  }
  
  private class ChangeSpeedListener implements ActionListener //ChangeSpeed-button
  {
    public void actionPerformed (ActionEvent e)
    {      
      String ans = JOptionPane.showInputDialog("Choose a speed between 1-50:");      
      speed = Integer.parseInt(ans.trim()); 
      if (speed<1 || speed>50 )
      {
        throw new IllegalArgumentException("You need to input a value 1-50.");
      }      
      timer.stop();
    }
  }
}
