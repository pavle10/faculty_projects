package les;

public class Node {
	
	private Node leftChild;
	private Node rightChild;
	private double value;
	private Point point;
	private ArrNode[] aArray;
	
	public Node(double value, Point point, Node left, Node right, ArrNode[] arr) {
		this.value = value;
		this.point = point;
		leftChild = left;
		rightChild = right;
		aArray = arr;
		
	}

	public ArrNode[] getAssociatedArray() {
		return aArray;
	}

	public Node getLeftChild() {
		return leftChild;
	}

	public Node getRightChild() {
		return rightChild;
	}

	public double getValue() {
		return value;
	}

	public Point getPoint() {
		return point;
	}
	
	public boolean isLeaf() {
		if (rightChild == null && leftChild == null) 
			return true;
		return false;
	}

}
