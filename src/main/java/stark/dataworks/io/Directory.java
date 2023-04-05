package stark.dataworks.io;

import stark.dataworks.*;
import stark.dataworks.datetime.*;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

/**
 * The {@link Directory} class Exposes static methods for creating, moving, and enumerating through directories and
 * sub-directories. This class cannot be inherited.
 * */
public final class Directory
{
    /**
     * This class should not be instantiated since it is designed to provide static methods only.
     * */
    private Directory(){}

    /**
     * Creates all directories and sub-directories in the specified path unless they already exist.
     * @param path The full path of the directory to create.
     * @return An object that represents the directory at the specified path. This object is returned regardless of
     * whether a directory at the specified path already exists.
     * @exception IOException The directory specified by {@code path} is a file. Or, the network name is
     * unknown.
     * @exception AccessDeniedException The caller does not have the required permission.
     * @exception IllegalArgumentException {@code path} is a zero-length string, contains only white space, or contains
     * one or more invalid characters.
     * @exception NullPointerException {@code path} is null.
     * @exception DirectoryNotFoundException The specified path is invalid (for example, it is on a unmapped drive).
     * @exception PathTooLongException The specified path, file name or both exceed the system-defined maximum length.
     * For example, on Windows-based platforms, paths must be less than 248 characters and file names must be less than
     * 260 characters.
     * */
    public static DirectoryInfo createDirectory(String path)
    {
        return null;
    }

    /**
     * Deletes an empty directory from a specified path.
     * @param path The name of the empty directory to remove. This directory must be writable and empty.
     * @exception IOException The specified path represents a file. Or, the directory is the application's current
     * working directory. Or, the directory specified by path is not empty. Or, the directory is read-only or contains a
     * read-only file. Or, the directory is being used by another process.
     * @exception AccessDeniedException The caller does not have the required permission.
     * @exception IllegalArgumentException {@code path} is a zero-length string, contains only white space, or contains
     * one or more invalid characters.
     * @exception NullPointerException {@code path} is null.
     * @exception DirectoryNotFoundException {@code path} does not exist or could not be found. Or, the specified path
     * is invalid (for example, it is on an unmapped drive).
     * @exception PathTooLongException The specified path, file name or both exceed the system-defined maximum length.
     * For example, on Windows-based platforms, paths must be less than 248 characters and file names must be less than
     * 260 characters.
     * */
    public static void delete(String path)
    {

    }

    /**
     * Deletes the specified directory and, if indicated, and sub-directories and files in the directory.
     * @param path The name of the empty directory to remove. This directory must be writable and empty.
     * @param recursive {@code true} to remove directories, sub-directories, and files in path; otherwise, false.
     * @exception IOException The specified path represents a file. Or, the directory is the application's current
     * working directory. Or, {@code recursive} is false and the directory specified by path is not empty. Or, the
     * directory is read-only or contains a read-only file. Or, the directory is being used by another process.
     * @exception AccessDeniedException The caller does not have the required permission.
     * @exception IllegalArgumentException {@code path} is a zero-length string, contains only white space, or contains
     * one or more invalid characters.
     * @exception NullPointerException {@code path} is null.
     * @exception DirectoryNotFoundException {@code path} does not exist or could not be found. Or, the specified path
     * is invalid (for example, it is on an unmapped drive).
     * @exception PathTooLongException The specified path, file name or both exceed the system-defined maximum length.
     * For example, on Windows-based platforms, paths must be less than 248 characters and file names must be less than
     * 260 characters.
     * */
    public static void delete(String path, boolean recursive)
    {

    }

    /**
     * Returns an iterable collection of directory names in a specified path.
     * @param path The relative or absolute path to the directory to search. This string is not case-sensitive.
     * @return An iterable collection of the full names (including paths) for the directories in the directory
     * specified by {@code path}.
     * @exception IllegalArgumentException {@code path}  is a zero-length string, contains only white space, or
     * contains invalid characters.
     * @exception NullPointerException {@code path} is null.
     * @exception DirectoryNotFoundException {@code path} does not exist or could not be found. Or, the specified path
     * is invalid (for example, it is on an unmapped drive).
     * @exception IOException The specified path represents a file.
     * @exception PathTooLongException The specified path, file name or both exceed the system-defined maximum length.
     * For example, on Windows-based platforms, paths must be less than 248 characters and file names must be less than
     * 260 characters.
     * @exception SecurityException The caller does not have the required permission.
     * @exception AccessDeniedException The caller does not have the required permission.
     * */
    public static Iterable<String> enumerateDirectories(String path)
    {
        return null;
    }

