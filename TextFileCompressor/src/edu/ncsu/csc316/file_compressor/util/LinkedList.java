/**
 * 
 */
package edu.ncsu.csc316.file_compressor.util;

/**
 * Creates a custom Linked List. This class allows to add and remove element from the list.
 * @author Pratik Khanal
 */
public class LinkedList {

	/** Node at the front of the list */
	private ListNode front;
	/** The size of the list */
	private int size;
	
	/** 
	 * Default constructor for the Linked list.
	 */
	public LinkedList() {
		this.front = null;
		this.size = 0;
	}
	
	/**
	 * This method gets the index of element in the list.
	 * @param element search if the element is in the list.
	 * @return return the index of element.
	 */
	public int removeAndGetIdx(String element){
		int i = 0;

		if (front == null){
			return -1;
		}
		if (element.equals(front.data)){
			front = front.next;
			return i;
		} else {
			ListNode current = front;
			while (current.next != null){
				if (element.equals(current.next.data)){
					current.next = current.next.next;
					size--;
					return i + 1;
				}
				current = current.next;
				i++;
			}
		}
		return -1;
		
	}
	/**
	 * This method removes the element in the given index and returns the removed 
	 * element.
	 * @param index the index of element to be removed. 
	 * @return the element that was removed. 
	 */
	public String getRemoveString(int index){
		String temp = null;
		if (index < 0 || index > size ){
			return null;
		}
		if(index == 0){
			temp = front.data;
			front = front.next;
		} else {
			ListNode current = front;
			for (int i = 0; i < index - 1; i++){
				current = current.next;
			}
			temp = current.next.data;
			current.next = current.next.next;
		}
		return temp;
		
	}
	/**
	 * Add the string element to the given index.
	 * @param element the element to add into the list.
	 */
	public void add(String element){
		front = new ListNode(element, front);
		this.size++;
	}
	

	/**
	 * The size of the list.
	 * @return the size of the list.
	 */
	public int getSize(){
		return size;
	}
	
	/**
	 * The Inner class for the nodes of the Linked list.
	 * @author Pratik Khanal
	 */
	private class ListNode {
		
		/** Data of nodes in the linked list.*/
		private String data;
		
		/** Next node after the current node in the linked list.*/
		private ListNode next;
		
		/**
		 * Default constructor for the ListNode class.
		 * @param data string data type.
		 */
		public ListNode(String data){
			this.data = data;
			this.next = null;
		}
		
		/**
		 * Constructor that includes the next node in addition to the data.
		 * @param data string data type.
		 * @param next the next node.
		 */
		public ListNode(String data, ListNode next){
			this.data = data;
			this.next = next;
		}
	}
}
