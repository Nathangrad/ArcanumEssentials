package gg.arcanum.teleportation;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

import gg.arcanum.utils.RequestType;

public class TeleportRequest {

	private static Map<Player, TeleportRequest> requests = new HashMap<Player, TeleportRequest>();
	private Player requestSender;
	private Player requestReceiver;
	private RequestType requestType;

	public TeleportRequest(Player sender, Player receiver, RequestType type) {
		requestSender = sender;
		requestReceiver = receiver;
		requestType = type;
		if (requests.containsKey(receiver)) {
			requests.put(receiver, this);
		} else {
			requests.put(receiver, this);
		}
	}

	public static TeleportRequest getRequest(Player receiver) {
		if (requests.containsKey(receiver)) {
			return requests.get(receiver);
		} else {
			return null;
		}
	}

	public void acceptRequest() {
		if (requestType == RequestType.TPA) {
			requestSender.teleport(requestReceiver.getLocation());
		} else if (requestType == RequestType.TPAHERE) {
			requestReceiver.teleport(requestSender.getLocation());
		}
		requests.remove(requestReceiver);
	}

	public void denyRequest() {
		requests.remove(requestReceiver);
	}

	public Player getSender() {
		return requestSender;
	}

	public Player getReceiver() {
		return requestReceiver;
	}

	public RequestType getRequestType() {
		return requestType;
	}

}
