/**
 *
 * @author kunal krishna
 */


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class ClusterDisplayer {

	
	void display(String[][] tweetString){
		int index;
		
		for(index = 1; index < tweetString.length; index++){
			for(int j = 1; j < 3; j++){
				System.out.println(tweetString[index][j]);
			}
		}
	}

	void displayClusters(String[][] tweets, int[] groups, int[] seeds, String outfile){
		int step = 1;
		  File outFile = new File (outfile);
		    FileWriter fw = null;
			try {
				fw = new FileWriter (outFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
			PrintWriter pw = new PrintWriter (fw);
			//pw.write("hello");
		
		for(int i = 1; i < seeds.length; i++){
			System.out.print("\nCluster " + i + ": Processed");
			pw.print("Cluster " + i + ":");
			for(int j = 1; j < groups.length; j++){
				if(groups[j] == i){
						if(step == 1){
							pw.print(tweets[j][2]);
							step++;
						}
						else
							pw.print(", " + tweets[j][2] );
					}
			}
			step = 1;
			pw.println();
		}
		System.out.println("");
		System.out.println("end");
		pw.close();
		
	}
	
	

}