    /**
     * Returns an iterable collection of directory names that match a search pattern in a specified path.
     * @param path The relative or absolute path to the directory to search. This string is not case-sensitive.
     * @param searchPattern The search string to match against the names of directories in {@code path}. This parameter
     *                      can contain a combination of valid literal path and wildcard (* and ?) characters, but
     *                      doesn't support regular expressions.
     * @return An iterable collection of the full names (including paths) for the directories in the directory
     * specified by {@code path} and that match the specified search pattern.
     * @exception IllegalArgumentException {@code path}  is a zero-length string, contains only white space, or
     * contains invalid characters.
     * @exception NullPointerException {@code path} is null.
     * @exception DirectoryNotFoundException {@code path} does not exist or could not be found. Or, the specified path
     * is invalid (for example, it is on an unmapped drive).
     * @exception IOException The specified path represents a file.
     * @exception PathTooLongException The specified path, file name or both exceed the system-defined maximum length.
     * For example, on Windows-based platforms, paths must be less than 248 characters and file names must be less than
     * 260 characters.
     * @exception SecurityException The caller does not have the required permission.
     * @exception AccessDeniedException The caller does not have the required permission.
     * */
    public static Iterable<String> enumerateDirectories(String path, String searchPattern)
    {
        return null;
    }

    /**
     * Returns an iterable collection of directory names that match a search pattern in a specified path, and optionally
     * searches sub-directories.
     * @param path The relative or absolute path to the directory to search. This string is not case-sensitive.
     * @param searchPattern The search string to match against the names of directories in {@code path}. This parameter
     *                      can contain a combination of valid literal path and wildcard (* and ?) characters, but
     *                      doesn't support regular expressions.
     * @param searchOption One of the enumeration values that specifies whether the search operation should include
     *                     only the current directory or should include all subdirectories.
     * @return An iterable collection of the full names (including paths) for the directories in the directory
     * specified by {@code path} and that match the specified search pattern and option.
     * @exception IllegalArgumentException {@code path}  is a zero-length string, contains only white space, or
     * contains invalid characters.
     * @exception NullPointerException {@code path} is null.
     * @exception DirectoryNotFoundException {@code path} does not exist or could not be found. Or, the specified path
     * is invalid (for example, it is on an unmapped drive).
     * @exception IOException The specified path represents a file.
     * @exception PathTooLongException The specified path, file name or both exceed the system-defined maximum length.
     * For example, on Windows-based platforms, paths must be less than 248 characters and file names must be less than
     * 260 characters.
     * @exception SecurityException The caller does not have the required permission.
     * @exception AccessDeniedException The caller does not have the required permission.
     * */
    public static Iterable<String> enumerateDirectories(String path, String searchPattern, SearchOption searchOption)
    {
        return null;
    }

    /**
     * Returns an iterable collection of file names in a specified path.
     * @param path The relative or absolute path to the directory to search. This string is not case-sensitive.
     * @return An iterable collection of the full names (including paths) for the files in the directory
     * specified by {@code path}.
     * @exception IllegalArgumentException {@code path}  is a zero-length string, contains only white space, or
     * contains invalid characters.
     * @exception NullPointerException {@code path} is null.
     * @exception DirectoryNotFoundException {@code path} does not exist or could not be found. Or, the specified path
     * is invalid (for example, it is on an unmapped drive).
     * @exception IOException The specified path represents a file.
     * @exception PathTooLongException The specified path, file name or both exceed the system-defined maximum length.
     * For example, on Windows-based platforms, paths must be less than 248 characters and file names must be less than
     * 260 characters.
     * @exception SecurityException The caller does not have the required permission.
     * @exception AccessDeniedException The caller does not have the required permission.
     * */
    public static Iterable<String> enumerateFiles(String path)
    {
        return null;
    }

