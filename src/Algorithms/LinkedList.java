package Algorithms;

import java.util.Random;
public class LinkedList<T> {
	private Node root, curr, last;
	private int length;
	private class Node {
		Node next, prev;
		T val;
		public Node(T v) {
			val = v;
		}
		public Node(Node p, T v) {
			this(v);
			p.next = this;
			prev = p;
		}
		public String toString() {
			return val + "";
		}
	}
	public LinkedList() {
		length = 0;
	}
	public void addLast(T val) {
		if(root == null) {
			root = new Node(val);
			last = root;
			curr = root;
		}
		else
			last = new Node(last, val);
		length++;
	}
	public void addFirst(T val) {
		if(root == null) {
			root = new Node(val);
			last = root;
			curr = root;
		}
		else {
			Node temp = new Node(val);
			root.prev = temp;
			temp.next = root;
			root = temp;
			curr = root;
		}
		length++;
	}
	public void addAfter(T val) throws IllegalArgumentException {
		if(curr == null)
			throw new IllegalArgumentException("null curr");
		if(curr == last) {
			addLast(val);
			length++;
			return;
		}
		Node t = new Node(val);
		t.next = curr.next;
		t.next.prev = t;
		curr.next = t;
		t.prev = t;
		length++;
	}
	public void addBefore(T val) throws IllegalArgumentException {
		if(curr == null)
			throw new IllegalArgumentException("null curr");
		if(curr == root) {
			addFirst(val);
			curr = curr.next;
			length++;
			return;
		}
		System.out.println(curr.prev);
		System.out.println(curr +" " + root);
		Node t = new Node(val);
		t.prev = curr.prev;
		t.prev.next = t;
		t.next = curr;
		curr.prev = t;
		length++;
	}
	public T next() {
		if(curr == null)
			return null;
		curr = curr.next;
		if(curr != null)
			return curr.val;
		return null;
	}
	public T prev() {
//		System.out.println(curr);
		if(curr == null)
			return null;
		curr = curr.prev;
		if(curr != null)
			return curr.val;
		return null;
	}
	public T getVal() {
		if(curr == null)
			return null;
		return curr.val;
	}
	public void reset() {
		curr = root;
	}
	public boolean hasNext() {
		return curr != null;
	}
	public boolean hasPrev() {
		return curr != null;
	}
	public int length() {
		return length;
	}
	public void remove() {
		if(curr == null)
			return;
		length--;
		if(curr.prev != null)
			curr.prev.next = null;
		else {
			if(curr == root)
				curr = curr.next;
			root = root.next;
			root.prev = null;
			return;
		}
		if(curr.next != null)
			curr.next.prev = null;
		else {
			if(curr == last)
				curr = curr.prev;
			last = last.prev;
			last.next = null;
			return;
		}
		curr.prev.next = curr.next;
	}
	public String toString() {
		String s = "[";
		Node temp = root;
		while(temp != null) {
			s += temp + ", ";
			temp = temp.next;
		}
		s += "]";
		return s;
	}
	public static void main(String[] args) {
		LinkedList<Integer> arr = new LinkedList<Integer>();
		Random r = new Random();
		int[] test = new int[r.nextInt(100000)];
		for(int i = 0; i < test.length; i++) {
			test[i] = r.nextInt();
			arr.addFirst(test[i]);
		}
		while(arr.hasNext()) {
			int t1 = r.nextInt();
			arr.addBefore(t1);
			System.out.println(t1);
//			System.out.println(arr.prev());
			arr.remove();
			arr.next();
		}
		int i = test.length - 1;;
		while(arr.hasNext()) {
			if(test[i] != arr.next())
				System.out.println("false");
//			else
//				System.out.println(true);
			i--;
		}
		
	}
}
