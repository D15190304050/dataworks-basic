package stark.dataworks.fun;

/**
 * References:
 * [1] https://blog.csdn.net/hrn1216/article/details/51534607
 * [2] https://blog.csdn.net/ten_sory/article/details/79798064
 */
public class LongestCommonSequence
{
    public static void main(String[] args)
    {
        String[][] testCases = new String[][]
                {
                        {
                                "123",
                                "1234",
                                "123"
                        },
                        {
                                "1234",
                                "1234",
                                "1234"
                        },
                        {
                                "222123",
                                "1234",
                                "123"
                        },
                        {
                                "23",
                                "1234",
                                "23"
                        },
                        {
                                "123456",
                                "1234",
                                "1234"
                        },
                        {
                                "1x2t3",
                                "1234",
                                "123"
                        },
                        {
                                "123",
                                "1z2x3c4",
                                "123"
                        },
                        {
                                "1zz2zz3",
                                "1ss2ss3ss4",
                                "123"
                        },
                };

        for (String[] testCase : testCases)
        {
            String s1 = testCase[0];
            String s2 = testCase[1];
            String answer = testCase[2];

            int lengthOfLcs = getLengthOfLongestCommonSequenceByRecursion(s1, s2);

            String result = getLongestCommonSequenceByLoop(s1, s2);

            System.out.println("s1 = " + s1);
            System.out.println("s2 = " + s2);
            System.out.println("answer = " + answer);
            System.out.println("result = " + result);
            System.out.println("lengthOfLcs = " + lengthOfLcs);
            System.out.println("-------------------------------------");
        }
    }

    public static int getLengthOfLongestCommonSequenceByRecursion(String s1, String s2)
    {
        s1 = " " + s1;
        s2 = " " + s2;
        return getLengthOfLongestCommonSequenceByRecursion(s1, s2, s1.length() - 1, s2.length() - 1);
    }

    private static int getLengthOfLongestCommonSequenceByRecursion(String s1, String s2, int x, int y)
    {
        if (x == 0 || y == 0)    //边界
            return 0;
        else                //非边界位置
        {
            if (s1.charAt(x) == s2.charAt(y))    //最后一个字符相同
                return getLengthOfLongestCommonSequenceByRecursion(s1, s2, x - 1, y - 1) + 1;
            else
                return Math.max(getLengthOfLongestCommonSequenceByRecursion(s1, s2, x, y - 1),
                        getLengthOfLongestCommonSequenceByRecursion(s1, s2, x - 1, y));
        }
    }

    public static String getLongestCommonSequenceByLoop(String s1, String s2)
    {
        StringBuilder sb1 = new StringBuilder(" ").append(s1);
        StringBuilder sb2 = new StringBuilder(" ").append(s2);

        int[][] lcsLengths = generateLcsLengthArray(sb1, sb2);
        return getLcsFromArray(sb1, sb2, lcsLengths);
    }

    private static int[][] generateLcsLengthArray(StringBuilder s1, StringBuilder s2)
    {
        int s1Length = s1.length();
        int s2Length = s2.length();

        int[][] lcsLengths = new int[s1Length][s2Length];

        for (int i = 0; i < s1Length; i++)
        {
            for (int j = 0; j < s2Length; j++)
            {
                if (i == 0 || j == 0)
                    lcsLengths[i][j] = 0;
                else
                {
                    if (s1.charAt(i) == s2.charAt(j))
                        lcsLengths[i][j] = lcsLengths[i - 1][j - 1] + 1;
                    else
                        lcsLengths[i][j] = Math.max(lcsLengths[i - 1][j], lcsLengths[i][j - 1]);
                }
            }
        }

        return lcsLengths;
    }

    /**
     * If there is more than 1 lcs, we retrieve 1 of them.
     *
     * @param s1
     * @param s2
     * @param lcsLengths
     * @return
     */
    private static String getLcsFromArray(StringBuilder s1, StringBuilder s2, int[][] lcsLengths)
    {
        int i = s1.length() - 1;
        int j = s2.length() - 1;

        StringBuilder sb = new StringBuilder();
        while ((i != 0) && (j != 0))
        {
            if (lcsLengths[i][j] == lcsLengths[i - 1][j])
                i--;
            else if (lcsLengths[i][j] == lcsLengths[i][j - 1])
                j--;
            else
            {
                sb.append(s1.charAt(i));
                i--;
                j--;
            }
        }

        return sb.reverse().toString();
    }
}
