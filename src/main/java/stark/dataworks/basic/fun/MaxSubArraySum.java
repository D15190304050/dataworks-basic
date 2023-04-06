package stark.dataworks.basic.fun;

public class MaxSubArraySum
{
    public static void main(String[] args)
    {
        int[] values = {1, -2, 3, 10, -4, 7, 2, -5};
        System.out.println(getMaxSubArraySum(values));
    }

    public static int getMaxSubArraySum(int[] values)
    {
        int sum = 0;
        int result = values[0];
        for (int value : values)
        {
            if (sum > 0)
                sum += value;
            else
                sum = value;

            result = Math.max(result, sum);
        }

        return result;
    }
}
