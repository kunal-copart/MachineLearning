


/* Author : kunal krishna
 * created_date: 26th Jan 2016 
 * Description:  Node class for Decision Tree 
 * */
public class DTNode implements Cloneable {
	private DTNode parent;
	private int oneCount;
	private int zeroCount;
	private double entropy;
	private double infGain;
	private String data;
	private int level; // this is used to provide indentation while printing the tree
	private DTNode leftNode;
	private DTNode rightNode;
	private double VI;
	private double VIgain;
	
	public DTNode(){
		
	}
	
	public DTNode(String data,int level,double entropy, double infGain, int posCount, int negCount){
		this.data = data;
		this.level = level;
		this.entropy = entropy;
		this.infGain = infGain;
		this.oneCount = posCount;
		this.zeroCount = negCount;
	}
	
	
	
	
	
	
	public double getVI() {
		return VI;
	}

	public void setVI(double vI) {
		VI = vI;
	}

	public double getVIgain() {
		return VIgain;
	}

	public void setVIgain(double vIgain) {
		VIgain = vIgain;
	}

	
	public DTNode getLeftNode() {
		return leftNode;
	}
	public void setLeftNode(DTNode leftNode) {
		this.leftNode = leftNode;
	}
	public DTNode getRightNode() {
		return rightNode;
	}
	public void setRightNode(DTNode rightNode) {
		this.rightNode = rightNode;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public DTNode getParent() {
		return parent;
	}
	public void setParent(DTNode parent) {
		this.parent = parent;
	}
	
	public int getOneCount() {
		return oneCount;
	}
	public void setOneCount(int oneCount) {
		this.oneCount = oneCount;
	}
	public int getZeroCount() {
		return zeroCount;
	}
	public void setZeroCount(int zeroCount) {
		this.zeroCount = zeroCount;
	}
	public double getEntropy() {
		return entropy;
	}
	public void setEntropy(double entropy) {
		this.entropy = entropy;
	}
	public double getInfGain() {
		return infGain;
	}
	public void setInfGain(double infGain) {
		this.infGain = infGain;
	}
	
	
}
