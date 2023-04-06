package stark.dataworks.basic.io;

import java.io.FileNotFoundException;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.AccessDeniedException;

public final class FileInfo
{
    /**
     * Initializes a new instance of the {@link FileInfo} class, which acts as a wrapper for a file path.
     * @param path the fully qualified path of the file, or the relative file path. Do not end the path with the
     *             directory separator character.
     * @exception NullPointerException {@code path} is null.
     * @exception SecurityException The caller does not have the required permission.
     * @exception IllegalArgumentException The file path is empty, contains only white spaces, or contains invalid
     * characters.
     * @exception PathTooLongException The specified path, file name, or both exceed the system-defined maximum length.
     * For example, on Windows-based platforms, paths must be less than 248 characters, and file names must be less than
     * 260 characters.
     * */
    public FileInfo(String path)
    {

    }

    /**
     * Creates a {@link OutputStreamWriter} that appends text to the file represented by this instance of the
     * {@link FileInfo}.
     * @return An instance of {@link OutputStreamWriter} that can appends text to the file represented by this instance
     * of the {@link FileInfo}.
     * */
    public OutputStreamWriter appendText()
    {
        return null;
    }

    /**
     * Copies an existing file to a new file, disallowing the overwriting of an existing file.
     * @param destFilePath The path of the new file to copy to.
     * @return A new file with a fully qualified path.
     * @exception IllegalArgumentException {@code destFilePath} is empty, contains only white spaces, or contains
     * invalid characters.
     * @exception IOException An error occurs, or the destination file already exists.
     * @exception SecurityException The caller does not have the required permission.
     * @exception NullPointerException {@code destFilePath} is null.
     * @exception AccessDeniedException A directory path is passed in, or the file is being moved to a
     * different drive.
     * @exception PathTooLongException The specified path, file name, or both exceed the system-defined maximum length.
     * For example, on Windows-based platforms, paths must be less than 248 characters, and file names must be less than
     * 260 characters.
     * */
    public FileInfo copyTo(String destFilePath)
    {
        return null;
    }

    /**
     * Copies an existing file to a new file, allowing the overwriting of an existing file.
     * @param destFilePath The path of the new file to copy to.
     * @param overwrite {@code true} to allow an existing file to be overwritten; otherwise, {@code false}.
     * @return A new file, or an overwrite of an existing file if overwrite is true. If the file exists and overwrite
     * is false, an {@link IOException} is thrown.
     * @exception IllegalArgumentException {@code destFilePath} is empty, contains only white spaces, or contains
     * invalid characters.
     * @exception IOException An error occurs, or the destination file already exists.
     * @exception SecurityException The caller does not have the required permission.
     * @exception NullPointerException {@code destFilePath} is null.
     * @exception AccessDeniedException A directory path is passed in, or the file is being moved to a
     * different drive.
     * @exception PathTooLongException The specified path, file name, or both exceed the system-defined maximum length.
     * For example, on Windows-based platforms, paths must be less than 248 characters, and file names must be less than
     * 260 characters.
     * */
    public FileInfo copyTo(String destFilePath, boolean overwrite)
    {
        return null;
    }

    /**
     * Permanently deletes a file.
     * @exception IOException There is an open handle on the file. This open handle can result from enumerating
     * directories and files. Or, the path is a directory.
     * @exception SecurityException The caller does not have the required permission.
     * */
    public void delete()
    {

    }

    /**
     * Gets a value indicating whether a file exists.
     * @return {@code true} if the file exists; {@code false} if the file does not exist or if the file is a directory.
     * */
    public boolean exists()
    {
        return false;
    }

    /**
     * Gets an instance of the parent directory.
     * @return A {@link DirectoryInfo} object representing the parent directory of this file.
     * @exception DirectoryNotFoundException The specified path is invalid, such as being on an unmapped drive.
     * @exception SecurityException The caller does not have the required permission.
     * */
    public DirectoryInfo getDirectory()
    {
        return null;
    }

    /**
     * Gets a string representing the parent directory's full path.
     * @return A string representing the parent directory's full path.
     * @exception PathTooLongException The specified path, file name, or both exceed the system-defined maximum length.
     * For example, on Windows-based platforms, paths must be less than 248 characters, and file names must be less than
     * 260 characters.
     * @exception SecurityException The caller does not have the required permission.
     * */
    public String getDirectoryPath()
    {
        return null;
    }

    /**
     * Gets the name of the file.
     * @return The name of the file.
     * */
    public String getName()
    {
        return null;
    }

    /**
     * Gets a value that determines if the current file is read only.
     * @return {@code true} if the current file is read only; otherwise, {@code false}.
     * @exception java.io.FileNotFoundException The file described by the current {@link FileInfo} object could not be found.
     * @exception IOException An I/O error occurred while opening the file.
     * @exception AccessDeniedException This operation is not supported on the current platform. Or, the caller does
     * not have the required permission.
     * */
    public boolean isReadOnly()
    {
        return false;
    }

    /**
     * Sets a value that determines if the current file is read only.
     * @exception java.io.FileNotFoundException The file described by the current {@link FileInfo} object could not be found.
     * @exception IOException An I/O error occurred while opening the file.
     * @exception AccessDeniedException This operation is not supported on the current platform. Or, the caller does
     * not have the required permission.
     * @exception IllegalArgumentException The user does not have write permission, but attempted to set this property
     * to false.
     * */
    public void setReadOnly(boolean readOnly)
    {

    }

