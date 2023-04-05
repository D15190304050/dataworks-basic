package stark.dataworks.fun;

import stark.dataworks.Binary;
import stark.dataworks.params.OutValue;

import java.io.*;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

public class Properties
{
    private static final int INITIAL_CAPACITY = 8;

    private final ConcurrentHashMap<String, String> map;

    public Properties()
    {
        this(INITIAL_CAPACITY);
    }

    public Properties(int initialCapacity)
    {
        map = new ConcurrentHashMap<>(initialCapacity);
    }

    public synchronized void setProperty(String key, String value)
    {
        map.put(key, value);
    }

    public String getProperty(String key)
    {
        return map.get(key);
    }

    public Iterable<String> keys()
    {
        return null;
    }

    public boolean containsKey(String key)
    {
        return map.containsKey(key);
    }

    public boolean containsValue(String value)
    {
        return map.containsValue(value);
    }

    public synchronized void removeProperty(String key)
    {
        map.remove(key);
    }

    public synchronized void putAll(Map<String, String> properties)
    {
        map.putAll(properties);
    }

    public synchronized void clear()
    {
        map.clear();
    }

    @Override
    public synchronized String toString()
    {
        return "";
    }

    @Override
    public synchronized int hashCode()
    {
        return map.hashCode();
    }

    @Override
    public synchronized boolean equals(Object o)
    {
        return map.equals(o);
    }

    public int size()
    {
        return map.size();
    }

    public boolean isEmpty()
    {
        return map.isEmpty();
    }

    public synchronized void forEach(BiConsumer<? super Object, ? super Object> action)
    {
        map.forEach(action);
    }

    public Iterable<String> getPropertyNames()
    {
        return null;
    }

    /**
     * Converts encoded &#92;uxxxx to unicode chars and changes special saved chars to their original forms.
     *
     * @param in          The input char array that contains the key or the value to read and parse.
     * @param startOffset Start offset of the part.
     * @param length      Length of the part.
     * @param outBuffer   A buffer that contains the intermediate and final result to return.
     * @return The parsed key or value.
     */
    private static String loadConvert(char[] in, int startOffset, int length, StringBuilder outBuffer)
    {
        char c;
        int end = startOffset + length;
        int left = startOffset;
        while (left < end)
        {
            c = in[left++];
            if (c == '\\')
                break;
        }

        // No escape (no backslash).
        if (left == end)
            return new String(in, startOffset, length);

        // Backslash found at left - 1, reset the shared buffer, rewind offset.
        outBuffer.setLength(0);
        left--;
        outBuffer.append(in, startOffset, left - startOffset);

        // Process each character one by one.
        // Convert escape characters if meet one.
        while (left < end)
        {
            c = in[left++];

            // If there is a \, then a escape conversion is needed; otherwise, it is a simple character.
            if (c == '\\')
            {
                c = in[left++];

                // \\uxxxx, unicode character.
                if (c == 'u')
                {
                    // Now we have "\\u", a unicode is a "\\uxxxx", next we read the "xxxx."
                    int value = 0;
                    for (int i = 0; i < 4; i++)
                    {
                        c = in[left++];
                        switch (Character.toLowerCase(c))
                        {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + c - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + c - 'a';
                                break;
                            default:
                                throw new IllegalArgumentException("Malformed \\uxxxx encoding.");
                        }
                    }
                    outBuffer.append((char) value);
                }
                else
                {
                    // Convert escapes to corresponding characters.
                    switch (c)
                    {
                        case 't':
                            c = '\t';
                            break;
                        case 'r':
                            c = '\r';
                            break;
                        case 'n':
                            c = '\n';
                            break;
                        case 'f':
                            c = '\f';
                            break;
                    }
                    outBuffer.append(c);
                }
            }
            else
                outBuffer.append(c);
        }

        return outBuffer.toString();
    }

    private static String saveConvert(String string, boolean escapeSpace, boolean escapeUnicode)
    {
        int bufferLength = string.length() * 2;

        if (bufferLength < 0)
            bufferLength = Integer.MAX_VALUE;

        StringBuilder outBuffer = new StringBuilder(bufferLength);

        for (int x = 0; x < string.length(); x++)
        {
            char c = string.charAt(x);

            // Handle common case first, selecting largest block that avoids the specials below.
            if ((c > '=') && (c < 127))
            {
                if (c == '\\')
                {
                    outBuffer.append('\\').append('\\');
                    continue;
                }
                outBuffer.append(c);
                continue;
            }

            switch (c)
            {
                // TODO: Figure out why leading space needs an escape.
                case ' ':
                    if ((x == 0) || escapeSpace)
                        outBuffer.append('\\');
                    outBuffer.append(' ');
                    break;
                case '\t':
                    outBuffer.append('\\').append('t');
                    break;
                case '\n':
                    outBuffer.append('\\').append('n');
                    break;
                case '\r':
                    outBuffer.append('\\').append('r');
                    break;
                case '\f':
                    outBuffer.append('\\').append('f');
                    break;
                case '=':
                case ':':
                case '#':
                case '!':
                    outBuffer.append('\\').append(c);
                    break;
                default:
                    if (((c < 0x0020) || (c > 0x007e)) && escapeUnicode)
                    {
                        outBuffer.append('\\');
                        outBuffer.append('u');
                        outBuffer.append(Binary.toHexadecimalChar((c >> 12) & 0xF));
                        outBuffer.append(Binary.toHexadecimalChar((c >> 8) & 0xF));
                        outBuffer.append(Binary.toHexadecimalChar((c >> 4) & 0xF));
                        outBuffer.append(Binary.toHexadecimalChar((c >> 0) & 0xF));
                    }
                    else
                        outBuffer.append(c);
            }
        }

        return outBuffer.toString();
    }

