package gg.arcanum.data;

import java.sql.SQLException;
import java.util.logging.Logger;

public abstract class Example
{
  static volatile DataTable result;
  
  public Example() {}
  
  public static void example()
  {
    MySqlManager.doRunnable(new Runnable()
    {
      public void run() {
        MySqlManager sql = new MySqlManager();
        try {
          sql.open();
          Example.result = sql.executeQuery("SELECT playerID, UUID, balance FROM players");
          sql.close();
          MySqlManager.doCallable(new java.util.concurrent.Callable()
          {
            public Void call() throws Exception {
              Logger log = gg.arcanum.core.Core.getPlugin().getLogger();
              for (DataRow row : Example.result.getRows()) {
                for (DataColumn col : Example.result.getColumns()) {
                  log.info(row.getCell(col.getName()).toString());
                }
              }
              return null;
            }
          });
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    });
  }
}
