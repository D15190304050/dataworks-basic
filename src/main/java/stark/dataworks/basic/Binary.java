package stark.dataworks.basic;

public class Binary
{
    private Binary()
    {
    }

    /**
     * A table of hexadecimal digits.
     */
    private static final char[] hexadecimalDigit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * Converts a nibble to a hexadecimal character.
     * @param nibble the nibble to convert.
     * @return The corresponding hexadecimal character.
     */
    public static char toHexadecimalChar(int nibble)
    {
        return hexadecimalDigit[nibble & 0xF];
    }
}
