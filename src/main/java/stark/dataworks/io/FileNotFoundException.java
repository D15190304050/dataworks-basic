package stark.dataworks.io;

import java.io.IOException;

public class FileNotFoundException extends IOException
{
    public FileNotFoundException(String message)
    {
        super(message);
    }
}
