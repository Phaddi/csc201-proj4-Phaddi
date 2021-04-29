import java.util.Comparator;

/**
 * A simple AVL tree class
 */
public class AVLTree<E> extends BinarySearchTree<E>{

    /**
     * Constructs an empty AVL that only accepts Comparables.
     */
    public AVLTree() {
        this(null);
    }

    /**
     * Constructs an AVL that orders its items according to the given comparator.
     * @param c
     */
    public AVLTree(Comparator<E> c) {
        super(c);
    }

    /**
     * Adds a single data item to the AVL tree, can overwrite
     * @param data
     */
    @Override
    public void add(E data) {
        //lets go ahead and add the new node, thanks BST
        super.add(data);
        //find the new node containing the data
        BinaryTreeNode<E> n = nodeContaining(data);
        //if there is a new node, then rebalance
        if(n != null) rebalance(n);
    }

    /**
     * Removes a data item from the AVL tree
     * @param data
     */
    @Override
    public void remove(E data) {
        //find the node containing the data
        BinaryTreeNode<E> n = nodeContaining(data);

        if(n != null) {
            //code here to determine the node n where the rebalance should start (done before removal)
            if(n.getLeft() == null || n.getRight() == null) {
                n = n.getParent();
            }
            else {
                n = predecessor(root);
                n = n.getParent();
            }
            //lets remove node containing the data
            super.remove(data);
            //rebalance starting at n
            rebalance(n);
        }
    }

    /**
     * will first check to see if there is an imbalance, if so will rebalance
     * @param node
     */
    protected void rebalance(BinaryTreeNode<E> node) {
        //lets check every node until we reach root
        while(node != null) {
            int heightDiff = getHeight(node.getLeft()) - getHeight(node.getRight()); //left rebalance
            System.out.println("Height Diff: " + heightDiff);
            if(heightDiff == 2) {
                heightDiff = getHeight(node.getLeft().getLeft()) - getHeight(node.getLeft().getRight());
                if(heightDiff > 0) //left - left
                rotateRight(node);
                else { //left - right
                    rotateLeft(node.getLeft());
                    rotateRight(node);
                }
            }
            else if(heightDiff == -2) { //right rebalance
                heightDiff = getHeight(node.getRight().getLeft()) - getHeight(node.getRight().getRight());
                if(heightDiff < 0) //right - right
                    rotateLeft(node);
                else { //right - left
                    rotateRight(node.getRight());
                    rotateLeft(node);
                }
            }
            node = node.getParent();
        }
    }
}
