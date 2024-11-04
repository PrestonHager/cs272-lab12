/**
 * The class for Binary Search Tree
 * @author Huiping Cao
 * @author Preston Hager
 */
public class BST {
	protected BSTNode root; //the tree root
	
	
	/**
	 * Get the left subtree of this tree
	 * @return the left subtree of this tree
	 */
	private BST getLeftSubtree(){
		return root.getLeft();
	}
	
	/**
	 * Get the right subtree of this tree
	 * @return the right subtree of this tree
	 */
	private BST getRightSubtree(){
		return root.getRight();
	}
	
	/**
	 * Print the tree using in-order traversal strategy
	 */
	public void inOrderPrt(){
		inOrderPrt(0);
	}
	
	/**
	 * private, recursive function
	 *     I slightly changed the method to print right subtree first
	 *     It is to make the display more easier to read
	 * 
	 * @param node
	 * @param indentation
	 * @param branch
	 */
	private void inOrderPrt(int indentation){
		if(root!=null){
			if(getRightSubtree()!=null) getRightSubtree().inOrderPrt(indentation+1);
			
			for(int i=0;i<indentation*4;i++) System.out.print(" ");
			
			System.out.println(root.getData());
			
			if(getLeftSubtree()!=null) getLeftSubtree().inOrderPrt(indentation+1);
		}
	}
	
	
	/**
	 * Debug function, print the tree for debugging purpose
	 */
	public String toString()
	{
		if(root!=null) return root.toString();
		else return null;
	}
	
	///////////////////////////////////////
	// Please add the functions required for your lab homework here.
  /*
  1. (20 points) Insert a new element e into the binary search tree. NO duplicate
  values are allowed in the tree. When e exists in the tree, return false;
  Otherwise, insert e to the tree and return true.
  public boolean insert (int e)
  */
  /**
   * Recursive case for inserting a new node into the tree
   *
   * @precondition The tree is a valid binary search tree
   * @param e The value to insert
   *
   * @return InsertResult enum of the result
   * @postcondition The tree is a valid binary search tree with the new data
   * inserted at the correct location with < on left and > on right
   */
   public boolean insert (int e) {
    // First find which node to insert the new node
    // Base case: tree is empty so the new node is the root
    if (root == null) {
      root = new BSTNode(e);
      return true;
    }
    // Base case: value already exists
    if (root.getData() == e) {
      return false;
    }
    // Recursive cases: value is > or < current data
    if (e < root.getData()) {
      // insert data into left if there is no node yet
      if (root.getLeft() == null) {
        root.setLeft(new BST());
        root.getLeft().root = new BSTNode(e);
        return true;
      }
      // otherwise, insert into the left subtree
      return root.getLeft().insert(e);
    }
    // otherwise insert into the right subtree (since >)
    if (root.getRight() == null) {
      root.setRight(new BST());
      root.getRight().root = new BSTNode(e);
      return true;
    }
    return root.getRight().insert(e);
  }

  /*
  2. (20 points) Remove a specified element from the binary search tree. When e
  exists in the tree and one instance is successfully removed, return true;
  Otherwise, return false.
  public boolean remove (int e)
  */
  /**
   * Remove a node from the tree given the data value
   *
   * @param e The value to remove
   *
   * @return True if the value was removed, false otherwise
   */
  public boolean remove (int e) {
    // Find the node to remove via binary search
    // Base case: tree is empty
    if (root == null) {
      return false;
    }
    // Base case: value is this root node
    if (root.getData() == e) {
      System.out.println("Removing " + e);
      System.out.println("Root: " + root);
      // Case 1: no children
      if (root.getLeft() == null && root.getRight() == null) {
        root = null;
        return true;
      }
      // actually remove the value by moving either the left/right node up to
      // the current root
      // Case 2: one child
      if (root.getLeft() == null) {
        root = root.getRight().root;
        return true;
      }
      if (root.getRight() == null) {
        root = root.getLeft().root;
        return true;
      }
      // Case 3: two children
      // Find the minimum value in the right subtree
      BSTNode min = root.getRight().root;
      while (min.getLeft().root.getLeft() != null) {
        min = min.getLeft().root;
      }
      // Replace the current root with the minimum value
      root.setData(min.getData());
      // Remove the minimum value from the right subtree
      min = null;
      return true;
    }
    // Recursive cases: value is > or < current data then pick left/right
    if (e < root.getData()) {
      if (root.getLeft() == null) {
        return false;
      }
      return root.getLeft().remove(e);
    }
    if (root.getRight() == null) {
      return false;
    }
    return root.getRight().remove(e);
  }

