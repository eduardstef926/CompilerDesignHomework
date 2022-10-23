package Lab12;

public class Main {


    public static void main(String[] args) {
        HashTable hashTable = new HashTable(3);
        hashTable.add("abc");
        hashTable.add("cba");
        hashTable.add("a");
        hashTable.add("ac");
        assert hashTable.getSize() == 4;
        hashTable.remove("abc");
        assert hashTable.getSize() == 3;
        hashTable.remove("ac");
        assert hashTable.getSize() == 2;
    }
}