    /**
     * Gets the size, in bytes, of the current file.
     * @return The size of the current file in bytes.
     * @exception java.io.FileNotFoundException The file does not exist. Or, the Length property is called for a directory.
     * */
    public long length()
    {
        return 0;
    }

    /**
     * Moves a specified file to a new location, providing the option to specify a new file path.
     * @param destFilePath The path to move the file to, which can specify a different file name.
     * @exception IOException An I/O error occurs, such as the destination file already exists or the destination
     * device is not ready.
     * @exception NullPointerException {@code destFilePath} is null.
     * @exception SecurityException The caller does not have the required permission.
     * @exception AccessDeniedException {@code destFilePath} is read-only or is a directory.
     * @exception java.io.FileNotFoundException The file is not found.
     * @exception DirectoryNotFoundException The specified path is invalid, such as being on an unmapped drive.
     * @exception PathTooLongException The specified path, file name, or both exceed the system-defined maximum length.
     * For example, on Windows-based platforms, paths must be less than 248 characters, and file names must be less than
     * 260 characters.
     * */
    public void moveTo(String destFilePath)
    {

    }

    /**
     * Creates a read-only {@link FileInputStream} for the specified file with UTF-8 encoding.
     * @return A new read-only {@link FileInputStream} object for reading contents of the specified file.
     * @exception AccessDeniedException {@code path} is read-only.
     * @exception DirectoryNotFoundException The specified path is invalid, such as being on an unmapped drive.
     * @exception IOException The file is already open, or path is a directory.
     * */
    public FileInputStream openRead()
    {
        return openRead(StandardCharsets.UTF_8);
    }

    /**
     * Creates a read-only {@link FileInputStream} for the specified file with the specified encoding.
     * @param charset The specified encoding.
     * @return A new read-only {@link FileInputStream} object for reading contents of the specified file.
     * @exception AccessDeniedException {@code path} is read-only.
     * @exception DirectoryNotFoundException The specified path is invalid, such as being on an unmapped drive.
     * @exception IOException The file is already open, or path is a directory.
     * */
    public FileInputStream openRead(Charset charset)
    {
        return null;
    }

    /**
     * Creates a write-only {@link FileOutputStream} for the specified file with UTF-8 encoding.
     * @return A new write-only unshared {@link FileOutputStream} object for a new or existing file.
     * @exception AccessDeniedException The path specified when creating an instance of the {@link FileInfo} object is
     * read-only or is a directory.
     * @exception DirectoryNotFoundException The path specified when creating an instance of the {@link FileInfo}
     * object is invalid, such as being on an unmapped drive.
     * */
    public FileOutputStream openWrite()
    {
        return openWrite(StandardCharsets.UTF_8);
    }

    /**
     * Creates a write-only {@link FileOutputStream} for the specified file with the specified encoding.
     * @param charset The specified encoding.
     * @return A new write-only unshared {@link FileOutputStream} object for a new or existing file.
     * @exception AccessDeniedException The path specified when creating an instance of the {@link FileInfo} object is
     * read-only or is a directory.
     * @exception DirectoryNotFoundException The path specified when creating an instance of the {@link FileInfo}
     * object is invalid, such as being on an unmapped drive.
     * */
    public FileOutputStream openWrite(Charset charset)
    {
        return null;
    }

    /**
     * Replaces the contents of a specified file with the file described by the current {@link FileInfo} object,
     * deleting the original file, and creating a backup of the replaced file.
     * @param destFilePath The path of a file to replace with the current file.
     * @param destBackupFilePath The path of a file with which to create a backup of the file described by the
     * {@code destFilePath} parameter.
     * @return A {@link FileInfo} object that encapsulates information about the file described by the
     * {@code destFilePath} parameter.
     * @exception IllegalArgumentException The path described by the {@code destFilePath} parameter was not of a legal
     * form.Or, the path described by the {@code destBackupFilePath} parameter was not of a legal form.
     * @exception NullPointerException The {@code destFilePath} parameter or the {@code destBackupFilePath} parameter
     * is null.
     * @exception java.io.FileNotFoundException The file described by the current {@link FileInfo} object could not be found.
     * Or, the file described by the {@code destFilePath} parameter could not be found.
     * */
    public FileInfo replace(String destFilePath, String destBackupFilePath)
    {
        return null;
    }

    /**
     * Replaces the contents of a specified file with the file described by the current {@link FileInfo} object,
     * deleting the original file, and creating a backup of the replaced file. Also specifies whether to ignore merge
     * errors.
     * @param destFilePath The path of a file to replace with the current file.
     * @param destBackupFilePath The path of a file with which to create a backup of the file described by the
     * {@code destFilePath} parameter.
     * @param ignoreMetadataErrors {@code true} to ignore merge errors (such as attributes and ACLs) from the replaced
     *                                         file to the replacement file; otherwise {@code false}.
     * @exception IllegalArgumentException The path described by the {@code destFilePath} parameter was not of a legal
     * form.Or, the path described by the {@code destBackupFilePath} parameter was not of a legal form.
     * @exception NullPointerException The {@code destFilePath} parameter or the {@code destBackupFilePath} parameter
     * is null.
     * @exception FileNotFoundException The file described by the current {@link FileInfo} object could not be found.
     * Or, the file described by the {@code destFilePath} parameter could not be found.
     * */
    public FileInfo replace(String destFilePath, String destBackupFilePath, boolean ignoreMetadataErrors)
    {
        return null;
    }

    /**
     * Returns the path as a string.
     * @return A string representing the path.
     * */
    @Override
    public String toString()
    {
        return super.toString();
    }
}
