/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kunal krishna
 */
public class Kmeans {
	Processor p = new Processor();
        Cluster c= new Cluster();
	int lines;
	int state = 0, iterator = 0;
        private 	float[][] 	dataMatrix;
	
	Kmeans(int k, String infile, String outfile){
		//Passing the file path to dataReader in Processor class.
		//prepares a matrix for data - considering IO is costlier than space.
		lines = p.fileSize(infile);
		
		//Data to Matrix
		dataMatrix=p.createDataMatrix(lines);
		
		if(k > lines - 1){
			System.out.println("Number of clusters entered is more than number of data lines,");
			System.out.println("Hence changing number to maximum - " + (lines - 1));
			k = lines - 1;
		}
		else if(k < 1){
			System.out.println("Number of clusters should be atleast 1,");
			System.out.println("Hence changing number to minimum - " + 1);
			k = 1;
		}
		
		//Matrix for Cluster points
		c.creatKMatrix(k,dataMatrix);
			
		//till centroid doesn't change or iterations are not 20
		while(state != 1 && iterator < 20){
			
			//calculate distance of each point from centroid
			c.distanceMatrix(k);
			
			//check if the centroid is same as the old centroid after clustering
			state = c.groupCluster();
			iterator++;		
		}
		//check if centroid was found or iterations reached 20
		System.out.println("Iterator is  " + iterator + " and state is " + state);
		c.displayClusters(outfile);
		c.error(k);
	}
	
	
	public static void main(String args[]){
		
		int k;
		String infile, outfile;
		
		System.out.println("\nWelcome To Kmeans Program");
		k 		= Integer.parseInt(args[0]);
		infile	= args[1];	 
		outfile	= args[2];
//                
//                k=5;
//                infile	= "data.txt";	 
//		outfile	= "output.txt";
		
		new Kmeans(k, infile, outfile);
		
	}
}

