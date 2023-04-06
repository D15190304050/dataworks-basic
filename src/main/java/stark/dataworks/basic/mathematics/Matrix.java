package stark.dataworks.basic.mathematics;

import stark.dataworks.basic.ArgumentOutOfRangeException;
import stark.dataworks.basic.Environment;
import stark.dataworks.basic.IPredicate;

public class Matrix
{
    private final Vector[] matrix;

    private final int rowCount;
    private final int columnCount;

    // ---------------------------Constructors---------------------------

    public Matrix(double[][] matrix)
    {
        // Check whether the input 2-D array is null.
        if (matrix == null)
            throw new NullPointerException("The input 2-D double array must not be null.");

        // Check whether the input 2-D array has the shape as a matrix or not.
        if (!isMatrix(matrix))
            throw new IllegalArgumentException("The input 2-D double array doesn't have the shape as a matrix.");

        // Get the number of rows and columns of this matrix.
        rowCount = matrix.length;
        columnCount = matrix[0].length;

        // Initialize the internal matrix.
        this.matrix = new Vector[rowCount];
        for (int i = 0; i < rowCount; i++)
        {
            this.matrix[i] = new Vector(columnCount);
            for (int j = 0; j < columnCount; j++)
                set(i, j, matrix[i][j]);
        }
    }

    public Matrix(Vector[] vectors, boolean isRowVector)
    {
        if (vectors == null)
            throw new NullPointerException("The input array of Vector is null.");

        if (!isMatrix(vectors))
            throw new IllegalArgumentException("The input array of Vector doesn't have the shape as a matrix.");

        if (isRowVector)
        {
            rowCount = vectors.length;
            columnCount = vectors[0].count();

            matrix = new Vector[rowCount];
            for (int i = 0; i < rowCount; i++)
            {
                matrix[i] = new Vector(columnCount);
                for (int j = 0; j < columnCount; j++)
                    set(i, j, vectors[i].get(j));
            }
        }
        else
        {
            rowCount = vectors[0].count();
            columnCount = vectors.length;

            matrix = new Vector[rowCount];
            for (int i = 0; i < rowCount; i++)
            {
                matrix[i] = new Vector(rowCount);
                for (int j = 0; j < columnCount; j++)
                    set(i, j, vectors[j].get(i));
            }
        }
    }

    public Matrix(int numRows, int numColumns, double value)
    {
        if ((numRows < 1) || (numColumns < 1))
            throw new IllegalArgumentException("The size of a Matrix must be greater than or equal to 1*1.");

        // Get the number of rows and columns of this matrix.
        rowCount = numRows;
        columnCount = numColumns;

        // Initialize the internal matrix.
        matrix = new Vector[rowCount];

        if (value == 0)
        {
            for (int i = 0; i < this.rowCount; i++)
                matrix[i] = new Vector(this.columnCount);
        }
        else
        {
            for (int i = 0; i < this.rowCount; i++)
            {
                matrix[i] = new Vector(columnCount);
                for (int j = 0; j < columnCount; j++)
                    set(i, j, value);
            }
        }
    }

    // ---------------------------Member methods---------------------------

    public int getRowCount()
    {
        return rowCount;
    }

    public int getColumnCount()
    {
        return columnCount;
    }

    private boolean rowIndexIsInRange(int rowIndex)
    {
        return (rowIndex >= 0) && (rowIndex < rowCount);
    }

    private boolean columnIndexIsInRange(int columnIndex)
    {
        return (columnIndex >= 0) && (columnIndex < columnCount);
    }

    private void validateRowIndex(int rowIndex)
    {
        if (!rowIndexIsInRange(rowIndex))
            throw new ArgumentOutOfRangeException("\"rowIndex\" is out of the range of this Matrix.");
    }

    private void validateColumnIndex(int columnIndex)
    {
        if (!columnIndexIsInRange(columnIndex))
            throw new ArgumentOutOfRangeException("\"columnIndex\" is out of the range of this Matrix.");
    }

    private void validateIndexes(int startRowIndex, int startColumnIndex, int endRowIndex, int endColumnIndex)
    {
        if (!rowIndexIsInRange(startRowIndex))
            throw new ArgumentOutOfRangeException("\"startRowIndex\" is out of the range of this Matrix.");
        if (!rowIndexIsInRange(endRowIndex))
            throw new ArgumentOutOfRangeException("\"endRowIndex\" is out or the range of this Matrix.");
        if (!columnIndexIsInRange(startColumnIndex))
            throw new ArgumentOutOfRangeException("\"startColumnIndex\" is out of the range of this Matrix.");
        if (!columnIndexIsInRange(endColumnIndex))
            throw new ArgumentOutOfRangeException("\"endColumnIndex\" is out of the range of this Matrix.");

        if (endRowIndex <= startRowIndex)
            throw new IllegalArgumentException("\"startRowIndex\" must be less than \"endRowIndex\".");
        if (endColumnIndex <= startColumnIndex)
            throw new IllegalArgumentException("\"startColumnIndex\" must be less than \"endColumnIndex\".");
    }

