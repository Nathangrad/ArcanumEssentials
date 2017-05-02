package gg.arcanum.data;

import org.bukkit.entity.Player;

public class EconomyPlayer
{
  private Player player;
  private double balance;
  
  public EconomyPlayer(Player player, double balance) {
    this.player = player;
    this.balance = balance;
  }
  




  public Player getPlayer()
  {
    return player;
  }
  




  public double getBalance()
  {
    return balance;
  }
  




  public void setBalance(Double balance)
  {
    this.balance = balance.doubleValue();
  }
  




  public void updateBalance(Double balanceUpdate)
  {
    balance += balanceUpdate.doubleValue();
  }
}
