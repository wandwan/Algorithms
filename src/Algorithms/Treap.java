package Algorithms;

import java.lang.reflect.Array;
import java.util.*;


public class Treap<T> {

	class Node {
		//children[0] = left, children[1] = right
		Node[] children;
		Node parent;
		int priority, size, childId;
		T value;
		/**
		 * Creates a new node with this value.
		 * It has a randomized priority, size of 1,
		 * and no children or parent
		 *
		 * @param value The value to initalize this node with (can be null)
		 */
		public Node(T value) {
			this.value = value;
			priority = new Random().nextInt();
			size = 1;
			children = (Node[]) Array.newInstance(Node.class, 2);
			parent = null;
			childId = -1;
		}
		/**
		 * @param val The value to be compared with this node
		 * @return The index of the child which this value should lie in
		 * or -1 if val = this.value
		 */
		public int compare(T val) {
			int cval = cmp.compare(val, value);
			if(cval==0) {
				return -1;
			}
			return cval<0 ? 0 : 1;
		}
		/**
		 * "Sets" this node to n
		 * The parent of this node will have the child n
		 * and n's parent will point to this node's parent
		 * Note that if this node is the current root,
		 * n will be set as the new root
		 *
		 * @param n The node to set to (can't be null)
		 */
		public void set(Node n) {
			n.setParent(parent, childId);
			if(this==root) {
				root = n;
			}
		}
		/**
		 * sets the parent of this Node to n
		 * and this node to be the child of n.children[id]
		 * This node's parent will point to n and n's child
		 * will contain this node
		 *
		 * @param n       The node to set as the parent (can be null)
		 * @param childId The ID of the child for n
		 */
		public void setParent(Node n, int childId) {
			parent = n;
			this.childId = childId;
			if(n!=null) {
				n.children[childId] = this;
			}
		}
		/**
		 * Maintains the value of this.size by using the
		 * sizes of its children
		 */
		public void maintain() {
			size = 1+getSize(children[0])+getSize(children[1]);
		}
		@Override
		public String toString() {
			return value.toString();
		}
	}

	public Treap() {
		this(null, false);
	}

	public Treap(boolean dup) {
		this(null, dup);
	}

	public Treap(Comparator<? super T> comparator) {
		this(comparator, false);
	}

	public Treap(Comparator<? super T> comparator, boolean dup) {
		if(comparator==null) {
			comparator = (o1, o2) -> ((Comparable<T>) o1).compareTo(o2);
		}
		this.cmp = comparator;
		duplicates = dup;
	}

	Comparator<? super T> cmp;

	private int getSize(Node n) {
		if(n==null) {
			return 0;
		}
		return n.size;
	}

	Node root;
	final boolean duplicates;

	/**
	 * rotates the node at o
	 * if d is 0 then a left rotation is made
	 * a right rotation is made otherwise
	 *
	 * @param o The node to rotate
	 * @param d The direction to rotate it
	 */
	private void rotate(Node o, int d) {
		Node k = o.children[d^1];
		if(k.children[d]!=null) {
			k.children[d].setParent(o, d^1);
		}else {
			o.children[d^1] = null;
		}
		k.children[d] = o;
		if(o==root) {
			root = k;
		}
		o.set(k);
		o.setParent(k, d);
		o.maintain();
		k.maintain();
	}

	@Override
	public String toString() {
		return "{"+toString(root)+"}";
	}

	private String toString(Node n) {
		if(n!=null) {
			return toString(n.children[0])+n.value.toString()+toString(n.children[1]);
		}
		return "";
	}

	/**
	 * inserts this value into the treap
	 *
	 * @param val The value to be inserted
	 */
	public void add(T val) {
		if(root==null) {
			root = new Node(val);
		}else {
			add(root, val);
		}
	}

	private void add(Node cur, T val) {
		int cmp = cur.compare(val);
		if(cmp==-1) {
			if(duplicates) {
				cmp = 1;
			}else {
				return;
			}
		}
		if(cur.children[cmp]==null) {
			new Node(val).setParent(cur, cmp);
		}else {
			add(cur.children[cmp], val);
		}
		if(cur.children[cmp].priority>cur.priority) {
			rotate(cur, cmp^1);
		}else {
			cur.maintain();
		}
	}

	/**
	 * @param val The value to find in the treap
	 * @return true if the value is in the treap, false otherwise
	 */
	public boolean contains(T val) {
		return find(root, val)!=null;
	}

	private Node find(Node cur, T val) {
		if(cur==null) {
			return null;
		}
		int cval = cur.compare(val);
		if(cval==-1) {
			return cur;
		}
		return find(cur.children[cval], val);
	}

	/**
	 * @param val The value to remove from this treap
	 * @return true if and only if the value was successfully removed
	 * it returns false otherwise
	 */
	public boolean remove(T val) {
		return remove(root, val);
	}

	private boolean remove(Node cur, T val) {
		if(cur==null) {
			return false;
		}
		int cmp = cur.compare(val);
		boolean ret;
		if(cmp==-1) {
			ret = true;
			if(cur.children[0]!=null&&cur.children[1]!=null) {
				int child = cur.children[0].priority>cur.children[1].priority ? 1 : 0;
				rotate(cur, child);
				remove(cur, val);
			}else {
				if(cur.children[0]!=null) {
					cur.set(cur.children[0]);
				}else if(cur.children[1]!=null) {
					cur.set(cur.children[1]);
				}else {
					cur.value = null;
					if(root==cur) {
						root = null;
					}
				}
			}
		}else {
			ret = remove(cur.children[cmp], val);
			if(cur.children[cmp]!=null&&cur.children[cmp].value==null) {
				cur.children[cmp] = null;
			}
		}
		cur.maintain();
		return ret;
	}

	/**
	 * @param k The index of the value to find
	 * @return The ith element (0-indexed)
	 */
	public T kth(int k) {
		return kth(root, k);
	}

	private T kth(Node cur, int k) {
		if(cur==null||k<0||k>=cur.size) {
			return null;
		}
		int size = getSize(cur.children[0]);
		if(k==size) {
			return cur.value;
		}else if(k<size) {
			return kth(cur.children[0], k);
		}else {
			return kth(cur.children[1], k-size-1);
		}
	}

	/**
	 * @param val The value to search for
	 * @return The number of values strictly less than val,
	 * or the 0-indexed value if this were to be represented
	 * as a sorted array
	 */
	public int indexOf(T val) {
		return indexOf(root, val);
	}

	private int indexOf(Node cur, T val) {
		if(cur==null) {
			return 0;
		}
		int cmp = cur.compare(val);
		if(cmp==-1) {
			return getSize(cur.children[0]);
		}
		return (cmp==1 ? getSize(cur.children[0])+1 : 0)+indexOf(cur.children[cmp], val);
	}

}
