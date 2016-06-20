
import java.util.ArrayList;
import java.util.List;
///import ML_Assignment.Input.DataPoint;

public class DT {

	public DTNode root;
	private List<String> attributeList;

	public void createDT(List<DataPoint> datapoints, List<String> attriList) {
		root = new DTNode();
		this.attributeList = attriList;
		createDTUtil(root, datapoints, attriList, 0);
	}

	public void createVIDT(List<DataPoint> datapoints, List<String> attriList) {
		root = new DTNode();
		this.attributeList = attriList;
		createVIDTUtil(root, datapoints, attriList, 0);
	}

	public double calculateRootVI(List<DataPoint> dataPoints, int[] O1Count) {
		double VI = 0;
		for (int index = 0; index < dataPoints.size(); index++) {
			if (dataPoints.get(index).attributeValues[dataPoints.get(index).attributeValues.length - 1]
					.equalsIgnoreCase("0"))
				O1Count[0]++;
			else if (dataPoints.get(index).attributeValues[dataPoints.get(index).attributeValues.length - 1]
					.equalsIgnoreCase("1"))
				O1Count[1]++;
		}
		if (O1Count[1] != 0 && O1Count[0] != 0) {
			int totalCount = O1Count[1] + O1Count[0];
			VI = (O1Count[0] * O1Count[1]) / Math.pow(totalCount, 2);
			return VI;
		}

		return VI;
	}

	public double calculateChildFracVI(List<DataPoint> datapoints, String outputClass, int noAttr, int column) {
		double fracVI = 0;
		int colCount = 0, zeroCount = 0, oneCount = 0;
		//System.out.println("Size of data is" + datapoints.size());
		for (int i = 0; i < datapoints.size(); i++) {
			String outputValue = datapoints.get(i).attributeValues[noAttr - 1];
			if (datapoints.get(i).attributeValues[column].equalsIgnoreCase(outputClass)) {
				colCount++;
				if (outputValue.equalsIgnoreCase("0"))
					zeroCount++;
				else if (outputValue.equalsIgnoreCase("1"))
					oneCount++;
			}

		}
		// this denotes the fraction of zeros and ones
		if (colCount > 0) {
			fracVI = ((colCount) / (double) datapoints.size()) * ((zeroCount * oneCount) / Math.pow(colCount, 2));
		}

		return fracVI;
	}