    /**
     * Returns an iterable collection of file names that match a search pattern in a specified path.
     * @param path The relative or absolute path to the directory to search. This string is not case-sensitive.
     * @param searchPattern The search string to match against the names of directories in {@code path}. This parameter
     *                      can contain a combination of valid literal path and wildcard (* and ?) characters, but
     *                      doesn't support regular expressions.
     * @return An iterable collection of the full names (including paths) for the files in the directory
     * specified by {@code path} and that match the specified search pattern.
     * @exception IllegalArgumentException {@code path}  is a zero-length string, contains only white space, or
     * contains invalid characters.
     * @exception NullPointerException {@code path} is null.
     * @exception DirectoryNotFoundException {@code path} does not exist or could not be found. Or, the specified path
     * is invalid (for example, it is on an unmapped drive).
     * @exception IOException The specified path represents a file.
     * @exception PathTooLongException The specified path, file name or both exceed the system-defined maximum length.
     * For example, on Windows-based platforms, paths must be less than 248 characters and file names must be less than
     * 260 characters.
     * @exception SecurityException The caller does not have the required permission.
     * @exception AccessDeniedException The caller does not have the required permission.
     * */
    public static Iterable<String> enumerateFiles(String path, String searchPattern)
    {
        return null;
    }

    /**
     * Returns an iterable collection of file names that match a search pattern in a specified path, and optionally
     * searches sub-directories.
     * @param path The relative or absolute path to the directory to search. This string is not case-sensitive.
     * @param searchPattern The search string to match against the names of directories in {@code path}. This parameter
     *                      can contain a combination of valid literal path and wildcard (* and ?) characters, but
     *                      doesn't support regular expressions.
     * @param searchOption One of the enumeration values that specifies whether the search operation should include
     *                     only the current directory or should include all subdirectories.
     * @return An iterable collection of the full names (including paths) for the files in the directory
     * specified by {@code path} and that match the specified search pattern and option.
     * @exception IllegalArgumentException {@code path}  is a zero-length string, contains only white space, or
     * contains invalid characters.
     * @exception NullPointerException {@code path} is null.
     * @exception DirectoryNotFoundException {@code path} does not exist or could not be found. Or, the specified path
     * is invalid (for example, it is on an unmapped drive).
     * @exception IOException The specified path represents a file.
     * @exception PathTooLongException The specified path, file name or both exceed the system-defined maximum length.
     * For example, on Windows-based platforms, paths must be less than 248 characters and file names must be less than
     * 260 characters.
     * @exception SecurityException The caller does not have the required permission.
     * @exception AccessDeniedException The caller does not have the required permission.
     * */
    public static Iterable<String> enumerateFiles(String path, String searchPattern, SearchOption searchOption)
    {
        return null;
    }

    /**
     * Determines whether the given path refers to an existing directory on disk.
     * @param path The {@code path} to test.
     * @return {@code true} if {@code path} refers to an existing directory; {@code false} if the directory does not
     * exist or an error occurs when trying to determine if the specified directory exists.
     * */
    public static boolean exists(String path)
    {
        return false;
    }

    /**
     * Gets the creation date and time of a directory.
     * @param path The path of the directory.
     * @return An instance of {@link DateTime} that is set to the creation date and time for the specified directory.
     * This value is expressed in local time.
     * @exception AccessDeniedException The caller does not have the required permission.
     * @exception IllegalArgumentException {@code path} is a zero-length string, contains only white space, or contains
     * one or more invalid characters.
     * @exception NullPointerException {@code path} is null.
     * @exception PathTooLongException The specified path, file name, or both exceed the system-defined maximum length.
     * For example, on Windows-based platforms, paths must be less than 248 characters and file names must be less than
     * 260 characters.
     * */
    public static DateTime getCreationTime(String path)
    {
        return null;
    }

    /**
     * Gets the creation date and time, in Coordinated Universal Time (UTC) format, of a directory.
     * @param path The path of the directory.
     * @return An instance of {@link DateTime} that is set to the creation date and time for the specified directory.
     * This time is expressed in UTC time.
     * @exception AccessDeniedException The caller does not have the required permission.
     * @exception IllegalArgumentException {@code path} is a zero-length string, contains only white space, or contains
     * one or more invalid characters.
     * @exception NullPointerException {@code path} is null.
     * @exception PathTooLongException The specified path, file name, or both exceed the system-defined maximum length.
     * For example, on Windows-based platforms, paths must be less than 248 characters and file names must be less than
     * 260 characters.
     * */
    public static DateTime getCreationTimeUtc(String path)
    {
        return null;
    }

