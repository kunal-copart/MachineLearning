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
import java.util.ArrayList;
import java.util.Collections;

public class Processor {
	
	//Declarations
	private 	float[][] 	dataMatrix;
	private 	float[][] 	distMatrix;
	private 	float[][] 	kMat;
	private		int[] 		groups;
	private		Display 	d;
	private 	String 		infile;
	Cluster c= new Cluster();
	//count number of lines in a file
	int fileSize(String in){
		int 	count = 0;
		
		FileReader fr = null;
			try {
				fr = new FileReader (in);
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		BufferedReader br = new BufferedReader(fr);
		//till end of file 
		try {
			while (br.readLine() != null) {
				count++;
				}
		} catch (IOException e) {
			System.out.println("problem reading file");
			e.printStackTrace();
		}
		
		this.infile = in;
		
		try {
			System.out.println("number of rows are = " + count);
			fr.close();
			br.close();
		} catch (IOException e) {
			System.out.println("Error Closing file");
			e.printStackTrace();
		}
		
		//return number of files
		return(count);
		
	}
		
	//fill the data in a matrix
	float[][] createDataMatrix(int dataRows){
		int 	index = 1, col = 0;
		String  line;
		
		dataMatrix = new float[dataRows][3];
		
		 FileReader fr = null;
			try {
				fr = new FileReader (infile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		BufferedReader br = new BufferedReader(fr);
		
		try {
			line = br.readLine();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		//till end of file
		
		try {
			while ((line = br.readLine() )!= null) {
				String[] splited = line.split("\\s+");
				for(int i = 0; i < 3; i++){
					dataMatrix[index][col++] = Float.parseFloat(splited[i]);
					System.out.println("Added " +dataMatrix[index][col - 1]);
				}
				index++;
				col = 0;
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
		   
		return dataMatrix;
		
	}
	
	
	
	

	
	
}

