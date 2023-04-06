package stark.dataworks.basic.io;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.AccessDeniedException;
import java.nio.file.DirectoryNotEmptyException;

/**
 * The {@link DirectoryInfo} class exposes instance methods for creating, moving, and enumerating through directories
 * and sub-directories. This class cannot be inherited.
 * */
public final class DirectoryInfo implements Serializable
{
    /**
     * Initializes a new instance of the {@link DirectoryInfo} class on the specified path.
     * @param path A string specifying the path on which to create the {@link DirectoryInfo}.
     * @exception NullPointerException {@code path} is null.
     * @exception SecurityException The caller does not have the required permission.
     * @exception IllegalArgumentException {@code path} contains invalid characters such as ", &lt;, &gt; or |.
     * */
    public DirectoryInfo(String path)
    {

    }

    /**
     * Creates a directory.
     * @exception IOException The directory cannot be created.
     * */
    public void create()
    {

    }

    /**
     * Creates a sub-directory or sub-directories on the specified path. The specified path can be relative to this instance.
     * @param path The specified path. This cannot be a different disk volume or Universal Naming Convention (UNC) name.
     * @return The last directory specified in path.
     * @exception IllegalArgumentException {@code path} does not specify a valid file path or contains invalid {@link DirectoryInfo} characters.
     * @exception NullPointerException {@code path} is null.
     * @exception SecurityException The caller does not have code access permission to create the directory. Or, The
     * caller does not have code access permission to read the directory described by the returned {@link DirectoryInfo}
     * object. This can occur when the path parameter describes an existing directory.
     * */
    public DirectoryInfo createSubDirectory(String path)
    {
        return null;
    }

    /**
     * Deletes this {@link DirectoryInfo} if it is empty.
     * @exception DirectoryNotEmptyException The directory to delete is not empty.
     * @exception DirectoryNotFoundException The directory described by this {@link DirectoryInfo} object does not
     * exist or could not be found.
     * @exception IOException The directory is the application's current working directory.
     * */
    public void delete()
    {

    }

    /**
     * Deletes this instance of a {@link DirectoryInfo}, specifying whether to delete sub-directories and files.
     * @param recursive {@code} true to delete this directory, its sub-directory, and all files; otherwise, false.
     * @exception DirectoryNotFoundException The directory described by this {@link DirectoryInfo} object does not
     * exist or could not be found.
     * @exception IOException The directory is read-only. Or, The directory contains one or more files or
     * subdirectories and recursive is false. Or, The directory is the application's current working directory.
     * */
    public void delete(boolean recursive)
    {

    }

    /**
     * Gets a value indicating whether the directory exists.
     * @return {@code true} if the directory exists; otherwise, {@code false}.
     * */
    public boolean exists()
    {
        return false;
    }

    /**
     * Gets the name of this {@link DirectoryInfo} instance.
     * @return The directory name.
     * */
    public String name()
    {
        return "";
    }

    /**
     * Gets the full name of this {@link DirectoryInfo} instance.
     * @return The full directory name.
     * */
    public String fullName()
    {
        return "";
    }

    /**
     * Gets the parent directory of a specified sub-directory.
     * @return The parent directory, or null if the path is null or if the directory path denotes a root (such as "\",
     * "C"", or "\\server\\share").
     * @exception SecurityException The caller does not have the required permission.
     * */
    public DirectoryInfo getParent()
    {
        return null;
    }

    /**
     * Gets the root portion of the directory.
     * @return An object that represents the root of the directory.
     * @exception SecurityException The caller does not have the required permission.
     * */
    public DirectoryInfo getRoot()
    {
        return null;
    }

    /**
     * Returns an iterable collection of directory information in the current directory.
     * @return An iterable collection of directories in the current directory.
     * @exception DirectoryNotFoundException The path encapsulated in the {@link DirectoryInfo} object is invalid.
     * @exception SecurityException The caller does not have the required permission.
     * */
    public Iterable<DirectoryInfo> enumerateDirectories()
    {
        return null;
    }

