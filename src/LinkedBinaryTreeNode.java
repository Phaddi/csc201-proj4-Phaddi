import java.lang.*;

/**
 * A simple class for binary tree nodes.
 *
 * LinkedBinaryTreeNode objects may not play well with others :
 * if one tries to mix them up with different types of binary tree nodes, exception may be thrown.
 */
public class LinkedBinaryTreeNode<E> implements BinaryTreeNode<E> {

    protected E data;
    protected LinkedBinaryTreeNode<E> parent;
    protected LinkedBinaryTreeNode<E> left;
    protected LinkedBinaryTreeNode<E> right;

    /**
     * Constructs a node as root of its own one-element tree
     * @param data
     */
    public LinkedBinaryTreeNode(E data) {
        this.data = data;
    }

    /**
     * Returns the data stored in this node
     * @return
     */
    public E getData() {return data;}

    /**
     * Modifies the data stored in this node
     * @param data
     */
    public void setData(E data) {this.data = data;}

    /**
     * Returns the parent of this node, or null if root;
     * @return
     */
    public BinaryTreeNode<E> getParent() {return parent;}

    /**
     * Returns the left child of this node, or null if none
     * @return
     */
    public BinaryTreeNode<E> getLeft() {return left;}

    /**
     * Returns the right child of this node, or null if none
     * @return
     */
    public BinaryTreeNode<E> getRight() {return right;}

    /**
     * Removes the child from its current parent and inserts it as the left child of this node.
     * If this node already has a left child then it is removed.
     *
     * @exception IllegalArgumentException if the child is an ancestor of this node
     * @param child
     */
    public void setLeft(BinaryTreeNode<E> child) {
        //Ensure the child is not an ancestor
        for(LinkedBinaryTreeNode<E> n = this; n!= null; n = n.parent) {
            if(n == child)
                throw new IllegalArgumentException();
        }
        //Ensure child is instance of LinkedBinaryTreeNode
        LinkedBinaryTreeNode<E> childNode = (LinkedBinaryTreeNode<E>) child;
        //Break old links, then reconnect properly
        if(this.left != null)
            left.parent = null;
        if(childNode != null) {
            childNode.removeFromParent();
            childNode.parent = this;
        }
        this.left = childNode;
    }

    /**
     * Removes the child from its current parent and inserts it as the right child of this node.
     * If this node already has a right child then it is removed.
     *
     * @exception IllegalArgumentException if the child is an ancestor of this node
     * @param child
     */
    public void setRight(BinaryTreeNode<E> child) {
        //Ensure the child is not an ancestor
        for(LinkedBinaryTreeNode<E> n = this; n!= null; n = n.parent) {
            if(n == child)
                throw new IllegalArgumentException();
        }
        //Ensure child is instance of LinkedBinaryTreeNode
        LinkedBinaryTreeNode<E> childNode = (LinkedBinaryTreeNode<E>) child;
        //Break old links, then reconnect properly
        if(this.right != null)
            right.parent = null;
        if(childNode != null) {
            childNode.removeFromParent();
            childNode.parent = this;
        }
        this.right = childNode;
    }

    /**
     * Removes this node, and all its descendants, from whatever tree it's in.
     * Nothing if node is a root.
     */
    public void removeFromParent(){
        if(parent != null) {
            if(parent.left == this)
                parent.left = null;
            else if(parent.right == this)
                parent.right = null;
            this.parent = null;
        }
    }

    /**
     * Visits the nodes in this tree in preorder
     * @param visitor
     */
    public void traversePreorder(BinaryTreeNode.Visitor<E> visitor) {
        visitor.visit(this);
        if(left != null) left.traversePreorder(visitor);
        if(right != null) right.traversePreorder(visitor);
    }

    /**
     * Visits the nodes in this tree in postorder
     * @param visitor
     */
    public void traversePostorder(BinaryTreeNode.Visitor<E> visitor) {
        if(left != null) left.traversePostorder(visitor);
        if(right != null) right.traversePostorder(visitor);
        visitor.visit(this);
    }

    /**
     * The most important traversal: we're printing the tree when we call this
     */
    public void traverseInorder() {
        System.out.print("(");
        if(left != null) left.traverseInorder();
        System.out.print(this);
        if(right != null) right.traverseInorder();
        System.out.print(")");
    }

    /*
    public int getHeight() {
        if(getLeft() == null && getRight() == null) {
            return 0;
        }
        else {
            int left = this.left.getHeight();
            int right = this.right.getHeight();
            if(left>right)
                return left+1;
            else
                return right+1;

            if(getLeft() != null)
                left = this.left.getHeight();
            if(getRight() != null)
                right = this.right.getHeight();

            //return 1 + Math.max(left, right);
        }

    }
    */

    @Override
    public String toString(){
        return data.toString();
    }
}
