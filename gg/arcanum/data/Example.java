package gg.arcanum.data;

import java.sql.SQLException;
import java.util.concurrent.Callable;
import java.util.logging.Logger;

import gg.arcanum.core.Core;

public abstract class Example {

	volatile static DataTable result;

	public static void example() {
		MySqlManager.doRunnable(new Runnable() {
			@Override
			public void run() {
				MySqlManager sql = new MySqlManager();
				try {
					sql.open();
					result = sql.executeQuery("SELECT playerID, UUID, balance FROM players");
					sql.close();
					MySqlManager.doCallable(new Callable<Void>() {
						@Override
						public Void call() throws Exception {
							Logger log = Core.getPlugin().getLogger();
							for (DataRow row : result.getRows()) {
								for (DataColumn col : result.getColumns()) {
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
