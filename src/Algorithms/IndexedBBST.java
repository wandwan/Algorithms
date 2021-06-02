package Algorithms;
//max PQ, root is dummy
//dont add int max value to PQ or it will break (make the root not the oldest ancestor)
public class IndexedBBST {
	Node root;
	class Node {
		int val, subTreeSize;
		Node lc, rc, par;
		public Node(int val) {
			super();
			this.val = val;
			subTreeSize = 1;
		}
	}
	public IndexedBBST() {
		root = new Node(Integer.MAX_VALUE);
		root.par = root;
	}
	public Node add(int val) {
		Node t = new Node(val);
		add(t, root);
		return t;
	}
	//add is log^2n
	private void add(Node t, Node curr) {
		if(curr.lc == null) {
			curr.lc = t;
			t.par = curr;
		} else if(curr.lc.val >= t.val) {
			add(t, curr.lc);
		}
		else if(curr.rc == null) {
			curr.rc = t;
			t.par = curr;
		} else
			add(t, curr.rc);
		curr.subTreeSize++;
		int diff = curr.lc.subTreeSize - (curr.rc != null ? 0 : curr.rc.subTreeSize);
		if(diff > 2) System.out.println("WTF");;
		if(diff == 2) {
			Node next = curr.lc;
			remove(next);
			if(curr.rc != null)
				add(next, curr.rc);
			else next = curr.rc;
		}
	}
	//remove is logn
	public void remove(Node a) {
		Node curr = a.par;
		while(curr.par != curr) {
			curr.subTreeSize--;
			curr = curr.par;
		}
		curr.subTreeSize--;
		remove(a, a.par);
		a.lc = null;
		a.rc = null;
		a.par = null;
		a.subTreeSize = 1;
	}
	//returns node that takes a's place
	private Node remove(Node a, Node par) {
		Node replacement = a.rc;
		if(a.rc != null) {
			replacement.rc = remove(a.rc, a);
			replacement.lc = a.lc;
		} else if(a.lc != null) {
			replacement = a.lc;
		} else {
			if(par.lc == a) par.lc = null;
			else par.rc = null;
			return null;
		}
		replacement.par = par;
		replacement.subTreeSize = a.subTreeSize - 1;
		return replacement;
	}
	public int getIdx(Node a) {
		int sum = 0;
		if(a.par.rc == a)
			sum += a.par.lc.subTreeSize;
		return sum + a.subTreeSize - 1;
	}
	//returns insertion index
	public int getIdx(int val) {
		Node curr = root;
		while(curr != null && curr.val >= val) {
			if(curr.lc != null && val <= curr.lc.val)
				curr = curr.lc;
			else if(curr.rc != null && val <= curr.rc.val)
				curr = curr.rc;
			else break;
		}
		return getIdx(curr);
			
	}
}
