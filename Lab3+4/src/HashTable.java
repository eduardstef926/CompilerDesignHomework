package Lab12;

import java.util.ArrayList;
import java.util.Objects;

public class HashTable {

    private ArrayList<String> hashtable;
    private int size;
    private int capacity;

    public HashTable(int capacity) {
        this.size = 0;
        this.capacity = capacity;
        this.hashtable = new ArrayList<String>();
        for(int i=0; i<capacity; ++i){
            this.hashtable.add("0");
        }
    }

    public int getSize(){
        /**
         * return: the size of the array.
         */
        return this.size;
    }

    public void resizeTable(){
        /**
         * this function will resize the table
         * by making a new table with double
         * the previous capacity and rehash the
         * elements onto it
         */
        var newHashTable = new ArrayList<String>();
        for(int i=0; i<2*capacity; ++i){
            newHashTable.add("0");
        }
        this.capacity *= 2;
        for(int i=0; i<size; ++i){
            var hashValue = this.hashtable.get(i);
            int hashKey = getHashKey(newHashTable, hashValue);
            newHashTable.set(hashKey, hashValue);
        }
        this.hashtable = newHashTable;
    }

    public int getPosition(String key){
        /**
         * this function will search for the
         * position of a given key into the array
         * (hash function is the sum of the ascii codes
         * of the strings divided by the capacity of the
         * table)
         *
         * return: the position
         */
        int asciiSum = 0;
        for (int i = 0; i < key.length(); ++i) {
            asciiSum += key.charAt(i);
        }
        int hashValue = asciiSum % capacity;
        while (!Objects.equals(hashtable.get(hashValue), key)) {
            hashValue += 1;
            if (hashValue >= capacity) {
                hashValue = 0;
            }
        }
        return hashValue;
    }

    public int getHashKey(ArrayList<String> hashtable, String key){
        /**
         * This function will compute the position
         * associated to the key by searching
         * the first free position in the hashtable.
         * return: the position
         */
        int asciiSum = 0;
        for (int i = 0; i < key.length(); ++i) {
            asciiSum += key.charAt(i);
        }
        int hashValue = asciiSum % capacity;
        while (!Objects.equals(hashtable.get(hashValue), "0")) {
            hashValue += 1;
            if (hashValue >= capacity) {
                hashValue = 0;
            }
        }
        return hashValue;
    }

    public void add(String value) {
        /***
         * This function will add a new value
         * to the hashtable(will resize the hashtable
         * if necessary) and will increment the size
         */
        if (size == capacity){
            this.resizeTable();
        }
        int hashKey = this.getHashKey(this.hashtable, value);
        this.size += 1;
        hashtable.set(hashKey, value);
    }

    public void remove(String value) {
        /***
         * This function will remove a value
         * from the table(set is to "0")
         * And will decrement the size.
         */
        int hashKey = this.getPosition(value);
        hashtable.set(hashKey, "0");
        this.size -= 1;
    }

    public String toString(){
        var word = new StringBuilder();
        for(var key: this.hashtable){
            if (!Objects.equals(key, "0"))
              word.append("[").append(key).append(":").append(getPosition(key)).append("]").append("\n");
        }
        return word.toString();
    }

}