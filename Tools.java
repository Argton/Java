import java.util.*;

public class Tools
{  
  public static void randomOrder(Object[] obj )
  {
    Random r1=new Random();    
    Object[] temp =new Object[obj.length]; 
    for(int i=0; i<obj.length; i++)
    {
      int pos=r1.nextInt(obj.length);
      while(temp[pos]!=null)
      {
        pos=r1.nextInt(obj.length);
      }
      temp[pos]=obj[i];      
    }
    for(int i=0; i<obj.length; i++)
    {
      obj[i]=temp[i];
    }
  }
}