	public void createVIDTUtil(DTNode node, List<DataPoint> datapoints, List<String> availAttributes, int level) {
		double VI = 0;
		int[] O1Count = { 0, 0 };
		VI = calculateRootVI(datapoints, O1Count);
		//System.out.println("root VI" + VI);
		node.setVI(VI);
		node.setOneCount(O1Count[1]);
		node.setZeroCount(O1Count[0]);

		// base conditions
		if (VI == 0) {
			node.setData("" + (O1Count[0] == 0 ? "1" : "0"));
			return;
		}

		if (availAttributes.size() == 1) {
			node.setData("" + (O1Count[0] > O1Count[1] ? "0" : "1"));
			return;
		}

		double maxVIGain = 0.0;
		int number_of_attributes = datapoints.get(0).attributeValues.length;
		int attributeChoosen = -1;
		double pVI = 0, nVI = 0;

		//System.out.println("Number of Attributes" + number_of_attributes);

		for (int column = 0; column < (number_of_attributes - 1); column++) {
			// if available attributes is not having that attribute
			if (!availAttributes.contains(this.attributeList.get(column)))
				continue;

			nVI = calculateChildFracVI(datapoints, "0", number_of_attributes, column);
			pVI = calculateChildFracVI(datapoints, "1", number_of_attributes, column);
			double gain = VI - nVI - pVI;
			//System.out.println("Max gain is" + maxVIGain);
			//System.out.println("Gain at column: " + column + " is: " + gain);

			if (gain > maxVIGain) {
				//System.out.println("In max gain for column: " + column + " is: " + gain);
				//System.out.println("Column Attribute Choosen" + this.attributeList.get(column));
				maxVIGain = gain;
				attributeChoosen = column;
			}

		} // attribute is chosen

		if(attributeChoosen ==-1) {
			node.setData("" + (O1Count[0] > O1Count[1] ? "0" : "1"));
			return ;
		}
		/*
		 * once the attribute is chosen with maximum information gain we have to
		 * partition our training dataset based on the domain of attributes
		 * chosen (in this case its 0 and 1)
		 */
		List<DataPoint> zeroDataPoints = new ArrayList<DataPoint>();
		List<DataPoint> oneDataPoints = new ArrayList<DataPoint>();
		List<String> availAttr_Zero = new ArrayList<String>(availAttributes);
		List<String> availAttr_one = new ArrayList<String>(availAttributes);
		for (int i = 0; i < datapoints.size(); i++) {
			if (datapoints.get(i).attributeValues[attributeChoosen].equalsIgnoreCase("0"))
				zeroDataPoints.add(datapoints.get(i));
			else if (datapoints.get(i).attributeValues[attributeChoosen].equalsIgnoreCase("1"))
				oneDataPoints.add(datapoints.get(i));
		}

		node.setData(this.attributeList.get(attributeChoosen));
		level++;
		DTNode zeroNode = new DTNode();
		zeroNode.setParent(node);
		zeroNode.setLevel(level);
		node.setLeftNode(zeroNode);

		if (zeroDataPoints.size() == 1) {
			zeroNode.setData("" + zeroDataPoints.get(0).attributeValues[number_of_attributes - 1]);
		} else {
			availAttr_Zero.remove(availAttr_Zero.indexOf(node.getData()));
			createVIDTUtil(zeroNode, zeroDataPoints, availAttr_Zero, level);
		}

		DTNode oneNode = new DTNode();
		oneNode.setParent(node);
		oneNode.setLevel(level);
		node.setRightNode(oneNode);

		if (oneDataPoints.size() == 1) {
			oneNode.setData("" + oneDataPoints.get(0).attributeValues[number_of_attributes - 1]);
		} else {
			availAttr_one.remove(availAttr_one.indexOf(node.getData()));
			createVIDTUtil(oneNode, oneDataPoints, availAttr_one, level);
		}

		level--;

	}

	public double calculateRootEntropy(List<DataPoint> datapoints, int[] O1Count) {
		double rootEntropy = 0;
		for (int index = 0; index < datapoints.size(); index++) {
			if (datapoints.get(index).attributeValues[datapoints.get(index).attributeValues.length - 1].equalsIgnoreCase("0"))
				O1Count[0]++;
			else if (datapoints.get(index).attributeValues[datapoints.get(index).attributeValues.length - 1].equalsIgnoreCase("1"))
				O1Count[1]++;
		}
		//System.out.println("pos Count" + O1Count[1] + "neg Count" + O1Count[0]);
		if (O1Count[1] != 0 && O1Count[0] != 0) {
			rootEntropy = calEntropy(O1Count[1], O1Count[0]);
			return rootEntropy;
		}

		return rootEntropy;
	}

	public double calculateChildEntropy(List<DataPoint> datapoints, String outputClass, int noAttr, int column) {
		double entropy = 0;
		int colCount = 0, zeroCount = 0, oneCount = 0;
		//System.out.println("Size of data is" + datapoints.size());
		for (int i = 0; i < datapoints.size(); i++) {
			String outputValue = datapoints.get(i).attributeValues[noAttr - 1];
			if (datapoints.get(i).attributeValues[column].equalsIgnoreCase(outputClass)) {
				colCount++;
				if (outputValue.equalsIgnoreCase("0"))
					zeroCount++;
				else if (outputValue.equalsIgnoreCase("1"))
					oneCount++;
			}

		}
		double pPos = (double) oneCount / (double) (colCount);
		double pNeg = (double) zeroCount / (double) (colCount);
		//System.out.println("pos is" + pPos + "neg is" + pNeg);
		if (pPos > 0 && pNeg > 0) {
			entropy = -(pPos * (Math.log10(pPos) / Math.log10(2))) - (pNeg * (Math.log10(pNeg) / Math.log10(2)));
			return ((double) colCount / (double) (datapoints.size())) * entropy;
		}
		return entropy;
	}

