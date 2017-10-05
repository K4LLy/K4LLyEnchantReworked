package main.java.de.k4lly.enchant.listener;

import main.java.de.k4lly.enchant.controller.*;
import main.java.de.k4lly.enchant.objects.*;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

public class Crafting implements Listener {

    private PluginController controller;

    public Crafting(PluginController controller) {
        this.controller = controller;
    }

    @EventHandler
    public void onItemCraft(PrepareItemCraftEvent itemCraftEvent) {
        craftEnchantedBook(itemCraftEvent);
    }

    private void craftEnchantedBook(PrepareItemCraftEvent itemCraftEvent) {
        boolean enchItem = false;
        boolean book = false;
        boolean xpBottle = false;
        ItemStack item = null;

        for (int i = 1; i <= 9; i++) {
            if (itemCraftEvent.getInventory().getItem(i) != null && UtilMaterial.isEnchantable(itemCraftEvent.getInventory().getItem(i).getType()) && !enchItem) {
                enchItem = true;
                item = itemCraftEvent.getInventory().getItem(i);
            } else if (itemCraftEvent.getInventory().getItem(i) != null && itemCraftEvent.getInventory().getItem(i).getType().equals(Material.BOOK) && !book) {
                book = true;
            } else if (itemCraftEvent.getInventory().getItem(i) != null && itemCraftEvent.getInventory().getItem(i).getType().equals(Material.EXP_BOTTLE) && !xpBottle) {
                xpBottle = true;
            }
        }

        if (enchItem && book && xpBottle) {
            ItemStack result = new ItemStack(Material.ENCHANTED_BOOK);
            EnchantmentStorageMeta resultMeta = (EnchantmentStorageMeta) result.getItemMeta();

            if (item.getItemMeta().hasEnchants()) {
                for (Enchantment enchantment : Enchantment.values()) {
                    if (item.getItemMeta().hasEnchant(enchantment)) {
                        resultMeta.addStoredEnchant(enchantment, item.getEnchantmentLevel(enchantment), true);
                    }
                }
                result.setItemMeta(resultMeta);
                itemCraftEvent.getInventory().setResult(result);
            } else {
                itemCraftEvent.getInventory().setResult(new ItemStack(Material.AIR));
            }
        }
    }
}
