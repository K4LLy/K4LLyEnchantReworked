package main.java.de.k4lly.enchant.objects;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class ResultItem extends ItemStack {
    private ItemStack itemStack;

    public ResultItem(Material material) {
        super(material);
    }

    public ResultItem(ItemStack itemStack) {
        super(itemStack);
    }

    public void addEnchantment(Enchantment enchantment, int level) {
        for (Enchantment ench : this.getEnchantments().keySet()) {
            if (ench != enchantment) {
                if (enchantment.conflictsWith(ench) || !enchantment.canEnchantItem(this)) {
                    return;
                }
            }
        }

        if (UtilMaterial.isEnchantedBook(this.getType())) {
            ((EnchantmentStorageMeta)(this.getItemMeta()==null?(this.setItemMeta(Bukkit.getItemFactory().getItemMeta(this.getType()))):this.getItemMeta())).addStoredEnchant(enchantment, level, true);
        } else {
            ((ItemMeta)(this.getItemMeta()==null?(this.setItemMeta(Bukkit.getItemFactory().getItemMeta(this.getType()))):this.getItemMeta())).addEnchant(enchantment, level, true);
        }
    }
}
