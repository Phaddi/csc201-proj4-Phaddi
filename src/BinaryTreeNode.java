//visitor maybe?

/**
 * A simple interface for binary trees
 */
public interface BinaryTreeNode<E> {

    /**
     * Returns the data stored in this node
     * @return
     */
    E getData();

    /**
     * Modifies the data stored in this node
     * @param data
     */
    void setData(E data);

    /**
     * Returns the parent of the node, or null if node is root
     * @return
     */
    BinaryTreeNode<E> getParent();

    /**
     * Returns the left child of this node, or null if none
     * @return
     */
    BinaryTreeNode<E> getLeft();

    /**
     * Returns the right child of this node, or null if none
     * @return
     */
    BinaryTreeNode<E> getRight();

    /**
     * Removes the child fromm its current parent and inserts it as the left child of this node.
     * If this node already has a left child it is removed.
     *
     * @exception IllegalArgumentException if the child is an ancestor that would make a cycle)
     * @param child
     */
    void setLeft(BinaryTreeNode<E> child);

    /**
     * Removes the child from its current parent and inserts it at the child child of node,
     * remove existing left child
     * @Exception IllegalArgumentException if child ancestor
     * @param child
     */
    void setRight(BinaryTreeNode<E> child);

    /**
     * Removes this node and all its descendants from tree
     */
    void removeFromParent();

    /**
     * Visits the nodes in this tree in preorder
     * @param visitor
     */
    void traversePreorder(Visitor<E> visitor);

    /**
     * Visits the nodes in this tree in postorder
     * @param visitor
     */
    void traversePostorder(Visitor<E> visitor);

    /**
     * Visits the nodes in this tree inorder
     * @param
     */
    void traverseInorder();

    //recursive
    //int getHeight();


    public interface Visitor<E>{
        void visit(BinaryTreeNode<E> node);
    }
}
