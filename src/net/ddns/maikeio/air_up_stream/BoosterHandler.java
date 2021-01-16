package net.ddns.maikeio.air_up_stream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

public class BoosterHandler {
	HashMap<Location, Booster> booster;
	final File saveFile;

	public BoosterHandler() {
		this.saveFile = new File(AirUpStream.SafeFolder + "Booster.save");
		load(this.saveFile);
	}

	private void load(File file) {
		if (saveFile.exists()) {
			// read File
			try {

				FileInputStream fileIn = new FileInputStream(file);
				BukkitObjectInputStream objectIn = new BukkitObjectInputStream(fileIn);
				Object obj = objectIn.readObject();
				this.booster = (HashMap<Location, Booster>) obj;
				objectIn.close();
				fileIn.close();

			} catch (Exception ex) {
				ex.printStackTrace();
			}
			// Initialisiere AutoCrafterMap
		} else
			this.booster = new HashMap<Location, Booster>();
	}

	public void check() {
		// tests every AutoCrafter if it is still there
		this.booster.keySet().removeIf(location -> (location.getBlock().getType() != Material.DROPPER));
	}

	public void save() {
		try {
			// saves aCrafter Map to File

			FileOutputStream fileOut = new FileOutputStream(this.saveFile);
			BukkitObjectOutputStream objectOut = new BukkitObjectOutputStream(fileOut);
			objectOut.writeObject(this.booster);
			objectOut.close();
			fileOut.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	static public ItemStack BOOSTER_ITEM() {
		ItemStack item = new ItemStack(Material.COMMAND_BLOCK);
		ItemMeta meta = item.getItemMeta();

		meta.setCustomModelData(1);
		meta.setDisplayName(ChatColor.WHITE + "Upgrade Module");

		// Discription of the Item
		List<String> discription = new ArrayList<String>();
		discription.add(ChatColor.DARK_AQUA + "this Block is used");
		discription.add(ChatColor.DARK_AQUA + "to boost you up while");
		discription.add(ChatColor.DARK_AQUA + "flying with Elytra above");
		meta.setLore(discription);
		item.setItemMeta(meta);
		return item;
	}

	static public boolean isSlowFallingPotion(ItemStack item) {
		if (!(item.getItemMeta() instanceof PotionMeta))
			return false;

		if (((PotionMeta) item.getItemMeta()).getBasePotionData().getType() == PotionType.SLOW_FALLING)
			return true;
		return false;
	}

	@SuppressWarnings("deprecation")
	public void BlockPlaced(Block block, Player player) {
		if (player.getItemInHand() != BOOSTER_ITEM()) {
			return;
		}
		this.booster.put(block.getLocation(), new Booster(block.getLocation()));
	}

	public boolean breakAutoCrafter(Location location) {
		if (!booster.containsKey(location))
			return true;
		booster.remove(location);
		location.getWorld().dropItemNaturally(location, BoosterHandler.BOOSTER_ITEM());
		return false;
	}

}
