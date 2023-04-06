package stark.dataworks.basic.fun;

public class LongestCommonString
{
    public static void main(String[] args)
    {
        String[][] testCases = new String[][]
                {
                        {
                            "this123",
                            "the123",
                            "123"
                        },
                        {
                                "this123",
                                "the1234",
                                "123"
                        },
                        {
                                "this123",
                                "123",
                                "123"
                        },
                        {
                                "this123",
                                "1234",
                                "123"
                        },
                        {
                                "this123",
                                "fun",
                                ""
                        },
                };

        for (String[] testCase : testCases)
        {
            String s1 = testCase[0];
            String s2 = testCase[1];
            String answer = testCase[2];

            String result = getLongestCommonString(s1, s2);

            System.out.println("s1 = " + s1);
            System.out.println("s2 = " + s2);
            System.out.println("answer = " + answer);
            System.out.println("result = " + result);
            System.out.println("-------------------------");
        }
    }

    public static String getLongestCommonString(String s1, String s2)
    {
        int s1Length = s1.length();
        int s2Length = s2.length();

        int[][] commonLengths = new int[s1Length][s2Length];

        // 对commonLengths矩阵的第一列赋值
        for (int i = 0; i < s1Length; i++)
        {
            if (s2.charAt(0) == s1.charAt(i))
                commonLengths[i][0] = 1;
            else
                commonLengths[i][0] = 0;
        }

        // 对commonLengths矩阵的第一行赋值
        for (int j = 0; j < s2Length; j++)
        {
            if (s1.charAt(0) == s2.charAt(j))
                commonLengths[0][j] = 1;
            else
                commonLengths[0][j] = 0;
        }

        int maxLength = 0;
        int iIndexOfLcs = 0;
        for (int i = 1; i < s1Length; i++)
        {
            for (int j = 1; j < s2Length; j++)
            {
                if (s1.charAt(i) == s2.charAt(j))
                {
                    commonLengths[i][j] = commonLengths[i - 1][j - 1] + 1;

                    if (maxLength < commonLengths[i][j])
                    {
                        maxLength = commonLengths[i][j];
                        iIndexOfLcs = i;
                    }
                }
                else
                    commonLengths[i][j] = 0;
            }
        }

        if (maxLength == 0)
            return "";
        else
        {
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < maxLength; i++)
                result.append(s1.charAt(iIndexOfLcs--));

            return result.reverse().toString();
        }
    }
}
