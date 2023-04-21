import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Your implementation of a MaxHeap.
 *
 * @author Panya Bhinder
 * @version 1.0
 * @userid pbhinder3
 * @GTID 903690652
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class MaxHeap<T extends Comparable<? super T>> {

    /*
     * The initial capacity of the MaxHeap when created with the default
     * constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new MaxHeap.
     *
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     */
    public MaxHeap() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     *
     * You must use the BuildHeap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     * As a reminder, this is the algorithm that involves building the heap
     * from the bottom up by repeated use of downHeap operations.
     *
     * Before doing the algorithm, first copy over the data from the
     * ArrayList to the backingArray (leaving index 0 of the backingArray
     * empty). The data in the backingArray should be in the same order as it
     * appears in the passed in ArrayList before you start the BuildHeap
     * algorithm.
     *
     * The backingArray should have capacity 2n + 1 where n is the
     * number of data in the passed in ArrayList (not INITIAL_CAPACITY).
     * Index 0 should remain empty, indices 1 to n should contain the data in
     * proper order, and the rest of the indices should be empty.
     *
     * Consider how to most efficiently determine if the list contains null data.
     * 
     * @param data a list of data to initialize the heap with
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public MaxHeap(ArrayList<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot use null list to intialize heap.");
        } else {
            backingArray = (T[]) new Comparable[2 * data.size() + 1];
            size = 0;

            for (int i = 0; i < data.size(); i++) {
                if (data.get(i) == null) {
                    throw new IllegalArgumentException("Cannot have null data in the passed in array.");
                } else {
                    backingArray[i + 1] = data.get(i);
                    size++;
                }
            }

            for (int i = size / 2; i > 0; i--) {
                heapify(i);
            }
            
        }
    }

    /**
     * Helper method that performs the down heap function.
     * @param index index at which to start the down heap.
     */
    private void heapify(int index) {
        int left = index * 2;
        int right = index * 2 + 1;
        int max = index;

        if (left <= size && right <= size) {
            if (findMax(index, left, right) == left) {
                max = left;
            } else if (findMax(index, left, right) == right) {
                max = right;
            }
        } else if (left <= size) {
            if (backingArray[left].compareTo(backingArray[index]) > 0) {
                max = left;
            }
        } else if (right <= size) {
            if (backingArray[right].compareTo(backingArray[index]) > 0) {
                max = right;
            }
        }

        if (max != index) {
            T temp = backingArray[index];
            backingArray[index] = backingArray[max];
            backingArray[max] = temp;

            heapify(max);
        }
    }

    /**
     * Helper method that finds the index of the max value between a parent node and its two children.
     * @param parent Index of the parent node.
     * @param left Index of the left child.
     * @param right Index of the right child.
     * @return index of the max value.
     */
    private int findMax(int parent, int left, int right) {
        if (backingArray[parent].compareTo(backingArray[left]) > 0
                && backingArray[parent].compareTo(backingArray[right]) > 0) {
            return parent;
        } else if (backingArray[left].compareTo(backingArray[parent]) > 0
                && backingArray[left].compareTo(backingArray[right]) > 0) {
            return left;
        } else  {
            return right;
        }
    }   


    /**
     * Adds the data to the heap.
     *
     * If sufficient space is not available in the backing array (the backing
     * array is full except for index 0), resize it to double the current
     * length.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null data to heap.");
        } else {
            if (size < backingArray.length - 1) {
                backingArray[size + 1] = data;
            } else {
                T[] temp = backingArray;
                backingArray = (T[]) new Comparable[backingArray.length * 2];
                for (int i = 1; i < temp.length; i++) {
                    backingArray[i] = temp[i];
                }
                backingArray[size + 1] = data;

            }
            size++;

            for (int i = size / 2; i > 0; i--) {
                heapify(i);
            }

        }
    }

    /**
     * Removes and returns the root of the heap.
     *
     * Do not shrink the backing array.
     *
     * Replace any unused spots in the array with null.
     *
     * @return the data that was removed
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {
        if (size == 0) {
            throw new NoSuchElementException("Cannot remove from an empty heap.");
        } else {
            T removed = backingArray[1];
            backingArray[1] = backingArray[size];
            backingArray[size] = null;
            size--;
            
            for (int i = size / 2; i > 0; i--) {
                heapify(i);
            }

            return removed;
        }
    }

    /**
     * Returns the maximum element in the heap.
     *
     * @return the maximum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T getMax() {
        if (size == 0) {
            throw new NoSuchElementException("Cannot get max from an empty heap.");
        } else {
            return backingArray[1];
        }
    }

    /**
     * Returns whether or not the heap is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the heap.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     */
    public void clear() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the backing array of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }


    
}
