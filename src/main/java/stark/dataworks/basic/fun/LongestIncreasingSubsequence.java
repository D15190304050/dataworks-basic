package stark.dataworks.basic.fun;

import java.util.Arrays;

/**
 * Reference: https://blog.csdn.net/qq_41765114/article/details/88415541
 */
public class LongestIncreasingSubsequence
{
    public static void main(String[] args)
    {
//        int[] values = {4, 2, 3, 1, 5};
//        int[] values = {4, 2, 1, 5};
//        int[] values = {1, 2, 3, 4, 5, 6};
//        int[] values = {9, 8, 7, 6, 5, 4, 3};
        int[] values = {10, 9, 2, 5, 3, 7, 101, 18};
        System.out.println(getLengthOfLongestIncreasingSubsequence(values));
    }

    public static int getLengthOfLongestIncreasingSubsequence(int[] array)
    {
        int[] largestValueOfLength = new int[array.length + 1];
        largestValueOfLength[1] = array[0];
        int length = 1;

        for (int i = 1; i < array.length; i++)
        {
            if (array[i] > largestValueOfLength[length])
                largestValueOfLength[++length] = array[i];
            else
            {
                int lengthMaxToRefresh = Arrays.binarySearch(largestValueOfLength, 0, length, array[i]);
                if (lengthMaxToRefresh < 0)
                    lengthMaxToRefresh = -(lengthMaxToRefresh + 1);
                largestValueOfLength[lengthMaxToRefresh] = array[i];
            }
        }

        return length;
    }

}
