import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Your implementation of a BST.
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
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data");
        } else {
            size = 0;
            for (T element : data) {
                add(element);
            }
        }
    }

    /**
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data");
        } else {
            root = addHelper(data, root);
        }
    }
    /**
     * Recrusive add helper method.
     * @param data Data to be added.
     * @param curr BST node to recursively traverse.
     * @return BST Node that represents the tree with the added data.
     */
    private BSTNode<T> addHelper(T data, BSTNode<T> curr) {
        if (curr == null) {
            size++;
            return new BSTNode<T>(data);
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(addHelper(data, curr.getLeft()));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(addHelper(data, curr.getRight()));
        } else if (data.compareTo(curr.getData()) == 0) {
            return curr;
        }
        return curr;
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot remove null data.");
        } else {
            BSTNode<T> removed = new BSTNode<T>(null);
            root = removeHelper(root, data, removed);
            size--;
            return removed.getData();
        }
    }
    /**
     * Recursive remove helper method.
     * @param curr BST node to remove data from.
     * @param data Data to search for and remove from the tree.
     * @param removed The removed data to return in the remove method above.
     * @return the node that represents the tree after the data was removed
     */
    private BSTNode<T> removeHelper(BSTNode<T> curr, T data, BSTNode<T> removed) {
        if (curr == null) {
            throw new NoSuchElementException("Data is not in tree.");
        } else if (curr.getData().compareTo(data) > 0) {
            curr.setLeft(removeHelper(curr.getLeft(), data, removed));
        } else if (curr.getData().compareTo(data) < 0) {
            curr.setRight(removeHelper(curr.getRight(), data, removed));
        } else if (curr.getData().compareTo(data) == 0) {
            removed.setData(curr.getData());
            if (curr.getLeft() == null) {
                return curr.getRight();
            } else if (curr.getRight() == null) {
                return curr.getLeft();
            } else {
                curr.setData(max(curr.getRight()));
                BSTNode<T> temp  = new BSTNode<T>(null);
                curr.setRight(removeHelper(curr.getRight(), curr.getData(), temp));
            }
        } else {
            throw new NoSuchElementException("Data is not in tree.");
        }
        return curr;
    }
    /**
     * Method to find the successor node.
     * @param curr Node to traverse in order to find the successor.
     * @return Value of the successor node.
     */
    private T max(BSTNode<T> curr) {
        if (curr.getLeft() == null) {
            return curr.getData();
        } else {
            return max(curr.getLeft());
        }
    }

    /**
     * Returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot get null data.");
        } else {
            return getHelper(root, data);
        }
    }
    /**
     * Recursive helper method to get an element in the tree
     * @param curr The tree to search through
     * @param data The data to search for
     * @return The data in the tree that is equal to the parameter data
     */
    private T getHelper(BSTNode<T> curr, T data) {
        if (curr == null) {
            throw new NoSuchElementException("Tree is empty.");
        } else if (curr.getData().compareTo(data) == 0) {
            return curr.getData();
        } else if (curr.getData().compareTo(data) > 0 && curr.getLeft() != null) {
            return getHelper(curr.getLeft(), data);
        } else if (curr.getData().compareTo(data) < 0 && curr.getRight() != null) {
            return getHelper(curr.getRight(), data);
        } else {
            throw new NoSuchElementException("Data not in tree.");
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * This must be done recursively.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Tree cannot contain null data");
        } else {
            return containsHelper(root, data);
        }
    }
    /**
     * Recursive helper method for contains
     * @param curr Tree to search for the data in
     * @param data the data to search for
     * @return boolean true if the data is in the tree, false otherwise
     */
    private boolean containsHelper(BSTNode<T> curr, T data) {
        if (curr == null) {
            return false;
        } else if (curr.getData().compareTo(data) == 0) {
            return true;
        } else if (curr.getData().compareTo(data) > 0 && curr.getLeft() != null) {
            return containsHelper(curr.getLeft(), data);
        } else if (curr.getData().compareTo(data) < 0 && curr.getRight() != null) {
            return containsHelper(curr.getRight(), data);
        } else {
            return false;
        }
    }

    /**
     * Generate a pre-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the pre-order traversal of the tree
     */
    public List<T> preorder() {
        List<T> nodes = new ArrayList<T>();
        return preorderHelper(root, nodes);
    }
    /**
     * Recursive helper method for a pre-order traversal of the tree
     * @param curr the tree to traverse through
     * @param elements the list to add the data to
     * @return a list of elements in a pre-order traversal
     */
    private List<T> preorderHelper(BSTNode<T> curr, List<T> elements) {
        if (curr == null) {
            return elements;
        } else {
            elements.add(curr.getData());
            preorderHelper(curr.getLeft(), elements);
            preorderHelper(curr.getRight(), elements);
            return elements;
        }
    }

    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the in-order traversal of the tree
     */
    public List<T> inorder() {
        List<T> nodes = new ArrayList<T>();
        return inorderHelper(root, nodes);
    }
    /**
     * Recursive helper method for an in-order traversal of the tree
     * @param curr tree to traverse through
     * @param elements arraylist to add the elements into
     * @return an arraylist of elements in an in-order traversal
     */
    private List<T> inorderHelper(BSTNode<T> curr, List<T> elements) {
        if (curr == null) {
            return elements;
        } else {
            inorderHelper(curr.getLeft(), elements);
            elements.add(curr.getData());
            inorderHelper(curr.getRight(), elements);
            return elements;
        }
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the post-order traversal of the tree
     */
    public List<T> postorder() {
        List<T> nodes = new ArrayList<T>();
        return postorderHelper(root, nodes);
    }
    /**
     * Recursive helper method for a post-order traversal of the tree
     * @param curr the tree to traverse through
     * @param elements an arraylist to add elements to
     * @return an arraylist of elements in an post-order traversal
     */
    private List<T> postorderHelper(BSTNode<T> curr, List<T> elements) {
        if (curr == null) {
            return elements;
        } else {
            postorderHelper(curr.getLeft(), elements);
            postorderHelper(curr.getRight(), elements);
            elements.add(curr.getData());
            return elements;
        }
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level-order traversal of the tree
     */
    public List<T> levelorder() {
        Queue<BSTNode<T>> q = new LinkedList<BSTNode<T>>();
        List<T> elements = new ArrayList<T>();
        q.add(root);
        while (!q.isEmpty()) {
            BSTNode<T> node = q.remove();
            elements.add(node.getData());
            if (node.getLeft() != null) {
                q.add(node.getLeft());
            }
            if (node.getRight() != null) {
                q.add(node.getRight());
            }
        }
        return elements;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return heightHelper(root);
    }
    /**
     * Recursive helper method to find the height of the root
     * @param curr the tree to return the height of
     * @return an int representing the height of the root, -1 if the tree is empty
     */
    private int heightHelper(BSTNode<T> curr) {
        if (curr == null) {
            return -1;
        } else {
            int left = heightHelper(curr.getLeft());
            int right = heightHelper(curr.getRight());

            if (left > right) {
                return left + 1;
            } else {
                return right + 1;
            }
        }
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Finds the path between two elements in the tree, specifically the path
     * from data1 to data2, inclusive of both.
     *
     * To do this, you must first find the deepest common ancestor of both data
     * and add it to the list. Then traverse to data1 while adding its ancestors
     * to the front of the list. Finally, traverse to data2 while adding its
     * ancestors to the back of the list. Please note that there is no
     * relationship between the data parameters in that they may not belong
     * to the same branch. You will most likely have to split off and
     * traverse the tree for each piece of data.
     * *
     * You may only use 1 list instance to complete this method. Think about
     * what type of list to use since you will have to add to the front and
     * back of the list.
     *
     * This method only need to traverse to the deepest common ancestor once.
     * From that node, go to each data in one traversal each. Failure to do
     * so will result in a penalty.
     *
     * If both data1 and data2 are equal and in the tree, the list should be
     * of size 1 and contain the element from the tree equal to data1 and data2.
     *
     * Ex:
     * Given the following BST composed of Integers
     *              50
     *          /        \
     *        25         75
     *      /    \
     *     12    37
     *    /  \    \
     *   11   15   40
     *  /
     * 10
     * findPathBetween(10, 40) should return the list [10, 11, 12, 25, 37, 40]
     * findPathBetween(50, 37) should return the list [50, 25, 37]
     * findPathBetween(75, 75) should return the list [75]
     *
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     *
     * @param data1 the data to start the path from
     * @param data2 the data to end the path on
     * @return the unique path between the two elements
     * @throws java.lang.IllegalArgumentException if either data1 or data2 is
     *                                            null
     * @throws java.util.NoSuchElementException   if data1 or data2 is not in
     *                                            the tree
     */
    public List<T> findPathBetween(T data1, T data2) {
        if (data1 == null || data2 == null) {
            throw new IllegalArgumentException("Data entered cannot be null.");
        } else {
            return findPathHelper(root, data1, data2);
        }
    }

    /**
     * Recursive helper method to find the path between two nodes
     * @param curr the tree to recurse through
     * @param d1 the data to start the path on
     * @param d2 the data to end the path on
     * @return an arraylist of values that represents the path between the two elements
     */
    private List<T> findPathHelper(BSTNode<T> curr, T d1, T d2) {
        List<T> path = new ArrayList<T>();
        if (curr == null) {
            throw new NoSuchElementException("Data is not in tree");
        } else {
            BSTNode<T> ancestor = commonAncestor(root, d1, d2);
            if (d1.equals(d2)) {
                path.add(d1);
                return path;
            } else if (d1.compareTo(d2) < 0) {
                addLess(d1, path, ancestor);
                addMore(d2, path, ancestor.getRight());
                return path;
            } else {
                addLess(d1, path, ancestor);
                if (!d2.equals(ancestor.getData())) {
                    addMore(d2, path, ancestor.getLeft());
                }
                return path;
            }
        }
    }
    /**
     * Adds values from the smaller data value to the deepest common ancestor.
     * @param lesser The smaller data value
     * @param path The list to add the values to
     * @param curr the tree to recurse through
     */
    private void addLess(T lesser, List<T> path, BSTNode<T> curr) {
        if (curr == null) {
            throw new NoSuchElementException("Data is not in tree"); 
        } else if (curr.getData().compareTo(lesser) == 0) {
            path.add(curr.getData());
        } else if (curr.getData().compareTo(lesser) > 0) {
            addLess(lesser, path, curr.getLeft());
            path.add(curr.getData());
        } else {
            addLess(lesser, path, curr.getRight());
            path.add(curr.getData());
        }


    }
    /**
     * Adds values from the deepest common ancestor to the larger element
     * @param greater the larger data element between data1 and data2
     * @param path the arraylist to add the values to
     * @param curr the tree to recurse through
     */
    private void addMore(T greater, List<T> path, BSTNode<T> curr) {
        if (curr == null) {
            throw new NoSuchElementException("Data is not in tree");
        } else if (curr.getData().compareTo(greater) == 0) {
            path.add(curr.getData());
        } else if (curr.getData().compareTo(greater) > 0) {
            path.add(curr.getData());
            addMore(greater, path, curr.getLeft());
        } else {
            path.add(curr.getData());
            addMore(greater, path, curr.getRight());
        }

    }
    /**
     * Method to find the deepest common ancestor between two elements
     * @param curr the tree to recurse through
     * @param d1 the first element to find the deepest common ancestor of
     * @param d2 the second element to find the deepest common ancestor of
     * @return the node that is the deepest common ancestor
     */
    private BSTNode<T> commonAncestor(BSTNode<T> curr, T d1, T d2) {
        if (curr == null) {
            return null;
        } else if (curr.getData().compareTo(d1) > 0 && curr.getData().compareTo(d2) > 0) {
            return commonAncestor(curr.getLeft(), d1, d2);
        } else if (curr.getData().compareTo(d1) < 0 && curr.getData().compareTo(d2) < 0) {
            return commonAncestor(curr.getRight(), d1, d2);
        } else {
            return curr;
        }
    }


    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