	public void createDTUtil(DTNode node, List<DataPoint> datapoints, List<String> availAttributes, int level) {
		double rootEntropy = 0;
		int[] O1Count = { 0, 0 };
		rootEntropy = calculateRootEntropy(datapoints, O1Count);
		//System.out.println("root entropy" + rootEntropy);
		node.setEntropy(rootEntropy);
		node.setOneCount(O1Count[1]);
		node.setZeroCount(O1Count[0]);

		// base conditions
		if (rootEntropy == 0) {
			node.setData("" + (O1Count[0] == 0 ? "1" : "0"));
			return;
		}

		if (availAttributes.size() == 1) {
			node.setData("" + (O1Count[0] > O1Count[1] ? "0" : "1"));
			return;
		}

		double maxInfGain = 0;
		int number_of_attributes = datapoints.get(0).attributeValues.length;
		int attributeChoosen = -1;
		double pEntropy = 0, nEntropy = 0;
		//System.out.println("Number of Attributes" + number_of_attributes);
		for (int column = 0; column < (number_of_attributes - 1); column++) {
			// if available attributes is not having that attribute
			if (!availAttributes.contains(this.attributeList.get(column)))
				continue;
			pEntropy = 0;
			nEntropy = 0;
			nEntropy = calculateChildEntropy(datapoints, "0", number_of_attributes, column);

			pEntropy = calculateChildEntropy(datapoints, "1", number_of_attributes, column);
			double gain = rootEntropy - (nEntropy + pEntropy);
			if (gain > maxInfGain) {
				//System.out.println("In max gain for column: " + column + " is: " + gain);
				maxInfGain = gain;
				attributeChoosen = column;
			}

		} // attribute is chosen
		
		if(attributeChoosen ==-1) {
			node.setData("" + (O1Count[0] > O1Count[1] ? "0" : "1"));
			return ;
		}
		//System.out.println("Attribute choosen" + attributeChoosen);

		/*
		 * once the attribute is chosen with maximum information gain we have to
		 * partition our training dataset based on the domain of attributes
		 * chosen (in this case its 0 and 1)
		 */
		List<DataPoint> zeroDataPoints = new ArrayList<DataPoint>();
		List<DataPoint> oneDataPoints = new ArrayList<DataPoint>();
		List<String> availAttr_Zero = new ArrayList<String>(availAttributes);
		List<String> availAttr_one = new ArrayList<String>(availAttributes);
		for (int i = 0; i < datapoints.size(); i++) {
			if (datapoints.get(i).attributeValues[attributeChoosen].equalsIgnoreCase("0"))
				zeroDataPoints.add(datapoints.get(i));
			else if (datapoints.get(i).attributeValues[attributeChoosen].equalsIgnoreCase("1"))
				oneDataPoints.add(datapoints.get(i));
		}

		node.setData(this.attributeList.get(attributeChoosen));
		level++;
		DTNode zeroNode = new DTNode();
		zeroNode.setEntropy(nEntropy);
		zeroNode.setParent(node);
		zeroNode.setLevel(level);
		node.setLeftNode(zeroNode);

		if (zeroDataPoints.size() == 1) {
			zeroNode.setData("" + zeroDataPoints.get(0).attributeValues[number_of_attributes - 1]);
		} else {
			availAttr_Zero.remove(availAttr_Zero.indexOf(node.getData()));
			createDTUtil(zeroNode, zeroDataPoints, availAttr_Zero, level);
		}

		DTNode oneNode = new DTNode();
		oneNode.setEntropy(pEntropy);
		oneNode.setParent(node);
		oneNode.setLevel(level);
		node.setRightNode(oneNode);

		if (oneDataPoints.size() == 1) {
			oneNode.setData("" + oneDataPoints.get(0).attributeValues[number_of_attributes - 1]);
		} else {
			availAttr_one.remove(availAttr_one.indexOf(node.getData()));
			createDTUtil(oneNode, oneDataPoints, availAttr_one, level);
		}

		level--;

	}

