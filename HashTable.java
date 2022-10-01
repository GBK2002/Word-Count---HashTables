import java.lang.*;

public class HashTable {
  
  //stores all hash entries in a HashEntry[]
  protected HashEntry[] table;
  
  //stores the table size
  private int tableSize;
  
  //The class that is the hash entry
  private class HashEntry {
    String key;
    int value;
    
    public HashEntry(String key, int value) {
      this.key = key;
      this.value = value;
    }
    
    public String getKey() {
      return key;
    }
    
    public int getValue() {
      return value;
    }
    
    public void setValue(int value) {
      this.value = value;
    }
  }
  //Default size of the table to a 1024 bytes table
  public HashTable() {
    tableSize = 1024;
    table = new HashEntry[tableSize];
  }
  
  public HashTable(int size) {
    tableSize = size;
    table = new HashEntry[size];
  }
  
  //Adds key to hashtable using linear probing
  public void put(String key, int value) throws IllegalArgumentException{
    if (key != null) {
      int i = probe(key, Math.abs(key.hashCode()));
      if (i == -1) {
        rehash();
        i = probe(key, Math.abs(key.hashCode()));
      }
      table[i] = new HashEntry(key, value);
      
    }
  else 
    throw new IllegalArgumentException();
  }
  
  //Adds key to hashtable using linear probing and given hashcode
  public void put(String key, int value, int hashCode) {
    int i = probe(key, Math.abs(hashCode));
    if (i == -1) {
      rehash();
      i = probe(key, Math.abs(hashCode()));
      }
      table[i] = new HashEntry(key, value);
      
  }
  
  //Changes value of a key
  public void update(String key, int value) {
    int i = find(key, Math.abs(key.hashCode()));
    if (i != -1) {
      table[i].setValue(value);
    }
    else
      put(key, value);
  }
  
  //Returns value associated with a key
  public int get(String key) {
    int i = find(key, Math.abs(key.hashCode()));
    if (i == -1) {
      return -1;
    }
    return table[i].getValue();
  }
  
  //Returns value associated with a key and hashCode
  public int get(String key, int hashCode) {
    int i = find(key, Math.abs(key.hashCode()));
    if (i == -1) {
      return -1;
    }
    return table[i].getValue();
  }
  
  //private helper method that helps in finding empty spot using linear probing
  private int probe(String key, int hashCode) throws IllegalArgumentException {
    if (key != null) {
      int i = Math.abs(hashCode) % table.length;
      int iterations = 0;
      
      while(table[i] != null) {
        i = (i + 1) % table.length;
        if (iterations++ > table.length) {
          return -1;
        }
      }
      return i;
    }
    else {
      throw new IllegalArgumentException();
    }
  }
  
  //rounds the size of the table to the prime number after double the size
  private void rehash() {
    int oldSize = tableSize;
    HashEntry[] oldTable = table;
    
    tableSize = nextPrime(2 * oldSize);
    table = new HashEntry[tableSize];
    for (int i = 0; i < oldSize; i++) {
      if (oldTable[i] != null) {
        put(oldTable[i].getKey(), oldTable[i].getValue());
      }
    }
  }
  
  //private helper that method finds the next prime after a number
  private int nextPrime(int num) {
    num++;
    for (int i = 2; i < num; i++) {
      if(num % i == 0) {
        num++;
        i=2;
      }
      else {
        continue;
      }
    }
    return num;
  }
  
  //private helper method that finds a key given a hashcode and key
  private int find(String key, int hashCode) {
    int i = Math.abs(hashCode) % tableSize;
    int iterations = 0;
    
    while(table[i] != null) {
      
      if (table[i].getKey().equals(key)) {
        return i;
      }
      i = (i + 1) % table.length;
      if (iterations++ > table.length) {
        
      }
    }
    return -1;
  }
  
  public int size() {
    return tableSize;
  }
  
    
  
  
}