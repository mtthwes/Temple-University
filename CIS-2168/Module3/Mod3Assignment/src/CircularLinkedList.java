import java.util.Iterator;
import java.util.NoSuchElementException;

public class CircularLinkedList<E> implements Iterable<E> {
	Node<E> head;
	Node<E> tail;
	int size;

	// Implement a constructor
	public CircularLinkedList() {
		head = null;
		tail = null;
		size = 0;
	}

	// Return Node<E> found at the specified index
	// Be sure to check for out of bounds cases
	private Node<E> getNode(int index ) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Index " + index + ", Size " + size);
		}
		Node<E> current = head;
		for (int i = 0; i < index; i++) {
			current = current.next;
		}
		return current;
	}

	// Add a node to the end of the list
	// HINT: Use the overloaded add method as a helper method
	public boolean add(E item) {
		add(size, item);
		return true;
	}


	// Cases to handle:
	//      Out of bounds
	//      Adding an element to an empty list
	//      Adding an element to the front
	//      Adding an element to the end
	//      Adding an element in the middle
	// HINT: Remember to keep track of the list's size
	public void add(int index, E item) {
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException("Index " + index + ", Size " + size);
		}
		Node<E> newNode = new Node<>(item);
		if (size == 0) { // Empty list
			head = newNode;
			tail = newNode;
			newNode.next = newNode; // circular link: node points to itself
		} else if (index == 0) { // Add at front
			newNode.next = head;
			head = newNode;
			tail.next = head; // update tail to point to new head
		} else if (index == size) { // Add at end
			tail.next = newNode;
			tail = newNode;
			tail.next = head; // maintain circular link
		} else { // Add in the middle
			Node<E> prev = getNode(index - 1);
			newNode.next = prev.next;
			prev.next = newNode;
		}
		size++;
	}

	// Cases to handle:
	//      Out of bounds
	//      Removing the last element in the list
	//      Removing the first element in the list
	//      Removing the last element
	//      Removing an element from the middle
	// HINT: Remember to keep track of the list's size
	public E remove(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Index " + index + ", Size " + size);
		}
		E removed;
		if (size == 1) { // Only one element
			removed = head.item;
			head = null;
			tail = null;
		} else if (index == 0) { // Remove head
			removed = head.item;
			head = head.next;
			tail.next = head;
		} else {
			Node<E> prev = getNode(index - 1);
			Node<E> toRemove = prev.next;
			removed = toRemove.item;
			prev.next = toRemove.next;
			if (toRemove == tail) { // If tail is removed, update tail
				tail = prev;
			}
		}
		size--;
		return removed;
	}

	// Stringify your list
	// Cases to handle:
	//      Empty list
	//      Single element list
	//      Multi-element list
	// Use "==>" to delimit each node
	public String toString() {
		StringBuilder result = new StringBuilder();
		if (size == 0) {
			result.append("Empty List");
			return result.toString();
		}
		Node<E> current = head;
		for (int i = 0; i < size; i++) {
			result.append(current.item);
			result.append(" ==> ");
			current = current.next;
		}
		return result.toString();
	}

	public Iterator<E> iterator() {
		return new ListIterator<E>();
	}

	// This class is not static because it needs to reference its parent class
	private class ListIterator<E> implements Iterator<E> {
		Node<E> nextItem;
		Node<E> lastReturned;
		int index;

		@SuppressWarnings("unchecked")
		// Creates a new iterator that starts at the head of the list
		public ListIterator() {
			nextItem = (Node<E>) head;
			lastReturned = null;
			index = 0;

		}

		// Returns true if there is a next node
		public boolean hasNext() {

			return head != null;
		}

		// Advances the iterator to the next item
		// Should wrap back around to the head
		public E next() {
			if(head == null) {
				throw new NoSuchElementException();
			}
			lastReturned = nextItem;
			E item = nextItem.item;
			nextItem = nextItem.next;  // automatically wraps around because of circularity
			index++;
			return item;
		}

		// Remove the last node visted by the .next() call
		// For example, if we had just created an iterator,
		// the following calls would remove the item at index 1 (the second person in the ring)
		// next() next() remove()
		// Use CircularLinkedList.this to reference the CircularLinkedList parent class
		public void remove() {
			if(lastReturned == null) {
				throw new IllegalStateException("next() must be called before calling remove()");
			}
			// Find the index of lastReturned by traversing from head.
			int removeIndex = 0;
			Node<E> current = (Node<E>) head;
			// Because the list is circular, we use a do-while loop
			do {
				if(current == lastReturned) {
					break;
				}
				current = current.next;
				removeIndex++;
			} while(current != head);

			CircularLinkedList.this.remove(removeIndex);
			lastReturned = null;
		}
	}

	// The Node class
	private static class Node<E>{
		E item;
		Node<E> next;

		public Node(E item) {
			this.item = item;
		}

	}

	public static void main(String[] args){
		java.util.Scanner sc = new java.util.Scanner(System.in);

		System.out.print("Enter the number of soldiers (n): ");
		int n = sc.nextInt();
		System.out.print("Enter the count (k): ");
		int k = sc.nextInt();
		System.out.println();
		System.out.println("For group of soldiers n = "+ n +" and count k = "+k+":");

		// Create a circular linked list and populate it with soldiers numbered 1 to n
		CircularLinkedList<Integer> soldiers = new CircularLinkedList<>();
		for (int i = 1; i <= n; i++) {
			soldiers.add(i);
		}
		System.out.println(soldiers);

		// Start simulation: remove every kth soldier until only 2 remain.
		int currentIndex = 0;
		while (soldiers.size > 2) {
			// Calculate the index to remove: (currentIndex + k - 1) modulo current size.
			int removeIndex = (currentIndex + k - 1) % soldiers.size;
			int removedSoldier = soldiers.getNode(removeIndex).item;
			soldiers.remove(removeIndex);
			System.out.println(soldiers);
			// Next round starts at the removed soldier's index (adjusted for new size)
			currentIndex = removeIndex % soldiers.size;
		}
		sc.close();
	}
}