    /**
     * Gets the current working directory of the application.
     * @return A string that contains the path of the current working directory, and does not end with a backslash(\).
     * @exception AccessDeniedException The caller does not have the required permission.
     * */
    public static String getCurrentDirectory()
    {
        return null;
    }

    /**
     * Returns an array of directory names in a specified path.
     * @param path The relative or absolute path to the directory to search. This string is not case-sensitive.
     * @return An array of the full names (including paths) for the directories in the directory
     * specified by {@code path}.
     * @exception IllegalArgumentException {@code path}  is a zero-length string, contains only white space, or
     * contains invalid characters.
     * @exception NullPointerException {@code path} is null.
     * @exception DirectoryNotFoundException {@code path} does not exist or could not be found. Or, the specified path
     * is invalid (for example, it is on an unmapped drive).
     * @exception IOException The specified path represents a file.
     * @exception PathTooLongException The specified path, file name or both exceed the system-defined maximum length.
     * For example, on Windows-based platforms, paths must be less than 248 characters and file names must be less than
     * 260 characters.
     * @exception SecurityException The caller does not have the required permission.
     * @exception AccessDeniedException The caller does not have the required permission.
     * */
    public static String[] getDirectories(String path)
    {
        return null;
    }

    /**
     * Returns an array of directory names that match a search pattern in a specified path.
     * @param path The relative or absolute path to the directory to search. This string is not case-sensitive.
     * @param searchPattern The search string to match against the names of directories in {@code path}. This parameter
     *                      can contain a combination of valid literal path and wildcard (* and ?) characters, but
     *                      doesn't support regular expressions.
     * @return An array of the full names (including paths) for the directories in the directory
     * specified by {@code path} and that match the specified search pattern.
     * @exception IllegalArgumentException {@code path}  is a zero-length string, contains only white space, or
     * contains invalid characters.
     * @exception NullPointerException {@code path} is null.
     * @exception DirectoryNotFoundException {@code path} does not exist or could not be found. Or, the specified path
     * is invalid (for example, it is on an unmapped drive).
     * @exception IOException The specified path represents a file.
     * @exception PathTooLongException The specified path, file name or both exceed the system-defined maximum length.
     * For example, on Windows-based platforms, paths must be less than 248 characters and file names must be less than
     * 260 characters.
     * @exception SecurityException The caller does not have the required permission.
     * @exception AccessDeniedException The caller does not have the required permission.
     * */
    public static String[] getDirectories(String path, String searchPattern)
    {
        return null;
    }

    /**
     * Returns an array of directory names that match a search pattern in a specified path, and optionally
     * searches sub-directories.
     * @param path The relative or absolute path to the directory to search. This string is not case-sensitive.
     * @param searchPattern The search string to match against the names of directories in {@code path}. This parameter
     *                      can contain a combination of valid literal path and wildcard (* and ?) characters, but
     *                      doesn't support regular expressions.
     * @param searchOption One of the enumeration values that specifies whether the search operation should include
     *                     only the current directory or should include all subdirectories.
     * @return An array of the full names (including paths) for the directories in the directory.
     * specified by {@code path} and that match the specified search pattern and option.
     * @exception IllegalArgumentException {@code path}  is a zero-length string, contains only white space, or
     * contains invalid characters.
     * @exception NullPointerException {@code path} is null.
     * @exception DirectoryNotFoundException {@code path} does not exist or could not be found. Or, the specified path
     * is invalid (for example, it is on an unmapped drive).
     * @exception IOException The specified path represents a file.
     * @exception PathTooLongException The specified path, file name or both exceed the system-defined maximum length.
     * For example, on Windows-based platforms, paths must be less than 248 characters and file names must be less than
     * 260 characters.
     * @exception SecurityException The caller does not have the required permission.
     * @exception AccessDeniedException The caller does not have the required permission.
     * */
    public static String[] getDirectories(String path, String searchPattern, SearchOption searchOption)
    {
        return null;
    }

