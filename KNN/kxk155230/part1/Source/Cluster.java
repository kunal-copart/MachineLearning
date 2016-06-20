/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author kunal krishna
 */
public class Cluster {
    
    private 	float[][] 	kMat;
    private 	float[][] 	dataMatrix;
    private 	float[][] 	distMatrix;
    private	Display d;
    private		int[] 		groups;
    
    
    //create cluster matrix
	void creatKMatrix(int k, float[][] dataM){
		d = new Display();
                dataMatrix=dataM;
		kMat = new float[8][k + 1];
		ArrayList<Integer> numbers = new ArrayList<Integer>();
		//generate k random numbers b/w 1 to dataMatrix number of rows
		numbers = getRandomNumbers(k,dataMatrix);
		
		for (int indexK = 1; indexK <= k; indexK++ ){
			kMat[1][indexK] = dataMatrix[numbers.get(indexK - 1)][1];
			kMat[2][indexK] = dataMatrix[numbers.get(indexK - 1)][2];
				
		}
		
		}
        
      void updateKMat(int col, int min_index){
		kMat[3][min_index] += 1;
		kMat[4][min_index] += dataMatrix[col][1];
		kMat[5][min_index] += dataMatrix[col][2];
		}
      
      public int groupCluster(){
		int min_index = -1;
		float min_dist = 100;
		groups = new int[distMatrix[1].length];
		
		for(int col = 1; col < distMatrix[1].length; col++){
			for( int row = 1; row < distMatrix.length; row++ ){
				if(distMatrix[row][col] < min_dist){
					//System.out.println("row " + row); 
					min_dist = distMatrix[row][col];
					min_index = row; 
				}
				
			}
			groups[col] = min_index;
			//System.out.println("The groups at " + col + " is " + groups[col] );
			updateKMat(col, min_index);
			min_dist = 100;
		}
		System.out.println("k matrix");
		//displayK();
		return(centroidRecal());
	}
      
      void distanceMatrix(int k){
		distMatrix = new float[k + 1][dataMatrix.length];
		for(int index_k = 1; index_k < kMat[1].length; index_k++){
		    for(int index_col = 1; index_col < dataMatrix.length; index_col++){
	                distMatrix[index_k][index_col] = (float) Math.sqrt(Math.pow(kMat[1][index_k] - dataMatrix[index_col][1], 2) + 
				Math.pow(kMat[2][index_k] - dataMatrix[index_col][2], 2)); 
			}
		}
		System.out.println();
		System.out.println();
		System.out.println();		
}
      
      
	public int centroidRecal(){
		int state = 1;
		for(int index = 1; index < kMat[1].length; index++){
			kMat[1][index] = kMat[4][index]/kMat[3][index];
			kMat[2][index] = kMat[5][index]/kMat[3][index];
			kMat[3][index] = 0;
			kMat[4][index] = 0;
			kMat[5][index] = 0;
		
			if(kMat[1][index] != kMat[6][index] || kMat[2][index] != kMat[7][index] )
				{
				state = 0;
				System.out.println("first Level");
				System.out.println(kMat[1][index]);
				System.out.println(kMat[2][index]);
				System.out.println(kMat[6][index]);
				System.out.println(kMat[7][index]);
				}
			else{
				System.out.println("next level ");
				
				System.out.println(kMat[1][index]);
				System.out.println(kMat[2][index]);
				System.out.println(kMat[6][index]);
				System.out.println(kMat[7][index]);
			}
			
			kMat[6][index] = kMat[1][index];
			kMat[7][index] = kMat[2][index];
		}
		
		System.out.println("\n The new matrix is ");
		
		//displayK();
		return(state);
	}
    
    private ArrayList<Integer> getRandomNumbers(int k,float[][] dataMatrix){
		
            ArrayList<Integer> numbers = new ArrayList<Integer>();
		for(int i = 0; i < dataMatrix.length - 1; i++){
			numbers.add(i+1); 
		}
		
		Collections.shuffle(numbers);
		System.out.println(numbers);
		
		return numbers;
	}
        
    public void displayClusters(String outfile){
		d.displayCluster(groups, kMat, outfile);
	}
    
    public void error(int k){
		float sum = 0;
		for(int cluster = 1; cluster <= k; cluster++ ){
			for (int j = 1; j < groups.length; j++){
				if(cluster == groups[j]){
					sum += (float) (Math.pow(kMat[1][cluster] - dataMatrix[j][1], 2) + Math.pow(kMat[2][cluster] - dataMatrix[j][2], 2));
				
				}
			}
		}
	System.out.println("sse is " + sum);	
	}
    

    
	void displayDist(){
		//d = new Display();
		d.displayData(distMatrix);
	}
        
        
        void displayK(){
		
		d.displayK(kMat);
	}
        
    
}
