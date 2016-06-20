/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author kunal krishna
 */
import java.io.*;
public class Display {

	
	void displayData(float[][] data){
		for( int indexRows = 1; indexRows < data.length; indexRows++){
			for( int indexCols = 1; indexCols < data[0].length; indexCols++){
				System.out.print(" " + data[indexRows][indexCols]);
			}
			System.out.println();
		}
	}
	
	void displayK(float[][] k){
		for( int indexRows = 1; indexRows < k.length; indexRows++){
			for( int indexCols = 1; indexCols < k[0].length; indexCols++){
				System.out.print(" " + k[indexRows][indexCols]);
			}
			System.out.println("");
		}
	}
	
	void displayCluster(int[] group, float[][] k, String outfile){
		//send to a file

	    File outFile = new File (outfile);
	    FileWriter fw = null;
		try {
			fw = new FileWriter (outFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    PrintWriter pw = new PrintWriter (fw);
	   
		for(int i = 1; i < k[1].length; i++){
			System.out.print("Cluser " + i + ":");
			pw.print("Cluser " + i + ":");
			for (int j = 1; j < group.length; j++){
				if(i == group[j]){
					System.out.print(" " + j);
					pw.print(" " + j);
				}
			}
			System.out.println("");
			pw.println("");
		}
		pw.close();
	}
	
}