    /**
     * Returns an iterable collection of directory information that matches a specified search pattern.
     * @param searchPattern The search string to match against the names of directories. This parameter contains a
     *                      combination of valid literal path and wildcard (* and ?) characters (see Remarks), but
     *                      doesn't support regular expressions. Use "*" to returns all directories.
     * @return An iterable collection of directories that matches {@code searchPattern} in the current directory.
     * @exception NullPointerException {@code searchPattern} is null.
     * @exception DirectoryNotFoundException The path encapsulated in the {@link DirectoryInfo} object is invalid.
     * @exception SecurityException The caller does not have the required permission.
     * */
    public Iterable<DirectoryInfo> enumerateDirectories(String searchPattern)
    {
        return null;
    }

    /**
     * Returns an enumerable collection of directory information that matches a specified search pattern and search
     * sub-directory option.
     * @param searchPattern The search string to match against the names of directories. This parameter can contain a
     *                      combination of valid literal path and wildcard (* and ?) characters (see Remarks), but
     *                      doesn't support regular expressions. Use "*" to returns all directories.
     * @param searchOption One of the enumeration values that specifies whether the search operation should include
     *                     only the current directory or all subdirectories.
     * @return An iterable collection of directories that matches {@code searchPattern} and {@code searchOption} in the
     *         current directory.
     * @exception NullPointerException {@code searchPattern} is null.
     * @exception dataworks.ArgumentOutOfRangeException {@code searchOption} is not a valid {@link SearchOption} value.
     * @exception DirectoryNotFoundException The path encapsulated in the {@link DirectoryInfo} object is invalid.
     * @exception SecurityException The caller does not have the required permission.
     * */
    public Iterable<DirectoryInfo> enumerateDirectories(String searchPattern, SearchOption searchOption)
    {
        return null;
    }

    /**
     * Returns an iterable collection of file information in the current directory.
     * @return An iterable collection of file information in the current directory.
     * @exception DirectoryNotFoundException The path encapsulated in the {@link DirectoryInfo} object is invalid.
     * @exception SecurityException The caller does not have the required permission.
     * */
    public Iterable<FileInfo> enumerateFiles()
    {
        return null;
    }

    /**
     * Returns an iterable collection of file information that matches a search pattern.
     * @param searchPattern The search string to match against the names of files. This parameter can contain a
     *                      combination of valid literal path and wildcard (* and ?) characters (see Remarks), but
     *                      doesn't support regular expressions. Use "*" to returns all files.
     * @return An iterable collection of files that matches {@code searchPattern} in the current directory.
     * @exception NullPointerException {@code searchPattern} is null.
     * @exception DirectoryNotFoundException The path encapsulated in the {@link DirectoryInfo} object is invalid.
     * @exception SecurityException The caller does not have the required permission.
     * */
    public Iterable<FileInfo> enumerateFiles(String searchPattern)
    {
        return null;
    }

    /**
     * Returns an iterable collection of file information that matches a specified search pattern and search
     * sub-directory option.
     * @param searchPattern The search string to match against the names of directories. This parameter can contain a
     *                      combination of valid literal path and wildcard (* and ?) characters (see Remarks), but
     *                      doesn't support regular expressions. Use "*" to returns all files.
     * @param searchOption One of the enumeration values that specifies whether the search operation should include
     *                     only the current directory or all subdirectories.
     * @return An iterable collection of files that matches {@code searchPattern} and {@code searchOption} in the
     *         current directory.
     * @exception NullPointerException {@code searchPattern} is null.
     * @exception dataworks.ArgumentOutOfRangeException {@code searchOption} is not a valid {@link SearchOption} value.
     * @exception DirectoryNotFoundException The path encapsulated in the {@link DirectoryInfo} object is invalid.
     * @exception SecurityException The caller does not have the required permission.
     * */
    public Iterable<DirectoryInfo> enumerateFiles(String searchPattern, SearchOption searchOption)
    {
        return null;
    }

    /**
     * Returns the sub-directories of the current directory.
     * @return An array of directories in the current directory.
     * @exception DirectoryNotFoundException The path encapsulated in the {@link DirectoryInfo} object is invalid.
     * @exception SecurityException The caller does not have the required permission.
     * @exception AccessDeniedException The caller does not have the required permission.
     * */
    public DirectoryInfo[] getDirectories()
    {
        return null;
    }

    /**
     * Returns an array of directory information that matches a specified search pattern.
     * @param searchPattern The search string to match against the names of directories. This parameter contains a
     *                      combination of valid literal path and wildcard (* and ?) characters (see Remarks), but
     *                      doesn't support regular expressions. Use "*" to returns all directories.
     * @return An array of directories that matches {@code searchPattern} in the current directory.
     * @exception NullPointerException {@code searchPattern} is null.
     * @exception DirectoryNotFoundException The path encapsulated in the {@link DirectoryInfo} object is invalid.
     * @exception SecurityException The caller does not have the required permission.
     * */
    public DirectoryInfo[] getDirectories(String searchPattern)
    {
        return null;
    }

