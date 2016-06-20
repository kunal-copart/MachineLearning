import java.io.*;
import java.util.*;
import java.io.FileReader;
import java.io.BufferedReader;
import java.net.*;

public class ReadFile{

  public static final String testfile="/test_set.csv";

   public ArrayList<Attribute_list> Prepare_Record(){

     String line="";
     ArrayList<Attribute_list> record= new ArrayList<Attribute_list>();
 
   try{
   	  File file = new File(getClass().getResource(testfile).toURI());
   	  BufferedReader br=new BufferedReader(new FileReader(file));
        while((line=br.readLine())!=null){     
           Attribute_list data=new Attribute_list();
           String lines[] = line.split(",");   
           
       /*  data.Set_XB(lines[0]);
           data.Set_XC(lines[1]);
           data.Set_XD(lines[2]);
           data.Set_XE(lines[3]);
           data.Set_XF(lines[4]);
           data.Set_XG(lines[5]);
           data.Set_XH(lines[6]);
           data.Set_XI(lines[7]);
           data.Set_XJ(lines[8]);
           data.Set_XK(lines[9]);
           data.Set_XL(lines[10]);
           data.Set_XM(lines[11]);
           data.Set_XN(lines[12]);
           data.Set_XO(lines[13]);
           data.Set_XP(lines[14]);
           data.Set_XQ(lines[15]);
           data.Set_XR(lines[16]);
           data.Set_XS(lines[17]);
           data.Set_XT(lines[18]);
           data.Set_XU(lines[19]);
           data.Set_output(lines[20]);      */

          
            record.add(data);
            
          }

          br.close();
      }
          catch (FileNotFoundException e) {
		       e.printStackTrace();}  
		       
		  catch (URISyntaxException e) {
		       e.printStackTrace();} 
		  
		  catch (IOException e) {
				e.printStackTrace();
			}  
    
  return record;
}


public double Get_Entropy(ArrayList<Attribute_list>> data){
	if (data!=null)
	{
   MainCal m= new MainCal();
   double entropy_value;
   String attribute;
   entropy_value=m.Calculate_Entropy(data);  
   }
   return entropy_value;
}


public static void main(String args[])
{
ReadFile rf= new ReadFile();
ArrayList<Attribute_list> a= new ArrayList<Attribute_list>();
a= rf.Prepare_Record();
double root_entropy;
int count=0;
     for(int i=0;i<a.size();i++){
        if(!(a.get(i).Get_output().equals("1"))){
        	//if(a.get(i).Get_XB().equals("1")){
        		count++;
        	//}
        }
     }
     System.out.println(count);
     System.out.println(a.size());
     System.out.println(a.get(3).Get_output());
     root_entropy=rf.Get_Entropy(a);
     

}

}