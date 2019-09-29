import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CrystalView extends JPanel
{  
  private CrystalModel model1;
  private Font font;
  boolean model;
  int width;
  
  public CrystalView(CrystalModel model1) 
  {
    this.model1 = model1;
    setPreferredSize(new Dimension(model1.getFrameSize(),model1.getFrameSize())); 
    setBackground(Color.BLACK);  
    width = model1.getFrameSize();
  }
  
  public void paintComponent(Graphics g) 
  {    
    super.paintComponent(g);
    g.setColor(Color.WHITE); 
    for (int i= 0; i<width; i++)
    {
      for (int j = 0; j<width; j++)
      {
        if (model1.getModelValue(i,j))
        {
          g.fillRect(i, j, 1, 1);
        }   
      }      
    }
    g.setColor(Color.RED);
    g.fillOval(model1.xBathToModel(model1.getX()), model1.yBathToModel(model1.getY()), 5, 5);
  }
}

