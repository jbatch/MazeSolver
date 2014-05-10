import java.util.*;

/****************************************/
/*DSALinkedList Class                    */
/*Represents a Linked List data stucture*/
/****************************************/
public class LinkedList<E> implements Iterable<E>
{
        /***************/
        /*MEMBER FIELDS*/
        /***************/

    private ListNode<E> head, tail;
    private int length;

        /***********/
        /*FUNCTIONS*/
        /***********/

    public Iterator<E> iterator() //returns an iterator for this linked list
    {
        return new LinkedListIterator<E>(this);
    }

    public LinkedList()
    {
        head = null;
        tail = null;
        length =0;
    }

    public void insertFirst(E newValue) //insert a new value at the head
    {
        ListNode<E> newNode = new ListNode<E>(newValue);
        if(head == null && tail == null)
        {
            head = newNode;
            tail = newNode;
        }
        else
        {
            newNode.setNext(head);
            head = newNode;
        }
        length++;
    }

    public void insertLast(E newValue) //insert a  new value at the tail
    {
        ListNode<E> newNode = new ListNode<E>(newValue);
        if (head == null && tail == null)
        {
            head = newNode;
            tail = newNode;
        }
        else
        {
            tail.setNext(newNode);
            tail = newNode;
        }
        length++;
    }

    public boolean isEmpty() //return whether linked list is empty
    {
        return (head == null);
    }

    public E peekFirst() //return head but don't remove it
    {
        E  nodeValue;
        if (head == null)
            throw new IllegalArgumentException("No head node set");
        nodeValue = head.getValue();
        return nodeValue;
    }

    public E peekLast() //return tail but don't remove it
    {
        E nodeValue;
        if(tail == null)
        {
            throw new IllegalArgumentException("No tail node set");
        }
        nodeValue = tail.getValue();
        return nodeValue;
    }

    public E removeFirst() //remove the first listnode
    {
        E nodeValue;

        if(head == null)
            throw new IllegalArgumentException("No head node set");
        nodeValue = head.getValue();
        if(head.getNext() == null)
        {
            tail = null;
            head = null;
        }
        else
            head = head.getNext();
        length--;
        return nodeValue;
    }

    public E removeLast() //remove the last listnode
    {
        E nodeValue;
        if(head == null)
        {
            throw new IllegalArgumentException("No tail node set");
        }

        if(head.getNext() == null)
        {
            nodeValue = tail.getValue();
            head = null;
            tail = null;
        }
        else
        {
            ListNode<E> prevNd = null, currNd = head;
            while(currNd.getNext() != null)
            {
                prevNd = currNd;
                currNd = currNd.getNext();
            }
            prevNd.setNext(null);
            nodeValue = currNd.getValue();
            tail = prevNd;
        }
        length--;
        return nodeValue;

    }

    int getLength(){return length;}; //return the length of the linked list

    ///////////////DSA LISTNODE PRIVATE CLASS ////////////////
    /**************************************/
    /*ListNode Class                      */
    /*Stores the value of the node and the*/
    /*address of the next node in the list*/
    /**************************************/
    private class ListNode<E>
    {
            /***************/
            /*MEMBER FIELDS*/
            /***************/

        private E value;
        private ListNode<E> next;

            /***********/
            /*FUNCTIONS*/
            /***********/
            //All functions are self-explainatory
        public ListNode(E inValue)
        {
            value = inValue;
            next = null;
        }

        public E getValue()
        {
            return value;
        }

        public void setValue(E inValue)
        {
            value = inValue;
        }

        public ListNode<E> getNext()
        {
            return next;
        }

        public void setNext(ListNode<E> inNext)
        {
            next = inNext;
        }

    }

    ////////////////DSALINKEDLISTITERATOR PRIVATE CLASS ////////////////
    private class LinkedListIterator<E> implements Iterator<E>
    {
        private LinkedList<E>.ListNode<E> iterNext;
        public LinkedListIterator(LinkedList<E> theList)
        {
            iterNext = theList.head;
        }
        // Iterator interface implementation
        public boolean hasNext() { return (iterNext != null); }
        public E next()
        {
            E value;
            if (iterNext == null)
                value = null;
            else
            {
                value = iterNext.getValue();
                iterNext = iterNext.getNext();
            }

        return value;
        }

        public void remove() { throw new UnsupportedOperationException("Not supported"); }
    }
}