    private void validateIsSquareMatrix()
    {
        if (!isSquareMatrix())
            throw new IllegalArgumentException("This is not a square matrix.");
    }

    public double get(int rowIndex, int columnIndex)
    {
        validateRowIndex(rowIndex);
        validateColumnIndex(columnIndex);
        return matrix[rowIndex].get(columnIndex);
    }

    public void set(int rowIndex, int columnIndex, double value)
    {
        validateRowIndex(rowIndex);
        validateColumnIndex(columnIndex);
        matrix[rowIndex].set(columnIndex, value);
    }

    public boolean isSquareMatrix()
    {
        return rowCount == columnCount;
    }

    public void clear()
    {
        for (int i = 0; i < rowCount; i++)
        {
            for (int j = 0; j < columnCount; j++)
                set(i, j, 0);
        }
    }

    private void validateColumnVector(Vector column)
    {
        if (column == null)
            throw new NullPointerException("The column to insert is null.");

        if (column.count() != rowCount)
            throw new IllegalArgumentException("The length of the vector is not equal to the row count of this Matrix.");
    }

    private void validateRowVector(Vector row)
    {
        if (row == null)
            throw new NullPointerException("The row to insert is null.");

        if (row.count() != columnCount)
            throw new IllegalArgumentException("The length of the vector is not equal to the column count of this Matrix.");
    }

    public void clearRow(int rowIndex)
    {
        // Check whether the argument is in range.
        validateRowIndex(rowIndex);

        // Clear the specified row.
        for (int j = 0; j < columnCount; j++)
            set(rowIndex, j, 0);
    }

    public void clearColumn(int columnIndex)
    {
        // Check whether the argument is in range.
        validateColumnIndex(columnIndex);

        // Clear the specified column.
        for (int i = 0; i < rowCount; i++)
            set(i, columnIndex, 0);
    }

    public void clearRows(int... rowIndexes)
    {
        // Check whether the row indices are in range.
        for (int i = 0; i < rowIndexes.length; i++)
        {
            if (!rowIndexIsInRange(rowIndexes[i]))
                throw new ArgumentOutOfRangeException("Row index ( with index: " + i + " is out of the range of the Matrix.");
        }

        // Clear those rows.
        for (int rowIndex : rowIndexes)
            clearRow(rowIndex);
    }

    public void clearColumns(int... columnIndexes)
    {
        // Check whether the row indices are in range.
        for (int i = 0; i < columnIndexes.length; i++)
        {
            if (!columnIndexIsInRange(columnIndexes[i]))
                throw new ArgumentOutOfRangeException("Column index ( with index: " + i + " is out of the range of the Matrix.");
        }

        // Clear those rows.
        for (int columnIndex : columnIndexes)
            clearColumn(columnIndex);
    }

    public void clearSubMatrix(int startRowIndex, int startColumnIndex, int endRowIndex, int endColumnIndex)
    {
        validateIndexes(startRowIndex, startColumnIndex, endRowIndex, endColumnIndex);

        for (int i = startRowIndex; i <= endRowIndex; i++)
        {
            for (int j = startColumnIndex; j <= endColumnIndex; j++)
                set(i, j, 0);
        }
    }

    public void coerceZero(double threshold)
    {
        if (threshold <= 0)
            throw new IllegalArgumentException("threshold must be positive.");

        for (int i = 0; i < rowCount; i++)
        {
            for (int j = 0; j < columnCount; j++)
            {
                if (Math.abs(get(i, j)) < threshold)
                    set(i, j, 0);
            }
        }
    }

    public void coerceZero(IPredicate<Double> zeroPredicate)
    {
        if (zeroPredicate == null)
            throw new NullPointerException("The input zeroPredicate must not be null.");

        for (int i = 0; i < rowCount; i++)
        {
            for (int j = 0; j < columnCount; j++)
            {
                if (zeroPredicate.predicate(get(i, j)))
                    set(i, j, 0);
            }
        }
    }

    public Matrix clone()
    {
        return new Matrix(this.matrix, true);
    }

