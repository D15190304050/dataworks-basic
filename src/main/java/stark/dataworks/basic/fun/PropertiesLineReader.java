package stark.dataworks.basic.fun;

import stark.dataworks.basic.params.OutValue;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Objects;

/**
 * The {@link PropertiesLineReader} class provides a method that reads a "logical line" from an {@link InputStream}/{@link Reader},
 * skip all comment and blank lines and filter out those leading whitespace characters (\u0020, \u0009, \u000c) from the beginning
 * of a "natural line".<br/>
 * The {@link PropertiesLineReader#readLine(OutValue <Integer>)} ()} method assumes that all logical lines contain only at most 1024 characters.
 */
public class PropertiesLineReader
{
    /**
     * Capacity of the input buffer in byte.
     */
    private static final int INPUT_BUFFER_CAPACITY = 8192;

    /**
     * Initial capacity of line buffer.
     */
    private static final int LINE_BUFFER_INITIAL_CAPACITY = 1024;

    /**
     * A char buffer that contains contents read by the reader.
     */
    private char[] lineBuffer;

    /**
     * The reader that reads contents (properties) from input.
     */
    private final Reader reader;

    /**
     * Character buffer that contains what the reader reads.
     */
    private final char[] inputBuffer;

    /**
     * The index of the next character to read in the input buffer.
     */
    private int inputBufferOffset;

    /**
     * The number of characters in the input buffer, determined by the Reader#read() method.
     */
    private int inputLimit;

    public PropertiesLineReader(InputStream inputStream)
    {
        this(new InputStreamReader(inputStream));
    }

    public PropertiesLineReader(Reader reader)
    {
        Objects.requireNonNull(reader, "The argument \"inputStream\" can not be null.");
        this.reader = reader;
        this.lineBuffer = new char[LINE_BUFFER_INITIAL_CAPACITY];
        this.inputBuffer = new char[INPUT_BUFFER_CAPACITY];
        this.inputBufferOffset = 0;
        this.inputLimit = 0;
    }