  /*
  3. (15 points) Design a recursive function to search whether an element exists
  in a binary search tree. If e exists, return the node that contains this
  element; Otherwise, return null.
  public BSTNode searchRecursion(int e)
  Please analyze its running time and get its complexity in Big-O. Analysis takes 5 points.
  */
  /**
   * Search for a value in the tree using recursion
   *
   * @precondition The root node is this BST
   * @param e The value to search for
   *
   * @return The node containing the value or null if not found
   *
   * @complexity O(n) where n is the depth of the tree
   */
  public BSTNode searchRecursion(int e) {
    // Base case: tree is empty
    if (root == null) {
      return null;
    }
    // Base case: value is this root node
    if (root.getData() == e) {
      return root;
    }
    // Recursive cases: value is > or < current data
    if (e < root.getData()) {
      if (root.getLeft() == null) {
        return null;
      }
      return root.getLeft().searchRecursion(e);
    }
    if (root.getRight() == null) {
      return null;
    }
    return root.getRight().searchRecursion(e);
  }

  /*
  4. (20 points) Design a non-recursive function to search whether an element
  exists in a binary search tree. If e exists, return the node that contains
  this element; Otherwise, return null.
  public BSTNode searchNonRecursion(int e)
  */
  /**
   * Search for a value in the tree using a non-recursive method
   *
   * @precondition The root node is this BST
   * @param e The value to search for
   *
   * @return The node containing the value or null if not found
   *
   * @complexity O(n) where n is the depth of the tree
   */
  public BSTNode searchNonRecursion(int e) {
    // Base case for empty tree
    if (root == null) {
      return null;
    }
    // Loop over tree nodes to find value under end of the tree (leaf node) is
    // found
    BSTNode current = root;
    while (current != null) {
      // Base case: value is found
      if (current.getData() == e) {
        return current;
      }
      // Recursive cases: value is > or < current data
      if (e < current.getData()) {
        current = current.getLeft().root;
      } else {
        current = current.getRight().root;
      }
    }
    // Value not found
    return null;
  }

  /*
  5. (20 points) Design a recursive function to add up all the elements in this
  binary search tree. Return the summation of all the elements. (Hint: you can
  use any type of traversal.)
  public int sum()
  */
  /**
   * Sum all the values in the tree
   *
   * @precondition The root node is this BST
   *
   * @return The sum of all the values in the tree
   */
  public int sum() {
    int runningSum = 0;
    if (root != null) {
      runningSum += root.getData();
      if (root.getLeft() != null) {
        runningSum += root.getLeft().sum();
      }
      if (root.getRight() != null) {
        runningSum += root.getRight().sum();
      }
    }
    return runningSum;
  }

  /**
   * Test cases for student implemented functions
   */
  private static void tests() {
  }
  ///////////////////////////////////////

