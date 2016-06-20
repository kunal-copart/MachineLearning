/**
 *
 * @author kunal krishna
 */



import java.io.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class DataReader {
	static int count_lines = 0;
	
	void fileSize(String infile){
		
		FileReader fr = null;
		try {
			fr = new FileReader (infile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		BufferedReader br = new BufferedReader(fr);
		//till end of file 
		try {
			while (br.readLine() != null) {
				count_lines++;
				}
		} catch (IOException e) {
			System.out.println("problem reading file");
			e.printStackTrace();
		}
	
	
		try {
			System.out.println("number of rows are = " + count_lines);
			fr.close();
			br.close();
		} catch (IOException e) {
			System.out.println("Error Closing file");
			e.printStackTrace();
		}
		
			
	}
		
	
	public String[][] reader(String infile){
		
		JSONObject obj 		= null;
		String[][] tweet 	= new String[count_lines + 1][3];
		int index = 1;
		FileReader fr = null;
		String line;
		
		try {
			fr = new FileReader (infile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		BufferedReader br = new BufferedReader(fr);
		//till end of file 
		try {
			while ((line = br.readLine()) != null) {
				
				try {
					obj = (JSONObject) new JSONParser().parse(line);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				String tweetText = (String) obj.get("text");
				String id = obj.get("id").toString();
				
				tweet[index][1] = tweetText;
				tweet[index][2] = id;
				index++;
	    
			}
		} catch (IOException e) {
			System.out.println("problem reading file");
			e.printStackTrace();
		}
	
	
		try {
			fr.close();
			br.close();
		} catch (IOException e) {
			System.out.println("Error Closing file");
			e.printStackTrace();
		}
				
		
		return(tweet);
	}
		
public int[] seedReader(String[][] tweet, String seedfile){
	
	String seedFromFile;
	int[] seed = new int[26];
	int index = 1;
	//Data at a URL
	FileReader fr = null;
	String line = null;
	
	try {
		fr = new FileReader (seedfile);
	} catch (IOException e) {
		e.printStackTrace();
	}

	BufferedReader br = new BufferedReader(fr);
	//till end of file 
	try {
		while ((line = br.readLine()) != null) {
			
			seedFromFile = line.replaceAll(",$", "");
			seedFromFile = seedFromFile.replaceAll("\n", "");
			for(int row = 1; row < tweet.length; row++){
				if(tweet[row][2].equals(seedFromFile)){
					seed[index++] = row;
					
				}
			}	
	
		}
	} catch (IOException e) {
		System.out.println("problem reading file");
		e.printStackTrace();
	}


	try {
		System.out.println("number of rows are = " + count_lines);
		fr.close();
		br.close();
	} catch (IOException e) {
		System.out.println("Error Closing file");
		e.printStackTrace();
	}
			

	
		return(seed);
	}
}

