/**
 *
 * @author kunal krishna
 */


import java.util.ArrayList;
import java.util.Collections;


public class Twitter {
	
	DataReader 				r = new DataReader();
	ClusterDisplayer  		d = new ClusterDisplayer();
	
	Twitter(int k, String seedfile, String infile, String outfile){
		
		String[][] tweet;
		int[]   seed;
		int[]   seed_list;
		
		
		r.fileSize(infile);
		
		
		tweet 	= r.reader(infile);
		seed 	= r.seedReader(tweet, seedfile);
		
		if(k > 25){
			k = 25;
		}
		else if(k < 2){
			k = 2;
		}
		
		seed_list = new int[k];
		if(k == 25)
			seed_list = seed;
		else
		{
			seed_list = randomSeeds(k, seed);
		}
		
		
		new ClusterProcessor(seed_list, tweet, k, outfile);
		
	}
	
	int[] randomSeeds(int k, int[] seed){
		int[] seed_list = new int[k + 1];
		
		ArrayList<Integer> numbers = new ArrayList<Integer>();
		numbers = getRandomNumbers(seed);
		
		for (int indexK = 1; indexK <= k; indexK++ ){
			seed_list[indexK] = seed[numbers.get(indexK - 1)];
				
		}
		
		return seed_list;
	}
	
	
	private ArrayList<Integer> getRandomNumbers(int[] seed){
		ArrayList<Integer> numbers = new ArrayList<Integer>();
		for(int i = 0; i < seed.length - 1; i++){
			numbers.add(i+1); 
		}
		
		Collections.shuffle(numbers);
		System.out.println(numbers);
		
		return numbers;
	}
	public static void main(String args[]){
		
		int k;
		String infile, outfile, seed;

		k 		= Integer.parseInt(args[0]);
		seed	= args[1];
		infile	= args[2];	 
		outfile	= args[3];
		
//		k = 25;
//		seed 	= "InitialseedsFile.txt";
//		infile 	= "tweets.json";
//		outfile = "output.txt";
//		
		
		new Twitter(k, seed, infile, outfile);
	}
}