    /**
     * Returns the volume information, root information, or both for the specified path.
     * @param path The path of a file or directory.
     * @return A string that contains the volume information, root information, or both for the specified path.
     * @exception IllegalArgumentException {@code path}  is a zero-length string, contains only white space, or
     * contains invalid characters.
     * @exception NullPointerException {@code path} is null.
     * @exception IOException The specified path represents a file.
     * @exception PathTooLongException The specified path, file name or both exceed the system-defined maximum length.
     * For example, on Windows-based platforms, paths must be less than 248 characters and file names must be less than
     * 260 characters.
     * @exception SecurityException The caller does not have the required permission.
     * @exception AccessDeniedException The caller does not have the required permission.
     * */
    public static String getDirectoryRoot(String path)
    {
        return null;
    }

    /**
     * Returns an array of file names in a specified path.
     * @param path The relative or absolute path to the directory to search. This string is not case-sensitive.
     * @return An array of the full names (including paths) for the files in the directory
     * specified by {@code path}.
     * @exception IllegalArgumentException {@code path}  is a zero-length string, contains only white space, or
     * contains invalid characters.
     * @exception NullPointerException {@code path} is null.
     * @exception DirectoryNotFoundException {@code path} does not exist or could not be found. Or, the specified path
     * is invalid (for example, it is on an unmapped drive).
     * @exception IOException The specified path represents a file.
     * @exception PathTooLongException The specified path, file name or both exceed the system-defined maximum length.
     * For example, on Windows-based platforms, paths must be less than 248 characters and file names must be less than
     * 260 characters.
     * @exception SecurityException The caller does not have the required permission.
     * @exception AccessDeniedException The caller does not have the required permission.
     * */
    public static String[] getFiles(String path)
    {
        return null;
    }

    /**
     * Returns an array of file names that match a search pattern in a specified path.
     * @param path The relative or absolute path to the directory to search. This string is not case-sensitive.
     * @param searchPattern The search string to match against the names of directories in {@code path}. This parameter
     *                      can contain a combination of valid literal path and wildcard (* and ?) characters, but
     *                      doesn't support regular expressions.
     * @return An array of the full names (including paths) for the files in the directory
     * specified by {@code path} and that match the specified search pattern.
     * @exception IllegalArgumentException {@code path}  is a zero-length string, contains only white space, or
     * contains invalid characters.
     * @exception NullPointerException {@code path} is null.
     * @exception DirectoryNotFoundException {@code path} does not exist or could not be found. Or, the specified path
     * is invalid (for example, it is on an unmapped drive).
     * @exception IOException The specified path represents a file.
     * @exception PathTooLongException The specified path, file name or both exceed the system-defined maximum length.
     * For example, on Windows-based platforms, paths must be less than 248 characters and file names must be less than
     * 260 characters.
     * @exception SecurityException The caller does not have the required permission.
     * @exception AccessDeniedException The caller does not have the required permission.
     * */
    public static String[] getFiles(String path, String searchPattern)
    {
        return null;
    }

    /**
     * Returns an array of file names that match a search pattern in a specified path, and optionally
     * searches sub-directories.
     * @param path The relative or absolute path to the directory to search. This string is not case-sensitive.
     * @param searchPattern The search string to match against the names of directories in {@code path}. This parameter
     *                      can contain a combination of valid literal path and wildcard (* and ?) characters, but
     *                      doesn't support regular expressions.
     * @param searchOption One of the enumeration values that specifies whether the search operation should include
     *                     only the current directory or should include all subdirectories.
     * @return An array of the full names (including paths) for the files in the directory
     * specified by {@code path} and that match the specified search pattern and option.
     * @exception IllegalArgumentException {@code path}  is a zero-length string, contains only white space, or
     * contains invalid characters.
     * @exception NullPointerException {@code path} is null.
     * @exception DirectoryNotFoundException {@code path} does not exist or could not be found. Or, the specified path
     * is invalid (for example, it is on an unmapped drive).
     * @exception IOException The specified path represents a file.
     * @exception PathTooLongException The specified path, file name or both exceed the system-defined maximum length.
     * For example, on Windows-based platforms, paths must be less than 248 characters and file names must be less than
     * 260 characters.
     * @exception SecurityException The caller does not have the required permission.
     * @exception AccessDeniedException The caller does not have the required permission.
     * */
    public static String[] getFiles(String path, String searchPattern, SearchOption searchOption)
    {
        return null;
    }

