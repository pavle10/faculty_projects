package les;

import java.util.LinkedList;
import java.util.Queue;

public class RangeTree {
	
	private Node root;
	private Point[] points;
	private Point[] temp;
	private double maxX, minX, maxY, minY;
	
	public RangeTree(Point[] points) {
		this.points = points;
		
		int len = points.length;
		
		temp = new Point[len];
		sortPoints(0, len-1);
		
	
		root = build(this.points);
		ArrNode[] arr = root.getAssociatedArray();
		
		minX = this.points[0].getX();
		maxX = this.points[len-1].getX();
		minY = arr[0].getValue();
		maxY = arr[len-1].getValue();
		
	}
	
	private Node build(Point[] p) {
		if (p.length == 1) {
			ArrNode tempNode = new ArrNode(p[0].getY(), null, null);
			ArrNode[] tempArr = new ArrNode[1];
			tempArr[0] = tempNode;
			Node leaf = new Node(-1, p[0], null, null, tempArr);
			return leaf;
		}
		else {
			int median = p.length/2;
			Point[] pLeft;
			Point[] pRight;
			if (p.length%2 == 0) {
				pLeft = new Point[median];
				pRight = new Point[median];
			}
			else {
				pLeft = new Point[median+1];
				pRight = new Point[median];
			}
			
			
			int k = 0, j = 0;
			if (p.length%2 == 0) {
				for(int i = 0; i < p.length; i++) {
					if (i < median) pLeft[j++] = p[i];
					else pRight[k++] = p[i];
				}
			}
			else {
				for(int i = 0; i < p.length; i++) {
					if (i <= median) pLeft[j++] = p[i];
					else pRight[k++] = p[i];
				}
			}
			
			Node vLeft = build(pLeft);
			Node vRight = build(pRight);
			double xMid;
			if (p.length%2==0) xMid  = p[median-1].getX();
			else xMid = p[median].getX();
			ArrNode[] arrNode = makeAssociatedArray(vLeft.getAssociatedArray(), vRight.getAssociatedArray());
			Node v = new Node(xMid, null, vLeft, vRight, arrNode);
			return v;
		}
	}
	
	public boolean query(double x1, double x2, double y1, double y2) {
		Node splitNode = findSplitNode(x1, x2);
		ArrNode aNode = findEntry(splitNode.getAssociatedArray(), y1);
		boolean stop = false;

		if (splitNode.isLeaf()) {
			if (checkPoint(splitNode.getPoint(), x1, x2, y1, y2))
				return true;
			return false;
		}
		
		Node v = splitNode.getLeftChild();
		ArrNode a;
		if (aNode.getLeftChild() == null) {
			a = aNode;
			stop = true;
		}
		else a = aNode.getLeftChild();
		
		while(!v.isLeaf()) {
			if (x1 <= v.getValue()) {
				if (!stop && toReport(a.getRightChild(), y1, y2)) return true;
				v = v.getLeftChild();
				if (!stop && a.getLeftChild() != null) a = a.getLeftChild();
				else stop = true;
				
			}
			else {
				v = v.getRightChild();
				if (!stop && a.getRightChild() != null) a = a.getRightChild();
				else stop = true;
				
			}
		}
		if (checkPoint(v.getPoint(), x1, x2, y1, y2))
			return true;
		
		v = splitNode.getRightChild();
		if (aNode.getRightChild() != null){
			a = aNode.getRightChild();
			stop = false;
		}
		else {
			a = aNode;
			stop = true;
		}
		
		while(!v.isLeaf()) {
			if (x2 > v.getValue()) {
				if (!stop && toReport(a.getLeftChild(), y1, y2)) return true;
				v = v.getRightChild();
				if (!stop && a.getRightChild() != null) a = a.getRightChild();
				else stop = true;
			}
			else {
				v = v.getLeftChild();
				if (!stop && a.getLeftChild() != null) a = a.getLeftChild();
				else stop = true;
			}
		}
		if (checkPoint(v.getPoint(), x1, x2, y1, y2))
			return true;
		
		return false;
	}
	
