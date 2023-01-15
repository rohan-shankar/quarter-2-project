//import java.util.Iterator;
import java.io.Serializable;
public class MyHashSet<E> implements Serializable {
    private static final int MAX_MEMORY_ALLOCATED = 1 << 11;
    
    private Object[] memory;
    private int size;

    public MyHashSet() {
	memory = new Object[MAX_MEMORY_ALLOCATED];
	size = 0;
    }

    public boolean add(E e) {
	if(memory[e.hashCode() % MAX_MEMORY_ALLOCATED] == null) {
	    memory[e.hashCode() % MAX_MEMORY_ALLOCATED] = e;
	    //does allow for collisions but prevents overflow issues
	    size++;
	    return true;
	}
	return false;
    }

    public void clear() {
	memory = new Object[MAX_MEMORY_ALLOCATED];
	size = 0;
	//memory leak but cleaned up
    }

    public boolean contains(E e) {
	return memory[e.hashCode()] != null;
    }

    public boolean remove(E e) {
	if(memory[e.hashCode() % MAX_MEMORY_ALLOCATED] != null) {
	    memory[e.hashCode() % MAX_MEMORY_ALLOCATED] = null;
	    size--;
	    return true;
	}
	return false;
    }

	@SuppressWarnings("unchecked")
	public String toString() {
	String str = "[";
	for(Object e : memory) {
	    if(e != null) {
		str += ((E)e).toString() + ", ";
	    }
	}
	str = str.substring(0, str.length() - 2); //remove leading ", "
	str += "]";
	return str;
    }

    public int size() {
		return size;
    }

    @SuppressWarnings("unchecked")
	public DLList<E> toDLList() {
	DLList<E> l = new DLList<>();
	
	for(Object e : memory) {
	    if(e != null) {
			l.add((E)e);
	    }
	}
	return l;
    }

    /*
    public Iterator<E> iterator() {
	return new HashSetIterator(this) {
	    
	};
    }
    */
}
