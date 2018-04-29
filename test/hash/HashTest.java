package hash;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HashTest {
    Hash<Integer, Integer> hash;

    @Before
    public void initialize() {
        hash = new Hash<>(5);
    }

    @Test
    public void insert() {
        Hash<String, Integer> hash = new Hash<>(1);
        assertTrue(hash.insert("a", 1));
    }


    @Test
    public void insertSameKey() {
        hash.insert(2, 1);
        hash.insert(2, 5);
        assertEquals(5, hash.get(2).intValue());
        System.out.println(hash.get(2));

    }

    @Test
    public void insertSameNode() {
        hash.insert(2, 1);
        assertFalse(hash.insert(3, 1));
    }

    @Test
    public void deleteNotExist() {
        assertFalse(hash.delete(2));
    }

    @Test
    public void deleteExist() {
        hash.insert(9, 85);
        hash.insert(4, 4);
        assertTrue(hash.delete(4));
    }

    @Test
    public void get() {
        assertEquals(null, hash.get(2));
    }

    @Test
    public void insertSamHash() {
        hash = new Hash<>(1);
        hash.insert(4, 10);
        hash.delete(4);
        assertTrue(hash.insert(4, 85));
    }

    @Test
    public void insertOverSize() {
        hash = new Hash<>(1);
        hash.insert(1, 1);
        assertFalse(hash.insert(2, 2));
    }

    @Test
    public void insertSameHash() {
        hash.insert(0, 10);
        hash.delete(0);
        assertTrue(hash.insert(4, 85));
    }

    @Test
    public void megaInsert() {
        hash.insert(1, 10);
        hash.insert(2, 11);
        hash.insert(3, 12);
        hash.insert(4, 13);
        hash.insert(6, 15);
        hash.insert(7, 16);
        hash.insert(8, 17);
        hash.insert(9, 18);
        hash.insert(5, 14);
        System.out.println(hash.toString());
        assertFalse(hash.insert(10, 20));
        assertTrue(hash.delete(5));
        assertTrue(hash.insert(10, 20));
    }

    @Test
    public void equalsHashTable() {
        hash.insert(1, 1);
        hash.insert(2, 2);
        hash.insert(3, 3);
        Hash<Integer, Integer> hash2 = new Hash<>(15);
        hash2.insert(3, 3);
        hash2.insert(2, 2);
        hash2.insert(1, 1);
        assertTrue(hash.equals(hash2));
    }

    @Test
    public void collision(){
        hash.insert(1, 1);
        hash.insert(6, 6);
        hash.insert(11, 11);
        hash.insert(16, 16);
        assertEquals(11, hash.get(11).intValue());
    }
}