package gg.arcanum.data;

import java.util.ArrayList;
import java.util.List;








public class DataTable
{
  private DataColumn[] columns;
  private List<DataRow> rows = new ArrayList();
  





  public DataTable(String[] columns)
  {
    this.columns = new DataColumn[columns.length];
    for (int index = 0; index < columns.length; index++) {
      this.columns[index] = new DataColumn(columns[index], index);
    }
  }
  







  public DataRow getRow(int index)
    throws Exception
  {
    try
    {
      return (DataRow)rows.get(index);
    } catch (IndexOutOfBoundsException ioobe) {
      throw new Exception("Invalid data row");
    }
  }
  




  public DataRow[] getRows()
  {
    int totalRows = rows.size();
    DataRow[] result = new DataRow[totalRows];
    for (int row = 0; row < totalRows; row++) {
      result[row] = ((DataRow)rows.get(row));
    }
    return result;
  }
  




  public DataRow addRow()
  {
    DataRow row = new DataRow(this);
    rows.add(row);
    return row;
  }
  




  public DataColumn[] getColumns()
  {
    return columns;
  }
}
