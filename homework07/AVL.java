import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ArrayList;

/**
 * Your implementation of an AVL Tree.
 *
 * @author Panya Bhinder
 * @userid pbhinder3
 * @GTID 903690652
 * @version 1.0
 */
public class AVL<T extends Comparable<? super T>> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private AVLNode<T> root;
    private int size;

    /**
     * A no-argument constructor that should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Initializes the AVL tree with the data in the Collection. The data
     * should be added in the same order it appears in the Collection.
     *
     * @throws IllegalArgumentException if data or any element in data is null
     * @param data the data to add to the tree
     */
    public AVL(Collection<T> data) {
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
     * Adds the data to the AVL. Start by adding it as a leaf like in a regular
     * BST and then rotate the tree as needed.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors going up the tree,
     * rebalancing if necessary.
     *
     * @throws java.lang.IllegalArgumentException if the data is null
     * @param data the data to be added
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null data.");
        } else {
            root = addHelper(root, data);
        }
    }

    /**
     * Recursive helper method for add.
     * @param curr AVLNode to perform add on.
     * @param data Data to add to the tree.
     * @return The tree with the element added.
     */
    private AVLNode<T> addHelper(AVLNode<T> curr, T data) {
        if (curr == null) {
            size++;
            curr = new AVLNode<T>(data);
            return curr;
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(addHelper(curr.getLeft(), data));
            updateHBF(curr);
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(addHelper(curr.getRight(), data));
            updateHBF(curr);
        } else if (data.compareTo(curr.getData()) == 0) {
            return curr;
        }
        return rebalance(curr);
    }

    /**
     * Private helper method to update height and balance factor of a node.
     * @param node Node to update parameters on.
     */
    private void updateHBF(AVLNode<T> node) {
        if (node.getLeft() == null && node.getRight() == null) {
            node.setBalanceFactor(0);
            node.setHeight(0);
        } else if (node.getLeft() == null) {
            node.setBalanceFactor(-1 - node.getRight().getHeight());
            node.setHeight(1 + Math.max(-1, node.getRight().getHeight()));
        } else if (node.getRight() == null) {
            node.setBalanceFactor(node.getLeft().getHeight() + 1);
            node.setHeight(1 + Math.max(node.getLeft().getHeight(), -1));
        } else {
            node.setBalanceFactor(node.getLeft().getHeight() - node.getRight().getHeight());
            node.setHeight(1 + Math.max(node.getLeft().getHeight(), node.getRight().getHeight()));
        }
    }

    /**
     * Private helper method to perform a left rotation.
     * @param curr Node to perform rotation on.
     * @return Tree with rotation performed.
     */
    private AVLNode<T> rotateLeft(AVLNode<T> curr) {
        AVLNode<T> right = curr.getRight();
        curr.setRight(right.getLeft());
        right.setLeft(curr);
        updateHBF(curr);
        updateHBF(right);
        return right;
    }

    /**
     * Helper method to perform right rotation.
     * @param curr Node to perform rotation on.
     * @return Node with rotation performed.
     */
    private AVLNode<T> rotateRight(AVLNode<T> curr) {
        AVLNode<T> left = curr.getLeft();
        curr.setLeft(left.getRight());
        left.setRight(curr);
        updateHBF(curr);
        updateHBF(left);
        return left;
    }

    /**
     * Helper method to rebalance nodes by performing rotations based on the balance factor.
     * @param curr Node to reabalance.
     * @return Rebalanced node with rotations performed on it.
     */
    private AVLNode<T> rebalance(AVLNode<T> curr) {
        if (curr == null) {
            return null;
        }
        updateHBF(curr);
        if (curr.getBalanceFactor() > 1) {
            if (curr.getLeft().getBalanceFactor() < 0) {
                curr.setLeft(rotateLeft(curr.getLeft()));
                curr = rotateRight(curr);
            } else {
                curr = rotateRight(curr);
            }
        } else if (curr.getBalanceFactor() < -1) {
            if (curr.getRight().getBalanceFactor() > 0) {
                curr.setRight(rotateRight(curr.getRight()));
                curr = rotateLeft(curr);
            } else {
                curr = rotateLeft(curr);
            }
        }
        return curr;
    }


    /**
     * Removes the data from the tree. There are 3 cases to consider:
     *
     * 1: the data is a leaf. In this case, simply remove it.
     * 2: the data has one child. In this case, simply replace it with its
     * child.
     * 3: the data has 2 children. Use the successor to replace the data,
     * not the predecessor. As a reminder, rotations can occur after removing
     * the successor node.
     *
     * Remember to recalculate heights going up the tree, rebalancing if
     * necessary.
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to remove from the tree.
     * @return the data removed from the tree. Do not return the same data
     * that was passed in.  Return the data that was stored in the tree.
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot remove null data.");
        } else {
            AVLNode<T> removed = new AVLNode<T>(null);
            root = removeHelper(root, data, removed);
            return removed.getData();
        }
    }
    /**
     * Recursive remove helper method.
     * @param curr AVL node to remove data from.
     * @param data Data to search for and remove from the tree.
     * @param removed The removed data to return in the remove method above.
     * @return the node that represents the tree after the data was removed
     */
    private AVLNode<T> removeHelper(AVLNode<T> curr, T data, AVLNode<T> removed) {
        if (curr == null) {
            throw new NoSuchElementException("Data is not in tree.");
        } else if (curr.getData().compareTo(data) > 0) {
            curr.setLeft(removeHelper(curr.getLeft(), data, removed));
        } else if (curr.getData().compareTo(data) < 0) {
            curr.setRight(removeHelper(curr.getRight(), data, removed));
        } else if (curr.getData().compareTo(data) == 0) {
            removed.setData(curr.getData());
            size--;
            if (curr.getLeft() == null && curr.getRight() == null) {
                return null;
            } else if (curr.getLeft() == null && curr.getRight() != null) {
                updateHBF(curr);
                return rebalance(curr.getRight());
            } else if (curr.getRight() == null && curr.getLeft() != null) {
                updateHBF(curr);
                return rebalance(curr.getLeft());
            } else {
                AVLNode<T> max = new AVLNode<T>(null);
                curr.setRight(removeSuccessor(curr.getRight(), max));
                curr.setData(max.getData());
            }
        } else {
            throw new NoSuchElementException("Data is not in tree.");
        }
        updateHBF(curr);
        return rebalance(curr);
    }

    /**
     * Method to find and remove the successor node.
     * @param curr Node to traverse in order to find the successor.
     * @param max Node to save the value of the removed successor.
     * @return Value of the successor node.
     */
    private AVLNode<T> removeSuccessor(AVLNode<T> curr, AVLNode<T> max) {
        if (curr.getLeft() == null) {
            T removed = curr.getData();
            max.setData(removed);
            return rebalance(curr.getRight());
        } else {
            curr.setLeft(removeSuccessor(curr.getLeft(), max));
        }
        return rebalance(curr);
    }


    /**
     * Returns the data in the tree matching the parameter passed in (think
     * carefully: should you use value equality or reference equality?).
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to search for in the tree.
     * @return the data in the tree equal to the parameter. Do not return the
     * same data that was passed in.  Return the data that was stored in the
     * tree.
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
    private T getHelper(AVLNode<T> curr, T data) {
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
     * Returns whether or not data equivalent to the given parameter is
     * contained within the tree. The same type of equality should be used as
     * in the get method.
     *
     * @throws IllegalArgumentException if the data is null
     * @param data the data to search for in the tree.
     * @return whether or not the parameter is contained within the tree.
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
    private boolean containsHelper(AVLNode<T> curr, T data) {
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
     * The predecessor is the largest node that is smaller than the current data.
     *
     * This method should retrieve (but not remove) the predecessor of the data
     * passed in. There are 2 cases to consider:
     * 1: The left subtree is non-empty. In this case, the predecessor is the
     * rightmost node of the left subtree.
     * 2: The left subtree is empty. In this case, the predecessor is the lowest
     * ancestor of the node containing data whose right child is also
     * an ancestor of data.
     *
     * This should NOT be used in the remove method.
     *
     * Ex:
     * Given the following AVL composed of Integers
     *     76
     *   /    \
     * 34      90
     *  \    /
     *  40  81
     * predecessor(76) should return 40
     * predecessor(81) should return 76
     *
     * @param data the data to find the predecessor of
     * @return the predecessor of data. If there is no smaller data than the
     * one given, return null.
     * @throws java.lang.IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T predecessor(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot retrieve predecessor of null data.");
        } else if (!contains(data)) {
            throw new NoSuchElementException("Data is not in tree.");
        } else {
            return predHelper(root, data, null);
        }
    }

    /**
     * Recursive helper method to find predecessor.
     * @param curr Node to traverse through.
     * @param data Data to find the predecessor of.
     * @param pred Stored value of the predecessor while traversing.
     * @return Value of the predecessor node.
     */
    private T predHelper(AVLNode<T> curr, T data, T pred) {
        if (data.compareTo(curr.getData()) > 0 && curr.getRight() != null) {
            return predHelper(curr.getRight(), data, curr.getData());
        } else if (data.compareTo(curr.getData()) < 0 && curr.getLeft() != null) {
            return predHelper(curr.getLeft(), data, pred);
        } else if (data.compareTo(curr.getData()) == 0) {
            if (curr.getLeft() != null) {
                T predecessor = max(curr.getLeft());
                return predecessor;
            } else {
                return pred;
            }
        }
        return pred;
    }

    /**
     * Recursive helper method to find the max of the left subtree.
     * @param curr Node to find max of.
     * @return Value of the max of the left subtree.
     */
    private T max(AVLNode<T> curr) {
        if (curr.getRight() == null) {
            return curr.getData();
        } else {
            return max(curr.getRight());
        }
    }

    /**
     * Finds and retrieves the k-smallest elements from the AVL in sorted order,
     * least to greatest.
     *
     * In most cases, this method will not need to traverse the entire tree to
     * function properly, so you should only traverse the branches of the tree
     * necessary to get the data and only do so once. Failure to do so will
     * result in an efficiency penalty.
     *
     * Ex:
     * Given the following AVL composed of Integers
     *              50
     *            /    \
     *         25      75
     *        /  \     / \
     *      13   37  70  80
     *    /  \    \      \
     *   12  15    40    85
     *  /
     * 10
     * kSmallest(0) should return the list []
     * kSmallest(5) should return the list [10, 12, 13, 15, 25].
     * kSmallest(3) should return the list [10, 12, 13].
     *
     * @param k the number of smallest elements to return
     * @return sorted list consisting of the k smallest elements
     * @throws java.lang.IllegalArgumentException if k < 0 or k > n, the number
     *                                            of data in the AVL
     */
    public List<T> kSmallest(int k) {
        if (k < 0 || k > size) {
            throw new
                IllegalArgumentException("Number of elements to return can't be negative or greater than size.");
        } else {
            List<T> elements = new ArrayList<T>();
            return smallestHelper(root, elements, k);
        }
    }

    /**
     * Recursive helper method to find the smallest k values.
     * @param curr Node to traverse through.
     * @param list List to add values to.
     * @param k Number of elements to add to the list.
     * @return list of k smallest values.
     */
    private List<T> smallestHelper(AVLNode<T> curr, List<T> list, int k) {
        if (curr == null) {
            return list;
        } else {
            smallestHelper(curr.getLeft(), list, k);
            if (list.size() < k) {
                list.add(curr.getData());
                smallestHelper(curr.getRight(), list, k);
            }
            return list;
        }
    }

    /**
     * Clears the tree.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * Since this is an AVL, this method does not need to traverse the tree
     * and should be O(1)
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (root == null) {
            return -1;
        } else {
            return root.getHeight();
        }
    }

    /**
     * Returns the size of the AVL tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return number of items in the AVL tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD
        return size;
    }

    /**
     * Returns the root of the AVL tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the AVL tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }



}