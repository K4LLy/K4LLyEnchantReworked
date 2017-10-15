package main.java.de.k4lly.enchant.listener;

import main.java.de.k4lly.enchant.controller.PluginController;
import main.java.de.k4lly.enchant.objects.*;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Anvil implements Listener {

    private PluginController controller;
    private static int SLOT_0 = 0;
    private static int SLOT_1 = 1;

    public Anvil(PluginController controller) {
        this.controller = controller;
    }

    @EventHandler
    public void onPrepareAnvil(PrepareAnvilEvent prepareAnvilEvent) throws Exception {
        setAnvilResult(prepareAnvilEvent);
    }

    private void setAnvilResult(PrepareAnvilEvent prepareAnvilEvent) throws Exception {
        ItemStack leftItem = prepareAnvilEvent.getInventory().getItem(SLOT_0);
        ItemStack rightItem = prepareAnvilEvent.getInventory().getItem(SLOT_1);
        if (leftItem != null && rightItem != null && !UtilMaterial.isRepairMaterial(rightItem.getType())) {
            if ((UtilMaterial.isEnchantable(leftItem.getType()) && UtilMaterial.isEnchantedBook(rightItem.getType())) || leftItem.getType().equals(rightItem.getType())) {
                prepareAnvilEvent.setResult(resultItem(leftItem, rightItem, prepareAnvilEvent));
            }
        }
    }

    public ItemStack resultItem(ItemStack leftItem, ItemStack rightItem, PrepareAnvilEvent prepareAnvilEvent) throws Exception {
        ItemStack resultItem = new ItemStack(leftItem.getType());
        ItemMeta resultMeta = resultItem.getItemMeta();
        UtilEnchantment.combineEnchantments(controller, leftItem, rightItem, resultMeta);

        if (leftItem.getType().equals(Material.ENCHANTED_BOOK)) {
            if (prepareAnvilEvent.getInventory().getRenameText() == null && leftItem.getItemMeta().getDisplayName().equals(prepareAnvilEvent.getInventory().getRenameText())) {
                resultItem.getItemMeta().setDisplayName(leftItem.getItemMeta().getDisplayName());
            } else {
                resultItem.getItemMeta().setDisplayName(prepareAnvilEvent.getInventory().getRenameText());
            }
            //TODO: add Custom Enchantments
            //for (int i = 0; i < anvilItems.getItemResultCustomEnchantmentSize(); i++) {
            //    UtilEnchantment.enchantItem(anvilItems.getItemResultCustomEnchantment(i), anvilItems.getItemResultCELevel(i), item2);
            //}
        } else {
            if (prepareAnvilEvent.getInventory().getRenameText() == null && leftItem.getItemMeta().getDisplayName().equals(prepareAnvilEvent.getInventory().getRenameText())) {
                resultItem.getItemMeta().setDisplayName(leftItem.getItemMeta().getDisplayName());
            } else {
                resultItem.getItemMeta().setDisplayName(prepareAnvilEvent.getInventory().getRenameText());
            }
            //TODO: add Custom Enchantments
            //for (int i = 0; i < anvilItems.getItemResultCustomEnchantmentSize(); i++) {
            //    UtilEnchantment.enchantItem(anvilItems.getItemResultCustomEnchantment(i), anvilItems.getItemResultCELevel(i), item2);
            //}

            if (rightItem.getType().equals(Material.ENCHANTED_BOOK)) {
                resultItem.setDurability(leftItem.getDurability());
            } else {
                short max = leftItem.getType().getMaxDurability();
                int durability = (max - ((max - leftItem.getDurability()) + (max - rightItem.getDurability()) + max / 20));
                resultItem.setDurability(durability < 0 ? 0 : (short) durability);
            }
        }
        resultItem.setItemMeta(resultMeta);
        return resultItem;
    }
}
