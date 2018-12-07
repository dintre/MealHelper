import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

/**
 * Implementation of a B+ tree to allow efficient access to
 * many different indexes of a large data set. 
 * BPTree objects are created for each type of index
 * needed by the program.  BPTrees provide an efficient
 * range search as compared to other types of data structures
 * due to the ability to perform log_m N lookups and
 * linear in-order traversals of the data items.
 * 
 * @author sapan (sapan@cs.wisc.edu)
 *
 * @param <K> key - expect a string that is the type of id for each item
 * @param <V> value - expect a user-defined type that stores all data for a food item
 */
public class BPTree<K extends Comparable<K>, V> implements BPTreeADT<K, V> {

    // Root of the tree
    private Node root;
    
    // Branching factor is the number of children nodes 
    // for internal nodes of the tree
    private int branchingFactor;
    
    //minimum children for non-root internal node and minimum keys for leafs
    private int floor;
    
    //max keys in a node
    private int maxKeys;
    
    
    /**
     * Public constructor
     * 
     * @param branchingFactor 
     */
    public BPTree(int branchingFactor) {
        if (branchingFactor <= 2) {
            throw new IllegalArgumentException(
               "Illegal branching factor: " + branchingFactor);
        }
        root = new LeafNode();
        this.branchingFactor = branchingFactor;
        floor = branchingFactor/2;
        maxKeys = branchingFactor - 1;
        // TODO : Complete
    }
    
    
    /*
     * (non-Javadoc)
     * @see BPTreeADT#insert(java.lang.Object, java.lang.Object)
     */
    @Override
    public void insert(K key, V value) {
        // TODO : Complete
    	if (key==null) {
    		throw new IllegalArgumentException();
    	}
    	
    	//LeafNode leaf = this.find(key);
    	
    	root.insert(key, value);

    }

	/*
     * (non-Javadoc)
     * @see BPTreeADT#rangeSearch(java.lang.Object, java.lang.String)
     */
    @Override
    public List<V> rangeSearch(K key, String comparator) {
        if (!comparator.contentEquals(">=") && 
            !comparator.contentEquals("==") && 
            !comparator.contentEquals("<=") )
            return new ArrayList<V>();
        // TODO : Complete
        return null;
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        Queue<List<Node>> queue = new LinkedList<List<Node>>();
        queue.add(Arrays.asList(root));
        StringBuilder sb = new StringBuilder();
        while (!queue.isEmpty()) {
            Queue<List<Node>> nextQueue = new LinkedList<List<Node>>();
            while (!queue.isEmpty()) {
                List<Node> nodes = queue.remove();
                sb.append('{');
                Iterator<Node> it = nodes.iterator();
                while (it.hasNext()) {
                    Node node = it.next();
                    sb.append(node.toString());
                    if (it.hasNext())
                        sb.append(", ");
                    if (node instanceof BPTree.InternalNode)
                        nextQueue.add(((InternalNode) node).children);
                }
                sb.append('}');
                if (!queue.isEmpty())
                    sb.append(", ");
                else {
                    sb.append('\n');
                }
            }
            queue = nextQueue;
        }
        return sb.toString();
    }
    
    
    /**
     * This abstract class represents any type of node in the tree
     * This class is a super class of the LeafNode and InternalNode types.
     * 
     * @author sapan
     */
    private abstract class Node {
        
        // List of keys
        List<K> keys;
        

        
        /**
         * Package constructor
         */
        Node() {
            // TODO : Complete
        	keys = new ArrayList<K>();
        }
        
        /**
         * Inserts key and value in the appropriate leaf node 
         * and balances the tree if required by splitting
         *  
         * @param key
         * @param value
         */
        abstract void insert(K key, V value);

        /**
         * Gets the first leaf key of the tree
         * 
         * @return key
         */
        abstract K getFirstLeafKey();
        
        /**
         * Gets the new sibling created after splitting the node
         * 
         * @return Node
         */
        abstract Node split();
        
        /*
         * (non-Javadoc)
         * @see BPTree#rangeSearch(java.lang.Object, java.lang.String)
         */
        abstract List<V> rangeSearch(K key, String comparator);

