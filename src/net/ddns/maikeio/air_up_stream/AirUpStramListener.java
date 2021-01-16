package net.ddns.maikeio.air_up_stream;

import java.util.ArrayList;
import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class AirUpStramListener implements Runnable {
	Collection<? extends Player> player;
	ArrayList<Player> flying;

	public AirUpStramListener() {
		flying = new ArrayList<Player>();
	}

	@Override
	public void run() {
		player = Bukkit.getOnlinePlayers();
		for (Player player : this.player) {
			Location loc = player.getLocation();
			if (loc.getBlock().getType() == Material.VOID_AIR && 0 < loc.getY() && 255 > loc.getY()
					&& player.isGliding()) {
				flying.add(player);
				player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 20, 50));
			}

			if (flying.contains(player) && loc.getBlock().getType() != Material.VOID_AIR) {

				this.flying.remove(player);
				player.removePotionEffect(PotionEffectType.LEVITATION);
				player.setGliding(true);

			}

		}
	}
}
