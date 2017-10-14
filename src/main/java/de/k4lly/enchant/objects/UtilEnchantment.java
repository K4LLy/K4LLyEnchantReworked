package main.java.de.k4lly.enchant.objects;

import com.sun.org.apache.xpath.internal.operations.Bool;
import main.java.de.k4lly.enchant.controller.PluginController;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.entity.ItemMergeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.ArrayList;
import java.util.List;

public class UtilEnchantment {

    public static boolean hasCustomEnchant(List<String> lore) {
        for (String str : lore) {
            if (isCustomEnchant(str)) return true;
        }
        return false;
    }

    public static boolean isCustomEnchant(String str) {
        return str.startsWith(ChatColor.GRAY + "Wither ") || str.startsWith(ChatColor.GRAY + "Fire Touch ") || str.startsWith(ChatColor.GRAY + "Poison Touch ") || str.startsWith(ChatColor.GRAY + "XP-Boost ") || str.startsWith(ChatColor.GRAY + "Night Vision ") || str.startsWith(ChatColor.GRAY + "Rapid Fire ");
    }

    public static boolean containsEnchant(String name, ItemStack item) {
        for (String str : item.getItemMeta().getLore()) {
            if (str.startsWith(ChatColor.GRAY + name)) return true;
        }
        return false;
    }

    public static void enchantItem(String name, int level, ItemStack item) {
        if (level == 0) return;
        ItemMeta itemMeta = item.getItemMeta();
        List<String> lore = itemMeta.getLore();
        if (lore == null || isEmpty(lore)) {
            List<String> newLore = new ArrayList<>();
            if (name.startsWith(ChatColor.GRAY + "")) {
                newLore.add(name + " " + Util.getRomanNumber(level));
                itemMeta.setLore(newLore);
            } else {
                newLore.add(ChatColor.GRAY + name + " " + Util.getRomanNumber(level));
                itemMeta.setLore(newLore);
            }
        } else {
            if (name.startsWith(ChatColor.GRAY + "")) {
                if (!containsEnchant(name, item)) lore.add(name + " " + Util.getRomanNumber(level));
                itemMeta.setLore(lore);
            } else {
                if (!containsEnchant(name, item)) lore.add(ChatColor.GRAY + name + " " + Util.getRomanNumber(level));
                itemMeta.setLore(lore);
            }
        }
        item.setItemMeta(itemMeta);
    }

    public static boolean isEmpty(List<String> lore) {
        for (String str : lore) {
            if (!str.equals("") && str != null) return false;
        }
        return true;
    }

    public static int getCELevel(String str) throws Exception {
        String[] words = str.trim().split("\\s+");
        return words.length == 3 ? Util.parseRomanNumber(words[2]) : Util.parseRomanNumber(words[1]);
    }

    public static String getCEName(String str) {
        String[] words = str.trim().split("\\s+");
        return words.length == 3 ? words[0] + " " + words[1] : words [0];
    }

