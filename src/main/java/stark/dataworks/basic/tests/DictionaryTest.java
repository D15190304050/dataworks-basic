package stark.dataworks.basic.tests;

import stark.dataworks.basic.collections.IDictionary;
import stark.dataworks.basic.collections.KeyValuePair;
import stark.dataworks.basic.collections.*;

import java.io.IOException;

public class DictionaryTest
{
    private DictionaryTest(){}

    public static void runTest(IDictionary<String, Integer> dictionary) throws IOException
    {
        // Run the FrequencyCounter.MaxFrequency() test.
        String tinyTale = "./test/tinyTale.txt";
        MaxFrequency.maxFrequency(tinyTale, 1, dictionary);

        // Try to traverse all the key-value pairs.
        System.out.println("\nList the contents in the dictionary");
        for (KeyValuePair<String, Integer> kvp : dictionary)
            System.out.println("The occurrence of word \"" + kvp.getKey() + "\" is " + kvp.getValue());

        // Report the size.
        System.out.println("\nCurrent size of the dictionary is " + dictionary.count());

        // Try to remove element from dictionary.

        // Remove an KVP by key and list the rest content.
        System.out.println("\nList the contents in the dictionary after removing the element associated with key \"it\".");
        dictionary.removeByKey("it");
        for (KeyValuePair<String, Integer> kvp : dictionary)
            System.out.println("The occurrence of word \"" + kvp.getKey() + "\" is " + kvp.getValue());

        // Remove the KVP doesn't exist by the key "qwe".
        System.out.println("\nList the contents in the dictionary after removing the element doesn't exist in the dictionary with key \"qwe\".");
        dictionary.removeByKey("qwe");
        for (KeyValuePair<String, Integer> kvp : dictionary)
            System.out.println("The occurrence of word \"" + kvp.getKey() + "\" is " + kvp.getValue());

        // Remove the element by key-value pair.
        // Remove the KVP ("winter", 1).
        System.out.println("\nList the contents in the dictionary after removing the element by key value pair (\"winter\", 1).");
        dictionary.remove(new KeyValuePair<>("winter", 1));
        for (KeyValuePair<String, Integer> kvp : dictionary)
            System.out.println("The occurrence of word \"" + kvp.getKey() + "\" is " + kvp.getValue());

        // Try to clear the dictionary.
        dictionary.clear();
        System.out.println("\nAfter clearing the dictionary, the size of the dictionary is " + dictionary.count());

        // Try to re-add some elements.
        // An easy way here is just re-run the FrequencyCounter.MaxFrequency() method.
        System.out.println("\nRe-add some elements to the dictionary.");
        MaxFrequency.maxFrequency(tinyTale, 1, dictionary);
        System.out.println("Current size of the dictionary is " + dictionary.count());

        // Test for the keys() method.
        System.out.println("\nList all keys in the dictionary.");
        for (String word : dictionary.keys())
            System.out.println(word);

        // Test for the values() method.
        System.out.println("\nList all values in the dictionary.");
        for (int count : dictionary.values())
            System.out.println(count);
    }
}