	private ArrNode[] makeAssociatedArray(ArrNode[] a, ArrNode[] b) {
		int len = a.length + b.length;
		ArrNode[] tempArr = new ArrNode[len];
		int i = 0, j = 0, k = 0;
		
		while (j < a.length && k < b.length) {
			if (a[j].getValue() < b[k].getValue()) {
				ArrNode tempNode = new ArrNode(a[j].getValue(), a[j], b[k]);
				tempArr[i] = tempNode;
				j++;
			}
			else {
				ArrNode tempNode = new ArrNode(b[k].getValue(), a[j], b[k]);
				tempArr[i] = tempNode;
				k++;
			}
			i++;
		}
		if (j == a.length) {
			while (k < b.length) {
				ArrNode tempNode = new ArrNode(b[k].getValue(), null, b[k]);
				tempArr[i++] = tempNode;
				k++;
			}
		}
		else if (k == b.length) {
			while(j < a.length) {
				ArrNode tempNode = new ArrNode(a[j].getValue(), a[j], null);
				tempArr[i++] = tempNode;
				j++;
			}
			
		}
		
		return tempArr;
	}
	
	private Node findSplitNode(double x1, double x2) {
		Node v = root;
		while (!v.isLeaf() && (x2 <= v.getValue() || x1 > v.getValue())) {
			if (x2 <= v.getValue())
				v = v.getLeftChild();
			else
				v = v.getRightChild();
		}
		return v;
	}
	
	private boolean checkPoint(Point p, double x1, double x2, double y1, double y2) {
		if (p.getX() > x1 && p.getX() < x2 && p.getY() > y1 && p.getY() < y2)
			return true;
		return false;
	}
	
	private ArrNode findEntry(ArrNode[] arr, double y) {
		int len = arr.length;
		int lo = 0, curr = 0;
        int hi = len - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if      (y < arr[mid].getValue()) {
            	hi = mid - 1;
            	curr = mid;
            }
            else if (y > arr[mid].getValue()) lo = mid + 1;
            else return arr[mid];
        }
        
        return arr[curr];
	}
	
	private boolean toReport(ArrNode a, double y1, double y2) {
		if (a == null)
			return false;
		if (a.getValue() == y1)
			return false;
		if (a.getValue() < y2)
			return true;
		return false;
	}
	
	private void sortPoints(int low, int high) {
		if (low < high) {
			int middle = low + (high-low)/2;
			sortPoints(low, middle);
			sortPoints(middle+1, high);
			merge(low, middle, high);
			
		}
		
	}
	
	private void merge(int low, int middle, int high) {
		
		for(int i = low; i <= high; i++)
			temp[i] = points[i];
		
		int i = low;
		int j = middle+1;
		int k = low;

		while(i <= middle && j <= high) {
			if (temp[i].compareX(temp[j]) == -1) {
				points[k] = temp[i];
				i++;
			}
			else {
				points[k] = temp[j];
				j++;
			}
			k++;
		}
		
		while(i <= middle) {
			points[k] = temp[i];
			k++;
			i++;
		}
	}
	
	private void printTree(Node root) {
		Queue<Node> queue = new LinkedList<Node>();
		queue.add(root);
		
		while (!queue.isEmpty()) {
			Node tempNode = queue.poll();
			if (tempNode.getValue() != -1) System.out.println(tempNode.getValue() + " ");
			else System.out.println("Leaf: " + tempNode.getPoint());
			
			if (tempNode.getLeftChild() != null) {
                queue.add(tempNode.getLeftChild());
            }
			
            if (tempNode.getRightChild() != null) {
                queue.add(tempNode.getRightChild());
            }
        }
	}

	public double getMaxX() {
		return maxX;
	}

	public double getMinX() {
		return minX;
	}

	public double getMaxY() {
		return maxY;
	}

	public double getMinY() {
		return minY;
	}

	public Node getRoot() {
		return root;
	}

	public Point[] getPoints() {
		return points;
	}
	

}
