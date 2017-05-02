package gg.arcanum.data;

import gg.arcanum.core.Core;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;







public class MySqlManager
{
  private static String url = "jdbc:mysql://192.3.120.89:3306/sys";
  private Connection con;
  private Statement st;
  private ResultSet rs;
  
  public MySqlManager() {}
  
  public static void doRunnable(Runnable runnable) { Bukkit.getScheduler().runTaskAsynchronously(Core.getPlugin(), runnable); }
  
  public static void doCallable(Callable<?> callable)
  {
    Bukkit.getScheduler().callSyncMethod(Core.getPlugin(), callable);
  }
  



  public void open()
    throws SQLException
  {
    con = DriverManager.getConnection(url, "MineCraft", "glass$123");
  }
  






  public DataTable executeQuery(String sql)
    throws SQLException
  {
    st = con.createStatement();
    rs = st.executeQuery(sql);
    DataTable dt = null;
    String[] col = null;
    int i; for (; rs.next(); 
        











        i <= rs.getMetaData().getColumnCount())
    {
      if (rs.isFirst()) {
        List<String> columns = new ArrayList();
        for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
          columns.add(rs.getMetaData().getColumnName(i));
        }
        col = new String[columns.size()];
        for (int i = 0; i < columns.size(); i++) {
          col[i] = ((String)columns.get(i));
        }
        dt = new DataTable(col);
      }
      DataRow row = dt.addRow();
      i = 1; continue;
      try {
        row.setCell(col[(i - 1)], rs.getObject(i));
      } catch (Exception e) {
        e.printStackTrace();
      }
      i++;
    }
    





    return dt;
  }
  





  public void executeNonQuery(String sql)
    throws SQLException
  {
    st = con.createStatement();
    st.executeUpdate(sql);
  }
  

  public void close()
  {
    try
    {
      if (rs != null) {
        rs.close();
      }
      if (st != null) {
        st.close();
      }
      if (con != null) {
        con.close();
      }
    }
    catch (SQLException localSQLException) {}
  }
}
