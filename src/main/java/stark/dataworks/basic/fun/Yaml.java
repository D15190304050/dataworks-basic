package stark.dataworks.basic.fun;

import java.io.OutputStream;
import java.io.Reader;
import java.util.concurrent.ConcurrentHashMap;

public class Yaml
{
    private static final int INITIAL_CAPACITY = 8;

    /**
     * Key-value pairs are stored in this map. Synchronization is omitted from
     * simple read operations. Writes and bulk operations remain synchronized,
     * as in Hashtable.
     */
    private volatile ConcurrentHashMap<String, String> map;

    public Yaml()
    {
        this(INITIAL_CAPACITY);
    }

    public Yaml(int initialCapacity)
    {
        map = new ConcurrentHashMap<>(initialCapacity);
    }

    public synchronized void setProperty(String key, String value)
    {

    }

    public String getProperty(String key)
    {
        return null;
    }

    public Iterable<String> getPropertyNames()
    {
        return null;
    }

    public synchronized void load(String yamlText)
    {

    }

    public synchronized void load(Reader yamlReader)
    {

    }

    public void write(OutputStream outputStream)
    {

    }

    public void save(String filePath)
    {

    }

    public String toXml()
    {
        return "";
    }
}
