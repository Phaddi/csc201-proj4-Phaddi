import java.util.Comparator;

/**
 * A binary search tree class with insertion, removal and lookup.
 * All  tree items must  be distinct according to the comparator.
 * If no comparator is supplied the "natural order" of three elements is used.
 * @param <E>
 */
public class BinarySearchTree<E> {

    private int size = 0;
    private int updates = 0;

    /**
     * Root of the tree
     */
    protected BinaryTreeNode<E> root = null;

    /**
     * Comparator used to order the items in the tree
     */
    private Comparator<E> comparator;

    /**
     * Constructs an empty BST that can only accept Comparables as items
     */
    public BinarySearchTree() {
        this(null);
    }

    /**
     * Constructs a BST that orders its items according ot the given comparator
     * @param c
     */
    public BinarySearchTree(Comparator<E> c) {
        comparator = c;
    }

    /**
     * Returns whether or not the tree contains an object with the given value
     * @param data
     * @return
     */
    public boolean contains(E data) {
        return nodeContaining(data) != null;
    }

    /**
     * Adds a single data item to the tree, can overwrite
     * @param data
     */
    public void add(E data) {
        if(root == null) {
            root = new LinkedBinaryTreeNode<E>(data);
            size++;
            updates++;
        }
        BinaryTreeNode<E> n = root;
        while(true) {
            int comparisonResult = compare(data, n.getData());
            if(comparisonResult == 0) { //if match, overwrite
                n.setData(data); return;
            } else if(comparisonResult < 0) {
                if(n.getLeft() == null) {
                    n.setLeft(new LinkedBinaryTreeNode<E>(data));
                    size++;
                    updates++;
                    return;
                }
                n = n.getLeft();
            } else { //comparisonResult > 0
                if (n.getRight() == null) {
                    n.setRight(new LinkedBinaryTreeNode<E>(data));
                    size++;
                    updates++;
                    return;
                }
                n = n.getRight();
            }
        }
    }

    /**
     * Removes a single data item from the tree, can overwrite
     * @param data
     */
    public void remove(E data) {
        //find the node containing the data
        BinaryTreeNode<E> node = nodeContaining(data);
        if(node == null) //No such object, do nothing
            return;
        else if(node.getLeft()!=null && node.getRight()!=null){
            // Node has two children, we cannot delete it.
            // Copy predecessor data and prepare to delete predecessor
            BinaryTreeNode<E> predecessor = predecessor(node);
            node.setData(predecessor.getData());
            node = predecessor; //now predecessor will be deleted
        }
        size--;
        updates++;
        //At this point node has zero or one child
        BinaryTreeNode<E> pullUp = (node.getLeft() == null)?node.getRight():node.getLeft();
        //if node is the root
        if(node == root)
            setRoot(pullUp);
        //if node is the left child of parent
        else if(node.getParent().getLeft() == node)
            node.getParent().setLeft(pullUp);
        //otherwise node is the right child of parent
        else
            node.getParent().setRight(pullUp);
    }

    public int getSize() {return size;}

    public int getUpdates() {return updates;}

    /**
     * Returns the root of the tree
     * @return
     */
    public BinaryTreeNode getRoot() {return root;}

    /**
     * Makes the given node the new root of the tree
     * @param node
     */
    protected void setRoot(BinaryTreeNode<E> node) {
        if(node != null) node.removeFromParent();
        root = node;
    }

    /**
     * Returns the rightmost node in the left subtree
     * @param node
     * @return
     */
    protected BinaryTreeNode<E> predecessor(BinaryTreeNode<E> node) {
        BinaryTreeNode<E> n = node.getLeft();
        if(n != null) {
            while (n.getRight() != null)
                n = n.getRight();
        }
        return n;
    }

    /**
     * Helper method that returns node containing data
     * This is used in both contains and remove
     * @param data
     * @return
     */
    protected BinaryTreeNode<E> nodeContaining(E data) {
        for(BinaryTreeNode<E> n = root; n != null;) {
            int comparisonResult = compare(data, n.getData());
            if(comparisonResult == 0)
                return n;
            else if(comparisonResult < 0)
                n = n.getLeft();
            else
                n = n.getRight();
        }
        return null;
    }

    /**
     * Put comparison code in one place (it's better design)
     * @param x
     * @param y
     * @return
     */
    protected int compare(E x, E y) {
        if(comparator == null)
            return ((Comparable<E>)x).compareTo(y);
        else
            return comparator.compare(x, y);
    }

    protected void rotateLeft(BinaryTreeNode<E> n) {
        //need a right child (R) to rotate left...
        if(n.getRight() == null) return;

        BinaryTreeNode<E> oldRight = n.getRight();

        //set right child of n (pivot) to left child of R
        n.setRight(oldRight.getLeft());

        //determine if rotated around BST root (or not)
        if(n.getParent() == null)
            root = oldRight;
        else if(n.getParent().getLeft() == n)
            n.getParent().setLeft(oldRight);
        else
            n.getParent().setRight(oldRight);

        //set left child of R to n (the pivot)
        oldRight.setLeft(n);
    }

    protected void rotateRight(BinaryTreeNode<E> n) {
        //need a left child (L) to rotate right...
        if(n.getLeft() == null) return;

        BinaryTreeNode<E> oldLeft = n.getLeft();

        //set left child of n (pivot) to right child of L
        n.setLeft(oldLeft.getRight());

        //determine if rotated around BST root (or not)
        if(n.getParent() == null)
            root = oldLeft;
        else if(n.getParent().getRight() == n)
            n.getParent().setRight(oldLeft);
        else
            n.getParent().setLeft(oldLeft);

        //set right child of L to n (the pivot)
        oldLeft.setRight(n);
    }

    /**
     * gets the height of the tree, if its a single node the height is 0
     * @param node
     * @return int
     */
    public int getHeight(BinaryTreeNode<E> node) {
        if(node == null)
            return -1;
        int left = getHeight(node.getLeft());
        int right = getHeight(node.getRight());
        if(left>right)
            return left+1;
        else
            return right+1;
    }

    public int getHeight() {
        return getHeight(root);
    }
} //end of BinarySearchTree

