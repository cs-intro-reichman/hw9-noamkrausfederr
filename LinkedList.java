/**
 * Represents a list of Nodes. 
 */
public class LinkedList {
	
	private Node first; // pointer to the first element of this list
	private Node last;  // pointer to the last element of this list
	private int size;   // number of elements in this list
	
	/**
	 * Constructs a new list.
	 */ 
	public LinkedList () {
		first = null;
		last = first;
		size = 0;
	}
	
	/**
	 * Gets the first node of the list
	 * @return The first node of the list.
	 */		
	public Node getFirst() {
		return this.first;
	}

	/**
	 * Gets the last node of the list
	 * @return The last node of the list.
	 */		
	public Node getLast() {
		return this.last;
	}
	
	/**
	 * Gets the current size of the list
	 * @return The size of the list.
	 */		
	public int getSize() {
		return this.size;
	}
	
	/**
	 * Gets the node located at the given index in this list. 
	 * 
	 * @param index
	 *        the index of the node to retrieve, between 0 and size
	 * @throws IllegalArgumentException
	 *         if index is negative or greater than the list's size
	 * @return the node at the given index
	 */		
	public Node getNode(int index) {
		if (index < 0 || index > size) {
			throw new IllegalArgumentException(
					"index must be between 0 and size");
		}
		Node current = first;
		int n = 0;
		if (index == 0) {
			return current;
		}
		while (n < index) {
			current = current.next;
			n++;
		}
		return current;
	}
	
	/**
	 * Creates a new Node object that points to the given memory block, 
	 * and inserts the node at the given index in this list.
	 * <p>
	 * If the given index is 0, the new node becomes the first node in this list.
	 * <p>
	 * If the given index equals the list's size, the new node becomes the last 
	 * node in this list.
     * <p>
	 * The method implementation is optimized, as follows: if the given 
	 * index is either 0 or the list's size, the addition time is O(1). 
	 * 
	 * @param block
	 *        the memory block to be inserted into the list
	 * @param index
	 *        the index before which the memory block should be inserted
	 * @throws IllegalArgumentException
	 *         if index is negative or greater than the list's size
	 */
	public void add(int index, MemoryBlock block) {
		if (index < 0 || index > size) {
			throw new IllegalArgumentException(
					"index must be between 0 and size");
		}
		Node newNode = new Node(block);
		// if the list is empty, i make the first node the newnode
		if (size == 0) {
			first = newNode;
			last = newNode;
			size++;
		} 
		else if (index == 0) {
			addFirst(block);
		}
		// if the index equals the size of the list, i point the last node
		// to the newnode
		else if (index == size) {
			addLast(block);
		} 
		// i use two nodes so that i can insert the newnode between them
		// at the given index.
		else {
			Node prev = getNode(index - 1);
			newNode.next = prev.next;
			prev.next = newNode;
			// i make the size bigger since i added a node.
			size++;
		}
	}

	/**
	 * Creates a new node that points to the given memory block, and adds it
	 * to the end of this list (the node will become the list's last element).
	 * 
	 * @param block
	 *        the given memory block
	 */
	public void addLast(MemoryBlock block) {
		// i make the size bigger since i'm adding a node
		size++;
		Node newNode = new Node(block);
		// if the list is empty i make the first node the newnode
		if (first == null) {
			first = newNode;
			last = newNode;
		} 
		// i point the last node to the newnode 
		else {
			last.next = newNode;
			last = newNode;
		}
	}
	
	/**
	 * Creates a new node that points to the given memory block, and adds it 
	 * to the beginning of this list (the node will become the list's first element).
	 * 
	 * @param block
	 *        the given memory block
	 */
	public void addFirst(MemoryBlock block) {
		Node newNode = new Node(block);
		if (size == 0) {
			first = newNode;
			last = newNode;
		} else {
			newNode.next = first;
			first = newNode;
		}
		size++;
	}

	/**
	 * Gets the memory block located at the given index in this list.
	 * 
	 * @param index
	 *        the index of the retrieved memory block
	 * @return the memory block at the given index
	 * @throws IllegalArgumentException
	 *         if index is negative or greater than or equal to size
	 */
	public MemoryBlock getBlock(int index) {
		if ((index < 0 || index > size)||(size == 0)) {
			throw new IllegalArgumentException(
					"index must be between 0 and size");
		}
		Node current = first;
		int i = 0;
		while (i < index) {
			current = current.next;
			i++;
		}
		return current.block;
	}	

	/**
	 * Gets the index of the node pointing to the given memory block.
	 * 
	 * @param block
	 *        the given memory block
	 * @return the index of the block, or -1 if the block is not in this list
	 */
	public int indexOf(MemoryBlock block) {
		Node current = first;
		int i = 0;
		while (current != null) {
			if (current.block == block) {
				return i;
			}
			current = current.next;
			i++;
		}
		return -1;
	}

	/**
	 * Removes the given node from this list.	
	 * 
	 * @param node
	 *        the node that will be removed from this list
	 */
	public void remove(Node node) {
		Node prev = null;
		Node current = first;
		// the while loop runs as long as it doesnt reach the end of
		// the list and os long as i didnt find the node yet.
		while (current != null && current.block != node.block) {
			prev = current;
			current = current.next;
		}
		// if the node prev that i set to null before the while loop
		// stayed null, then the node i need to remove is the first one,
		// so i set first to point to the next node.
		if (prev == null) {
			first = first.next;
		} 
		// otherwise it removes the node by "skipping" it
		else {
			prev.next = current.next;
		}
		size--;
		if (size == 0) {
			last = null;
		} else if (size == 1) {
			last = first;
		} else {
			Node findLast = first;
			while (findLast.next != null) {
				findLast = findLast.next;
				if(findLast == null) {
					break;
				}
			}
			last = findLast; 
		}
	}

	/**
	 * Removes from this list the node which is located at the given index.
	 * 
	 * @param index the location of the node that has to be removed.
	 * @throws IllegalArgumentException
	 *         if index is negative or greater than or equal to size
	 */
	public void remove(int index) {
		if (index < 0 || index > size) {
			throw new IllegalArgumentException(
					"index must be between 0 and size");
		}
		Node newnNode = new Node(getBlock(index));
		remove(newnNode);
	}

	/**
	 * Removes from this list the node pointing to the given memory block.
	 * 
	 * @param block the memory block that should be removed from the list
	 * @throws IllegalArgumentException
	 *         if the given memory block is not in this list
	 */
	public void remove(MemoryBlock block) {
		Node current = first;
		int n = 0;
		while (current != null) {
			if (current.block == block) {
				n++;
				break;
			}
			current = current.next;
		}
		if (n == 0) {
			throw new IllegalArgumentException(
				"index must be between between 0 and size");
		}
		Node newnNode = new Node(block);
		remove(newnNode);
	}	

	/**
	 * Returns an iterator over this list, starting with the first element.
	 */
	public ListIterator iterator(){
		return new ListIterator(first);
	}
	
	/**
	 * A textual representation of this list, for debugging.
	 */
	public String toString() {
		if (size == 0) return "";
		// i go over the list and add the blocks of the nodes to the string
		String str = "(";
		Node current = first;
		while (current != null) {
			str += current.block + " ";
			current = current.next;
		}

		//removes the trailing space and adds a ')'
		return str.substring(0, str.length() - 1) + ")";
	}
}