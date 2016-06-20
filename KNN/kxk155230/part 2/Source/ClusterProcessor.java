/**
 *
 * @author kunal krishna
 */

public class ClusterProcessor {

	int[] 				seeds;
	String[][] 			tweets;
	float[][] 			centroidMatrix;
	DistanceCalculator 	dist;
	int[]				groups;
	float[][]   		distanceMatrix;
	int 				systemState = 0;
	int 				iteration = 1;
	ClusterDisplayer	disp;
	int					clusters = 0;
	//if system state = 0 then it still needs processing

	
	
	ClusterProcessor(int[] seeds, String[][] tweets, int k, String outfile){
		dist = new DistanceCalculator();
		disp = new ClusterDisplayer();
		this.seeds 	= seeds;
		this.tweets = tweets;
		clusters	= k;
		
		while(systemState == 0){
			System.out.println("Iteration = " + iteration);
			centroid();
			iteration++;
		}
		
		disp.displayClusters(this.tweets, this.groups, this.seeds, outfile);
		error(k);
	}
	
	
	public void centroid(){
	
		centroidMatrix = new float[seeds.length][tweets.length + 1];
		distanceMatrix = new float[tweets.length][tweets.length]; 
		
		for (int seed_index = 1; seed_index < centroidMatrix.length; seed_index++ ){
			for(int tweet_index = 1; tweet_index < centroidMatrix[1].length - 1; tweet_index++){
				centroidMatrix[seed_index][tweet_index] = dist.jackdistance(tweets[seeds[seed_index]][1], tweets[tweet_index][1]);
			}
		}
		
		for (int row = 1; row < tweets.length; row++ ){
			for(int col = 1; col < tweets.length; col++){
				distanceMatrix[row][col] = dist.jackdistance(tweets[row][1],tweets[col][1]);
				
			}
		}
		
		
		groupCluster(centroidMatrix);
		
		newCentroids();
		
		
	}
	
	@SuppressWarnings("unused")
	private void displayCentroid(float[][] cent){
		for(int i = 1; i < cent.length; i++){
				System.out.println(" !" + cent[i][cent[1].length - 1]);
		}
	}
	
	public void groupCluster(float[][] centroidDistanceMatrix){
		int min_index = -1;
		float min_dist = 100;
		groups = new int[centroidDistanceMatrix[1].length - 1];
		
		for(int col = 1; col < centroidDistanceMatrix[1].length - 1; col++){
			for( int row = 1; row < centroidDistanceMatrix.length; row++ ){
				if(centroidDistanceMatrix[row][col] < min_dist){
					min_dist = centroidDistanceMatrix[row][col];
					min_index = row; 
				}
				
			}
			groups[col] = min_index;
			centroidMatrix[min_index][tweets.length] += 1;
			min_dist = 100;
		}
		
	}
	
	void displayGroups(){
			for( int row = 1; row < groups.length; row++ ){
					System.out.println("row ->" + groups[row]); 
			}
	}
	
	void newCentroids(){
		float sdist = 1000;
		float sum = 0;
		int old_seed;
		int new_seed;
		systemState = 1;
		for (int seeder = 1; seeder <seeds.length; seeder++){
			old_seed = seeds[seeder];
			for (int i = 1; i < groups.length; i++){
				sum = 0;
				if(groups[i] == seeder){
					for (int ji = 1; ji < tweets.length; ji++){
						if(seeder == groups[ji]){
							sum += dist.jackdistance(tweets[i][1], tweets[ji][1]);
							
						}
						
					}
							
					if(sum < sdist){
						sdist = sum;
						sum = 0;
						seeds[seeder] = i;
					}
					
				}
			}
			new_seed = seeds[seeder];
			System.out.println("New seed = " + tweets[new_seed][2]);
			if(new_seed != old_seed){
				systemState = 0;
			}
			else{
				System.out.println("end");
			}
			sdist = 1000;
			sum = 0;
			
		}
		
		
	}
	
	public void error(int k){
		float sum = 0;
		for(int cluster = 1; cluster <= k; cluster++ ){
			for (int j = 1; j < groups.length; j++){
				if(cluster == groups[j]){
					sum += (float) (Math.pow(dist.jackdistance(tweets[seeds[cluster]][1], tweets[j][1]),2));
				}
			}
		}
	System.out.println("sse is " + sum);	
	}

	
	
}
