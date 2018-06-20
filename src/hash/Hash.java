package hash;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;

public class Hash<K, V> {

    private LinkedList<Node<K, V>>[] hashTable;
    private int size = 0;
    private boolean[] help;


    /**
     * Create HashTable
     *
     * @param k, number of elements in the array
     */

    public Hash(int k) {
        hashTable = new LinkedList[k];
        for (int i = 0; i < k; i++) {
            hashTable[i] = new LinkedList<>();
        }
        help = new boolean[k];
    }

    /**
     * adding a node
     *
     * @param key, value
     * @return true, if node is added; false, if node is not added ;"table is full"
     * @throws IllegalStateException if the table is completely filled
     */

    public boolean insert(final K key, final V value) {
        if (size >= hashTable.length)
            return false;

        Node<K, V> newNode = new Node<>(key, value);
        int index = hash(key);

        if (!help[index]) {
            size++;
            help[index] = true;
        }
        for (LinkedList<Node<K, V>> list : hashTable) {
            for (Node<K, V> node : list) {
                if (key.equals(node.getKey())) {
                    node.setValue(value);
                    return true;
                }
            }
        }

        hashTable[index].add(newNode);
        return true;
    }

    /**
     * deleting a node by key
     *
     * @param key
     * @return true, if node was deleted; false, if node with such a key are not in the table;
     */

    boolean delete(final K key) {
        int index = hash(key);

        if (!help[index])
            return false;

        for (Node<K, V> node : hashTable[index]) {
            if (key.equals(node.getKey())) {
                hashTable[index].remove(node);
                if (hashTable[index].size() == 0) {
                    help[index] = false;
                    size--;
                }
                return true;
            }
        }
        return true;
    }

    /**
     * Getting value by key
     *
     * @param key
     * @return value, if such key is in the table; null, if such key is not in the table
     */

    public V get(final K key) {
        int index = hash(key);
        for (Node<K, V> node : hashTable[index]) {
            if (key.equals(node.getKey())) {
                return node.getValue();
            }
        }
        return null;
    }

    private int hash(final K key) {
        int hash = 31;
        hash = hash * 17 + key.hashCode();
        return hash % hashTable.length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hash<K, V> in = (Hash<K, V>) o;
        ArrayList<Node<K, V>> help1 = new ArrayList<>();
        ArrayList<Node<K, V>> help2 = new ArrayList<>();
        for (LinkedList<Node<K, V>> list : hashTable) {
            for (Node<K, V> node : list) {
                help1.add(node);
            }
        }
        for (LinkedList<Node<K, V>> list : in.hashTable) {
            for (Node<K, V> node : list) {
                help2.add(node);
            }
        }
        int q = 0;
        for (Node<K, V> node1 : help1) {
            for (Node<K, V> node2 : help2) {
                if (node1.equals(node2))
                    q++;
            }
        }
        if (q == help1.size() && help1.size() == help2.size())
            return true;
        return false;
    }

    @Override
    public String toString() {
        return "Hash{" +
                "hashTable=" + Arrays.toString(hashTable) +
                ", hashHelp=" + Arrays.toString(help) +
                ", size=" + size +
                '}';
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(size);
        result = 31 * result + Arrays.hashCode(hashTable);
        result = 31 * result + Arrays.hashCode(help);
        return result;


    }
}