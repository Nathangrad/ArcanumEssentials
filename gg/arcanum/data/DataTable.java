package gg.arcanum.data;

import java.util.ArrayList;
import java.util.List;

/**
 * DataTable used for storing data in a table-like format with DataRows and
 * DataColumns for organisational purposes.
 * 
 * @author NathanGrad
 *
 */
public class DataTable {

	private DataColumn[] columns;
	private List<DataRow> rows = new ArrayList<DataRow>();

	/**
	 * Creation of a DataTable
	 * 
	 * @param columns
	 *            A string array of the columns in the table
	 */
	public DataTable(String[] columns) {
		this.columns = new DataColumn[columns.length];
		for (int index = 0; index < columns.length; index++) {
			this.columns[index] = new DataColumn(columns[index], index);
		}
	}

	/**
	 * Get a specific DataRow within the DataTable
	 * 
	 * @param index
	 *            A 0 based index as a unique identifier for each row
	 * @return The DataRow of the index specified
	 * @throws InvalidDataRowException
	 *             Thrown if index is not assigned to a DataRow within the
	 *             DataTable
	 */
	public DataRow getRow(int index) throws Exception {
		try {
			return rows.get(index);
		} catch (IndexOutOfBoundsException ioobe) {
			throw new Exception("Invalid data row");
		}
	}

	/**
	 * Get all DataRows contained within the DataTable
	 * 
	 * @return Returns all the DataRows within the DataTable
	 */
	public DataRow[] getRows() {
		int totalRows = rows.size();
		DataRow[] result = new DataRow[totalRows];
		for (int row = 0; row < totalRows; row++) {
			result[row] = rows.get(row);
		}
		return result;
	}

	/**
	 * Add a new DataRow to the DataTable
	 * 
	 * @return Returns the DataRow which has been added to the DataTable
	 */
	public DataRow addRow() {
		DataRow row = new DataRow(this);
		rows.add(row);
		return row;
	}

	/**
	 * Get all the DataColumns within the DataTable
	 * 
	 * @return Returns all DataColumns within the DataTable
	 */
	public DataColumn[] getColumns() {
		return columns;
	}

}
