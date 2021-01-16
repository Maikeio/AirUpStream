package net.ddns.maikeio.air_up_stream;

import java.io.Serializable;

import org.bukkit.Location;

public class Booster  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2562351579219092970L;
	Location location;
	int fuel;
	public Booster(Location location) {
		this.location = location;
	}

}
