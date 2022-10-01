
import org.junit.Test;

import static org.junit.Assert.*;

public class HashTableTester {

    // testing the size method
    @Test
    public void sizeTest() {
        HashTable h1 = new HashTable(11);
        assertEquals(11, h1.size());
    }

    // testing the put method of the hash table
    @Test
    public void putTest() {
        HashTable h2 = new HashTable(11);
        try {
            h2.put(null, 2);
        }
        catch(IllegalArgumentException e) {

        }
        h2.put("Hello", 10);
        // should return 10 because value is 10
        assertEquals(10, h2.get("Hello"));


    }
    // testing the get method of the hash table.
    @Test
    public void getTest() {
        // creating a new hash table to test the get method.
        HashTable h3 = new HashTable();
        // putting a key with a value 2
        h3.put("test", 2);
        // should return 2 because "test" is on index 2
        assertEquals(2, h3.get("test"));

        // Checking for the null case.
        try{
            h3.get(null);

        }
        catch (Exception e){
            System.out.println("They key can't be null");
        }
        // the code should try catch the null pointer exception.
        try{
            h3.get(null);

        }
        catch (Exception e){
            System.out.println("The key can't be null.");
        }
        //the key does not exist so get() should return -1
        assertEquals(-1, h3.get("notTest"));
    }

    /**
     * Test to check update method of the hash table
     */
    @Test
    public void updateTest() {
        HashTable updatedHT = new HashTable(10);
        // catches exceptions  mainly when the update key is null.
        try{
            updatedHT.update(null , 5);
        }
        catch (Exception e){
            System.out.println("The key can't be null");
        }

        updatedHT.put("test" , 2);
        assertEquals(2 , updatedHT.get("test"));
        updatedHT.update("test", 10);

        assertEquals(10 , updatedHT.get("test"));


    }
}