import java.util.*; // needed for Random

public class CrystalModel
{
  private int frameSize; 
  boolean[][] model; 
  int x, y; // coordinates  
  int outerRadius;
  int startRadius;
  Random number = new Random();
  static final double PI = 3.14159265;
  int angle; 
  
  public CrystalModel(int width)
  {
    frameSize = width; 
    outerRadius = (frameSize/2)-25; 
    startRadius = (int) (outerRadius*0.90);
    reset();
  }
  
  public boolean crystallizeOneIon() // Crystallize ion and check if it reaches border
  {
    dropNewIon(); 
    while(anyNeighbours(x, y)==false)
    {
      if((outsideCircle(outerRadius, x, y)))
      {
        dropNewIon();
      }
      moveIon(number.nextInt(4));
    }
    model[xBathToModel(x)][yBathToModel(y)] = true; 
    return !outsideCircle(startRadius, x, y);
  }
    
  private boolean outsideCircle(int r, int x, int y) 
  {
    if(x*x + y*y > r*r) 
    {
      return true; 
    }
    return false; 
  }
  
  private boolean anyNeighbours(int x, int y) // 
  {
    if(model[xBathToModel(x)+1][yBathToModel(y)] || model[xBathToModel(x)-1][yBathToModel(y)] || model[xBathToModel(x)][yBathToModel(y)+1] || model[xBathToModel(x)][yBathToModel(y)-1])
    {
      return true;
    }
    return false;
  }                          
  
  void dropNewIon()
  {
    angle = number.nextInt(360);
    x = (int) (startRadius*Math.cos(Math.toRadians(angle)));
    y = (int) (startRadius*Math.sin(Math.toRadians(angle))); 
  }
      
  void reset()
  {
    model = new boolean[frameSize][frameSize];
    for(int i=0; i<model.length; i++)
    {
      for(int j=0; j<model.length; j++)
      {
        model[i][j] = false; 
      }
    } 
    model[xBathToModel(0)][yBathToModel(0)] = true;
  }
    
  void moveIon(int direction) 
  {
    if(direction == 0) 
      x = x+1;
    if(direction == 1)
      x = x-1;
    if(direction == 2)
      y = y+1;
    if(direction == 3)
      y = y-1;
  }
    
  int xBathToModel(int x) 
  {
    return x+frameSize/2;
  }
  
    int yBathToModel(int y) 
  {
    return y+frameSize/2;
  }
  
  public int getFrameSize()
  {
    return frameSize;
  }
  
  boolean getModelValue(int x, int y)
  {
    return model[x][y];
  }
  
  public int getX()
  {
      return x;
  }
  
  public int getY()
  {
      return y;
  }
    
  public boolean runSomeSteps(int steps) {
    int i= 0;
    boolean goOn = false;
    do 
    {
      goOn = crystallizeOneIon();
      i++;
    } 
    while ( i<steps && goOn );
    return goOn; 
  }  
  
  public String toString() 
  {
    StringBuffer s = new StringBuffer(1000);
    for(int i=0; i<model.length+2; i++) 
    {
      s.append("-");
    }
    s.append("\n");
    for(int i=0; i<model.length; i++) 
    {
      s.append("|");
      for(int j=0; j<model.length; j++) 
      {
        if (model[i][j]) {
          if (i==xBathToModel(x) && j==yBathToModel(y)) 
          {
            s.append("#");
          } 
          else 
          {
            s.append("*");
          }
        } 
        else 
        {
          s.append(" ");
        }
      }
      s.append("|");
      s.append("\n");
    }
    for(int i=0; i<model.length+2; i++) 
    {
      s.append("-");
    }
    s.append("\n");
    return s.toString();
  }
}
