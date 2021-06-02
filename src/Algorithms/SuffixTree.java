package Algorithms;

import java.io.*;
import java.util.*;

public class SuffixTree {
	String s;
	int length;
	Node root;
	private Node activeNode;
	private char activeEdge;
	private int activeLength;
	private int remainder;
	public SuffixTree(String s) {
		this.s = s;
		root = new Node();
		activeNode = root;
		activeEdge = '\u0000';
		activeLength = 0;
		remainder = 1;
		length = s.length();
		init();
	}
	private void init() {
		for(int i = 0; i < length; i++) {
			if(activeNode.contains(s.charAt(i))) {
				remainder++;
				activeLength++;
				if(activeEdge == '\u0000')
					activeEdge = s.charAt(i);
			}
		}
	}
	private class Node {
		Node parent;
		HashMap<Character, Edge> children;
		Node suffixLink;
		public Node() {
			children = new HashMap<Character, Edge>();
		}
		public Node(Node parent) {
			this();
			this.parent = parent;
		}
		public void add(char c, Edge e) {
			children.put(c, e);
		}
		public boolean contains(char c) {
			return children.containsKey(c);
		}
	}
	private class Edge {
		Node child;
		int start, end;
		public Edge(int start) {
			this.start = start;
			end = -1;
		}
		public void split(int end, Node parent) {
			assert end < length - 1;
			this.end = end;
			child = new Node(parent);
			child.add(s.charAt(end + 1), new Edge(end + 1));
		}
	}
}