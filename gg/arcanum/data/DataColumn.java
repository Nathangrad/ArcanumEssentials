package gg.arcanum.data;






public class DataColumn
{
  private String columnName;
  




  private int index;
  





  public DataColumn(String columnName, int index)
  {
    this.columnName = columnName;
    this.index = index;
  }
  




  public String getName()
  {
    return columnName;
  }
  




  public int getIndex()
  {
    return index;
  }
}