	/**
	 * Test cases provided by the instructor
	 * @throws Exception
	 */
	private static void test1() throws Exception{
		BST tree = new BST();
		System.out.println("Initial tree:");
		tree.inOrderPrt(); //test inOrder traversal
		
		boolean rc = true;
		
		//test 1: insert method, and test 2 in-order traversal
		rc = tree.insert(12);
    System.out.println("\nInsert 12, inserted="+rc+", after adding 12:");
    tree.inOrderPrt();
		rc = tree.insert(6);
    System.out.println("\nInsert 6, inserted="+rc+", after adding 6:");
    tree.inOrderPrt();
		rc = tree.insert(19);
    System.out.println("\nInsert 19, inserted="+rc+", after adding 19:");
    tree.inOrderPrt();
		rc = tree.insert(4);
    System.out.println("\nInsert 4, inserted="+rc+", after adding 4:");
    tree.inOrderPrt();
		rc = tree.insert(8);
    System.out.println("\nInsert 8, inserted="+rc+", after adding 8:");
    tree.inOrderPrt();
		rc = tree.insert(16);
    System.out.println("\nInsert 16, inserted="+rc+", after adding 16:");
    tree.inOrderPrt();
		rc = tree.insert(8);
    System.out.println("\nInsert 8 (second), inserted="+rc+", after adding 8:");
    tree.inOrderPrt();
		rc = tree.insert(5);
    System.out.println("\nInsert 5, inserted="+rc+", after adding 5:");
    tree.inOrderPrt();
		rc = tree.insert(9);
    System.out.println("\nInsert 9, inserted="+rc+", after adding 9:");
    tree.inOrderPrt();
		rc = tree.insert(20);
    System.out.println("\nInsert 20, inserted="+rc+", after adding 20:");
    tree.inOrderPrt();
		
		System.out.println("Inorder traversal results:");
		tree.inOrderPrt(); 
		System.out.print("\n\n");
		
		//test 3: search method
		BSTNode node = tree.searchRecursion(6);
		System.out.println("searchRecursion 6, node="+node);
		node = tree.searchRecursion(22);
		System.out.println("searchRecursion 22, node="+node);
		node = tree.searchRecursion(8);
		System.out.println("searchRecursion 8, node="+node+"\n");
		
		node = tree.searchNonRecursion(6);
		System.out.println("searchNonRecursion 6, node="+node);
		node = tree.searchNonRecursion(22);
		System.out.println("searchNonRecursion 22, node="+node);
		node = tree.searchNonRecursion(8);
		System.out.println("searchNonRecursion 8, node="+node);
		
		//test 4: remove method
		rc = tree.remove(30); //test case 0: e does not exist
		System.out.println("\nRemove 30, rc="+rc);
		tree.inOrderPrt();
		
		rc = tree.remove(20); //test case 1: leaf
		System.out.println("\nRemove 20, rc="+rc);
		tree.inOrderPrt();
		
		rc = tree.remove(4); //test case 2: left is empty
		System.out.println("\nRemove 4, rc="+rc);
		tree.inOrderPrt();
		
		
		rc = tree.remove(19); //test case 3: right is empty
		System.out.println("\nRemove 19, rc="+rc);
		tree.inOrderPrt();
		
		rc = tree.remove(6); //test case 4: no subtree is empty
		System.out.println("\nRemove 6, rc="+rc);
		tree.inOrderPrt();
		
		rc = tree.remove(12); //more tests: remove root
		System.out.println("\nRemove 12, rc="+rc);
		tree.inOrderPrt();
		
		rc = tree.remove(8); //more tests
		System.out.println("\nRemove 8, rc="+rc);
		tree.inOrderPrt();
		
		rc = tree.remove(5); //more tests
		System.out.println("\nRemove 5, rc="+rc);
		tree.inOrderPrt();
		
		rc = tree.remove(8); //more tests
		System.out.println("\nRemove 8, rc="+rc);
		tree.inOrderPrt();
		rc = tree.remove(16); //more tests
		System.out.println("\nRemove 16, rc="+rc);
		tree.inOrderPrt();
		System.out.print("sum="+tree.sum()+"\n");
		
		System.out.println("\nAdding a series of numbers:");
		tree.insert(30);
		tree.insert(20);tree.insert(10);tree.insert(25);tree.insert(28);tree.insert(24);
		tree.insert(11);tree.insert(5);tree.insert(11);tree.insert(12);
		tree.insert(50);tree.insert(40);tree.insert(35);
		tree.inOrderPrt();
		System.out.print("sum="+tree.sum()+"\n");
		
		System.out.print("sum="+tree.sum()+"\n");
		System.out.print("\n\n");
		
		System.out.println("\nRemove 20 (removeNode case 4):");
		tree.remove(20);
		tree.inOrderPrt();
		System.out.print("sum="+tree.sum()+"\n");
		
		System.out.println("Inorder traversal results: ");
		tree.inOrderPrt();
		System.out.print("\n");
		System.out.print("sum="+tree.sum()+"\n");
	}
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		//You need to let your program pass the  test cases in this function
		test1(); 
		
		//You can add your own test cases here. 
    /*
     Note for grader: use `awk -f '===...' '{print $1}'` to get the results for
     the first test cases, pipe to file or straight `diff` to compare.
     For example:
     javac -d class *.java && java -cp class BST | awk -F '====================================' '{print $1}' | diff expected_out.txt -
    */
    System.out.println("====================================");
    tests();
	}
}
