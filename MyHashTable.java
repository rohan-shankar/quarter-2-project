import java.io.Serializable;


public class MyHashTable<K, V> implements Serializable {
    
    private DLList<V>[] table;
    private DLList<K> keySet;
    private int size;

    @SuppressWarnings("unchecked")
    public MyHashTable() {
        table = new DLList[10000000];
        keySet = new DLList<K>();
        this.size = 10000000;
    }

    public void put(K k, V v) {

        if (table[k.hashCode()%10000000] == null) {
            table[k.hashCode()%10000000] = new DLList<V> () ;
        }
        table[k.hashCode()%10000000].add(v) ;
        //System.out.println(table[k.hashCode()%10000000].toString());
        keySet.add(k);
        
    }

    public DLList<V> get(K k) {
        return table[k.hashCode()%10000000];
    }

    public DLList<K> keySet() {
        return keySet;
    }

    public String toString() {
        String bigResult = "";
        for (int i = 0; i < keySet.size(); i ++) {
            String result = "";
            result += keySet.get(i).toString();
            result += " -- " + (table[keySet.get(i).hashCode()%10000000].size());
            result += "\n";
            if (table[keySet.get(i).hashCode()%10000000].size() != 1) {
                bigResult += result;
            }
            
        }
        return bigResult;
    }

    

    public void remove(K k, V v) {
        table[k.hashCode()%10000000].remove(v);
        if (table[k.hashCode()%10000000].size() == 0) {
            table[k.hashCode()%10000000] = null;
            keySet.remove(k);
        }
    }

    public void remove(K k) {
        table[k.hashCode()%10000000] = null;
        keySet.remove(k);
    }

    public String keyString() {
        String result = "";
        for (int i = 0; i < keySet.size(); i++) {
            result += keySet.get(i).toString() + ", ";
        }
        return result;
    }

    



}
