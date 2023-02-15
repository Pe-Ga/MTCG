package at.technikum.lessons;

import java.util.*;

public class Collections {

    public static void main(String[] args) {
        explainCollections();
    }

    private static void explainCollections() {

        List<String> stringList = new ArrayList<>();
        stringList.add("Hello");
        stringList.add("World");

        System.out.println(stringList.get(1));

        Set<String> stringSet = new HashSet<>();
        stringSet.add("Hallo");
        stringSet.add("Hallo");

        Map<String, Integer> stringStringMap = new HashMap<>();
        stringStringMap.put("key", 23);

        System.out.println(stringStringMap.get("key"));

        System.out.println(stringSet.size());

        // print all strings in stringList
        for (String s: stringList) {
            System.out.println(s);
        }



    }
}
