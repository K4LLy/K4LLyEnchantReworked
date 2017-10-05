package main.java.de.k4lly.enchant.objects;

import main.java.de.k4lly.enchant.controller.PluginController;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
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

    public static void combineEnchantments(PluginController controller,ItemStack leftItem, ItemStack rightItem, ResultItem resultItem) {
        if (UtilMaterial.isEnchantedBook(leftItem.getType())) {
            combineBooks(controller, leftItem, rightItem, resultItem);
        } else if (UtilMaterial.isEnchantable(leftItem.getType()) && UtilMaterial.isEnchantedBook(rightItem.getType())) {
            combineItemWithBook(controller, leftItem, rightItem, resultItem);
        } else if ((UtilMaterial.isEnchantable(leftItem.getType()) && UtilMaterial.isEnchantable(rightItem.getType()))) {
            combineItems(controller, leftItem, rightItem, resultItem);
        }
    }

    private static void combineBooks(PluginController controller, ItemStack leftBook, ItemStack rightBook, ResultItem resultItem) {
        EnchantmentStorageMeta leftEnchantmentMeta = (EnchantmentStorageMeta) leftBook.getItemMeta();
        EnchantmentStorageMeta rightEnchantmentMeta = (EnchantmentStorageMeta) rightBook.getItemMeta();

        for (Enchantment enchantment : Enchantment.values()) {
            if (leftEnchantmentMeta.hasStoredEnchant(enchantment) && rightEnchantmentMeta.hasStoredEnchant(enchantment)) {
                if (leftEnchantmentMeta.getStoredEnchantLevel(enchantment) == rightEnchantmentMeta.getStoredEnchantLevel(enchantment) &&
                        leftEnchantmentMeta.getStoredEnchantLevel(enchantment) < controller.getMain().getConfig().getInt(enchantment.getName())) {
                    System.out.println("[DEBUG] increased Enchantment.");
                    resultItem.addEnchantment(enchantment, leftEnchantmentMeta.getStoredEnchantLevel(enchantment) + 1);
                } else if (leftEnchantmentMeta.getStoredEnchantLevel(enchantment) >= rightEnchantmentMeta.getStoredEnchantLevel(enchantment)) {
                    resultItem.addEnchantment(enchantment, leftEnchantmentMeta.getStoredEnchantLevel(enchantment));
                } else if (leftEnchantmentMeta.getStoredEnchantLevel(enchantment) < rightEnchantmentMeta.getStoredEnchantLevel(enchantment)) {
                    resultItem.addEnchantment(enchantment, rightEnchantmentMeta.getStoredEnchantLevel(enchantment));
                }
            } else if (leftEnchantmentMeta.hasStoredEnchant(enchantment) && !rightEnchantmentMeta.hasStoredEnchant(enchantment)) {
                resultItem.addEnchantment(enchantment, leftEnchantmentMeta.getStoredEnchantLevel(enchantment));
            } else if (!leftEnchantmentMeta.hasStoredEnchant(enchantment) && rightEnchantmentMeta.hasStoredEnchant(enchantment)) {
                resultItem.addEnchantment(enchantment, rightEnchantmentMeta.getStoredEnchantLevel(enchantment));
            }
        }
        //TODO: Add Custom Enchantments
        //addCustomEnchantment(itemLeft, itemRight);
        //checkCEConfliction(itemLeft, itemRight);
    }

    private static void combineItemWithBook(PluginController controller, ItemStack item, ItemStack book, ResultItem resultItem) {
        ItemMeta itemItemMeta = item.getItemMeta();
        EnchantmentStorageMeta bookEnchantmentMeta = (EnchantmentStorageMeta) book.getItemMeta();

        for (Enchantment enchantment : Enchantment.values()) {
            if (itemItemMeta.hasEnchant(enchantment) && bookEnchantmentMeta.hasStoredEnchant(enchantment)) {
                if (itemItemMeta.getEnchantLevel(enchantment) == bookEnchantmentMeta.getStoredEnchantLevel(enchantment) && itemItemMeta.getEnchantLevel(enchantment) < controller.getMain().getConfig().getInt(enchantment.getName())) {
                    resultItem.addEnchantment(enchantment, itemItemMeta.getEnchantLevel(enchantment) + 1);
                } else if (itemItemMeta.getEnchantLevel(enchantment) >= bookEnchantmentMeta.getStoredEnchantLevel(enchantment)) {
                    resultItem.addEnchantment(enchantment, itemItemMeta.getEnchantLevel(enchantment));
                } else if (itemItemMeta.getEnchantLevel(enchantment) < bookEnchantmentMeta.getStoredEnchantLevel(enchantment)) {
                    resultItem.addEnchantment(enchantment, bookEnchantmentMeta.getStoredEnchantLevel(enchantment));
                }
            } else if (itemItemMeta.hasEnchant(enchantment) && !bookEnchantmentMeta.hasStoredEnchant(enchantment)) {
                resultItem.addEnchantment(enchantment, itemItemMeta.getEnchantLevel(enchantment));
            } else if (!itemItemMeta.hasEnchant(enchantment) && bookEnchantmentMeta.hasStoredEnchant(enchantment)) {
                resultItem.addEnchantment(enchantment, bookEnchantmentMeta.getStoredEnchantLevel(enchantment));
            }
        }
        //TODO: Add Custom Enchantmentsa
        //addCustomEnchantment(itemLeft, itemRight);
        //checkCEConfliction(itemLeft, itemRight);
    }

    private static void combineItems(PluginController controller, ItemStack leftItem, ItemStack rightItem, ResultItem resultItem) {
        ItemMeta leftItemMeta = leftItem.getItemMeta();
        ItemMeta rightItemMeta = rightItem.getItemMeta();

        for (Enchantment enchantment : Enchantment.values()) {
            if (leftItemMeta.hasEnchant(enchantment) && rightItemMeta.hasEnchant(enchantment)) {
                if (leftItemMeta.getEnchantLevel(enchantment) == rightItemMeta.getEnchantLevel(enchantment) && leftItemMeta.getEnchantLevel(enchantment) < controller.getMain().getConfig().getInt(enchantment.getName())) {
                    resultItem.addEnchantment(enchantment, leftItemMeta.getEnchantLevel(enchantment) + 1);
                } else if (leftItemMeta.getEnchantLevel(enchantment) >= rightItemMeta.getEnchantLevel(enchantment)) {
                    resultItem.addEnchantment(enchantment, leftItemMeta.getEnchantLevel(enchantment));
                } else if (leftItemMeta.getEnchantLevel(enchantment) < rightItemMeta.getEnchantLevel(enchantment)) {
                    resultItem.addEnchantment(enchantment, rightItemMeta.getEnchantLevel(enchantment));
                }
            } else if (leftItemMeta.hasEnchant(enchantment) && !rightItemMeta.hasEnchant(enchantment)) {
                resultItem.addEnchantment(enchantment, leftItemMeta.getEnchantLevel(enchantment));
            } else if (!leftItemMeta.hasEnchant(enchantment) && rightItemMeta.hasEnchant(enchantment)) {
                resultItem.addEnchantment(enchantment, rightItemMeta.getEnchantLevel(enchantment));
            }
        }
        //TODO: Add Custom Enchantments
        //addCustomEnchantment(itemLeft, itemRight);
        //checkCEConfliction(itemLeft, itemRight);
    }
}