    public Vector getRow(int rowIndex)
    {
        validateRowIndex(rowIndex);
        return new Vector(matrix[rowIndex]);
    }

    public Vector getSubRow(int rowIndex, int startColumnIndex, int endColumnIndex)
    {
        validateRowIndex(rowIndex);

        // Remainging check will be done here in the Vector's constructor.
        return matrix[rowIndex].getSubVector(startColumnIndex, endColumnIndex);
    }

    public Vector getColumn(int columnIndex)
    {
        validateColumnIndex(columnIndex);

        double[] vector = new double[rowCount];
        for (int i = 0; i < vector.length; i++)
            vector[i] = get(i, columnIndex);

        return new Vector(vector);
    }

    public Vector getSubColumn(int columnIndex, int startRowIndex, int endRowIndex)
    {
        validateColumnIndex(columnIndex);

        double[] vector = new double[endRowIndex - startRowIndex + 1];
        for (int i = 0; i < vector.length; i++)
            vector[i] = get(i + startRowIndex, columnIndex);

        return new Vector(vector);
    }

    public Matrix upperTriangular()
    {
        // Only a square matrix have its upper triangular matrix.
        validateIsSquareMatrix();

        // Make a deep copy of this Matrix.
        Matrix result = this.clone();

        // Clear all entries in the strict lower triangular region.
        for (int i = 1; i < rowCount; i++)
        {
            for (int j = 0; j < i; j++)
                result.set(i, j, 0);
        }

        // Return the upper triangular matrix.
        return result;
    }

    public Matrix lowerTriangular()
    {
        // Only a square matrix have its lower triangular matrix.
        validateIsSquareMatrix();

        // Make a deep copy of this Matrix.
        Matrix result = this.clone();

        // Clear all entries in the strict upper triangular region.
        for (int j = 1; j < columnCount; j++)
        {
            for (int i = 0; i < j; i++)
                result.set(i, j, 0);
        }

        // Return the lower triangular matrix.
        return result;
    }

    public Matrix getSubMatrix(int startRowIndex, int startColumnIndex, int endRowIndex, int endColumnIndex)
    {
        // Check input indices before extraction.
        validateIndexes(startRowIndex, startColumnIndex, endRowIndex, endColumnIndex);

        // Get the row count and column count of the sub-matrix.
        int numRows = endRowIndex - startRowIndex + 1;
        int numColumns = endColumnIndex - startColumnIndex + 1;

        // Initialize the sub-matrix.
        Matrix result = zeros(numRows, numColumns);

        // Extract contents for the sub-matrix.
        for (int i = 0; i < result.rowCount; i++)
        {
            for (int j = 0; j < result.columnCount; j++)
                result.set(i, j, this.get(i + startRowIndex, j + startColumnIndex));
        }

        // Return the sub-matrix.
        return result;
    }

    public Vector getDiagonal()
    {
        // Only a square matrix have its diagonal.
        validateIsSquareMatrix();

        // Initialize the result Vector.
        Vector result = new Vector(this.rowCount);

        // Assign and return.
        for (int i = 0; i < result.count(); i++)
            result.set(i, this.get(i, i));
        return result;
    }

    public Matrix appendColumn(Vector column)
    {
        // Check the column Vector before processing.
        validateColumnVector(column);

        // Initialize the result Matrix.
        Matrix result = zeros(this.rowCount, this.columnCount + 1);

        // Copy the values of this Matrix to the result Matrix.
        for (int i = 0; i < this.rowCount; i++)
        {
            for (int j = 0; j < this.columnCount; j++)
                result.set(i, j, this.get(i, j));
        }

        // Copy the values of the column Vector to the result Matrix.
        for (int i = 0; i < column.count(); i++)
            result.set(i, this.columnCount, column.get(i));

        // Return the result Matrix.
        return result;
    }

    public Matrix removeColumn(int columnIndex)
    {
        // Check the column index before processing.
        validateColumnIndex(columnIndex);

        // Initialize the result Matrix.
        Matrix result = zeros(this.rowCount, this.columnCount - 1);

        // Copy the sub-matrix on the left of the specified column.
        for (int j = 0; j < columnIndex; j++)
        {
            for (int i = 0; i < result.rowCount; i++)
                result.set(i, j, this.get(i, j));
        }

        // Copy the sub-matrix on the right of the specified column.
        for (int j = columnIndex + 1; j < this.columnCount; j++)
        {
            for (int i = 0; i < result.rowCount; i++)
                result.set(i, j - 1, this.get(i, j));
        }

        // Return the result Matrix.
        return result;
    }

