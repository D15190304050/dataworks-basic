package stark.dataworks.basic.tests;

import stark.dataworks.basic.collections.KeyValuePair;
import stark.dataworks.basic.io.File;
import stark.dataworks.basic.collections.IDictionary;
import stark.dataworks.basic.io.*;

import java.io.IOException;

/**
 * This class is only used for testing APIs in this project.
 * */
public class MaxFrequency
{
    private MaxFrequency(){}

    public static void maxFrequency(String filePath, int minLength, IDictionary<String, Integer> dictionary) throws IOException
    {
        // Read all content from the file.
        String text = File.readAllText(filePath);

        // Split the text into words.
        String[] words = text.split("\\s+");

        // Build symbol table and count frequencies.
        for (String word : words)
        {
            // Ignore short keys.
            if (word.length() < minLength)
                continue;

            // Add the word to symbol table if doesn't exist.
            // Else, increase the frequency counter.
            if (!dictionary.containsKey(word))
                dictionary.add(word, 1);
            else
                dictionary.set(word, dictionary.get(word) + 1);
        }

        // Find a key with the highest frequency count.
        String maxWord = "";
        int maxOccurrence = 0;
        dictionary.add(maxWord, 0);
        for (KeyValuePair<String, Integer> kvp : dictionary)
        {
            if (kvp.getValue() > maxOccurrence)
            {
                maxOccurrence = kvp.getValue();
                maxWord = kvp.getKey();
            }
        }

        // Report the result.
        System.out.println("The max word is \"" + maxWord + "\", and its frequency is " + maxOccurrence + ".");
    }
}
