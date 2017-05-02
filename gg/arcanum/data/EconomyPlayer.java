package gg.arcanum.data;

import org.bukkit.entity.Player;

public class EconomyPlayer {

	private Player player;
	private double balance;

	public EconomyPlayer(Player player, double balance) {
		this.player = player;
		this.balance = balance;
	}

	/**
	 * Get player
	 * 
	 * @return
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Get player balance
	 * 
	 * @return
	 */
	public double getBalance() {
		return balance;
	}

	/**
	 * Set player balance
	 * 
	 * @param balance
	 */
	public void setBalance(Double balance) {
		this.balance = balance;
	}

	/**
	 * Update player balance
	 * 
	 * @param balanceUpdate
	 */
	public void updateBalance(Double balanceUpdate) {
		this.balance += balanceUpdate;
	}

}