    public void setColumn(int columnIndex, Vector column)
    {
        // Check the column index and column Vector before processing.
        validateColumnVector(column);
        validateColumnIndex(columnIndex);

        // Assignment.
        for (int i = 0; i < this.rowCount; i++)
            set(i, columnIndex, column.get(i));
    }

    public void setColumn(int columnIndex, double[] column)
    {
        // Check the column index before processing.
        validateColumnIndex(columnIndex);

        // Throw exception if the given array is not suitable for the assignment operation.
        if (column == null)
            throw new NullPointerException("The column to insert is null.");
        if (column.length != this.rowCount)
            throw new IllegalArgumentException("The length of the vector is not equal to the row count of this Matrix.");

        // Assignment.
        for (int i = 0; i < this.rowCount; i++)
            set(i, columnIndex, column[i]);
    }

    public Matrix appendRow(Vector row)
    {
        // Check the row Vector before processing.
        validateRowVector(row);

        // Initialize the result Matrix.
        Matrix result = zeros(this.rowCount + 1, this.columnCount);

        // Copy the values of this Matrix to the result Matrix.
        for (int i = 0; i < this.rowCount; i++)
        {
            for (int j = 0; j < this.columnCount; j++)
                result.set(i, j, this.get(i, j));
        }

        // Copy the values of the row Vector to the result Matrix.
        for (int j = 0; j < row.count(); j++)
            result.set(this.rowCount, j, row.get(j));

        // Return the result Matrix.
        return result;
    }

    public Matrix removeRow(int rowIndex)
    {
        // Check the column index before processing.
        validateRowIndex(rowIndex);

        // Initialize the result Matrix.
        Matrix result = zeros(this.rowCount - 1, this.columnCount);

        // Copy the sub-matrix on the above the specified column.
        for (int i = 0; i < rowIndex; i++)
        {
            for (int j = 0; j < this.columnCount; j++)
                result.set(i, j, this.get(i, j));
        }

        // Copy the sub-matrix on the below the specified column.
        for (int i = rowIndex + 1; i < this.rowCount; i++)
        {
            for (int j = 0; j < this.columnCount; j++)
                result.set(i - 1, j, this.get(i, j));
        }

        // Return the result Matrix.
        return result;
    }

    public void setRow(int rowIndex, Vector row)
    {
        // Check the column index and column Vector before processing.
        validateRowIndex(rowIndex);
        validateRowVector(row);

        // Assignment.
        for (int j = 0; j < this.columnCount; j++)
            set(rowIndex, j, row.get(j));
    }

    public void setRow(int rowIndex, double[] row)
    {
        // Check the row index before processing.
        validateRowIndex(rowIndex);

        // Throw exception if the given array is not suitable for the assignment operation.
        if (row == null)
            throw new NullPointerException("The row to insert is null.");
        if (row.length != this.columnCount)
            throw new IllegalArgumentException("The length of the vector is not equal to the column count of this Matrix.");

        // Assignment.
        for (int j = 0; j < this.columnCount; j++)
            set(rowIndex, j, row[j]);
    }

    public void setSubMatrix(Matrix subMatrix, int startRowIndex, int startColumnIndex)
    {
        // Check start row index and start column index.
        validateRowIndex(startColumnIndex);
        validateColumnIndex(startColumnIndex);

        // Check whether the size of the sub-region is smaller than the given Matrix.
        if (this.rowCount < subMatrix.rowCount + startRowIndex)
            throw new ArgumentOutOfRangeException("The row count of subMatrix is larger than the remaining capacity of this Matrix.");
        if (this.columnCount < subMatrix.columnCount + startColumnIndex)
            throw new ArgumentOutOfRangeException("The column count of subMatrix is larger than the remaining capacity of this Matrix.");

        // Assignment.
        for (int i = 0; i < subMatrix.rowCount; i++)
        {
            for (int j = 0; j < subMatrix.columnCount; j++)
                this.set(i + startRowIndex, j + startColumnIndex, subMatrix.get(i, j));
        }
    }

    public void setDiagonal(Vector diagonal)
    {
        // Check whether this Matrix is square matrix.
        validateIsSquareMatrix();

        // Check the diagonal Vector.
        if (diagonal == null)
            throw new NullPointerException("The input Vector must not be null.");
        if (this.rowCount != diagonal.count())
            throw new IllegalArgumentException("The length of the input vector is not equal to the size of the diagonal of this Matrix.");

        // Assignment.
        for (int i = 0; i < diagonal.count(); i++)
            set(i, i, diagonal.get(i));
    }

