package collections;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.*;


public class Collection {
    public void main(String[] args) {
        ArrayList<Integer> arr = new ArrayList<>();
        arr.add(5);
        arr.add(6);
        arr.add(9);
        arr.add(1);
        arr.add(0);
        arr.add(8);
        arr.add(4);
        arr.add(0);
        arr.add(6);
        arr.add(9);
        System.out.println(arr);
        arr.set(0,9);
        arr.set(1,4);
        System.out.println(arr);
        arr.remove(0);
        System.out.println(arr);
        System.out.println(arr.size());
        System.out.println(arr.indexOf(4));
        LinkedList<Integer> arr1 = new LinkedList<>();

        // Adding elements
        arr1.add(5);
        arr1.add(6);
        arr1.add(9);
        arr1.add(1);
        arr1.add(0);
        arr1.add(8);
        arr1.add(4);
        arr1.add(0);
        arr1.add(6);
        arr1.add(9);

        System.out.println(arr);

        // Updating elements
        arr1.set(0, 9);
        arr1.set(1, 4);
        System.out.println(arr1);

        // Removing element by index
        arr1.remove(0);
        System.out.println(arr1);

        // Size of LinkedList
        System.out.println(arr1.size());

        // Finding index of an element
        System.out.println(arr1.indexOf(4));

        // Adding elements at first and last
        arr.addFirst(100);
        arr.addLast(200);
        System.out.println(arr1);

        // Getting first and last elements
        System.out.println(arr1.getFirst());
        System.out.println(arr1.getLast());

        // Removing first and last elements
        arr1.removeFirst();
        arr1.removeLast();
        System.out.println(arr1);

        // Checking whether an element exists
        System.out.println(arr1.contains(8));

        // Getting an element by index
        System.out.println(arr1.get(2));

        // Finding last occurrence of an element
        System.out.println(arr1.lastIndexOf(9));

        // Checking whether the list is empty
        System.out.println(arr1.isEmpty());

        // Removing all elements
        arr1.clear();
        System.out.println(arr1);
    }

    }