        /**
         * 
         * @return boolean
         */
        abstract boolean isOverflow();
        
        public String toString() {
            return keys.toString();
        }
    
    } // End of abstract class Node
    
    /**
     * This class represents an internal node of the tree.
     * This class is a concrete sub class of the abstract Node class
     * and provides implementation of the operations
     * required for internal (non-leaf) nodes.
     * 
     * @author sapan
     */
    private class InternalNode extends Node {

        // List of children nodes
        List<Node> children;
        
        /**
         * Package constructor
         */
        InternalNode() {
            super();
            children = new ArrayList<Node>();
            // TODO : Complete
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#getFirstLeafKey()
         */
        K getFirstLeafKey() {
            // TODO : Complete
        	Node current = this;
        	while(current instanceof BPTree.InternalNode) {
        		Node first = children.get(0);
        		K firstK = first.keys.get(0);
            	Node compare;
            	K compareK;
            	for(int i = 1; i < children.size(); i++) {
            		compare = children.get(i);
            		compareK = compare.keys.get(0);
            		if(compareK.compareTo(firstK) < 1) {
            			first = compare;
            			firstK = compareK;
            		}
            	}
            	
            	current = first;
        	}
        	K firstKey = current.getFirstLeafKey();
            return firstKey;
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#isOverflow()
         */
        boolean isOverflow() {
        	if(children.size() > branchingFactor) {
            	return true;
            }
            
            else
            	return false;
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#insert(java.lang.Comparable, java.lang.Object)
         */
        void insert(K key, V value) {
            // TODO : Complete
        	
        	int insertionPoint = Collections.binarySearch(keys,key);
        	if(insertionPoint < 0) {
        		insertionPoint = (1 + insertionPoint)  * -1;
        	}
        	Node insertionNode = children.get(insertionPoint);
        	insertionNode.insert(key, value);
        	
        	if(insertionNode.isOverflow()) {
        		Node newKid = insertionNode.split();
        		int newKidPlace = Collections.binarySearch(keys, newKid.getFirstLeafKey());
        		if(newKidPlace < 0) {
        			newKidPlace = (1 + newKidPlace)  * -1;
            	}
        		this.keys.add(newKidPlace, newKid.getFirstLeafKey());
        		this.children.add(newKidPlace + 1, newKid);
        	}
        	
        	
        	if(root.isOverflow()) {
        		Node newSib = this.split();
        		InternalNode newRoot = new InternalNode();
        		newRoot.children.add(root);
        		newRoot.children.add(newSib);
        		newRoot.keys.add(newSib.getFirstLeafKey());
        		root = newRoot;	
        		
        	}
        }
        
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#split()
         */
        Node split() {
            // TODO : Complete
        	int midpoint = keys.size() / 2;
        	int sibKeys = keys.size() - midpoint;
        	InternalNode sibling= new InternalNode();
        	int spot = midpoint;
        	for(int i = 0; i < sibKeys; i++) {
        		sibling.keys.add(i, this.keys.get(spot));
        		sibling.children.add(this.children.get(spot+1));
        		this.keys.remove(spot);
        		this.children.remove(spot+1);
        	}
        	
            return sibling;
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#rangeSearch(java.lang.Comparable, java.lang.String)
         */
        List<V> rangeSearch(K key, String comparator) {
            // TODO : Complete
            return null;
        }
    
    } // End of class InternalNode
    
    
    /**
     * This class represents a leaf node of the tree.
     * This class is a concrete sub class of the abstract Node class
     * and provides implementation of the operations that
     * required for leaf nodes.
     * 
     * @author sapan
     */
    private class LeafNode extends Node {
        
        // List of values
        List<V> values;
        
        // Reference to the next leaf node
        LeafNode next;
        
        // Reference to the previous leaf node
        LeafNode previous;
        
        /**
         * Package constructor
         */
        LeafNode() {
            super();
            next = null;
            previous = null;
            values = new ArrayList<V>();
            // TODO : Complete
        }
        
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#getFirstLeafKey()
         */
        K getFirstLeafKey() {
            // TODO : Complete
        	K first = keys.get(0);
            return first;
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#isOverflow()
         */
        boolean isOverflow() {
            if(keys.size() > maxKeys) {
            	return true;
            }
            
            else
            	return false;
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#insert(Comparable, Object)
         */
        void insert(K key, V value) {
            // TODO : Complete
        	int place = keys.size() - 1;
        	if(place < 0 || keys.get(place).compareTo(key) < 0) {
        		keys.add(key);
        		values.add(value);
        	}
        	
        	else {
        		
        		for(place = 0; place < keys.size(); place++) {
        			K currentPlace = keys.get(place);
        			if (currentPlace.compareTo(key) > 0) {
        				keys.add(place,key);
        				values.add(place,value);
        				break;
        			}
        			
        			if (currentPlace.compareTo(key) == 0) {
        				keys.add(place+1,key);
        				values.add(place+1,value);
        				break;
        			}
        			
        		}
        	}
        	if(root.isOverflow()) {
        		LeafNode newSib = (BPTree<K, V>.LeafNode) this.split();
        		InternalNode newRoot = new InternalNode();
        		newRoot.children.add(root);
        		newRoot.children.add(newSib);
        		newRoot.keys.add(newSib.getFirstLeafKey());
        		root = newRoot;	
        	}
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#split()
         */
        Node split() {
            // TODO : Complete
        	int midpoint = keys.size() / 2;
        	int sibKeys = keys.size() - midpoint;
        	LeafNode sibling= new LeafNode();
        	int spot = midpoint;
        	for(int i = 0; i < sibKeys; i++) {
        		sibling.keys.add(i, this.keys.get(spot));
        		sibling.values.add(i, this.values.get(spot));
        		this.keys.remove(spot);
        		this.values.remove(spot);
        		//spot++;
        	}
        	
        	sibling.next = this.next;
        	sibling.previous = this;
        	this.next = sibling;
        	
            return sibling;
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#rangeSearch(Comparable, String)
         */
        List<V> rangeSearch(K key, String comparator) {
            // TODO : Complete
            return null;
        }
        
    } // End of class LeafNode
    
    
    /**
     * Contains a basic test scenario for a BPTree instance.
     * It shows a simple example of the use of this class
     * and its related types.
     * 
     * @param args
     */
    public static void main(String[] args) {
        // create empty BPTree with branching factor of 3
        BPTree<Double, Double> bpTree = new BPTree<>(4);
        System.out.println("Hey this actually runs at least");

        // create a pseudo random number generator
        Random rnd1 = new Random();

        // some value to add to the BPTree
        Double[] dd = {0.0d, 0.5d, 0.2d, 0.8d};
        
        bpTree.insert(0.3d, 0.4d);
        bpTree.insert(0.3d, 0.5d);
        bpTree.insert(0.4d, 0.4d);
        bpTree.insert(0.5d, 0.4d);
        bpTree.insert(0.6d, 0.4d);
        bpTree.insert(0.1d, 0.4d);
        bpTree.insert(0.8d, 0.4d);
        bpTree.insert(0.2d, 0.8d);
        bpTree.insert(0.9d, 0.9d);
        bpTree.insert(1.0d, 0.9d);
        
        System.out.println("\n\nTree structure:\n" + bpTree.toString());
        

        // build an ArrayList of those value and add to BPTree also
        // allows for comparing the contents of the ArrayList 
        // against the contents and functionality of the BPTree
        // does not ensure BPTree is implemented correctly
        // just that it functions as a data structure with
        // insert, rangeSearch, and toString() working.
        /*List<Double> list = new ArrayList<>();
        for (int i = 0; i < 400; i++) {
            Double j = dd[rnd1.nextInt(4)];
            list.add(j);
            bpTree.insert(j, j);
            System.out.println("\n\nTree structure:\n" + bpTree.toString());
        }
        List<Double> filteredValues = bpTree.rangeSearch(0.2d, ">=");
        System.out.println("Filtered values: " + filteredValues.toString());*/
    }

} // End of class BPTree