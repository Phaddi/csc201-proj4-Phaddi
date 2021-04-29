import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;
import java.io.FileWriter;

// compile: javac Inventory.java
// run: java Inventory invent.dat
// both: javac Inventory.java; java Inventory invent.dat

public class Inventory {
    public static void main(String[] args) {
        if(!argsOK(args))
            System.exit(1);

        String file = args[0];
        //AVLTree<String> invenAVL = new AVLTree<String>();
        BinarySearchTree<String> invenBST = new BinarySearchTree<String>();
        AVLTree<String> invenAVL = new AVLTree<String>();

        //System.out.println("Test");

        try {
            Scanner input = new Scanner(new File(file));
            while (input.hasNext()) {
                String line = input.nextLine();
                String[] token = line.split("[ ]+");
                char action = token[0].charAt(0);
                if (action == 'a' || action == 'A') {
                    invenBST.add(token[1]);
                    invenAVL.add(token[1]);
                }
                else if (action == 'd' || action == 'D') {
                    invenBST.remove(token[1]);
                    invenAVL.remove(token[1]);
                }
            }
            //outputs
            System.out.println("Number of inventory updates: " + invenBST.getUpdates());
            System.out.println();
            System.out.print("BST tree size: " + invenBST.getSize());
            System.out.println(", height: " + invenBST.getHeight());
            invenBST.getRoot().traverseInorder();
            System.out.println();


            //AVL output
            System.out.print("AVL tree size: " + invenAVL.getSize());
            System.out.println(", height: " + invenAVL.getHeight());
            invenAVL.getRoot().traverseInorder();
            System.out.println();
            input.close();

        }
        catch (FileNotFoundException e) {
            System.out.println("Error in opening inventory file");
            System.exit(1);
        }

        /*
        if(args[0].equals("A")||args[0].equals("a")) {
            //add
        }

        if(args[0].equals("D")||args[0].equals("d")) {
            //delete
        }
        */
    }

    /**
     * ensures we have at least one argument
     * @param args
     * @return
     */
    private static boolean argsOK(String[] args) {
        if(args.length < 1) {
            System.out.println("Usage: java Inventory inventoryFile ");
            System.out.println(" inventoryFile file containing inventory updates ");
            return false;
        }
        return true;
    }

}