    public void setDiagonal(double[] diagonal)
    {
        // Check whether this Matrix is square matrix.
        validateIsSquareMatrix();

        // Check the diagonal Vector.
        if (diagonal == null)
            throw new NullPointerException("The input input diagonal must not be null.");
        if (this.rowCount != diagonal.length)
            throw new IllegalArgumentException("The length of the input Vector is not equal to the size of the diagonal of this Matrix.");

        // Assignment.
        for (int i = 0; i < diagonal.length; i++)
            set(i, i, diagonal[i]);
    }

    public Matrix transpose()
    {
        Matrix result = zeros(this.columnCount, this.rowCount);
        for (int i = 0; i < this.rowCount; i++)
        {
            for (int j = 0; j < this.columnCount; j++)
                result.set(j, i, this.get(i, j));
        }

        return result;
    }

    public Matrix appendRight(Matrix right)
    {
        // Check the given Matrix.
        if (right == null)
            throw new NullPointerException("The Matrix to append is null.");
        if (this.rowCount != right.rowCount)
            throw new IllegalArgumentException("The row count of the input Matrix is not equal to the the row count of this Matrix.");

        // Initialize the result Matrix with.
        Matrix result = zeros(this.rowCount, this.columnCount + right.columnCount);

        // Copy the left half.
        for (int j = 0; j < this.columnCount; j++)
        {
            for (int i = 0; i < this.rowCount; i++)
                result.set(i, j, this.get(i, j));
        }

        // Copy the right half.
        for (int j = 0; j < right.columnCount; j++)
        {
            for (int i = 0; i < right.rowCount; i++)
                result.set(i, j + this.columnCount, right.get(i, j));
        }

        // Return the result Matrix.
        return result;
    }

    public Matrix appendBottom(Matrix bottom)
    {
        // Check the given Matrix.
        if (bottom == null)
            throw new NullPointerException("The Matrix to stack is null.");
        if (this.columnCount != bottom.columnCount)
            throw new IllegalArgumentException("The column count of the input Matrix is not equal to the column count of this Matrix.");

        // Initialize the result Matrix with.
        Matrix result = zeros(this.rowCount + bottom.rowCount, this.columnCount);

        // Copy the left half.
        for (int i = 0; i < this.rowCount; i++)
        {
            for (int j = 0; j < this.columnCount; j++)
                result.set(i, j, this.get(i, j));
        }

        // Copy the right half.
        for (int i = 0; i < bottom.rowCount; i++)
        {
            for (int j = 0; j < bottom.columnCount; j++)
                result.set(i + this.rowCount, j, bottom.get(i, j));
        }

        // Return the result Matrix.
        return result;
    }

    public boolean isSymmetric()
    {
        // Check whether this Matrix is a square matrix.
        if (!isSquareMatrix())
            return false;

        // Check if this Matrix is symmetric.
        for (int i = 1; i < this.rowCount; i++)
        {
            for (int j = 0; j < i; j++)
            {
                if (Math.abs(this.get(i, j) - this.get(j, i)) >= Mathematics.getEpsilon())
                    return false;
            }
        }
        return true;
    }

    /**
     * Returns the matrix's entries as an array with the data laid out column by column (column major).
     * The returned array will be independent from this matrix. A new memory block will be allocated for the array.<br/>
     * Example:<br/>
     * 1, 2, 3<br/>
     * 4, 5, 6<br/>
     * 7, 8, 9<br/>
     * will be returned as  1, 4, 7, 2, 5, 8, 3, 6, 9
     *
     * @return An array containing the matrix's entries.
     */
    public double[] toColumnMajorArray()
    {
        double[] result = new double[this.rowCount * this.columnCount];
        int nextIndex = 0;

        for (int j = 0; j < this.columnCount; j++)
        {
            for (int i = 0; i < this.rowCount; i++)
                result[nextIndex++] = this.get(i, j);
        }

        return result;
    }

    public double[] toRowMajorArray()
    {
        double[] result = new double[this.rowCount * this.columnCount];
        int nextIndex = 0;

        for (int i = 0; i < this.rowCount; i++)
        {
            for (int j = 0; j < this.columnCount; j++)
                result[nextIndex++] = this.get(i, j);
        }

        return result;
    }

    public double[][] toRowArrays()
    {
        double[][] result = new double[this.rowCount][this.columnCount];
        for (int i = 0; i < this.rowCount; i++)
        {
            for (int j = 0; j < this.columnCount; j++)
                result[i][j] = this.get(i, j);
        }

        return result;
    }

