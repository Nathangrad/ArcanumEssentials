package gg.arcanum.data;





public class DataRow
{
  private DataTable table;
  



  private Object[] cells;
  




  public DataRow(DataTable table)
  {
    this.table = table;
    cells = new Object[this.table.getColumns().length];
  }
  




  public DataTable getTable()
  {
    return table;
  }
  







  public Object getCell(String columnName)
    throws Exception
  {
    for (DataColumn col : table.getColumns()) {
      if (col.getName().equalsIgnoreCase(columnName)) {
        return cells[col.getIndex()];
      }
    }
    throw new Exception("Invalid data column");
  }
  








  public void setCell(String columnName, Object value)
    throws Exception
  {
    for (DataColumn col : table.getColumns()) {
      if (col.getName().equalsIgnoreCase(columnName)) {
        cells[col.getIndex()] = value;
        return;
      }
    }
    throw new Exception("Invalid data column");
  }
}