    /**
     * Returns the date and time the specified file or directory was last accessed.
     * @param path The file or directory for which to obtain access date and time information.
     * @return An instance of {@link DateTime} that is set to the creation date and time for the specified directory.
     * This value is expressed in local time.
     * @exception AccessDeniedException The caller does not have the required permission.
     * @exception IllegalArgumentException {@code path} is a zero-length string, contains only white space, or contains
     * one or more invalid characters.
     * @exception NullPointerException {@code path} is null.
     * @exception PathTooLongException The specified path, file name, or both exceed the system-defined maximum length.
     * For example, on Windows-based platforms, paths must be less than 248 characters and file names must be less than
     * 260 characters.
     * */
    public static DateTime getLastAccessTime(String path)
    {
        return null;
    }

    /**
     * Returns the date and time, in Coordinated Universal Time (UTC) format, that the specified file or directory was
     * last accessed.
     * @param path The file or directory for which to obtain access date and time information. This value is expressed
     *             in UTC time.
     * @return An instance of {@link DateTime} that is set to the creation date and time for the specified directory.
     * This value is expressed in local time.
     * @exception AccessDeniedException The caller does not have the required permission.
     * @exception IllegalArgumentException {@code path} is a zero-length string, contains only white space, or contains
     * one or more invalid characters.
     * @exception NullPointerException {@code path} is null.
     * @exception PathTooLongException The specified path, file name, or both exceed the system-defined maximum length.
     * For example, on Windows-based platforms, paths must be less than 248 characters and file names must be less than
     * 260 characters.
     * */
    public static DateTime getLastAccessTimeUtc(String path)
    {
        return null;
    }

    /**
     * Returns the date and time the specified file or directory was last written to.
     * @param path The file or directory for which to obtain modification date and time information.
     * @return An instance of {@link DateTime} that is set to the date and time the specified file or directory was
     * last accessed. This value is expressed in local time.
     * @exception AccessDeniedException The caller does not have the required permission.
     * @exception IllegalArgumentException {@code path} is a zero-length string, contains only white space, or contains
     * one or more invalid characters.
     * @exception NullPointerException {@code path} is null.
     * @exception PathTooLongException The specified path, file name, or both exceed the system-defined maximum length.
     * For example, on Windows-based platforms, paths must be less than 248 characters and file names must be less than
     * 260 characters.
     * */
    public static DateTime getLastWriteTime(String path)
    {
        return null;
    }

    /**
     * Returns the date and time, in Coordinated Universal Time (UTC) format, that the specified file or directory was
     * last written to.
     * @param path The file or directory for which to obtain modification date and time information.
     * @return An instance of {@link DateTime} that is set to the date and time the specified file or directory was
     * last accessed. This value is expressed in UTC time.
     * @exception AccessDeniedException The caller does not have the required permission.
     * @exception IllegalArgumentException {@code path} is a zero-length string, contains only white space, or contains
     * one or more invalid characters.
     * @exception NullPointerException {@code path} is null.
     * @exception PathTooLongException The specified path, file name, or both exceed the system-defined maximum length.
     * For example, on Windows-based platforms, paths must be less than 248 characters and file names must be less than
     * 260 characters.
     * */
    public static DateTime getLastWriteTimeUtc(String path)
    {
        return null;
    }

    /**
     * Retrieves the parent directory of the specified path, including both absolute and relative paths.
     * @param path The path for which to retrieve the parent directory.
     * @return The parent directory, or null if the path if the root directory, including the root of a UNC server or
     * share time.
     * @exception IOException The directory specified by {@code path} is read only.
     * @exception AccessDeniedException The caller does not have the required permission.
     * @exception IllegalArgumentException {@code path} is a zero-length string, contains only white space, or contains
     * one or more invalid characters.
     * @exception NullPointerException {@code path} is null.
     * @exception PathTooLongException The specified path, file name, or both exceed the system-defined maximum length.
     * For example, on Windows-based platforms, paths must be less than 248 characters and file names must be less than
     * 260 characters.
     * */
    public static DirectoryInfo getParent(String path)
    {
        return null;
    }