    public double[][] toColumnArrays()
    {
        double[][] result = new double[this.columnCount][this.rowCount];
        for (int j = 0; j < this.columnCount; j++)
        {
            for (int i = 0; i < this.rowCount; i++)
                result[j][i] = this.get(i, j);
        }

        return result;
    }

    public Vector[] toRowVectors()
    {
        Vector[] rows = new Vector[this.rowCount];
        for (int i = 0; i < this.rowCount; i++)
            rows[i] = new Vector(matrix[i]);

        return rows;
    }

    public Vector[] toColumnVectors()
    {
        // Initialize the columns vector.
        Vector[] columns = new Vector[this.columnCount];

        // Copy values and return.
        double[][] columnArrays = toColumnArrays();
        for (int i = 0; i < columns.length; i++)
            columns[i] = new Vector(columnArrays[i]);
        return columns;
    }

    public Vector sum(int axis)
    {
        if (axis == 0)
        {
            Vector result = new Vector(this.columnCount);
            for (Vector rowVector : matrix)
                result = result.add(rowVector);
            return result;
        }
        else if (axis == 1)
        {
            Vector result = new Vector(this.rowCount);
            Vector[] columnVectors = this.toColumnVectors();
            for (Vector columnVector : columnVectors)
                result = result.add(columnVector);
            return result;
        }
        else
            throw new IllegalArgumentException("axis can be only 0 or 1.");
    }

    public Vector mean(int axis)
    {
        if (axis == 0)
            return sum(0).divide(this.rowCount);
        else if (axis == 1)
            return sum(1).divide(this.columnCount);
        else
            throw new IllegalArgumentException("axis can be only 0 or 1.");
    }

    @Override
    public String toString()
    {
        return toString(6, 2);
    }

    public String toString(int totalMinLength, int floatingMinLength)
    {
        // Use StringBuilder to accelerate.
        StringBuilder s = new StringBuilder();

        // Gather contents for the StringBuilder.
        for (int i = 0; i < this.rowCount; i++)
        {
            for (int j = 0; j < this.columnCount; j++)
                s.append(String.format("%" + totalMinLength + "." + floatingMinLength + "f ", this.get(i, j)));
            s.append(Environment.newLine());
        }

        // Remove the line breaks at the end of this StringBuilder.
        // Return the string representation of this Matrix.
        return s.substring(0, s.length() - 1);
    }

    // ---------------------------Operators---------------------------

    public Matrix add(double scalar)
    {
        // Make a deep copy of this Matrix.
        Matrix result = this.clone();

        // Add operation.
        for (int i = 0; i < result.rowCount; i++)
        {
            for (int j = 0; j < result.columnCount; j++)
                result.set(i, j, result.get(i, j) + scalar);
        }

        // Return the result Matrix.
        return result;
    }

    /**
     * Perform an elementwise addition.
     * @param matrix2
     * @return
     */
    public Matrix add(Matrix matrix2)
    {
        // Check 2 input matrices.
        validateShape(this, matrix2);

        // Make a deep copy of this Matrix.
        Matrix result = this.clone();

        // Add operation.
        for (int i = 0; i < this.rowCount; i++)
        {
            for (int j = 0; j < this.columnCount; j++)
                result.set(i, j, result.get(i, j) + matrix2.get(i, j));
        }

        // Return the result Matrix.
        return result;
    }

    public Matrix subtract(double scalar)
    {
        // Make a deep copy of this Matrix.
        Matrix result = this.clone();

        // Subtraction operation.
        for (int i = 0; i < result.rowCount; i++)
        {
            for (int j = 0; j < result.columnCount; j++)
                result.set(i, j, result.get(i, j) - scalar);
        }

        // Return the result Matrix.
        return result;
    }

    /**
     * Perform an elementwise subtraction.
     * @param matrix2
     * @return
     */
    public Matrix subtract(Matrix matrix2)
    {
        // Check 2 input matrices.
        validateMatrices(this, matrix2);

        // Make a deep copy of this Matrix.
        Matrix result = this.clone();

        // Subtraction operation.
        for (int i = 0; i < result.rowCount; i++)
        {
            for (int j = 0; j < matrix2.rowCount; j++)
                result.set(i, j, result.get(i, j) - matrix2.get(i, j));
        }

        // Return the result Matrix.
        return result;
    }

    public Matrix multiply(double scalar)
    {
        // Make a deep copy of this Matrix.
        Matrix result = this.clone();

        // Multiplication operation.
        for (int i = 0; i < result.rowCount; i++)
        {
            for (int j = 0; j < result.columnCount; j++)
                result.set(i, j, result.get(i, j) * scalar);
        }

        // Return the result Matrix.
        return result;
    }

