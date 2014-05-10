import java.util.*;

public class Stack<E> implements Iterable<E>
{
    private LinkedList<E> stack;

    public Stack()
    {
        stack = new LinkedList<E>();
    }

    public void push(E value)
    {
        stack.insertLast(value);
    }

    public E pop()
    {
        return stack.removeLast();
    }

    public E top()
    {
        return stack.peekLast();
    }

    public boolean isEmpty()
    {
        return (stack.isEmpty());
    }

    public Iterator<E> iterator()
    {
        return stack.iterator();
    }
}
