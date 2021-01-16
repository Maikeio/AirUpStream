package net.ddns.maikeio.air_up_stream;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public class AirUpStream extends JavaPlugin {

	public static String SafeFolder = "plugins/AitUpStream/";

	@Override
	public void onEnable() {

		// adds Listener for AutoCrafter
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new AirUpStramListener(), 0, 1);
		getServer().getPluginManager().registerEvents(new BoosterListener(), this);
		
		// adds the Custom Recpies
		recipes();

		// test for save Folder
		if (!new File("plugins/AitUpStream").exists())
			new File("plugins/AitUpStream").mkdirs();
	}

	@Override
	public void onDisable() {

	}

	private void recipes() {

		// AUTOCRAFTER_UPGRADE Recipe
		NamespacedKey Booster_key = new NamespacedKey(this, "Autocrafter_Upgrade");
		ShapedRecipe Booster_recipe = new ShapedRecipe(Booster_key, BoosterHandler.BOOSTER_ITEM());

		// Pattern
		Booster_recipe.shape("S", "P", "S");

		// Definition
		Booster_recipe.setIngredient('S', Material.SHULKER_SHELL);
		Booster_recipe.setIngredient('P', Material.PHANTOM_MEMBRANE);
		Bukkit.addRecipe(Booster_recipe);

	}

}
