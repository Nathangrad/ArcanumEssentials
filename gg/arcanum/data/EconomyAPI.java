package gg.arcanum.data;

import java.sql.SQLException;

import org.bukkit.entity.Player;

/**
 * Economy API for getting, setting and updating the player's balance
 *
 */
public class EconomyAPI {

	/**
	 * Obtain the balance as a string with the 'Crowns' suffix
	 * 
	 * @param player
	 * @return
	 * @throws NumberFormatException
	 * @throws InvalidDataColumnException
	 */
	public static String getBalanceString(Player player) throws NumberFormatException {
		return get(player) + " Coins";
	}

	/**
	 * Pay an amount from their own balance into the balance of another player
	 * 
	 * @param playerFrom
	 *            The player to be debited
	 * @param playerTo
	 *            The player to be credited
	 * @param amount
	 *            The amount to be transferred
	 * @return
	 * @throws NumberFormatException
	 * @throws InvalidDataColumnException
	 */
	public static boolean payPlayer(Player playerFrom, Player playerTo, double amount) throws NumberFormatException {
		if (get(playerFrom) < amount) {
			return false;
		}
		update(playerFrom, 0 - amount);
		update(playerTo, amount);
		MySqlManager sql = new MySqlManager();
		try {
			sql.open();
			sql.executeNonQuery("INSERT INTO PaymentHistory (Sender, Receiver, Amount, Date) VALUES ('"
					+ playerFrom.getUniqueId() + "','" + playerTo.getUniqueId() + "'," + amount + ", NOW())");
			sql.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Get the balance of a player
	 * 
	 * @param player
	 * @return
	 */
	public static Double get(Player player) {
		MySqlManager sql = new MySqlManager();
		try {
			sql.open();
			DataTable dt = sql.executeQuery("SELECT * FROM players WHERE UUID='" + player.getUniqueId() + "'");
			sql.close();
			return Double.parseDouble(dt.getRows()[0].getCell("balance").toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0.0;
	}

	/**
	 * Set the balance of a player
	 */
	public static void set(Player player, Double amount) {
		MySqlManager sql = new MySqlManager();
		try {
			sql.open();
			sql.executeNonQuery("UPDATE players SET Balance=" + amount + " WHERE UUID='" + player.getUniqueId() + "'");
			sql.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Update the balance of a player
	 * 
	 * @param player
	 * @param amount
	 */
	public static void update(Player player, Double amount) {
		MySqlManager sql = new MySqlManager();
		try {
			sql.open();
			sql.executeNonQuery(
					"UPDATE players SET Balance=(Balance+(" + amount + ")) WHERE UUID='" + player.getUniqueId() + "'");
			sql.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Set the player
	 * 
	 * @param player
	 * @param amount
	 */
	public static void setup(Player player, Double amount) {
		MySqlManager sql = new MySqlManager();
		try {
			sql.open();
			sql.executeNonQuery("INSERT INTO players (UUID,Balance,Online,Joined) VALUES ('" + player.getUniqueId()
					+ "'," + amount + ",0,NOW(),NOW())");
			sql.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