	public void getCount(List<DataPoint> dataPoints, String outputClass, int outputColumn, int curCol, int[] O1count) {
		for (int i = 0; i < dataPoints.size(); i++) {
			String outputValue = dataPoints.get(i).attributeValues[outputColumn];
			if (dataPoints.get(i).attributeValues[curCol].equalsIgnoreCase(outputClass)) {
				O1count[2]++;
				if (outputValue.equalsIgnoreCase("0"))
					O1count[0]++;
				else if (outputValue.equalsIgnoreCase("1"))
					O1count[1]++;
			}

		}

	}

	/*
	 * This function calculates Entropy of a given Node Entropy(S) = -p(+) log
	 * p(+) - p(-) log (p-)
	 */
	public double calEntropy(int posCount, int negCount) {

		int TotalCount = posCount + negCount;
		return -((double) posCount / (double) (TotalCount))
				* (Math.log10((double) posCount / (double) (TotalCount)) / Math.log10(2))
				- ((double) negCount / (double) (TotalCount))
				* (Math.log10((double) negCount / (double) (TotalCount)) / Math.log10(2));

	}

	public void printTree(DTNode root) {
		printTreeUtil(root);
	}

	/*
	 * Function to print the tree in the format specified in the assignment
	 */
	public void printTreeUtil(DTNode node) {

		String indentation = "";

		for (int i = 0; i < node.getLevel(); i++) {
			indentation = indentation + "|";
		}

		if (node.getLeftNode().getData().equalsIgnoreCase("0") || node.getLeftNode().getData().equalsIgnoreCase("1")) {
			System.out.println(indentation + node.getData() + " = 0 : " + node.getLeftNode().getData());
		} else {
			System.out.println(indentation + node.getData() + " = 0 :");
			printTreeUtil(node.getLeftNode());
		}

		if (node.getRightNode().getData().equalsIgnoreCase("0") || node.getRightNode().getData().equalsIgnoreCase("1")) {
			System.out.println(indentation + node.getData() + " = 1 : " + node.getRightNode().getData());
		} else {
			System.out.println(indentation + node.getData() + " = 1 :");
			printTreeUtil(node.getRightNode());
		}

	}

	public double evaluateTree(List<DataPoint> testData) {
		return evaluateTreeUtil(root, testData);
	}

	/*
	 * Evaluate Tree : this function evaluates the accuracy of tree
	 */

	public double evaluateTreeUtil(DTNode root, List<DataPoint> testData) {
		int testPass = 0, testFail = 0;
		for (int i = 0; i < testData.size(); i++) {
			String[] copyAttr = testData.get(i).attributeValues;
			String output = evaulateSample(root, copyAttr);
			if ((output.equals("0") && copyAttr[copyAttr.length - 1].equals("0"))
					|| (output.equals("1") && copyAttr[copyAttr.length - 1].equals("1"))) {
				testPass++;
			} else if ((output.equals("0") && copyAttr[copyAttr.length - 1].equals("1"))
					|| (output.equals("1") && copyAttr[copyAttr.length - 1].equals("0"))) {
				String str = "";
				for (int j = 0; j < copyAttr.length; j++) {
					str = str + copyAttr[j] + ",";
				}
				testFail++;
			}
		}

		return ((double) testPass / (testPass + testFail));
	}

	public String evaulateSample(DTNode root, String[] instance) {
		return evaluateSampleDataUtil(root, instance);
	}

