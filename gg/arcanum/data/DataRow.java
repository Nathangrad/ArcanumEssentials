package gg.arcanum.data;

/**
 * DataRows are records stored within a DataTable with specific DataColumns
 * assigned to it by the DataTable it's contained in which has objects as cells
 * 
 * @author NathanGrad
 *
 */
public class DataRow {

	private DataTable table;
	private Object[] cells;

	/**
	 * Create a new DataRow within a DataTable
	 * 
	 * @param table
	 *            The DataTable which it's contained in
	 */
	public DataRow(DataTable table) {
		this.table = table;
		this.cells = new Object[this.table.getColumns().length];
	}

	/**
	 * Get the DataTable which it's contained in
	 * 
	 * @return Returns the DataTable it belongs to
	 */
	public DataTable getTable() {
		return table;
	}

	/**
	 * Get a specific cell within the DataRow identified by its DataColumn name
	 * 
	 * @param columnName
	 *            The DataColumn name of which data you'd like to access
	 * @return The object of the cell in that column
	 * @throws InvalidDataColumnException
	 *             Thrown when an invalid DataColumn name has been provided
	 */
	public Object getCell(String columnName) throws Exception {
		for (DataColumn col : table.getColumns()) {
			if (col.getName().equalsIgnoreCase(columnName)) {
				return cells[col.getIndex()];
			}
		}
		throw new Exception("Invalid data column");
	}

	/**
	 * Set a specific cell within the DataRow identified by its DataColumn name
	 * 
	 * @param columnName
	 *            The DataColumn name of which data you'd like to set
	 * @param value
	 *            The object which you'd like to bind the cell's contents to
	 * @throws InvalidDataColumnException
	 *             Thrown when an invalid DataColumn name has been provided
	 */
	public void setCell(String columnName, Object value) throws Exception {
		for (DataColumn col : table.getColumns()) {
			if (col.getName().equalsIgnoreCase(columnName)) {
				cells[col.getIndex()] = value;
				return;
			}
		}
		throw new Exception("Invalid data column");
	}

}