    private static void writeComments(BufferedWriter writer, String comments) throws IOException
    {
        // TODO: Figure out if the following operation adds a redundant '#' if the given "comments" starts with '#'.
        // Write a comment marker.
        writer.write("#");

        // Length of comments.
        int commentsLength = comments.length();

        // Index of the current/next character to process.
        int currentIndex = 0;

        // Index of last ASCII character.
        int lastIndex = 0;

        // An array that represent the escape of a unicode character.
        char[] unicodeEscape = new char[6];
        unicodeEscape[0] = '\\';
        unicodeEscape[1] = 'u';

        while (currentIndex < commentsLength)
        {
            char c = comments.charAt(currentIndex);
            if ((c > '\u00ff') ||
                (c == '\n') ||
                (c == '\r'))
            {
                // If the program reaches here, then it reads a line splitter or a non-ASCII character.

                // Write the ASCII substring.
                if (lastIndex != currentIndex)
                    writer.write(comments.substring(lastIndex, currentIndex));

                // Convert escape if it is a non-ASCII character.
                if (c > '\u00ff')
                {
                    unicodeEscape[2] = Binary.toHexadecimalChar((c >> 12) & 0xF);
                    unicodeEscape[3] = Binary.toHexadecimalChar((c >>  8) & 0xF);
                    unicodeEscape[4] = Binary.toHexadecimalChar((c >>  4) & 0xF);
                    unicodeEscape[5] = Binary.toHexadecimalChar((c >>  0) & 0xF);

                    writer.write(new String(unicodeEscape));
                }
                else
                {
                    // If the program reaches here, then it reads a line splitter.
                    // So, we put a new line character in the output.
                    writer.newLine();

                    // Take care of CRLF ("\r\n").
                    if ((c == '\r') &&
                        (currentIndex != commentsLength - 1) &&
                        (comments.charAt(currentIndex + 1) == '\n'))
                        currentIndex++;

                    // Write comment header at the beginning of a new line.
                    if ((currentIndex == commentsLength - 1) ||
                        (comments.charAt(currentIndex + 1) != '#') && (comments.charAt(currentIndex + 1) != '!'))
                        writer.write("#");
                }

                // Update the start index of ASCII substring.
                lastIndex = currentIndex + 1;
            }

            // Get the index of the next character to process.
            currentIndex++;
        }

        // Write the end of the ASCII substring if there is one.
        if (lastIndex != currentIndex)
            writer.write(comments.substring(lastIndex, currentIndex));

        // Put a new line splitter so that the following contents will start at a new line, and will not be processed as comments.
        writer.newLine();
    }

    public synchronized void load(Reader reader) throws IOException
    {
        Objects.requireNonNull(reader, "The argument \"reader\" can not be null.");
        loadProperties(new PropertiesLineReader(reader));
    }

    public synchronized void load(InputStream inputStream) throws IOException
    {
        Objects.requireNonNull(inputStream, "The argument \"inputStream\" can not be null.");
        loadProperties(new PropertiesLineReader(inputStream));
    }

    private void loadProperties(PropertiesLineReader reader) throws IOException
    {
        StringBuilder outputBuffer = new StringBuilder();
        OutValue<Integer> currentLineLength = new OutValue<>(0);
        int keyLength;
        int valueStartIndex;
        boolean hasSeparator;
        boolean precedingBackslash;
        char[] lineBuffer;

        while ((lineBuffer = reader.readLine(currentLineLength)) != null)
        {
            keyLength = 0;
            valueStartIndex = currentLineLength.getValue();
            hasSeparator = false;

//            System.out.println("line=<" + new String(lineBuffer, 0, currentLineLength.getValue()) + ">");
            precedingBackslash = false;
            while (keyLength < currentLineLength.getValue())
            {
                char c = lineBuffer[keyLength];

                // Check if escaped.
                if ((c == '=') || (c == ':') && !precedingBackslash)
                {
                    valueStartIndex = keyLength + 1;
                    hasSeparator = true;
                    break;
                }
                else if (((c == ' ') || (c == '\t') || (c == '\f')) &&
                    !precedingBackslash)
                {
                    valueStartIndex = keyLength + 1;
                    break;
                }

                if (c == '\\')
                    precedingBackslash = !precedingBackslash;
                else
                    precedingBackslash = false;

                keyLength++;
            }

            // Note that we only need to find the start index of "value".
            while (valueStartIndex < currentLineLength.getValue())
            {
                char c = lineBuffer[valueStartIndex];
                if ((c != ' ') &&
                    (c != '\t') &&
                    (c != '\f'))
                {
                    if ((!hasSeparator) &&
                        (c == '=' || c == ':'))
                        hasSeparator = true;
                    else
                        break;
                }

                valueStartIndex++;
            }

            String key = loadConvert(lineBuffer, 0, keyLength, outputBuffer);
            String value = loadConvert(lineBuffer, valueStartIndex, currentLineLength.getValue() - valueStartIndex, outputBuffer);
            setProperty(key, value);
        }
    }

    public void save(String filePath)
    {

    }

    public void write(OutputStream outputStream)
    {

    }

    public void writeXml(OutputStream outputStream)
    {

    }

    public String toXml()
    {
        return null;
    }
}