    /**
     * Returns an array of directory information that matches a specified search pattern and search
     * sub-directory option.
     * @param searchPattern The search string to match against the names of directories. This parameter can contain a
     *                      combination of valid literal path and wildcard (* and ?) characters (see Remarks), but
     *                      doesn't support regular expressions. Use "*" to returns all directories.
     * @param searchOption One of the enumeration values that specifies whether the search operation should include
     *                     only the current directory or all subdirectories.
     * @return An array of directories that matches {@code searchPattern} and {@code searchOption} in the current
     *         directory.
     * @exception NullPointerException {@code searchPattern} is null.
     * @exception dataworks.ArgumentOutOfRangeException {@code searchOption} is not a valid {@link SearchOption} value.
     * @exception DirectoryNotFoundException The path encapsulated in the {@link DirectoryInfo} object is invalid.
     * @exception SecurityException The caller does not have the required permission.
     * */
    public DirectoryInfo[] getDirectories(String searchPattern, SearchOption searchOption)
    {
        return null;
    }

    /**
     * Returns an array of file information in the current directory.
     * @return An iterable collection of file information in the current directory.
     * @exception DirectoryNotFoundException The path encapsulated in the {@link DirectoryInfo} object is invalid.
     * @exception SecurityException The caller does not have the required permission.
     * */
    public FileInfo[] getFiles()
    {
        return null;
    }

    /**
     * Returns an array of file information that matches a search pattern.
     * @param searchPattern The search string to match against the names of files. This parameter can contain a
     *                      combination of valid literal path and wildcard (* and ?) characters (see Remarks), but
     *                      doesn't support regular expressions. Use "*" to returns all files.
     * @return An array of files that matches {@code searchPattern} in the current directory.
     * @exception NullPointerException {@code searchPattern} is null.
     * @exception DirectoryNotFoundException The path encapsulated in the {@link DirectoryInfo} object is invalid.
     * @exception SecurityException The caller does not have the required permission.
     * */
    public FileInfo[] getFiles(String searchPattern)
    {
        return null;
    }

    /**
     * Returns an array of file information that matches a specified search pattern and search
     * sub-directory option.
     * @param searchPattern The search string to match against the names of directories. This parameter can contain a
     *                      combination of valid literal path and wildcard (* and ?) characters (see Remarks), but
     *                      doesn't support regular expressions. Use "*" to returns all files.
     * @param searchOption One of the enumeration values that specifies whether the search operation should include
     *                     only the current directory or all subdirectories.
     * @return An array of files that matches {@code searchPattern} and {@code searchOption} in the current directory.
     * @exception NullPointerException {@code searchPattern} is null.
     * @exception dataworks.ArgumentOutOfRangeException {@code searchOption} is not a valid {@link SearchOption} value.
     * @exception DirectoryNotFoundException The path encapsulated in the {@link DirectoryInfo} object is invalid.
     * @exception SecurityException The caller does not have the required permission.
     * */
    public FileInfo[] getFiles(String searchPattern, SearchOption searchOption)
    {
        return null;
    }

    /**
     * Moves a {@link DirectoryInfo} instance and its contents to a new path.
     * @param destDirPath The name and path to which to move this directory. The destination cannot be another disk
     *                    volume or a directory with the identical name. It can be an existing directory to which you
     *                    want to add this directory as a subdirectory.
     * @exception NullPointerException {@code destDirPath} is null.
     * @exception IllegalArgumentException {@code destDirPath} is an empty string ("").
     * @exception IOException An attempt was made to move a directory to a different volume. Or, {@code destDirPath}
     *                        already exists. Or, you are not authorized to access this path. Or, the directory being
     *                        moved and the destination directory have the same name.
     * @exception SecurityException The caller does not have the required permission.
     * @exception DirectoryNotFoundException The destination directory cannot be found.
     * */
    public void moveTo(String destDirPath)
    {

    }

    /**
     * Returns the original path that was passed by the user.
     * @return The original path that was passed by the user.
     * */
    @Override
    public String toString()
    {
        return super.toString();
    }
}
