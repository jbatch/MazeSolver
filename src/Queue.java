import java.util.*;

public class Queue<E> implements Iterable<E>
{
    private LinkedList<E> queue;

    public Queue()
    {
        queue = new LinkedList<E>();
    }
    /*
    public Queue(int maxCapacity)
    {
        queue = new Object[maxCapacity];
        count = 0;
    }

    public int getCount()
    {
        return this.count;
    }*/

    public boolean isEmpty()
    {
        return (queue.isEmpty());
    }
    /*
    public boolean isFull()
    {
        boolean full = false;
        if(count == queue.length)
            full = true;
        return full;
    }*/

    public void enqueue(E value)
    {
        queue.insertLast(value);
    }

    public E dequeue()
    {
        E topValue =  queue.peekFirst();
        queue.removeFirst();
        return topValue;
    }

    public E peek()
    {
        E returnV;
        if(isEmpty())
            throw new IllegalArgumentException("Queue is empty");
        returnV = queue.peekFirst();
        return (returnV);
    }

    public Iterator<E> iterator()
    {
        return queue.iterator();
    }
}