    public static Boolean hasConfliction(ItemMeta meta, Enchantment enchantment, Boolean isEnchantedBook) {
        if (isEnchantedBook) {
            for (Enchantment ench : ((EnchantmentStorageMeta) meta).getStoredEnchants().keySet()) {
                if (enchantment != ench && enchantment.conflictsWith(ench)) {
                    return true;
                }
            }
        } else {
            for (Enchantment ench : meta.getEnchants().keySet()) {
                if (enchantment != ench && enchantment.conflictsWith(ench)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void combineEnchantments(PluginController controller, ItemStack leftItem, ItemStack rightItem, ItemMeta resultMeta) {
        if (UtilMaterial.isEnchantedBook(leftItem.getType())) {
            combineBooks(controller, leftItem, rightItem, (EnchantmentStorageMeta) resultMeta);
        } else if (UtilMaterial.isEnchantable(leftItem.getType()) && UtilMaterial.isEnchantedBook(rightItem.getType())) {
            combineItemWithBook(controller, leftItem, rightItem, resultMeta);
        } else if ((UtilMaterial.isEnchantable(leftItem.getType()) && UtilMaterial.isEnchantable(rightItem.getType()))) {
            combineItems(controller, leftItem, rightItem, resultMeta);
        }
    }

    private static void combineBooks(PluginController controller, ItemStack leftBook, ItemStack rightBook, EnchantmentStorageMeta resultMeta) {
        EnchantmentStorageMeta leftEnchantmentMeta = (EnchantmentStorageMeta) leftBook.getItemMeta();
        EnchantmentStorageMeta rightEnchantmentMeta = (EnchantmentStorageMeta) rightBook.getItemMeta();

        for (Enchantment leftEnchantment : leftEnchantmentMeta.getEnchants().keySet()) {
            for (Enchantment rightEnchantment : rightEnchantmentMeta.getStoredEnchants().keySet()) {
                if (leftEnchantmentMeta.hasStoredEnchant(rightEnchantment) && rightEnchantmentMeta.hasStoredEnchant(leftEnchantment)) {
                    if (leftEnchantmentMeta.getStoredEnchantLevel(leftEnchantment) == rightEnchantmentMeta.getStoredEnchantLevel(rightEnchantment) && leftEnchantmentMeta.getStoredEnchantLevel(leftEnchantment) < controller.getMain().getConfig().getInt(leftEnchantment.getName())) {
                        resultMeta.addEnchant(leftEnchantment, leftEnchantmentMeta.getStoredEnchantLevel(leftEnchantment) + 1, true);
                    } else if (leftEnchantmentMeta.getStoredEnchantLevel(leftEnchantment) >= rightEnchantmentMeta.getStoredEnchantLevel(rightEnchantment)) {
                        resultMeta.addEnchant(leftEnchantment, leftEnchantmentMeta.getStoredEnchantLevel(leftEnchantment), true);
                    } else if (leftEnchantmentMeta.getStoredEnchantLevel(leftEnchantment) < rightEnchantmentMeta.getStoredEnchantLevel(rightEnchantment)) {
                        resultMeta.addEnchant(rightEnchantment, rightEnchantmentMeta.getStoredEnchantLevel(rightEnchantment), true);
                    }
                } else if (!rightEnchantmentMeta.hasStoredEnchant(leftEnchantment)) {
                    if (!hasConfliction(resultMeta, leftEnchantment, false)) {
                        resultMeta.addEnchant(leftEnchantment, leftEnchantmentMeta.getStoredEnchantLevel(leftEnchantment), true);
                    }
                } else if (!leftEnchantmentMeta.hasStoredEnchant(rightEnchantment)) {
                    if (!hasConfliction(resultMeta, rightEnchantment, false)) {
                        resultMeta.addEnchant(rightEnchantment, rightEnchantmentMeta.getStoredEnchantLevel(rightEnchantment), true);
                    }
                }
            }
        }
        //TODO: Add Custom Enchantments
        //addCustomEnchantment(itemLeft, itemRight);
        //checkCEConfliction(itemLeft, itemRight);
    }

    private static void combineItemWithBook(PluginController controller, ItemStack item, ItemStack book, ItemMeta resultMeta) {
        ItemMeta itemItemMeta = item.getItemMeta();
        EnchantmentStorageMeta bookEnchantmentMeta = (EnchantmentStorageMeta) book.getItemMeta();

        for (Enchantment leftEnchantment : itemItemMeta.getEnchants().keySet()) {
            for (Enchantment rightEnchantment : bookEnchantmentMeta.getStoredEnchants().keySet()) {
                if (itemItemMeta.hasEnchant(rightEnchantment) && bookEnchantmentMeta.hasStoredEnchant(leftEnchantment)) {
                    if (itemItemMeta.getEnchantLevel(leftEnchantment) == bookEnchantmentMeta.getStoredEnchantLevel(rightEnchantment) && itemItemMeta.getEnchantLevel(leftEnchantment) < controller.getMain().getConfig().getInt(leftEnchantment.getName())) {
                        resultMeta.addEnchant(leftEnchantment, itemItemMeta.getEnchantLevel(leftEnchantment) + 1, true);
                    } else if (itemItemMeta.getEnchantLevel(leftEnchantment) >= bookEnchantmentMeta.getStoredEnchantLevel(rightEnchantment)) {
                        resultMeta.addEnchant(leftEnchantment, itemItemMeta.getEnchantLevel(leftEnchantment), true);
                    } else if (itemItemMeta.getEnchantLevel(leftEnchantment) < bookEnchantmentMeta.getStoredEnchantLevel(rightEnchantment)) {
                        resultMeta.addEnchant(rightEnchantment, bookEnchantmentMeta.getStoredEnchantLevel(rightEnchantment), true);
                    }
                } else if (!bookEnchantmentMeta.hasStoredEnchant(leftEnchantment)) {
                    if (!hasConfliction(resultMeta, leftEnchantment, false)) {
                        resultMeta.addEnchant(leftEnchantment, itemItemMeta.getEnchantLevel(leftEnchantment), true);
                    }
                } else if (!itemItemMeta.hasEnchant(rightEnchantment)) {
                    if (!hasConfliction(resultMeta, rightEnchantment, false) && rightEnchantment.canEnchantItem(item)) {
                        resultMeta.addEnchant(rightEnchantment, bookEnchantmentMeta.getStoredEnchantLevel(rightEnchantment), true);
                    }
                }
            }
        }
        //TODO: Add Custom Enchantmentsa
        //addCustomEnchantment(itemLeft, itemRight);
        //checkCEConfliction(itemLeft, itemRight);
    }

    private static void combineItems(PluginController controller, ItemStack leftItem, ItemStack rightItem, ItemMeta resultMeta) {
        ItemMeta leftItemMeta = leftItem.getItemMeta();
        ItemMeta rightItemMeta = rightItem.getItemMeta();

        for (Enchantment leftEnchantment : leftItemMeta.getEnchants().keySet()) {
            for (Enchantment rightEnchantment : rightItemMeta.getEnchants().keySet()) {
                if (leftItemMeta.hasEnchant(rightEnchantment) && rightItemMeta.hasEnchant(leftEnchantment)) {
                    if (leftItemMeta.getEnchantLevel(leftEnchantment) == rightItemMeta.getEnchantLevel(rightEnchantment) && leftItemMeta.getEnchantLevel(leftEnchantment) < controller.getMain().getConfig().getInt(leftEnchantment.getName())) {
                        resultMeta.addEnchant(leftEnchantment, leftItemMeta.getEnchantLevel(leftEnchantment) + 1, true);
                    } else if (leftItemMeta.getEnchantLevel(leftEnchantment) >= rightItemMeta.getEnchantLevel(rightEnchantment)) {
                        resultMeta.addEnchant(leftEnchantment, leftItemMeta.getEnchantLevel(leftEnchantment), true);
                    } else if (leftItemMeta.getEnchantLevel(leftEnchantment) < rightItemMeta.getEnchantLevel(rightEnchantment)) {
                        resultMeta.addEnchant(rightEnchantment, rightItemMeta.getEnchantLevel(rightEnchantment), true);
                    }
                } else if (!rightItemMeta.hasEnchant(leftEnchantment)) {
                    if (!hasConfliction(resultMeta, leftEnchantment, false)) {
                        resultMeta.addEnchant(leftEnchantment, leftItemMeta.getEnchantLevel(leftEnchantment), true);
                    }
                } else if (!leftItemMeta.hasEnchant(rightEnchantment)) {
                    if (!hasConfliction(resultMeta, rightEnchantment, false)) {
                        resultMeta.addEnchant(rightEnchantment, rightItemMeta.getEnchantLevel(rightEnchantment), true);
                    }
                }
            }
        }
        //TODO: Add Custom Enchantments
        //addCustomEnchantment(itemLeft, itemRight);
        //checkCEConfliction(itemLeft, itemRight);
    }
}
