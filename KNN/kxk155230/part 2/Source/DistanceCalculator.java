/**
 *
 * @author kunal krishna
 */

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


public class DistanceCalculator {

	
	
	float jackdistance(String s1, String s2){
		float dist;
		String[] s1_array = s1.split(" ",-1);
		String[] s2_array = s2.split(" ",-1);
		
		Set<String> set2 = new HashSet<String>(Arrays.asList(s2_array));
		Set<String> intersect = new HashSet<String>(Arrays.asList(s1_array));
		intersect.retainAll(set2);
		Set<String> union = new HashSet<String>(Arrays.asList(s1_array));
		union.addAll(set2);
		dist = (1 - (float)intersect.size()/union.size());
		return dist;
		
	}

}

