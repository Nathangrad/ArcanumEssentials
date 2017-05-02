package gg.arcanum.data;

/**
 * DataColumns are columns which the DataRows use to organise their cell
 * content. DataColumns are declared on the DataTable's creation and don't
 * normally need to be accessed unless using loops
 * 
 * @author NathanGrad
 *
 */
public class DataColumn {

	private String columnName;
	private int index;

	/**
	 * Create a new DataColumn with a column name and a 0-based index
	 * 
	 * @param columnName
	 *            Name of the DataColumn
	 * @param index
	 *            Index of the DataColumn in the DataTable
	 */
	public DataColumn(String columnName, int index) {
		this.columnName = columnName;
		this.index = index;
	}

	/**
	 * Get the name of the DataColumn
	 * 
	 * @return Returns the name of the DataColumn
	 */
	public String getName() {
		return columnName;
	}

	/**
	 * Get the index of the DataColumn
	 * 
	 * @return Returns the 0-based index of the DataColumn
	 */
	public int getIndex() {
		return index;
	}

}
