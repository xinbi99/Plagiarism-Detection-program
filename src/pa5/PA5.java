/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pa5;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author bixin
 */


public class PA5 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Map<String, Integer> myMap0 = new HashMap<>();
        Map<String, Integer> myMap1 = new HashMap<>();
        createMap(myMap0, args[0]);
        createMap(myMap1, args[1]);
        System.out.printf("Number of unique words in %s: %s%n", args[0], myMap0.size());
        System.out.printf("Number of unique words in %s: %s%n", args[1], myMap1.size());
        Set<String> keys0 = myMap0.keySet();
        Set<String> keys1 = myMap1.keySet();
        TreeSet<String> sortedKeys0 = new TreeSet<>(keys0);
        TreeSet<String> sortedKeys1 = new TreeSet<>(keys1);
        TreeSet<String> intersection = new TreeSet<String> (sortedKeys0);
        intersection.retainAll(sortedKeys1);
        int repeated = intersection.size();
        System.out.printf("They have %s words in common.%n%n", repeated);
        System.out.printf("The common words and their counts in %s and %s, respectivately:%n", args[0], args[1]);
        System.out.println("Word                Count1              Count2");
        for(String t : intersection){
            int count0 = myMap0.get(t);
            int count1 = myMap1.get(t);
            System.out.printf("%-20s%-20d%-20d%n",t,count0,count1);
        }
        System.out.println();
        float index = compareMaps(myMap0, myMap1);
        float target = Float.parseFloat(args[2]);
        if (index >= target){
            System.out.printf("This is a plagarism case. Similarity score = %s >= threshold score %s%n", index, target);
        }
        else{
            System.out.printf("This is not a plagarism case. Similarity score = %s < threshold score %s%n", index, target);
        }
    }

    private static void createMap(Map<String, Integer> map, String textFile) {
        try(Scanner input = new Scanner(Paths.get(textFile))){
            input.useDelimiter("\\s+|(\\.|,|-|;|:|!|\\?|\"|\\[|\\]|\\(|\\)|'$|\\r|\\n)*(\\s)*(\\.|,|-|;|:|!|\\?|\"|\\[|\\]|\\(|\\)|'$|\\r|\\n)+(\\s)*");
            while(input.hasNext()){
                String token = input.next();
                token = token.toLowerCase();
                if(map.containsKey(token)){
                    int count = map.get(token);
                    map.put(token, count + 1);
                }
                else{
                    map.put(token, 1);
                }
            }
        }
        catch(IOException | NoSuchElementException | IllegalStateException e){
            e.printStackTrace();
        }
    }

    private static float compareMaps(Map<String, Integer> map0, Map<String, Integer>map1) {
        Set<String> keys0 = map0.keySet();
        Set<String> keys1 = map1.keySet();
        TreeSet<String> sortedKeys0 = new TreeSet<>(keys0);
        TreeSet<String> sortedKeys1 = new TreeSet<>(keys1);
        TreeSet<String> intersection = new TreeSet<String> (sortedKeys0);
        TreeSet<String> union = new TreeSet<String> (sortedKeys0);
        intersection.retainAll(sortedKeys1);
        union.addAll(sortedKeys1);
        float index = (float)intersection.size()/union.size();
        return index;
    }
}
