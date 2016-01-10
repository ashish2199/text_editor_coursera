package textgen;

import java.util.AbstractList;


/** A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		// TODO: Implement this method
		head = null;
		tail=null;
		size=0;
	}

	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 */
	public boolean add(E element ) 
	{
		// TODO: Implement this method
		if(element == null){
			throw new NullPointerException();
		}
		LLNode<E> node = new LLNode(element);
		if(head==null){
			head=node;
			tail=node;
			size++;
		}else{
			LLNode<E> ptr = head;
			while(ptr.next!=null){
				ptr=ptr.next;
			}
			ptr.next=node;
			node.prev=ptr;
			tail = node;
			size++;
		}	
		
		return true;
	}

	/** Get the element at position index 
	 * @throws IndexOutOfBoundsException if the index is out of bounds. */
	public E get(int index) 
	{
		// TODO: Implement this method.
		if(index>=size||index<0){
			throw new IndexOutOfBoundsException();
		}
		LLNode<E> ptr = head;
		int i=0;
		while(i!=index){
			ptr=ptr.next;
			i++;
		}
		return ptr.data;
	}

	/**
	 * Add an element to the list at the specified index
	 * @param The index where the element should be added
	 * @param element The element to add
	 */
	public void add(int index, E element ) 
	{
	
		if(element == null){
			throw new NullPointerException();
		}
		// TODO: Implement this method
		if(index>size||index<0){
			throw new IndexOutOfBoundsException();
		}
		LLNode<E> ptr = head;
		LLNode<E> node = new LLNode<E>(element);
		int i=0;
		while(i!=index){
			ptr=ptr.next;
			++i;
		}
                if(head==null){
                    head=node;
                    tail=node;
                    size++;
                    return;
                }
                if(index==0&&head!=null){
                    head=node;
                }
                if(index==size-1){tail=ptr;}
		node.next=ptr;
		node.prev=ptr.prev;
		if(ptr.prev!=null){
                    ptr.prev.next=node;
                }
                ptr.prev=node;
		
                size++;
	}


	/** Return the size of the list */
	public int size() 
	{
		// TODO: Implement this method
		return size;
	}

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) 
	{
		// TODO: Implement this method
		if(index>=size||index<0){
			throw new IndexOutOfBoundsException();
		}
		LLNode<E> ptr = head;
		int i=0;
		while(i!=index){
			ptr=ptr.next;
			i++;
		}
		
		if(ptr.prev==null)
		{
			head=ptr.next;
		}
		else
		{
			ptr.prev.next=ptr.next;
		}
		
		if(ptr.next==null)
		{
			tail=ptr.prev;
		}
		else
		{
			ptr.next.prev=ptr.prev;	
		}
		size--;
		return ptr.data;
	}

	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element) 
	{
		// TODO: Implement this method
		if(element == null){
			throw new NullPointerException();
		}
		if(head==null||index>=size||index<0){
			throw new IndexOutOfBoundsException();
		}
		LLNode<E> ptr = head;
		int i=0;
		while(i!=index){
			ptr=ptr.next;
			++i;
		}
		E prevdata = ptr.data;
		ptr.data=element;
		return prevdata;
	}   
	
	public String toString(){
		String s="";
		LLNode<E> ptr = head;
		while(ptr!=null){
			s=s+"-"+ptr.data;
			ptr=ptr.next;
		}
		return s;
	}
	
}

class LLNode<E> 
{
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	// TODO: Add any other methods you think are useful here
	// E.g. you might want to add another constructor

	public LLNode(E e) 
	{
		this.data = e;
		this.prev = null;
		this.next = null;
	}

}