    /**
     * Moves a file or directory and its contents to a new location.
     * @param sourceDirName The path of the file or directory to move.
     * @param destDirName The path to the new location for {@code sourceDirName}. If {@code sourceDirName} is a file,
     *                    then {@code destDirName} must also be a file name.
     * @exception IOException An attempt was made to move a directory to a different volume. Or, {@code destDirName}
     * already exists. Or, the {@code sourceDirName} and {@code destDirName} parameters refer to the same file or
     * directory. Or, the directory or a file within it is being used by another process.
     * @exception AccessDeniedException The caller does not have the required permission.
     * @exception IllegalArgumentException {@code path} is a zero-length string, contains only white space, or contains
     * one or more invalid characters.
     * @exception NullPointerException {@code path} is null.
     * @exception PathTooLongException The specified path, file name, or both exceed the system-defined maximum length.
     * For example, on Windows-based platforms, paths must be less than 248 characters and file names must be less than
     * 260 characters.
     * @exception DirectoryNotFoundException The path specified by {@code sourceDirName} is invalid (for example, it is
     * on an unmapped drive).
     * */
    public static void Move (String sourceDirName, String destDirName)
    {

    }

    /**
     * Sets the creation date and time for the specified file or directory.
     * @param path The file or directory for which to set the creation date and time information.
     * @param creationTime The date and time the file or directory was last written to. This value is expressed in
     *                     local time.
     * @exception IllegalArgumentException {@code path} is a zero-length string, contains only white space, or contains
     * one or more invalid characters.
     * @exception NullPointerException {@code path} or {@code creationTime} is null.
     * @exception PathTooLongException The specified path, file name, or both exceed the system-defined maximum length.
     * For example, on Windows-based platforms, paths must be less than 248 characters and file names must be less than
     * 260 characters.
     * @exception DirectoryNotFoundException The path specified by {@code sourceDirName} is invalid (for example, it is
     * on an unmapped drive).
     * @exception AccessDeniedException The caller does not have the required permission.
     * @exception ArgumentOutOfRangeException {@code creationTime} specifies a value outside the range of dates
     * or times permitted for this operation.
     * */
    public static void setCreationTime(String path, DateTime creationTime)
    {

    }

    /**
     * Sets the creation date and time, in Coordinated Universal Time (UTC) format, for the specified file or directory.
     * @param path The file or directory for which to set the creation date and time information.
     * @param creationTime The date and time the file or directory was last written to. This value is expressed in
     *                     UTC time.
     * @exception IllegalArgumentException {@code path} is a zero-length string, contains only white space, or contains
     * one or more invalid characters.
     * @exception NullPointerException {@code path} or {@code creationTime} is null.
     * @exception PathTooLongException The specified path, file name, or both exceed the system-defined maximum length.
     * For example, on Windows-based platforms, paths must be less than 248 characters and file names must be less than
     * 260 characters.
     * @exception DirectoryNotFoundException The path specified by {@code sourceDirName} is invalid (for example, it is
     * on an unmapped drive).
     * @exception AccessDeniedException The caller does not have the required permission.
     * @exception ArgumentOutOfRangeException {@code creationTime} specifies a value outside the range of dates
     * or times permitted for this operation.
     * */
    public static void setCreationTimeUtc(String path, DateTime creationTime)
    {

    }

    /**
     * Sets the application's current working directory to the specified directory.
     * @param path The path to which the current working directory is set.
     * @exception IOException An I/O error occured.
     * @exception IllegalArgumentException {@code path} is a zero-length string, contains only white space, or contains
     * one or more invalid characters.
     * @exception NullPointerException {@code path} or {@code creationTime} is null.
     * @exception PathTooLongException The specified path, file name, or both exceed the system-defined maximum length.
     * For example, on Windows-based platforms, paths must be less than 248 characters and file names must be less than
     * 260 characters.
     * @exception DirectoryNotFoundException The path specified was not found.
     * @exception FileNotFoundException The specified file was not found.
     * @exception SecurityException The caller does not have the required permission.
     * */
    public static void setCurrentDirectory(String path)
    {

    }