	/*
	 * 
	 * 
	 * 
	 * 
	 */

	public String evaluateSampleDataUtil(DTNode node, String[] instance) {

		int column = -1;
		String retVal = "";

		// leaf has value 0 or 1
		if (node.getData().equals("0") || node.getData().equals("1")) {
			return node.getData();
		}

		for (int i = 0; i < instance.length; i++) {
			// match with the root which attributes to be picked and what is the
			// value at that attribute
			if (this.attributeList.get(i).equalsIgnoreCase(node.getData())) {
				column = i;
				break;
			}
		}
		// if its zero in the instance then go to left else right
		if (instance[column].equalsIgnoreCase("0")) {
			retVal = evaluateSampleDataUtil(node.getLeftNode(), instance);
		} else {
			retVal = evaluateSampleDataUtil(node.getRightNode(), instance);
		}

		return retVal;
	}

	/*
	 * This function is used to clone the tree Its a deep copy of a tree
	 */
	private DTNode cloneDTree(DTNode node) {
		DTNode newNode = new DTNode(node.getData(), node.getLevel(), node.getEntropy(), node.getInfGain(),
				node.getOneCount(), node.getZeroCount());

		if (node.getLeftNode() != null) {
			newNode.setLeftNode(cloneDTree(node.getLeftNode()));
		}

		if (node.getRightNode() != null) {
			newNode.setRightNode(cloneDTree(node.getRightNode()));
		}

		return newNode;
	}

	/*
	 * This function is used to prune the decision tree based on the logic
	 * provided (L and K will be provided by user)
	 */
	public DTNode postPrune(int L, int K, List<DataPoint> validationSet) {

		DTNode dBest = cloneDTree(root);
		double bestAccuracy = evaluateTreeUtil(dBest, validationSet);
		System.out.println("Best Accuary with Validation Set without Pruning: " + bestAccuracy);

		for (int i = 0; i < L; i++) {
			DTNode dTemp = cloneDTree(root);

			int M = (int) (Math.floor(Math.random() * K) + 1);

			for (int j = 0; j < M; j++) {
				int N = getNonLeafNodeCount(dTemp);
				//System.out.println("Non Leaf Count" + N);
				int P = (int) (Math.floor(Math.random() * N) + 1);
				//System.out.println("Node chosen to be pruned: " + P);

				pruneNode(dTemp, P);

			}

			double accuracy = evaluateTreeUtil(dTemp, validationSet);
			//System.out.println("Local Accuary in pass: " + (i + 1) + " is: " + accuracy);
			if (accuracy > bestAccuracy) {
				//System.out.println("Resetting best accuracy with accuracy");
				dBest = dTemp;
				bestAccuracy = accuracy;
			}
		}

		return dBest;
	}

	public int getNonLeafNodeCount(DTNode node) {
		return (node.getLeftNode() != null ? getNonLeafNodeCount(node.getLeftNode()) : 0)
				+ (node.getRightNode() != null ? getNonLeafNodeCount(node.getRightNode()) : 0)
				+ (node.getData().equals("0") || node.getData().equals("1") ? 0 : 1);
	}

	private int pruneNode(DTNode node, int P) {

		if (P == 1) {
			P--;
			node.setData(node.getZeroCount() > node.getOneCount() ? "0" : "1");
			node.setLeftNode(null);
			node.setRightNode(null);
		} else {
			P--;
			if (P >= 1 && node.getLeftNode() != null
					&& !(node.getLeftNode().getData().equals("0") || node.getLeftNode().getData().equals("1"))) {
				P = pruneNode(node.getLeftNode(), P);
			}

			if (P >= 1 && node.getRightNode() != null
					&& !(node.getRightNode().getData().equals("0") || node.getRightNode().getData().equals("1"))) {
				P = pruneNode(node.getRightNode(), P);
			}

		}

		return P;

	}

