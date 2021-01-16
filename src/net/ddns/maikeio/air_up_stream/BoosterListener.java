package net.ddns.maikeio.air_up_stream;

import org.bukkit.Material;
import org.bukkit.block.Dropper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.ddns.maikeio.blatt.AutoCraftHandler;

public class BoosterListener implements Listener {
	BoosterHandler handler;

	public BoosterListener() {
		handler = new BoosterHandler();
	}

	@EventHandler
	public void BlockPlaceEvent(org.bukkit.event.block.BlockPlaceEvent event) {
		handler.BlockPlaced(event.getBlock(), event.getPlayer());
	}

	@EventHandler
	public void PluginDisableEvent(org.bukkit.event.server.PluginDisableEvent event) {
		// saves all AutoCrafters to File
		handler.check();
		handler.save();
	}

	@EventHandler
	public void BlockBreakEvent(org.bukkit.event.block.BlockBreakEvent event) {
		if (event.getBlock().getType() == Material.DROPPER) {
			event.setDropItems(handler.breakAutoCrafter(event.getBlock().getLocation()));
		}
	}

	public void InventoryEvent(InventoryClickEvent event) {

		// Inventory of deInvstination
		Inventory deInv = event.getInventory();
		Player player = (Player) event.getWhoClicked();

		// Test for Upgrade Item was put into upgrable Dropper
		if (deInv.getHolder() instanceof Dropper && BoosterHandler.isSlowFallingPotion(event.getCursor())
				&& event.getRawSlot() < 9) {

			int amount = event.getCursor().getAmount();
			event.setCursor(new ItemStack(Material.AIR));
			handler.addToEdditedAutoCrafter((Player) event.getWhoClicked(), deInv.getLocation(), amount);

		}

		// Test for take out an Item from Edit-Menu
		if (deInv.getType() == InventoryType.WORKBENCH && event.getRawSlot() == 0) {
			handler.register(player, deInv, event.getCurrentItem());
		}
	}

}
