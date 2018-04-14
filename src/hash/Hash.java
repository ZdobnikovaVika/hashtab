package hash;


import java.util.LinkedList;
import java.util.Objects;
import java.util.List;

public class Hash<K, V> {

    private Node<K, V>[] hashTable;
    private int size = 0;
    private float threshold;

    /**
     * Create HashTable
     *
     * @param k, number of elements in the array
     */

    public Hash(int k) {
        hashTable = new Node[k];
        threshold = hashTable.length * 0.75f;
    }

    /**
     * adding a node
     *
     * @param key, value
     * @return true, if node is added; false, if node is not added ;"table is full", if the table is completely filled
     */

    public boolean insert(final K key, final V value) {
        if (size + 1 > hashTable.length)
            throw new IllegalArgumentException("table is full");

        Node<K, V> newNode = new Node<>(key, value);
        int index = hash(key);
        if (hashTable[index] == null)
            return simpleAdd(index, newNode);

        List<Node<K, V>> nodeList = hashTable[index].getNodes();
        for (Node<K, V> node : nodeList)
            if (keyExistButValueNew(node, newNode, value)
                    || collisionProcessing(node, newNode, nodeList))
                return true;

        return false;
    }

    private boolean simpleAdd(int index, Node<K, V> newNode) {
        hashTable[index] = new Node<>(null, null);
        hashTable[index].getNodes().add(newNode);
        size++;
        return true;
    }

    private boolean keyExistButValueNew(final Node<K, V> nodeFromList, final Node<K, V> newNode, final V value) {
        if (newNode.getKey().equals(nodeFromList.getKey())
                && !newNode.getValue().equals(nodeFromList.getValue())) {
            nodeFromList.setValue(value);
            return true;
        }
        return false;
    }

    private boolean collisionProcessing(final Node<K, V> nodeFromList, final Node<K, V> newNode,
                                        final List<Node<K, V>> nodes) {
        if (newNode.hashCode() == nodeFromList.hashCode()
                && !Objects.equals(newNode.key, nodeFromList.key) && !Objects.equals(newNode.value, nodeFromList.value)) {
            nodes.add(newNode);
            size++;
            return true;
        }
        return false;
    }

    /**
     * deleting a node by key
     *
     * @param key
     * @return true, if node was deleted; false, if node with such a key are not in the table;
     */

    public boolean delete(final K key) {
        int index = hash(key);
        if (hashTable[index] == null)
            return false;

        if (hashTable[index].getNodes().size() == 1) {
            hashTable[index] = null;
            return true;
        }
        List<Node<K, V>> nodeList = hashTable[index].getNodes();
        for (Node<K, V> node : nodeList)
            if (key.equals(node.getKey())) {
                nodeList.remove(node);
                return true;
            }
        return false;
    }

    /**
     * Getting value by key
     *
     * @param key
     * @return value, if such key is in the table; null, if such key is not in the table
     */

    public V get(final K key) {
        int index = hash(key);
        if (index < hashTable.length && hashTable[index] != null) {
            if (hashTable[index].getNodes().size() == 1)
                return hashTable[index].getNodes().get(0).getValue();

            List<Node<K, V>> list = hashTable[index].getNodes();
            for (Node<K, V> node : list)
                if ((key.equals(node.getKey())))
                    return node.getValue();

        }
        return null;
    }

    private int hash(final K key) {
        int hash = 31;
        hash = hash * 17 + key.hashCode();
        return hash % hashTable.length;
    }

    public class Node<K, V> {
        private List<Node<K, V>> nodes;
        private int hash;
        public K key;
        public V value;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            nodes = new LinkedList<Node<K, V>>();
        }

        private List<Node<K, V>> getNodes() {
            return nodes;
        }

        private int hash() {
            return hashCode() % hashTable.length;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }

        @Override
        public int hashCode() {
            hash = 31;
            hash = hash * 17 + key.hashCode();
            hash = hash * 17 + value.hashCode();
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;

            if (obj instanceof Node) {
                Node<K, V> node = (Node) obj;
                return (Objects.equals(key, node.getKey())
                        && Objects.equals(value, node.getValue()) || Objects.equals(hash, node.hashCode()));
            }
            return false;
        }
    }
}