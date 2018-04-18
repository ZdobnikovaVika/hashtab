package hash;

import org.junit.Test;

import static org.junit.Assert.*;

public class HashTest {

    @Test
    public void insert() {
        Hash<String, Integer> hash = new Hash<>(5);
        assertTrue(hash.insert("a", 1));
    }

    @Test
    public void insertOverSize() {
        Hash<String, Integer> hash = new Hash<>(1);
        hash.insert("a", 1);
        try {
            hash.insert("b", 2);
            fail("Exception expected");
        } catch (IllegalArgumentException e) {
            assertEquals("table is full", e.getMessage());
        }

    }

    /**
     * @Test public void insertSameKey() {
     * Hash<Integer, Integer> hash = new Hash<>(5);
     * hash.insert(2, 1);
     * hash.insert(2, 5);
     * assertEquals(5, hash.get(2).intValue());
     * }
     */
    @Test
    public void insertSameNode() {
        Hash<Integer, Integer> hash = new Hash<>(5);
        hash.insert(2, 1);
        assertFalse(hash.insert(2, 1));
    }

    @Test
    public void deleteNotExist() {
        Hash<Integer, Integer> hash = new Hash<>(5);
        assertFalse(hash.delete(2));
    }

    @Test
    public void deleteExist() {
        Hash<Integer, Integer> hash = new Hash<>(5);
        hash.insert(2, 4);
        assertTrue(hash.delete(2));
    }

    @Test
    public void get() {
        Hash<Integer, Integer> hash = new Hash<>(5);
        assertEquals(null, hash.get(2));
    }

    @Test
    public void insertSameHash() {
        Hash<Integer, Integer> hash = new Hash<>(5);
        hash.insert(5, 0);
        assertTrue(hash.insert(10, 85));
        System.out.println(hash.get(10));
    }
}