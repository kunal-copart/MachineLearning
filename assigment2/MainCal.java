import java.io.*;
import java.util.*;

public class MainCal{

public static double Calculate_Entropy(ArrayList<Attribute_list>  attribute){

     int count=0;
     double entropy;
	for(int i=0; i< attribute.size();i++)
	{
        if(attribute.get(i).get_output=="1")
        {
        	count++;
        }        
	}
          double probability = count / (double)attribute.size();
			if(count > 0) {
				entropy += -probability * (Math.log(probability) / Math.log(2));
			}



}



}