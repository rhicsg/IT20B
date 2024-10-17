

package it20b_geonzon;

import java.util.LinkedList;

public class IT20B_GEONZON {

    
    public static void main(String[] args) {
                  
        LinkedList<String> animals = new LinkedList<>();


        animals.add("Horse");
        animals.addFirst("Mouse");
        animals.addLast("Cow");
        animals.add(1, "Tiger");

        System.out.println("Linked List Original: " + animals);
        System.out.println(animals.size());


        if (animals.contains("Tiger")) {
            System.out.println("True");
        } else {
            System.out.println("False");
        }

        boolean containslion = animals.contains("Tiger");
        System.out.println(containslion);


    }
    
}
