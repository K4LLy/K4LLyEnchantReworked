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
            System.out.println("[DEBUG] FOR EACH: " + ench.getName());
            System.out.println("[DEBUG] " + enchantment.getName() + " conflicts with " + ench.getName() + "? " + enchantment.conflictsWith(ench));
            System.out.println("[DEBUG] " + enchantment.getName() + " can enchanted on " + this.getType().name() + "? " + !enchantment.canEnchantItem(this));
            if (ench != enchantment) {
                if (enchantment.conflictsWith(ench) || !enchantment.canEnchantItem(this)) {
                    System.out.println("[DEBUG] RETURN!!!");
                    return;
                }
            }
        }
        //this.addUnsafeEnchantment(enchantment, level);
        if (UtilMaterial.isEnchantedBook(this.getType())) {
            System.out.println("[DEBUG] Result = EnchantedBook");
            //System.out.println("[DEBUG] Added " + enchantment.getName() + "(" + level + ") successful? " + ((EnchantmentStorageMeta) this.getItemMeta()).addStoredEnchant(enchantment, level, true));
            ((EnchantmentStorageMeta)(this.getItemMeta()==null?(this.setItemMeta(Bukkit.getItemFactory().getItemMeta(this.getType()))):this.getItemMeta())).addStoredEnchant(enchantment, level, true);
        } else {
            System.out.println("[DEBUG] Result = Item");
            //this.getItemMeta().addEnchant(enchantment, level, true);
            ((ItemMeta)(this.getItemMeta()==null?(this.setItemMeta(Bukkit.getItemFactory().getItemMeta(this.getType()))):this.getItemMeta())).addEnchant(enchantment, level, true);
        }
    }
}
