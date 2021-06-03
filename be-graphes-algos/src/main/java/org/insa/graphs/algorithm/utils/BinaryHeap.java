package org.insa.graphs.algorithm.utils;

import java.util.ArrayList;

/**
 * Implements a binary heap containing elements of type E.
 *
 * Note that all comparisons are based on the compareTo method, hence E must
 * implement Comparable
 * 
 * @author Mark Allen Weiss
 * @author DLB
 */
public class BinaryHeap<E extends Comparable<E>> implements PriorityQueue<E> {

    private static final String NULL = null;

	// Number of elements in heap.
    private int currentSize;

    // The heap array.
    protected final ArrayList<E> array;

    /**
     * Construct a new empty binary heap.
     */
    public BinaryHeap() {
        this.currentSize = 0;
        this.array = new ArrayList<E>();
    }

    /**
     * Construct a copy of the given heap.
     * 
     * @param heap Binary heap to copy.
     */
    public BinaryHeap(BinaryHeap<E> heap) {
        this.currentSize = heap.currentSize;
        this.array = new ArrayList<E>(heap.array);
    }

    /**
     * Set an element at the given index.
     * 
     * @param index Index at which the element should be set.
     * @param value Element to set.
     */
    private void arraySet(int index, E value) {
        if (index == this.array.size()) {
            this.array.add(value);
        }
        else {
            this.array.set(index, value);
        }
    }

    /**
     * @return Index of the parent of the given index.
     */
    protected int indexParent(int index) {
        return (index - 1) / 2;
    }

    /**
     * @return Index of the left child of the given index.
     */
    protected int indexLeft(int index) {
        return index * 2 + 1;
    }

    /**
     * Internal method to percolate up in the heap.
     * 
     * @param index Index at which the percolate begins.
     */
    private void percolateUp(int index) {
        E x = this.array.get(index);

        for (; index > 0
                && x.compareTo(this.array.get(indexParent(index))) < 0; index = indexParent(
                        index)) {
            E moving_val = this.array.get(indexParent(index));
            this.arraySet(index, moving_val);
        }

        this.arraySet(index, x);
    }

    /**
     * Internal method to percolate down in the heap.
     * 
     * @param index Index at which the percolate begins.
     */
    private void percolateDown(int index) {
        int ileft = indexLeft(index);
        int iright = ileft + 1;

        if (ileft < this.currentSize) {
            E current = this.array.get(index);
            E left = this.array.get(ileft);
            boolean hasRight = iright < this.currentSize;
            E right = (hasRight) ? this.array.get(iright) : null;

            if (!hasRight || left.compareTo(right) < 0) {
                // Left is smaller
                if (left.compareTo(current) < 0) {
                    this.arraySet(index, left);
                    this.arraySet(ileft, current);
                    this.percolateDown(ileft);
                }
            }
            else {
                // Right is smaller
                if (right.compareTo(current) < 0) {
                    this.arraySet(index, right);
                    this.arraySet(iright, current);
                    this.percolateDown(iright);
                }
            }
        }
    }

    @Override
    public boolean isEmpty() {
        return this.currentSize == 0;
    }

    @Override
    public int size() {
        return this.currentSize;
    }

    @Override
    public void insert(E x) {
        int index = this.currentSize++;
        this.arraySet(index, x);
        this.percolateUp(index);
    }

    @Override
    public void remove(E x) throws ElementNotFoundException {
    	
    	if (isEmpty())
            throw new ElementNotFoundException(x);
    	
    	int index = 0;
    	int verif = 1;
    	E elem_fin = this.array.get(this.currentSize - 1);
    	
    	while (index<=this.currentSize-1 && verif == 1) {
    		
    		if (this.array.get(index) == x ) {
    			verif = 0;
    			this.arraySet(index, elem_fin); //Swap the element to delete with the last element
    			this.currentSize --; //Delete the element
    		}
    		index ++;
    	}
    	
    	if (verif == 1) {
    		throw new ElementNotFoundException(x);
    	}
        
    	//Restore heap property
        for (int i= 0;i<this.currentSize;i++) {
        	percolateDown(i);
        }
  
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /* first attempt :
    public void remove(E x) throws ElementNotFoundException {
    	
    	//Handler empty heap and not found
    	int k = this.array.indexOf(x);
        if (k == -1) {
        	this.currentSize = this.size()-1;
        	throw new ElementNotFoundException(x);
        }
        if (this.size() <= 0) {
        	this.currentSize = 0;
        	throw new ElementNotFoundException(x);
        }
        //Swap the element to delete with the last element
        int indexLast = this.size() - 1;
        E pivot = this.array.get(indexLast);
        this.arraySet(indexLast, x);
        this.arraySet(k, pivot);
        
        //Delete the element
        this.currentSize--;
        
        //Restore heap property
        this.percolateDown(k);
        
        return;
    }
    
    Optimized element index search : not finished
    int etage = 0;
    int i = 0;
    int nbEtage = (int) (Math.log(this.size())/Math.log(2)) + 1 ;
    boolean trouve = false;
    while ( (etage < nbEtage) && !trouve) {
    	i = (int) Math.pow(2, etage) - 1 ; //Cursor on the beginning of a stage
    	while (i < (int) Math.pow(2, etage + 1) - 1) {
    		if (this.array.get(i) == x) {
    			int indexElem = i;
    			trouve = true;
    		}
    		i++;
    	}
    	etage++;
    }

    //If we haven't found the element, throw an error
    if (!trouve) { 
    	throw new ElementNotFoundException(x);
    }
    */

    @Override
    public E findMin() throws EmptyPriorityQueueException {
        if (isEmpty())
            throw new EmptyPriorityQueueException();
        return this.array.get(0);
    }

    @Override
    public E deleteMin() throws EmptyPriorityQueueException {
        E minItem = findMin();
        E lastItem = this.array.get(--this.currentSize);
        this.arraySet(0, lastItem);
        this.percolateDown(0);
        return minItem;
    }
    

    /**
     * Creates a multi-lines string representing a sorted view of this binary heap.
     * 
     * @return a string containing a sorted view this binary heap.
     */
    public String toStringSorted() {
        return BinaryHeapFormatter.toStringSorted(this, -1);
    }

    /**
     * Creates a multi-lines string representing a sorted view of this binary heap.
     * 
     * @param maxElement Maximum number of elements to display. or {@code -1} to
     *                   display all the elements.
     * 
     * @return a string containing a sorted view this binary heap.
     */
    public String toStringSorted(int maxElement) {
        return BinaryHeapFormatter.toStringSorted(this, maxElement);
    }

    /**
     * Creates a multi-lines string representing a tree view of this binary heap.
     * 
     * @return a string containing a tree view of this binary heap.
     */
    public String toStringTree() {
        return BinaryHeapFormatter.toStringTree(this, Integer.MAX_VALUE);
    }

    /**
     * Creates a multi-lines string representing a tree view of this binary heap.
     * 
     * @param maxDepth Maximum depth of the tree to display.
     * 
     * @return a string containing a tree view of this binary heap.
     */
    public String toStringTree(int maxDepth) {
        return BinaryHeapFormatter.toStringTree(this, maxDepth);
    }

    @Override
    public String toString() {
        return BinaryHeapFormatter.toStringTree(this, 8);
    }
    

}
