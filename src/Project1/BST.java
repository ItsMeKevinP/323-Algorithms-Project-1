package Project1;
import java.util.*;

/**
 * Generic binary search tree
 *
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012
 * SOURCE--https://cs.dartmouth.edu/~cbk/classes/10/12fall/notes/10/BST.java
 */

public class BST<K extends Comparable<K>,V> {
	private K key;
	private V value;
	private BST<K,V> left, right;


	/**
	 * When key is not found in tree
	 */
	public static class InvalidKeyException extends RuntimeException {
		public InvalidKeyException(String message) {
			super(message);
		}
	}

	/**
	 * Constructs leaf node -- left and right are null
	 */
	public BST(K key, V value) {
		this.key = key; this.value = value;
	}

	/**
	 * Constructs inner node
	 */
	public BST(K key, V value, BST<K,V> left, BST<K,V> right) {
		this.key = key; this.value = value;
		this.left = left; this.right = right;
	}

	/**
	 * Is it a leaf node?
	 */
	public boolean isLeaf() {
		return left == null && right == null;
	}

	/**
	 * Does it have a left child?
	 */
	public boolean hasLeft() {
		return left != null;
	}

	/**
	 * Does it have a right child?
	 */
	public boolean hasRight() {
		return right != null;
	}

	/**
	 * Returns the value associated with the search key, or throws an exception if not in BST
	 */
	public V find(K search, int x) throws InvalidKeyException {
		//System.out.println(key); // to illustrate
		int compare = search.compareTo(key);
		if (compare == 0) {System.out.println(x+1); return value;}
		if (compare < 0 && hasLeft()) return left.find(search,x+1);
		if (compare > 0 && hasRight()) return right.find(search,x+1);
		throw new InvalidKeyException(search.toString());
	}

	/**
	 * Inserts the key & value into the tree (replacing old key if equal)
	 */
	public void insert(K key, V value) {
		int compare = key.compareTo(this.key);
		if (compare == 0) {
			// replace
			this.value = value;
		}
		else if (compare < 0) {
			// insert on left (new leaf if no left)
			if (hasLeft()) left.insert(key, value);
			else left = new BST<K,V>(key, value);
		}
		else if (compare > 0) {
			// insert on right (new leaf if no right)
			if (hasRight()) right.insert(key, value);
			else right = new BST<K,V>(key, value);
		}
	}

	/**
	 * Deletes the key and returns the modified tree, which might not be the same object as the original tree
	 * Thus must afterwards just use the returned one
	 */
	public BST<K,V> delete(K search) throws InvalidKeyException {
		int compare = search.compareTo(key);
		if (compare == 0) {
			// Easy cases: 0 or 1 child -- return other
			if (!hasLeft()) return right;
			if (!hasRight()) return left;
			// If both children are there, rotate up the successor (smallest on right)
			// Find it
			BST<K,V> successor = right;
			while (successor.hasLeft()) successor = successor.left;
			// Delete it
			right = right.delete(successor.key);
			// And take its key & value
			this.key = successor.key;
			this.value = successor.value;
			return this;
		}
		else if (compare < 0 && hasLeft()) {
			left = left.delete(search);
			return this; 
		}
		else if (compare > 0 && hasRight()) {
			right = right.delete(search);
			return this;
		}
		throw new InvalidKeyException(search.toString());
	}

	/**
	 * Parenthesized representation:
	 * <tree> = "(" <tree> ["," <tree>] ")" <key> ":" <value>
	 *        | <key> ":" <value>
	 */
	public String toString() {
		if (isLeaf()) return key+":"+value;
		String s = "(";
		if (hasLeft()) s += left;
		else s += "_";
		s += ",";
		if (hasRight()) s += right;
		else s += "_";
		return s + ")" + key+":"+value;
	}

	/**
	 * Very simplistic BST parser in a parenthesized representation
	 * <tree> = "(" <tree> ["," <tree>] ")" <key> ":" <value>
	 *        | <key> ":" <value>
	 * Assumes that the tree actually has the BST property!!!
	 * No effort at all to handle malformed trees
	 */
	public static BST<String,String> parseBST(String s) {
		return parseBST(new StringTokenizer(s, "(,)", true));
	}

	/**
	 * Does the real work of parsing, now given a tokenizer for the string
	 */
	public static BST<String,String> parseBST(StringTokenizer st) {
		String token = st.nextToken();
		if (token.equals("(")) {
			// Inner node
			BST<String,String> left = parseBST(st);
			BST<String,String> right = null;
			String comma = st.nextToken();
			if (comma.equals(",")) {
				right = parseBST(st);	
				String close = st.nextToken();
			}
			String label = st.nextToken();
			String[] pieces = label.split(":");
			return new BST<String,String>(pieces[0], pieces[1], left, right);
		}
		else {
			// Leaf
			String[] pieces = token.split(":");
			return new BST<String,String>(pieces[0], pieces[1]);
		}
	}
}