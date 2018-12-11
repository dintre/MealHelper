//package application;
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
	        
	        //creates initial node as a leaf
	        root = new LeafNode();
	        this.branchingFactor = branchingFactor;
	        maxKeys = branchingFactor - 1;
	    }
	    
    
    /*
     * (non-Javadoc)
     * @see BPTreeADT#insert(java.lang.Object, java.lang.Object)
     */
    @Override
    public void insert(K key, V value) {
    	if (key==null) {
    		throw new IllegalArgumentException();
    	}
    	
    	//runs the Node insert methods beginning with the root
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

      //runs the Node rangeSearch methods beginning with the root
        List<V> returnList = root.rangeSearch(key, comparator);
        
        return returnList;
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
        List<K> childrenKeys;
        
        /**
         * Package constructor
         */
        InternalNode() {
            super();
            children = new ArrayList<Node>();
            childrenKeys = new ArrayList<K>();
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#getFirstLeafKey()
         */
        K getFirstLeafKey() {
        	K firstKey = children.get(0).getFirstLeafKey();
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
        	
        	//runs a binary search to find where the key would slot in to
        	//this node
        	int insertionPoint = Collections.binarySearch(keys,key);
        	
        	//formats the output of the search based on how binarySearch 
        	//returns for searches of keys not already in the node
        	if(insertionPoint < 0) {
        		insertionPoint = (insertionPoint  * -1) - 1;
        	}

        	//gets the child node of this node where the new key would
        	//fit in
        	Node insertionNode = children.get(insertionPoint);
        	insertionNode.insert(key, value);
        	
        	//splits the child if it has too many nodes in it now
        	if(insertionNode.isOverflow()) {
        		
        		//splits the overfilled insertion node
        		Node newKid = insertionNode.split();
        		
        		//finds the position of the new key node
        		int newKidPlace = Collections.binarySearch(keys, 
        				newKid.getFirstLeafKey());
        		if(newKidPlace < 0) {
        			newKidPlace = (1 + newKidPlace)  * -1;
            	}
        		
        		int newKidChildPlace = newKidPlace + 1;
        		
        		//finds the position of the insertion node
        		int currentPlace = Collections.binarySearch(keys, 
        				insertionNode.getFirstLeafKey());
        		if(currentPlace < 0) {
        			currentPlace = (1 + currentPlace)  * -1;
            	}
        		
        		//puts the new kid one forward if it's in an equal 
        		//position as the insertion node and its first LeafKey is bigger
        		/*if(newKidPlace == currentPlace && (insertionNode
        				.getFirstLeafKey()
        				.compareTo(newKid.getFirstLeafKey()) >= 0)) {
        			newKidPlace++;
        		}*/
        	
        		
        		//adds the new kid's first key into this node's keys
        		this.keys.add(newKidPlace, newKid.getFirstLeafKey());
        		
        		//adds the new node as the child of this one
        		this.children.add(newKidChildPlace, newKid);
        		
        		//removes the key from newKid if this is an internal node 
        		//(since it graduated to this node)
        		if(newKid instanceof BPTree.InternalNode) {
        			newKid.keys.remove(0);
        		}
        	}
        	
        	//splits root if it's now overflowing
        	if(root.isOverflow()) {
        		
        		//creates a new sibling for this node
        		Node newSib = this.split();
        		
        		//creates a new root for them to go into
        		InternalNode newRoot = new InternalNode();
        		
        		//adds this and the new sibling to root
        		newRoot.children.add(this);
        		newRoot.children.add(newSib);
        		
        		//gets the first leaf of the new sibling and makes it the 
        		//key of the parent node, removing it from the sibling since
        		//it's graduated
        		newRoot.keys.add(newSib.getFirstLeafKey());
        		newSib.keys.remove(0);
        		root = newRoot;	
        		
        	}
        }
        
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#split()
         */
        Node split() {
        	
        	//figures out where the node is going to be be split
        	int midpoint = ( keys.size()) / 2;
        	int sibKeys = keys.size() - midpoint;
        	
        	//creates the new split sibling
        	InternalNode sibling= new InternalNode();
        	int spot = midpoint;
        	
        	//removes this node's keys and kids according to the split and
        	//gives them to the new sibling
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
        	
        	//finds the child of this node to look for the key in
        	int searchPoint = Collections.binarySearch(keys,key);
        	if(searchPoint < 0) {
        		searchPoint = (searchPoint  * -1) - 1;
        	}
        	
        	//runs the search on the appropriate child
            return (children.get(searchPoint).rangeSearch(key, comparator)); 
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
        }
        
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#getFirstLeafKey()
         */
        K getFirstLeafKey() {
        	
        	//gets the first key in this node
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
        	
        	//finds the point in the node where the new key should fit 
        	//and formats appropriately based on the binarySearch results
        	int insertionPoint = Collections.binarySearch(keys,key);
        	if(insertionPoint < 0) {
        		insertionPoint = (1 + insertionPoint)  * -1;
        	}
        	
        	//adds key and value at appropriate point
    		this.keys.add(insertionPoint,key);
    		this.values.add(insertionPoint,value);
        	   		
    		//splits the root if it's now overflowing. This should only run
    		//when the root is a LeafNode
        	if(root.isOverflow()) {
        		Node newSib = split();
        		InternalNode newRoot = new InternalNode();
        		newRoot.children.add(this);
        		newRoot.children.add(newSib);
        		newRoot.keys.add(newSib.getFirstLeafKey());
        		newRoot.childrenKeys.add(key);
        		root = newRoot;	
        	}
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#split()
         */
        Node split() {
        	//figures out where the node is going to be be split
        	int midpoint = (keys.size()) / 2 ;
        	int sibKeys = keys.size() - midpoint;
        	
        	//creates a new sibling for this node
        	LeafNode sibling= new LeafNode();
        	int spot = midpoint;
        	
        	//removes keys and values from this node and gives them to the
        	//sibling post split
        	for(int i = 0; i < sibKeys; i++) {
        		sibling.keys.add(i, this.keys.get(spot));
        		sibling.values.add(i, this.values.get(spot));
        		this.keys.remove(spot);
        		this.values.remove(spot);
        	}
        	
        	
        	//fixes the ordering of the split nodes so that they connect
        	//this<->sibling<->this node's original next
        	sibling.next = this.next;
        	if(this.next != null) {
        	this.next.previous = sibling;
        	}
        	sibling.previous = this;
        	this.next = sibling;
        	
            return sibling;
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#rangeSearch(Comparable, String)
         */
        List<V> rangeSearch(K key, String comparator) {
        	List<V> returnList = new ArrayList<V>();
        	if(keys.size()<=0) {
        		return returnList;
        	}
        	
        	//finds the point where this key would fit into this node and
        	//formats it correctly
        	int findPoint = Collections.binarySearch(keys,key);
        	if(findPoint < 0) {
        		findPoint = (findPoint  * -1) - 1;
        	}

        	//the node that was found for the first point of key matching
        	LeafNode foundNode = this;
        	
        	//the current node being looked at 
    		LeafNode currentNode = foundNode;
    		
    		//the current place of the key of the current node that we are
    		//looking at right now
    		int currentPoint = findPoint;
    		
    		//Greater Than or Equal To case
        	if(comparator.contentEquals(">=")) {
        		
        		
        		//if the spot where the key would go doesn't currently exist
        		//looks at the key before it and first node of the key next to it
        		//and changes findPoint/foundNode appropriately
        		if(keys.size() < (findPoint + 1)) {
        			
        			if((foundNode.keys.get(findPoint-1).compareTo(key)) >= 0) {
        				findPoint--;
        			}
        			else if(foundNode.next == null) {
    					return returnList;
    				}
        			else {
        				foundNode = foundNode.next;
            			findPoint = 0;
        			}
        			
        		}
        		
        		//if key at the foundNode is smaller than the given key, this
        		// checks the next key and returns blank if it doesn't exist
        		if(foundNode.keys.get(findPoint).compareTo(key) < 0) {
        			findPoint++;
        			if((foundNode.keys.get(findPoint).compareTo(key) < 0) && 
        					findPoint > this.keys.size()-1) {
        				if(foundNode.next == null) {
        					return returnList;
        				}
        				
        				else {
                			
            				foundNode = foundNode.next;
            				findPoint = foundNode.keys.size()-1;
            			}
        			}
        		}
        		
        		//sets current to one key previous to found to check for duplicates
        		currentNode = foundNode;
        		currentPoint = findPoint - 1;
        		
        		if(currentPoint < 0 && currentNode.previous != null) {
        			currentNode = currentNode.previous;
        			currentPoint = currentNode.keys.size()-1;
        			
        		}
        		
        		//moves foundNode/point back until they're to the earliest duplicate
        		while(currentPoint >= 0 && currentNode.keys.get(currentPoint)
        				.compareTo(key) >=0) {
        			foundNode = currentNode;
        			findPoint = currentPoint;
        			
        			currentPoint--;
        			
        			if(currentPoint < 0 && currentNode.previous != null) {
            			currentNode = currentNode.previous;
            			currentPoint = currentNode.keys.size()-1;
        			
        			}
        		}
        		
        		//resets current to where found is
        		currentNode = foundNode;
        		currentPoint = findPoint;
        		
        		//adds everything after found to the list
        		while(currentPoint <= currentNode.keys.size()-1) {
        			returnList.add(currentNode.values.get(currentPoint));
        			currentPoint++;
        			
        			if(currentPoint > currentNode.keys.size()-1) {
        				if(currentNode.next != null) {
        					currentNode = currentNode.next;
            				currentPoint = 0;
        				}
        					
        			}
        		}
        		
        	}
        	
        	//equals case
        	if(comparator.contentEquals("==")) {
        		
        		int spot;
        		int quit = 0;
        		
        		
        		//if the placement is outside of the current node or if the key at the 
        		//point found is not equivalent to the key itself, this will look 
        		//forwards and backwards to check for it
        		if((keys.size() < (findPoint + 1)) || !keys.get(findPoint).equals(key)) {
        			
        			while(quit == 0) {
        				
        				//checks the end of the previous node for the key
        				if(findPoint <= 0) {
        					currentNode = currentNode.previous;
        					if(currentNode != null) {
        						currentPoint = currentNode.keys.size()-1;
        						if(currentNode.keys.get(currentPoint).equals(key)) {
        							foundNode = currentNode;
        							findPoint = currentPoint;
        							break;
        						}
        					        					        					
        					}
        				}
        			
        				//checks the key before the sorted point for the key
        				if(findPoint < 0) {
        					currentPoint = findPoint - 1;
        					if(keys.get(currentPoint).equals(key)) {
        						foundNode = currentNode;
        						findPoint = currentPoint;
        						break;
        					}
        				}
        			
        				//checks the key after the sorted point for the key
        				if(findPoint < keys.size()-1) {
        					currentPoint = findPoint + 1;
        					if(keys.get(currentPoint).equals(key)) {
        						foundNode = currentNode;
        						findPoint = currentPoint;
        						break;
        					}
        				}
        				
        				//checks the beginning of the next node for the key
        				if(findPoint >= keys.size()-1) {
        					currentNode = currentNode.next;
        					if(currentNode != null) {
        						currentPoint = 0;
        						if(currentNode.keys.get(currentPoint).equals(key)) {
        							foundNode = currentNode;
        							findPoint = currentPoint;
        							break;
        						} 					        					
        					}
        				}
        			
        				
        				quit = 1;
        			}
        		}
        		
        		if(quit == 1) {
        			return returnList;
        		}
        		
        		returnList.add(foundNode.values.get(findPoint));
        		currentNode = foundNode;
        		currentPoint = findPoint;
        		
        		//checks before the found point for other keys that match
        		if(findPoint == 0 && foundNode.previous!=null) {
        			currentNode = foundNode.previous;
        			currentPoint = currentNode.keys.size() - 1;
        			if(currentNode.keys.get(currentPoint).equals(key)) {
						returnList.add(currentNode.values.get(currentPoint));
					}
        			
        			else {
        				quit = 1;
        			}
        		}
        		
        		currentPoint = currentPoint - 1;
        		
        		//searches for duplicates
        		if(quit == 0 && currentPoint > 0) {
        			while(currentNode.keys.get(currentPoint).equals(key)) {
        				while(currentPoint >= 0) {
        				
        					if(!(currentNode.keys.get(currentPoint).equals(key))) {
        						quit = 1;
        						break;
        					}
        					else {
        						returnList.add(currentNode.values.get(currentPoint));  				
        					}

        					currentPoint--;
        				}
        			
        				if(quit == 1) {
        					break;
        				}
        			
        				currentNode = currentNode.previous;
        				if(currentNode == null) {
        					break;
        				}
        				currentPoint = currentNode.keys.size() - 1;
        			}
        		}
        		
        		currentNode = foundNode;
        		currentPoint = findPoint;
        		quit = 0;
        		
        		//checks after the found point for other keys that match
        		if(findPoint == (foundNode.keys.size()-1) && foundNode.next!=null) {
        			currentNode = foundNode.next;
        			currentPoint = 0;
        			if(currentNode.keys.get(currentPoint).equals(key)) {
						returnList.add(currentNode.values.get(currentPoint));
					}
        			
        			else {
        				quit = 1;
        			}
        		}
        		
        		currentPoint = currentPoint + 1;
        		
        		
        		if(quit == 0 && currentPoint < currentNode.keys.size()) {
        			while(currentNode.keys.get(currentPoint).equals(key)) {
        				while(currentPoint < currentNode.keys.size()) {
        				
        					if(!(currentNode.keys.get(currentPoint).equals(key))) {
        						quit = 1;
        						break;
        					}
        					else {
        						returnList.add(currentNode.values.get(currentPoint));  				
        					}

        					currentPoint++;
        				}
        			
        				if(quit == 1) {
        					break;
        				}
        			
        				currentNode = currentNode.next;
        				if(currentNode == null) {
        					break;
        				}
        				currentPoint = 0;
        			}
        		}
        		

        		
        	}
        	
        	//less than or equal to case
        	if(comparator.contentEquals("<=")) {
        		
        		//if the placement is outside of the current node 
        		//this will look forwards and backwards to check for it
        		if(keys.size() < (findPoint + 1)) {
        			if((foundNode.keys.get(findPoint-1).compareTo(key)) <= 0) {
        				findPoint--;
        			}
        			else if(foundNode.next == null) {
    					return returnList;
    				}
        			else {
        				foundNode = foundNode.next;
            			findPoint = 0;
        			}
        		}
        		
        		//if key at the foundNode is bigger than the given key, this checks the previous key
        		//and returns blank if it doesn't exist
        		if(foundNode.keys.get(findPoint).compareTo(key) > 0) {
        			findPoint--;
        			if((foundNode.keys.get(findPoint).compareTo(key) > 0) && findPoint >= 0) {
        				if(foundNode.next == null) {
        					return returnList;
        				}
        				
        				else {
                			
            				foundNode = foundNode.next;
            				findPoint = foundNode.keys.size()-1;
            				}
        			}
        		}
        		
        		currentNode = foundNode;
        		currentPoint = findPoint + 1;
        		
        		
        		if(currentPoint > foundNode.keys.size()-1 && currentNode.next != null) {
        			currentNode = currentNode.next;
        			currentPoint = 0;
        			
        		}
        		
        		//searches for duplicates
        		while(currentPoint <= currentNode.keys.size() -1 && currentNode.keys
        				.get(currentPoint).compareTo(key) <=0) {
        			foundNode = currentNode;
        			findPoint = currentPoint;
        			
        			currentPoint++;
        			
        			if(currentPoint > currentNode.keys.size() -1 && currentNode.next != null) {
            			currentNode = currentNode.next;
            			currentPoint = 0;
        			
        			}
        		}
        		
        		currentNode = foundNode;
        		currentPoint = findPoint;
        		
        		//adds everything before foundpoint to the list
        		while(currentPoint >=0) {
        			returnList.add(currentNode.values.get(currentPoint));
        			currentPoint--;
        			
        			if(currentPoint < 0) {
        				if(currentNode.previous != null) {
        					currentNode = currentNode.previous;
            				currentPoint = currentNode.keys.size() -1;
        				}
        					
        			}
        		}
        	}
        	
        
        	return returnList;
        }
    } // End of class LeafNode
    
    
    /**
     * Contains a basic test scenario for a BPTree instance.
     * It shows a simple example of the use of this class
     * and its related types.
     * 
     * @param args
     */
   /* public static void main(String[] args) {
        // create empty BPTree with branching factor of 3
        BPTree<Double, Double> bpTree = new BPTree<>(3);

        // create a pseudo random number generator
        Random rnd1 = new Random();

        // some value to add to the BPTree
        Double[] dd = {0.0d, 0.5d, 0.2d, 0.8d};
        
       /* bpTree.insert(0.3d, 0.1d);
        bpTree.insert(0.2d, 0.2d);
        bpTree.insert(0.3d, 0.3d);
       bpTree.insert(0.5d, 0.4d);
      bpTree.insert(0.3d, 0.5d);
      bpTree.insert(0.3d, 0.6d);
   bpTree.insert(0.1d, 0.7d);
        bpTree.insert(0.3d, 0.8d);
       bpTree.insert(0.6d, 0.9d);
       bpTree.insert(1.0d, 1.0d);
       bpTree.insert(0.03d, 1.1d);
   //     bpTree.insert(0.21d, 1.2d);
   //    bpTree.insert(0.21d, 1.3d);
        BPTree<Integer, String> calorieTree = new BPTree<>(3);

        calorieTree.insert(80, "Egg");
        calorieTree.insert(100, "Soy");
        calorieTree.insert(288, "Milk");
        calorieTree.insert(150, "Eggo");
        calorieTree.insert(80, "Egg");
        calorieTree.insert(100, "Soy");
        calorieTree.insert(288, "Milk"); //search error here
        calorieTree.insert(150, "Eggo");
        calorieTree.insert(80, "Egg");
        calorieTree.insert(100, "Soy");
        calorieTree.insert(288, "Milk");
        calorieTree.insert(150, "Eggo");
        calorieTree.insert(80, "Egg");
       calorieTree.insert(100, "Soy");
        calorieTree.insert(288, "Milk");
        calorieTree.insert(150, "Eggo");
        calorieTree.insert(80, "Egg");
        calorieTree.insert(22, "Red Win Vinegar");
        calorieTree.insert(288, "Yogurt");
        calorieTree.insert(150, "Eggo");
        calorieTree.insert(32, "Chip");
        calorieTree.insert(231, "Candy");
     //   calorieTree.insert(100, "Soy Milk");
       // calorieTree.insert(130, "Bread");
    //    calorieTree.insert(830, "Pizza");
       // calorieTree.insert(1538, "Lasagna");
    //    calorieTree.insert(153, "Chicken");
        
        System.out.println("\n\nTree structure:\n" + calorieTree.toString());
        System.out.println(calorieTree.rangeSearch(150, "<="));
}*/

        // build an ArrayList of those value and add to BPTree also
        // allows for comparing the contents of the ArrayList 
        // against the contents and functionality of the BPTree
        // does not ensure BPTree is implemented correctly
        // just that it functions as a data structure with
        // insert, rangeSearch, and toString() working.
       /* List<Double> list = new ArrayList<>();
        for (int i = 0; i < 400; i++) {
            Double j = dd[rnd1.nextInt(4)];
            list.add(j);
            bpTree.insert(j, j);
            System.out.println("\n\nTree structure:\n" + bpTree.toString());
        }
        List<Double> filteredValues = bpTree.rangeSearch(0.2d, ">=");
        System.out.println("Filtered values: " + filteredValues.toString());
    }
    }*/
} // End of class BPTree