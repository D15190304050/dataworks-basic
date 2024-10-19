package stark.dataworks.basic.fun;

/**
 * References:
 * [1] <a href="https://www.jianshu.com/p/a617d20162cf">Reference 1</a>
 * [2] <a href="https://blog.csdn.net/tianjindong0804/article/details/115803158">Reference 2</a>
 */
public class EditDistance
{
    private static class TestCase
    {
        public String s1;
        public String s2;
        public int answer;

        public TestCase(String s1, String s2, int answer)
        {
            this.s1 = s1;
            this.s2 = s2;
            this.answer = answer;
        }
    }

    public static void main(String[] args)
    {
        TestCase[] testCases = new TestCase[]
                {
                        new TestCase("Java", "Jave", 1),
                        new TestCase("Sun", "Son", 1),
                        new TestCase("dog", "doge", 1),
                        new TestCase("kitten", "sitting", 3),
                };
        for (TestCase testCase : testCases)
        {
            String s1 = testCase.s1;
            String s2 = testCase.s2;
            int answer = testCase.answer;

            int result = getEditDistance(s1, s2);

            System.out.println("s1 = " + s1);
            System.out.println("s2 = " + s2);
            System.out.println("answer = " + answer);
            System.out.println("result = " + result);
            System.out.println("--------------------------------------");
        }
    }

    public static int getEditDistance(String s1, String s2)
    {
        StringBuilder sb1 = new StringBuilder(" ").append(s1);
        StringBuilder sb2 = new StringBuilder(" ").append(s2);

        int[][] distances = new int[sb1.length()][sb2.length()];

        for (int i = 0; i < sb1.length(); i++)
            distances[i][0] = i;

        for (int j = 0; j < sb2.length(); j++)
            distances[0][j] = j;

        for (int i = 1; i < sb1.length(); i++)
        {
            for (int j = 1; j < sb2.length(); j++)
            {
                int cost = sb1.charAt(i) == sb2.charAt(j) ? 0 : 1;

                distances[i][j] = min(distances[i - 1][j] + 1,
                        distances[i][j - 1] + 1,
                        distances[i - 1][j - 1] + cost);
            }
        }

        return distances[s1.length()][s2.length()];
    }

    private static int min(int x, int y, int z)
    {
        return Math.min(x, Math.min(y, z));
    }
}
