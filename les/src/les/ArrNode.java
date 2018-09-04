package les;

public class ArrNode {
	
	private double value;
	private ArrNode leftChild;
	private ArrNode rightChild;
	
	public ArrNode(double value, ArrNode left, ArrNode right) {
		this.value = value;
		leftChild = left;
		rightChild = right;
		
	}

	public double getValue() {
		return value;
	}

	public ArrNode getLeftChild() {
		return leftChild;
	}

	public ArrNode getRightChild() {
		return rightChild;
	}
	
	

}