    public char[] readLine(OutValue<Integer> currentLineLength) throws IOException
    {
        Objects.requireNonNull(currentLineLength, "The argument \"currentLineLength\" can not be null.");

        // Length of the current logical line.
        currentLineLength.setValue(0);

        // Whether we need to skip white space.
        // Only when the white spaces that are part of the value are remained.
        boolean skipWhiteSpace = true;

        boolean appendedLineBegin = false;

        // True if the previous read character is a backslash; otherwise, false.
        // All backslashes should be taken care of.
        boolean precedingBackslash = false;

        // The next character to process.
        char c;

        for (; ; )
        {
            // Fill the input buffer if all characters in the buffer is processed.
            if (inputBufferOffset >= inputLimit)
            {
                // Try to read next batch of characters.
                inputLimit = reader.read(inputBuffer);

                // Deal with EOF.
                if (inputLimit <= 0)
                {
                    // Return -1 if EOF is reached and nothing is processed.
                    // If the program reaches here, then there is nothing to read in the file.
                    // And if the current line length is 0, then there is nothing read.
                    if (currentLineLength.getValue() == 0)
                        return null;

                    // If the current line length is greater than 0, then at least something is read.
                    if (precedingBackslash)
                        currentLineLength.setValue(currentLineLength.getValue() - 1);
                    return lineBuffer;
                }

                // Reset the input buffer offset to read from the beginning of the buffer.
                inputBufferOffset = 0;
            }

            // Get next character to process.
            c = inputBuffer[inputBufferOffset++];

            if (skipWhiteSpace)
            {
                if ((c == ' ') ||
                    (c == '\t') ||
                    (c == '\f'))
                    continue;

                if ((!appendedLineBegin) && ((c == '\r') || (c == '\n')))
                    continue;

                // If the program reaches here, then it meets a non-whitespace character, then skip white space is unnecessary for now.
                skipWhiteSpace = false;
                appendedLineBegin = false;
            }

            // currentLineLength == 0 means that we are dealing with a new logical line.
            if (currentLineLength.getValue() == 0)
            {
                // Quick consume the rest of a comment line.
                // When checking for new line characters a range check, starting with the higher bound ('\r') means
                // one less branch in the common case.
                // i.e. If there is a new line character, then the code of the character must be less than '\r'.
                if ((c == '#') || (c == '!'))
                {
                    consumeCommentLoop:
                    for (; ; )
                    {
                        while (inputBufferOffset < inputLimit)
                        {
                            c = inputBuffer[inputBufferOffset++];
                            if ((c <= '\r') &&
                                (c == '\r' || c == '\n'))
                                break consumeCommentLoop;

                            // Read next batch of content to process if all characters in the current input character buffer is processed.
                            if (inputBufferOffset == inputLimit)
                            {
                                inputLimit = reader.read(inputBuffer);

                                // Return -1 if EOF is reached.
                                if (inputLimit <= 0)
                                    return null;

                                inputBufferOffset = 0;
                            }
                        }
                    }

                    // If the program reaches here, then it starts to process a new natural line.
                    // It means that the program should skip all leading white spaces at the beginning of the line.
                    skipWhiteSpace = true;
                    continue;
                }
            }

            // If 'c' is not a line separator, then c is part of the current line, which means that we need to store 'c'.
            if ((c != '\n') && (c != '\r'))
            {
                // Store the next char.
                lineBuffer[currentLineLength.getValue()] = c;
                currentLineLength.setValue(currentLineLength.getValue() + 1);

                // Double the capacity of the current line buffer if it is full and there is still new character to read in this logical line.
                if (currentLineLength.getValue() == lineBuffer.length)
                {
                    char[] newLineBuffer = new char[lineBuffer.length * 2];
                    System.arraycopy(lineBuffer, 0, newLineBuffer, 0, lineBuffer.length);
                    lineBuffer = newLineBuffer;
                }

                // Flip the preceding backslash flag.
                //precedingBackslash = (c == '\\') ? !precedingBackslash : false;
                precedingBackslash = c == '\\' && !precedingBackslash;
            }
            else
            {
                // Note that condition, if the program reaches here, then 'c' is a line separator.

                // Reached end of a natural line, try to process next natural line.
                // Nothing is in the input buffer, so we need to take care of the next natural line.
                if (currentLineLength.getValue() == 0)
                {
                    skipWhiteSpace = true;
                    continue;
                }

                // If the program reaches here, then we have currentLineLength >= 0.
                // This means that the input buffer contains at least 1 character.
                // And we have a line separator now.

                // Fill the input buffer if all characters in the buffer is processed.
                // We need to perform a fill operation here because we may need to read the next character later in this branch.
                if (inputBufferOffset >= inputLimit)
                {
                    // Try to read next batch of characters.
                    inputLimit = reader.read(inputBuffer);

                    // Return the length of processed content of this logical line if reached EOF.
                    if (inputLimit <= 0)
                    {
                        if (precedingBackslash)
                            currentLineLength.setValue(currentLineLength.getValue() - 1);

                        return lineBuffer;
                    }

                    // Reset the input buffer offset to read from the beginning of the buffer.
                    inputBufferOffset = 0;
                }

                // If there is a preceding backslash, then we need to take care of the next natural line.
                // Otherwise, we meet the end of a logical line, then we can return the length of the logical line.
                if (precedingBackslash)
                {
                    // Backslash at EOL is not part of the line.
                    currentLineLength.setValue(currentLineLength.getValue() - 1);

                    // Skip leading whitespace characters in the following line.
                    skipWhiteSpace = true;
                    appendedLineBegin = true;
                    precedingBackslash = false;

                    // Take care not to include any subsequent \n.
                    if ((c == '\r') && (inputBuffer[inputBufferOffset] == '\n'))
                        inputBufferOffset++;
                }
                else
                    return lineBuffer;
            }
        }
    }
}