    public Vector multiply(Vector columnVector)
    {
        if (columnVector == null)
            throw new NullPointerException("The input Vector is null.");

        // Check whether the column count of the matrix and the count of the vector must be equal
        if (this.columnCount != columnVector.count())
            throw new IllegalArgumentException("The column count of the matrix and the count of the vector must be equal.");

        // Get rows of the given matrix.
        Vector[] rows = this.matrix;

        // Initialize the result Vector.
        Vector result = new Vector(this.rowCount);

        // Multiplication operation.
        for (int i = 0; i < result.count(); i++)
            result.set(i, rows[i].dot(columnVector));

        // Return the result Vector.
        return result;
    }

    public Matrix multiply(Matrix right)
    {
        if (right == null)
            throw new NullPointerException("matrixRight is null.");

        // Check the inner dimension of 2 matrices.
        if (this.columnCount != right.rowCount)
            throw new IllegalArgumentException("The column count of left matrix and the row count of right matrix must be equal.");

        // Initialize the result Matrix.
        Matrix result = zeros(this.rowCount, right.columnCount);

        // Get the row and column vectors.
        Vector[] rows = this.matrix;
        Vector[] columns = right.toColumnVectors();

        // Matrix multiplication.
        for (int i = 0; i < result.rowCount; i++)
        {
            for (int j = 0; j < result.columnCount; j++)
                result.set(i, j, rows[i].dot(columns[j]));
        }

        // Return the result Matrix.
        return result;
    }

    public Matrix divide(double scalar)
    {
        // Make a deep copy of this Matrix.
        Matrix result = this.clone();

        // Division operation.
        for (int i = 0; i < result.rowCount; i++)
        {
            for (int j = 0; j < result.columnCount; j++)
                result.set(i, j, result.get(i, j) / scalar);
        }

        // Return the result Matrix.
        return result;
    }

    // ---------------------------Static methods---------------------------

    public static boolean isMatrix(double[][] array)
    {
        if (array.length < 1)
            return false;

        int columnCount = array[0].length;
        if (columnCount < 1)
            return false;

        for (int i = 1; i < array.length; i++)
        {
            if (array[i].length != columnCount)
                return false;
        }

        return true;
    }

    public static boolean isMatrix(Vector[] vectors)
    {
        if (vectors[0] == null)
            return false;

        int columnCount = vectors[0].count();
        for (int i = 1; i < vectors.length; i++)
        {
            Vector v = vectors[i];
            if (v == null)
                return false;
            if (v.count() != columnCount)
                return false;
        }
        return true;
    }

    public static Matrix zeros(int rowCount, int columnCount)
    {
        return new Matrix(rowCount, columnCount, 0);
    }

    public static Matrix ones(int rowCount, int columnCount)
    {
        return new Matrix(rowCount, columnCount, 1);
    }

    public static Matrix generateRandomIntMatrix(int rowCount, int columnCount, int min, int max)
    {
        Random random = new Random();

        Matrix matrix = zeros(rowCount, columnCount);
        for (int i = 0; i < matrix.rowCount; i++)
        {
            for (int j = 0; j < matrix.columnCount; j++)
                matrix.set(i, j, random.uniform(min, max));
        }

        return matrix;
    }

    public static Matrix generateRandomIntMatrix(int rowCount, int columnCount, double min, double max)
    {
        Random random = new Random();

        Matrix matrix = zeros(rowCount, columnCount);
        for (int i = 0; i < matrix.rowCount; i++)
        {
            for (int j = 0; j < matrix.columnCount; j++)
                matrix.set(i, j, random.uniform(min, max));
        }

        return matrix;
    }

    private static void convolutionPreValidate(Matrix matrix1, Matrix matrix2, int padding, int stride)
    {
        if (matrix1 == null)
            throw new NullPointerException("matrix1 is null.");
        if (matrix2 == null)
            throw new NullPointerException("matrix2 is null.");

        if (padding < 0)
            throw new IllegalArgumentException("Padding of a matrix must be a non-negative integer.");
        if (stride <= 0)
            throw new IllegalArgumentException("Stride of a convolution or correlation operation must be a positive integer.");
        if ((matrix1.rowCount + 2 * padding - matrix2.rowCount) % stride != 0)
            throw new IllegalArgumentException("The stride is not fit.");
        if ((matrix1.columnCount + 2 * padding - matrix2.columnCount) % stride != 0)
            throw new IllegalArgumentException("The stride is not fit.");
    }