    /**
     * Sets the date and time the specified file or directory was last accessed.
     * @param path The file or directory for which to set the access date and time information.
     * @param lastAccessTime An instance of {@link DateTime} to set for the access date and time of {@code path}. This
     *                       value is expressed in local time.
     * @exception IllegalArgumentException {@code path} is a zero-length string, contains only white space, or contains
     * one or more invalid characters.
     * @exception NullPointerException {@code path} or {@code lastAccessTime} is null.
     * @exception PathTooLongException The specified path, file name, or both exceed the system-defined maximum length.
     * For example, on Windows-based platforms, paths must be less than 248 characters and file names must be less than
     * 260 characters.
     * @exception DirectoryNotFoundException The path specified by {@code sourceDirName} is invalid (for example, it is
     * on an unmapped drive).
     * @exception FileNotFoundException The specified file was not found.
     * @exception AccessDeniedException The caller does not have the required permission.
     * @exception ArgumentOutOfRangeException {@code creationTime} specifies a value outside the range of dates
     * or times permitted for this operation.
     * */
    public static void setLastAccessTime(String path, DateTime lastAccessTime)
    {

    }

    /**
     * Sets the date and time, in Coordinated Universal Time (UTC) format, that the specified file or directory was last accessed.
     * @param path The file or directory for which to set the access date and time information.
     * @param lastAccessTime An instance of {@link DateTime} to set for the access date and time of {@code path}. This
     *                       value is expressed in UTC time.
     * @exception IllegalArgumentException {@code path} is a zero-length string, contains only white space, or contains
     * one or more invalid characters.
     * @exception NullPointerException {@code path} or {@code lastAccessTime} is null.
     * @exception PathTooLongException The specified path, file name, or both exceed the system-defined maximum length.
     * For example, on Windows-based platforms, paths must be less than 248 characters and file names must be less than
     * 260 characters.
     * @exception DirectoryNotFoundException The path specified by {@code sourceDirName} is invalid (for example, it is
     * on an unmapped drive).
     * @exception FileNotFoundException The specified file was not found.
     * @exception AccessDeniedException The caller does not have the required permission.
     * @exception ArgumentOutOfRangeException {@code creationTime} specifies a value outside the range of dates
     * or times permitted for this operation.
     * */
    public static void setLastAccessTimeUtc(String path, DateTime lastAccessTime)
    {

    }

    /**
     * Sets the date and time the specified file or directory was last written to.
     * @param path The file or directory for which to set the access date and time information.
     * @param lastWriteTime An instance of {@link DateTime} to set for the write date and time of {@code path}. This
     *                       value is expressed in local time.
     * @exception IllegalArgumentException {@code path} is a zero-length string, contains only white space, or contains
     * one or more invalid characters.
     * @exception NullPointerException {@code path} or {@code lastWriteTime} is null.
     * @exception PathTooLongException The specified path, file name, or both exceed the system-defined maximum length.
     * For example, on Windows-based platforms, paths must be less than 248 characters and file names must be less than
     * 260 characters.
     * @exception DirectoryNotFoundException The path specified by {@code sourceDirName} is invalid (for example, it is
     * on an unmapped drive).
     * @exception FileNotFoundException The specified file was not found.
     * @exception AccessDeniedException The caller does not have the required permission.
     * @exception ArgumentOutOfRangeException {@code creationTime} specifies a value outside the range of dates
     * or times permitted for this operation.
     * */
    public static void setLastWriteTime(String path, DateTime lastWriteTime)
    {

    }

    /**
     * Sets the date and time, in Coordinated Universal Time (UTC), that the specified file or directory was last written to.
     * @param path The file or directory for which to set the access date and time information.
     * @param lastWriteTime An instance of {@link DateTime} to set for the write date and time of {@code path}. This
     *                       value is expressed in UTC time.
     * @exception IllegalArgumentException {@code path} is a zero-length string, contains only white space, or contains
     * one or more invalid characters.
     * @exception NullPointerException {@code path} or {@code lastWriteTime} is null.
     * @exception PathTooLongException The specified path, file name, or both exceed the system-defined maximum length.
     * For example, on Windows-based platforms, paths must be less than 248 characters and file names must be less than
     * 260 characters.
     * @exception DirectoryNotFoundException The path specified by {@code sourceDirName} is invalid (for example, it is
     * on an unmapped drive).
     * @exception FileNotFoundException The specified file was not found.
     * @exception AccessDeniedException The caller does not have the required permission.
     * @exception ArgumentOutOfRangeException {@code creationTime} specifies a value outside the range of dates
     * or times permitted for this operation.
     * */
    public static void setLastWriteTimeUtc(String path, DateTime lastWriteTime)
    {

    }
}
