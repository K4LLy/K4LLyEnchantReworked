package main.java.de.k4lly.enchant.listener;

import main.java.de.k4lly.enchant.controller.PluginController;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import java.util.HashMap;

public class InfiniteArrow implements Listener {

    private PluginController controller;
    HashMap<Player, ItemStack> storedItem = new HashMap<>();

    public InfiniteArrow(PluginController controller) { this.controller = controller; }

    @EventHandler
    public void onEntityShootBow (EntityShootBowEvent entityShootBowEvent) {
        if (!(entityShootBowEvent.getEntity() instanceof Player)) return;
        returnItem((Player) entityShootBowEvent.getEntity());
    }

    @EventHandler
    public void onPlayerItemHeld (PlayerItemHeldEvent playerItemHeldEvent) {
        if (!controller.getMain().getConfig().getBoolean("enableInfiniteArrows")) return;
        Player p = playerItemHeldEvent.getPlayer();
        ItemStack prevItem = p.getInventory().getItem(playerItemHeldEvent.getPreviousSlot());
        if(!storedItem.containsKey(p) || prevItem.isSimilar(null)) return;
        if (prevItem.getItemMeta().hasEnchant(org.bukkit.enchantments.Enchantment.ARROW_INFINITE)) {
            returnItem(p);
        }
    }

    @EventHandler
    public void onInventoryOpen (InventoryOpenEvent event) {
        if (!controller.getMain().getConfig().getBoolean("enableInfiniteArrows")) return;
        Player p = (Player) event.getPlayer();
        if (!storedItem.containsKey(p)) return;
        returnItem(p);
    }

    @EventHandler
    public void onInventoryClick (InventoryClickEvent event) {
        if (!(event.getClickedInventory() instanceof PlayerInventory)) return;
        if (!controller.getMain().getConfig().getBoolean("enableInfiniteArrows")) return;
        Player p = (Player) event.getWhoClicked();
        ItemStack item9 = event.getClickedInventory().getItem(9);
        if (item9 == null || !item9.isSimilar(new ItemStack(Material.ARROW))) return;
        if (!storedItem.containsKey(p)) return;
        returnItem(p);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent playerInteractEvent) {
        if (!controller.getMain().getConfig().getBoolean("enableInfiniteArrows")) return;
        Player p = playerInteractEvent.getPlayer();
        if (!(playerInteractEvent.getAction().equals(Action.RIGHT_CLICK_AIR) || playerInteractEvent.getAction().equals(Action.RIGHT_CLICK_BLOCK))) return;
        if(storedItem.containsKey(p)) return;
        if(p.getItemInHand() == null || !(p.getItemInHand().getType().equals(Material.BOW) && p.getItemInHand().getItemMeta().hasEnchant(Enchantment.ARROW_INFINITE))) return;
        ItemStack item = p.getInventory().getItem(9);
        storedItem.put(p, item);
        p.getInventory().setItem(9, new ItemStack(Material.ARROW, 64));
    }

    private void returnItem(Player player) {
        if (storedItem.containsKey(player.getPlayer())) {
            player.getInventory().setItem(9, storedItem.get(player));
            player.updateInventory();
            storedItem.remove(player);
        }
    }
}