	public void createDTrandom(DTNode node, List<DataPoint> datapoints, List<String> availAttributes, int level) {
		double rootEntropy = 0;
		int[] O1Count = { 0, 0 };
		rootEntropy = calculateRootEntropy(datapoints, O1Count);
		node.setEntropy(rootEntropy);
		node.setOneCount(O1Count[1]);
		node.setZeroCount(O1Count[0]);

		// base conditions
		if (rootEntropy == 0) {
			node.setData("" + (O1Count[0] == 0 ? "1" : "0"));
			return;
		}

		if (availAttributes.size() == 1) {
			node.setData("" + (O1Count[0] > O1Count[1] ? "0" : "1"));
			return;
		}

		double maxInfGain = 0;
		int number_of_attributes = datapoints.get(0).attributeValues.length;
		int attributeChoosen = -1;
		double pEntropy = 0, nEntropy = 0;
		//System.out.println("Number of Attributes" + number_of_attributes);
		for (int column = 0; column < (number_of_attributes - 1); column++) {
			// if available attributes is not having that attribute
			if (!availAttributes.contains(this.attributeList.get(column)))
				continue;
			pEntropy = 0;
			nEntropy = 0;
			nEntropy = calculateChildEntropy(datapoints, "0", number_of_attributes, column);

			pEntropy = calculateChildEntropy(datapoints, "1", number_of_attributes, column);
			double gain = rootEntropy - (nEntropy + pEntropy);
			if (gain > maxInfGain) {
				//System.out.println("In max gain for column: " + column + " is: " + gain);
				maxInfGain = gain;
				attributeChoosen = column;
			}

		} // attribute is chosen
		
		if(attributeChoosen ==-1) {
			node.setData("" + (O1Count[0] > O1Count[1] ? "0" : "1"));
			return ;
		}
		//System.out.println("Attribute choosen" + attributeChoosen);

		/*
		 * once the attribute is chosen with maximum information gain we have to
		 * partition our training dataset based on the domain of attributes
		 * chosen (in this case its 0 and 1)
		 */
		List<DataPoint> zeroDataPoints = new ArrayList<DataPoint>();
		List<DataPoint> oneDataPoints = new ArrayList<DataPoint>();
		List<String> availAttr_Zero = new ArrayList<String>(availAttributes);
		List<String> availAttr_one = new ArrayList<String>(availAttributes);
		for (int i = 0; i < datapoints.size(); i++) {
			if (datapoints.get(i).attributeValues[attributeChoosen].equalsIgnoreCase("0"))
				zeroDataPoints.add(datapoints.get(i));
			else if (datapoints.get(i).attributeValues[attributeChoosen].equalsIgnoreCase("1"))
				oneDataPoints.add(datapoints.get(i));
		}

		node.setData(this.attributeList.get(attributeChoosen));
		level++;
		DTNode zeroNode = new DTNode();
		zeroNode.setEntropy(nEntropy);
		zeroNode.setParent(node);
		zeroNode.setLevel(level);
		node.setLeftNode(zeroNode);

		if (zeroDataPoints.size() == 1) {
			zeroNode.setData("" + zeroDataPoints.get(0).attributeValues[number_of_attributes - 1]);
		} else {
			availAttr_Zero.remove(availAttr_Zero.indexOf(node.getData()));
			createDTUtil(zeroNode, zeroDataPoints, availAttr_Zero, level);
		}

		DTNode oneNode = new DTNode();
		oneNode.setEntropy(pEntropy);
		oneNode.setParent(node);
		oneNode.setLevel(level);
		node.setRightNode(oneNode);

		if (oneDataPoints.size() == 1) {
			oneNode.setData("" + oneDataPoints.get(0).attributeValues[number_of_attributes - 1]);
		} else {
			availAttr_one.remove(availAttr_one.indexOf(node.getData()));
			createDTUtil(oneNode, oneDataPoints, availAttr_one, level);
		}

		level--;

	}

}
