package main.java.de.k4lly.enchant.objects;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.util.ArrayList;

public class ResultItem extends ItemStack {

    private ArrayList<Enchantment> enchantments = new ArrayList<>();

    public ResultItem() {
        super();
    }

    public void addEnchantment(Enchantment enchantment, int level) {
        for (Enchantment ench : enchantments) {
            if (ench.conflictsWith(enchantment) || enchantment.canEnchantItem(this)) {
                return;
            }
        }

        this.enchantments.add(enchantment);
        if (UtilMaterial.isEnchantedBook(this.getType())) {
            ((EnchantmentStorageMeta) this.getItemMeta()).addStoredEnchant(enchantment, level, true);
        } else {
            this.getItemMeta().addEnchant(enchantment, level, true);
        }
    }
}
