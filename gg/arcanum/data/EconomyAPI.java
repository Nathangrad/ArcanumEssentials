package gg.arcanum.data;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.entity.Player;






public class EconomyAPI
{
  private static Map<Player, EconomyPlayer> players = new HashMap();
  


  public EconomyAPI() {}
  


  public static String getBalanceString(Player player)
    throws NumberFormatException
  {
    return get(player) + " Coins";
  }
  











  public static boolean payPlayer(Player playerFrom, Player playerTo, double amount)
    throws NumberFormatException
  {
    if (get(playerFrom) < amount) {
      return false;
    }
    update(playerFrom, Double.valueOf(-amount));
    update(playerTo, Double.valueOf(amount));
    MySqlManager sql = new MySqlManager();
    try {
      sql.open();
      sql.executeNonQuery("INSERT INTO PaymentHistory (Sender, Receiver, Amount, Date) VALUES ('" + 
        playerFrom.getUniqueId() + "','" + playerTo.getUniqueId() + "'," + amount + ", NOW())");
      sql.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return true;
  }
  





  public static double get(Player player)
  {
    return ((EconomyPlayer)players.get(player)).getBalance();
  }
  
  public static void getInitial(Player player) {
    MySqlManager.doRunnable(new Runnable()
    {
      public void run() {
        MySqlManager sql = new MySqlManager();
        try {
          sql.open();
          DataTable dt = sql.executeQuery("SELECT * FROM players WHERE UUID='" + getUniqueId() + "'");
          sql.close();
          EconomyAPI.players.put(EconomyAPI.this, new EconomyPlayer(EconomyAPI.this, 
            Double.parseDouble(dt.getRows()[0].getCell("balance").toString())));
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }
  


  public static void set(final Player player, Double amount)
  {
    ((EconomyPlayer)players.get(player)).setBalance(amount);
    MySqlManager.doRunnable(new Runnable()
    {
      public void run() {
        MySqlManager sql = new MySqlManager();
        try {
          sql.open();
          sql.executeNonQuery(
            "UPDATE players SET Balance=" + EconomyAPI.this + " WHERE UUID='" + player.getUniqueId() + "'");
          sql.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    });
  }
  





  public static void update(final Player player, Double amount)
  {
    ((EconomyPlayer)players.get(player)).updateBalance(amount);
    MySqlManager.doRunnable(new Runnable()
    {
      public void run() {
        MySqlManager sql = new MySqlManager();
        try {
          sql.open();
          sql.executeNonQuery("UPDATE players SET Balance=(Balance+(" + EconomyAPI.this + ")) WHERE UUID='" + 
            player.getUniqueId() + "'");
          sql.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    });
  }
}
