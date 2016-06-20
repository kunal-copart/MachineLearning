import java.util.List;
/* Author : kunal krishna
 * created_date: 25th Jan 2015 
 * Description:  Implementation of ID3 Algorithm 
 * */

public class DTImpl{
	/* Function to print Usage of Program */
	public static void printUsage() {
		System.out.println("---------------------------- Usage-----------------------------");
		System.out.println("<L> <K> <training-set> <validation-set> <test-set> <to-print>");
		System.out.println("<L>: Inetger (used in post pruning Algorithm ");
		System.out.println("<K>: Inetger (used in post pruning Algorithm ");
		System.out.println("<training-set>: location of your training set data ");
		System.out.println("<validation-set>: location of your validation set data ");
		System.out.println("<test-set>: location of your test set data ");
		System.out.println("<to-print>: yes-> to print the tree no-> to not print the tree");
		System.out.println("---------------------------------------------------------------");
	}

	
	
	public static void displayData(){
		
	}

	public static void main(String[] args) {
		// show how to use the program
		// command to show usage is --usage
		if (args == null || args.length == 0 || args[0] == null || args[0].trim().length() == 0) {
			System.out.println("For Usage of this Program input --usage as parameter");
			System.exit(1);
		}

		if ("--usage".equals(args[0])) {
			printUsage();
			System.exit(1);
		}

		if (args.length < 6) {
			System.out.println("Not Sufficient Inputs ,see Usage of this program");
			printUsage();
			System.exit(1);
		}
		

		try {
			Input input = new Input();
			// load training data
			List<DataPoint> trainData  = input.loadData(args[2]);
			if(trainData==null){
				System.err.println("Error loading in training Data");
				System.exit(1);
			}
			
			List<String> attributeNames = input.getAttributeNames();
			DT dt = new DT();
			System.out.println("Running Information Gain Heuristic");
			dt.createDT(trainData, attributeNames);
			runHeuristic(dt, args[3], args[4],args[5], Integer.parseInt(args[0]), Integer.parseInt(args[1]));
			System.out.println("End of Infomarion Gain Heuristic");
			//System.out.println(trainData.size());
			System.out.println("Running Variance Impurity Gain Heuristic");
			DT dtVI = new DT();
			dtVI.createVIDT(trainData, attributeNames);
			runHeuristic(dtVI, args[3], args[4],args[5],Integer.parseInt(args[0]), Integer.parseInt(args[1]));
			System.out.println("End of Variance Impurity Gain Heuristic");
		} catch (Exception ex) {
			ex.printStackTrace();
			//System.err.println("Exception in reading File");
		}

	}
	
	public static void runHeuristic(DT dt , String validationFile , String testFile , String option  , int L , int K)  throws Exception{
		if("yes".equalsIgnoreCase(option)){
		 System.out.println("printing UnPruned Tree");
		 dt.printTree(dt.root);
		 System.out.println("End of printing UnPruned Tree");
		}
		Input test = new Input();
		//load test data 
		List<DataPoint> testData = test.loadData(testFile);
		if(testData==null){
			System.err.println("Error loading in test Data");
			System.exit(1);
		}
		double acc_bef_prune = dt.evaluateTree(testData);
		
		System.out.println("Accuracy before pruning is" + acc_bef_prune);
		
		Input val = new Input();
		//load validation data 
		List<DataPoint> validationData = val.loadData(validationFile);
		
		if(validationData==null){
			System.err.println("Error loading in validation Data");
			System.exit(1);
		}
		
		DTNode dBest =dt.postPrune(L, K, validationData);
		double acc_after_prune =  dt.evaluateTreeUtil(dBest,testData);
		if("yes".equalsIgnoreCase(option)){
		System.out.println("printing Pruned Tree");
		dt.printTree(dBest);
		System.out.println("End of printing Pruned Tree");
		}
		//System.out.println(validationData.size());
		//System.out.println(testData.size());
		System.out.println("Accuracy after pruning is" + acc_after_prune); 
		
	}

}