    public static Matrix computeConvolution(Matrix matrix1, Matrix matrix2, int padding, int stride)
    {
        // Check before computing.
        convolutionPreValidate(matrix1, matrix2, padding, stride);

        // Reflect matrix 2.
        double[][] reflectedMatrix2 = new double[matrix2.rowCount][matrix2.columnCount];
        for (int i = 0; i < matrix2.rowCount; i++)
        {
            for (int j = 0; j < matrix2.columnCount; j++)
                reflectedMatrix2[i][j] = matrix2.get(matrix2.rowCount - 1 - i, matrix2.columnCount - 1 - j);
        }

        // Pad zeros around matrix 1.
        double[][] extendedMatrix1 = new double[matrix1.rowCount + 2 * padding][matrix1.columnCount + 2 * padding];
        for (int i = 0; i < matrix1.rowCount; i++)
        {
            for (int j = 0; j < matrix1.columnCount; j++)
                extendedMatrix1[i + padding][j + padding] = matrix1.get(i, j);
        }

        // Initialize the result matrix.
        int resultRowCount = 1 + (extendedMatrix1.length - matrix2.rowCount) / stride;
        int resultColumnCount = 1 + (extendedMatrix1[0].length - matrix2.columnCount) / stride;
        Matrix result = zeros(resultRowCount, resultColumnCount);

        // Compute the convolution.
        for (int i = 0; i < result.rowCount; i++)
        {
            int startRowIndex = i * stride;

            for (int j = 0; j < result.columnCount; j++)
            {
                int startColumnIndex = j * stride;

                // Use reflectedMatrix2 here so that CPU doesn't need to read matrix2 to cache to get its row count and column count.
                // To avoid cache miss.
                for (int x = 0; x < reflectedMatrix2.length; x++)
                {
                    for (int y = 0; y < reflectedMatrix2[x].length; y++)
                    {
                        double z = extendedMatrix1[startRowIndex + x][startColumnIndex + y] * reflectedMatrix2[x][y];
                        result.set(i, j, result.get(i, j) + z);
                    }
                }
            }
        }

        // Return the convolution computed above.
        return result;
    }

    public static Matrix computeCorrelation(Matrix matrix1, Matrix matrix2, int padding, int stride)
    {
        // Check before computing.
        convolutionPreValidate(matrix1, matrix2, padding, stride);

        // Pad zeros around matrix 1.
        double[][] extendedMatrix1 = new double[matrix1.rowCount + 2 * padding][matrix1.columnCount + 2 * padding];
        for (int i = 0; i < matrix1.rowCount; i++)
        {
            for (int j = 0; j < matrix1.columnCount; j++)
                extendedMatrix1[i + padding][j + padding] = matrix1.get(i, j);
        }

        // Initialize the result matrix.
        int resultRowCount = 1 + (extendedMatrix1.length - matrix2.rowCount) / stride;
        int resultColumnCount = 1 + (extendedMatrix1[0].length - matrix2.columnCount) / stride;
        Matrix result = zeros(resultRowCount, resultColumnCount);

        // Compute the convolution.
        for (int i = 0; i < result.rowCount; i++)
        {
            int startRowIndex = i * stride;

            for (int j = 0; j < result.columnCount; j++)
            {
                int startColumnIndex = j * stride;

                // Use reflectedMatrix2 here so that CPU doesn't need to read matrix2 to cache to get its row count and column count.
                // To avoid cache miss.
                for (int x = 0; x < matrix2.rowCount; x++)
                {
                    for (int y = 0; y < matrix2.columnCount; y++)
                    {
                        double z = extendedMatrix1[startRowIndex + x][startColumnIndex + y] * matrix2.get(x, y);
                        result.set(i, j, result.get(i, j) + z);
                    }
                }
            }
        }

        // Return the correlation computed above.
        return result;
    }

    private static void validateShape(Matrix matrix1, Matrix matrix2)
    {
        if ((matrix1.rowCount != matrix2.rowCount) || (matrix1.columnCount != matrix2.columnCount))
            throw new IllegalArgumentException("Input matrices don't have the same shape.");
    }

    private static void validateMatrices(Matrix matrix1, Matrix matrix2)
    {
        // Check if one of the Matrix is null.
        if (matrix1 == null)
            throw new NullPointerException("matrix1 is null.");
        if (matrix2 == null)
            throw new NullPointerException("matrix2 is null");

        // Check whether they have the same shape.
        validateShape(matrix1, matrix2);
    }
}
