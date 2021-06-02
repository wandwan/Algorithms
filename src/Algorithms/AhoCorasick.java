package Algorithms;

import java.util.*;

public class AhoCorasick {
	private static final int ALPHABET_SIZE = 26;
	ArrayList<Node> nodes;
	private class Node {
		Node par, suffLink, output;
		char c;
		int length;
		Node[] children = new Node[ALPHABET_SIZE];
		boolean leaf;
		public Node(Node par, char c, Node suffLink, Node output) {
			this();
			this.par = par;
			this.c = c;
			this.suffLink = suffLink;
			this.output = output;
		}
		public Node() { }
		public String toString() {
			return c + " " + (suffLink == this);
		}
	}
	public AhoCorasick(ArrayList<String> s) {
		this();
		for(String str : s)
			addString(str);
		rebuild();
	}
	private AhoCorasick() {
		nodes = new ArrayList<Node>();
		nodes.add(new Node());
		nodes.get(0).par = nodes.get(0);
		nodes.get(0).suffLink = nodes.get(0);
		nodes.get(0).c = '\u0000';
	}
	public AhoCorasick(String[] s) {
		this();
		for(String str : s)
			addString(str);
		rebuild();
	}
	public void rebuild() {
		Queue<Node> q = new ArrayDeque<Node>();
		int numChildren = 0;
		for(Node e: nodes.get(0).children) {
			if(e != null) {
				q.add(e);
				e.suffLink = nodes.get(0);
				numChildren++;
			}
		}
		for(int i = 0; i < numChildren; i++) {
			Node t = q.poll();
			for(Node e: t.children) {
				if(e != null)
					q.add(e);
			}
		}
		while(!q.isEmpty()) {
			Node t = q.poll();
			Node p = t.par;
			while(p.suffLink != p && p.suffLink.children[t.c - 'a'] == null)
				p = p.suffLink;
			if(p.par == p && p.children[t.c - 'a'] == null)
				t.suffLink = nodes.get(0);
			else {
				t.suffLink = p.suffLink.children[t.c - 'a'];
				if(t.suffLink.leaf) {
					t.output = t.suffLink;
				}
				else
					t.output = t.suffLink.output;
			}
			for(Node e: t.children) {
				if(e != null)
					q.add(e);
			}
		}
	}
	//adds a string, must call rebuild after
	public void addString(String s) {
		Node cur = nodes.get(0);
		for(char ch: s.toCharArray()) {
			int c = ch - 'a';
			if(cur.children[c] == null) {
				nodes.add(new Node(cur, ch, null, null));
				cur.children[c] = nodes.get(nodes.size() - 1);
			}
			cur = cur.children[c];
		}
		cur.leaf = true;
		cur.length = s.length();
	}
	//returns starting index of all strings
	public ArrayList<Integer> getAll(String s) {
		ArrayList<Integer> ans = new ArrayList<Integer>();
		Node curr = nodes.get(0);
		for(int i = 0; i < s.length(); i++) {
			if(curr.children[s.charAt(i) - 'a'] != null) {
				curr = curr.children[s.charAt(i) - 'a'];
				if(curr.leaf)
					ans.add(i - curr.length + 1);
				Node t = curr;
				while(t.output != null) {
					t = t.output;
					ans.add(i - t.length + 1);
				}
			}
			else {
				if(curr != nodes.get(0)) {
					i--;
				}
				curr = curr.suffLink;
			}
		}
		return ans;
	}
}