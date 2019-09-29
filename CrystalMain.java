import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CrystalMain extends JFrame
{
  public static void main(String[] args)
  {
    int i = 200;
    try{
      i = Integer.parseInt(args[0]);
    }
    catch (NumberFormatException e) 
    {
      System.out.println("Needs to be an integer, setting to defaultvalue 200");
    }
    
    if (i <100 || i >1000)
    {
      System.out.println("Bad input. Setting to defaultvalue 200.");
      i=200;
    }
    JFrame window = new JFrame("Crystal");
    
    CrystalModel model = new CrystalModel(i); 
    CrystalView view = new CrystalView (model);
    CrystalControl control = new CrystalControl(model, view);
    
    window.setLocation(100,100);
    window.setPreferredSize(new Dimension(i,i));
    window.add(control);
    window.pack();
    window.setVisible(true);
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
}
