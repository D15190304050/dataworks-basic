package stark.dataworks.basic.io;

/**
 * The {@link SearchOption} enum specifies whether to search the current directory, or the current directory and all
 * sub-directories.
 * */
public enum SearchOption
{
    /**
     * Includes the current directory and all its subdirectories in a search operation. This option includes re-parse
     * points such as mounted drives and symbolic links in the search.
     * */
    ALL_DIRECTORIES,

    /**
     * Includes only the current directory in a search operation.
     * */
    TOP_DIRECTORY_ONLY
}
