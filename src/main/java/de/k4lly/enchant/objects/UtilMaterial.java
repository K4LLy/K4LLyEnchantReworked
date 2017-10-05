package main.java.de.k4lly.enchant.objects;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import java.util.ArrayList;

public class UtilMaterial {
    private static ArrayList<Material> enchantableMaterial = loadEnchantableMaterials();

    private static ArrayList<Material> loadEnchantableMaterials() {
        ArrayList<Material> outList = new ArrayList<>();
        for (Material m : Material.values()) {
            if (m != null && isEnchantable(m)) {
                outList.add(m);
            }
        }
        return outList;
    }

    public static boolean isBook(Material material) {
        switch (material) {
            case ENCHANTED_BOOK:
            case BOOK:
                return true;
            default:
                return false;
        }
    }

    public static boolean isEnchantedBook(Material material) {
        switch (material) {
            case ENCHANTED_BOOK:
                return true;
            default:
                return false;
        }
    }

    public static boolean isEnchantable(Material material) {
        switch (material) {
            case CHAINMAIL_BOOTS:
            case CHAINMAIL_CHESTPLATE:
            case CHAINMAIL_HELMET:
            case CHAINMAIL_LEGGINGS:
            case LEATHER_BOOTS:
            case LEATHER_CHESTPLATE:
            case LEATHER_HELMET:
            case LEATHER_LEGGINGS:
            case IRON_BOOTS:
            case IRON_CHESTPLATE:
            case IRON_HELMET:
            case IRON_LEGGINGS:
            case GOLD_BOOTS:
            case GOLD_CHESTPLATE:
            case GOLD_HELMET:
            case GOLD_LEGGINGS:
            case DIAMOND_BOOTS:
            case DIAMOND_CHESTPLATE:
            case DIAMOND_HELMET:
            case DIAMOND_LEGGINGS:
            case WOOD_AXE:
            case WOOD_HOE:
            case WOOD_PICKAXE:
            case WOOD_SPADE:
            case STONE_AXE:
            case STONE_HOE:
            case STONE_PICKAXE:
            case STONE_SPADE:
            case IRON_AXE:
            case IRON_HOE:
            case IRON_PICKAXE:
            case IRON_SPADE:
            case GOLD_AXE:
            case GOLD_HOE:
            case GOLD_PICKAXE:
            case GOLD_SPADE:
            case DIAMOND_AXE:
            case DIAMOND_HOE:
            case DIAMOND_PICKAXE:
            case DIAMOND_SPADE:
            case WOOD_SWORD:
            case STONE_SWORD:
            case IRON_SWORD:
            case GOLD_SWORD:
            case DIAMOND_SWORD:
            case BOW:
            case FISHING_ROD:
            case FLINT_AND_STEEL:
            case SHIELD:
            case ELYTRA:
            case SHEARS:
                return true;
            default:
                return false;
        }
    }

    public static boolean isArmor(Material material) {
        switch (material) {
            case CHAINMAIL_BOOTS:
            case CHAINMAIL_CHESTPLATE:
            case CHAINMAIL_HELMET:
            case CHAINMAIL_LEGGINGS:
            case LEATHER_BOOTS:
            case LEATHER_CHESTPLATE:
            case LEATHER_HELMET:
            case LEATHER_LEGGINGS:
            case IRON_BOOTS:
            case IRON_CHESTPLATE:
            case IRON_HELMET:
            case IRON_LEGGINGS:
            case GOLD_BOOTS:
            case GOLD_CHESTPLATE:
            case GOLD_HELMET:
            case GOLD_LEGGINGS:
            case DIAMOND_BOOTS:
            case DIAMOND_CHESTPLATE:
            case DIAMOND_HELMET:
            case DIAMOND_LEGGINGS:
                return true;
            default:
                return false;
        }
    }

    public static boolean isTool(Material material) {
        switch (material) {
            case WOOD_AXE:
            case WOOD_HOE:
            case WOOD_PICKAXE:
            case WOOD_SPADE:
            case STONE_AXE:
            case STONE_HOE:
            case STONE_PICKAXE:
            case STONE_SPADE:
            case IRON_AXE:
            case IRON_HOE:
            case IRON_PICKAXE:
            case IRON_SPADE:
            case GOLD_AXE:
            case GOLD_HOE:
            case GOLD_PICKAXE:
            case GOLD_SPADE:
            case DIAMOND_AXE:
            case DIAMOND_HOE:
            case DIAMOND_PICKAXE:
            case DIAMOND_SPADE:
            case SHEARS:
            case FISHING_ROD:
            case FLINT_AND_STEEL:
            case CARROT_STICK:
            case ELYTRA:
                return true;
            default:
                return false;
        }
    }

    public static boolean isWeapon(Material material) {
        switch (material) {
            case WOOD_SWORD:
            case STONE_SWORD:
            case IRON_SWORD:
            case GOLD_SWORD:
            case DIAMOND_SWORD:
            case BOW:
            case SHIELD:
                return true;
            default:
                return false;
        }
    }

    public static boolean isAxe (Material material) {
        switch (material) {
            case WOOD_AXE:
            case STONE_AXE:
            case IRON_AXE:
            case GOLD_AXE:
            case DIAMOND_AXE:
                return true;
            default:
                return false;
        }
    }

    public static boolean isElytra (Material material) {
        switch (material) {
            case ELYTRA:
                return true;
            default:
                return false;
        }
    }

    public static boolean isShield (Material material) {
        switch (material) {
            case SHIELD:
                return true;
            default:
                return false;
        }
    }

    public static boolean isBow (Material material) {
        switch (material) {
            case BOW:
                return true;
            default:
                return false;
        }
    }

    public static boolean isHelmet (Material material) {
        switch (material) {
            case LEATHER_HELMET:
            case CHAINMAIL_HELMET:
            case IRON_HELMET:
            case GOLD_HELMET:
            case DIAMOND_HELMET:
                return true;
            default:
                return false;
        }
    }

    public static boolean isBoots (Material material) {
        switch (material) {
            case LEATHER_BOOTS:
            case CHAINMAIL_BOOTS:
            case IRON_BOOTS:
            case GOLD_BOOTS:
            case DIAMOND_BOOTS:
                return true;
            default:
                return false;
        }
    }

    public static boolean isShears (Material material) {
        switch (material) {
            case SHEARS:
                return true;
            default:
                return false;
        }
    }

    public static boolean isFishingRod (Material material) {
        switch (material) {
            case FISHING_ROD:
                return true;
            default:
                return false;
        }
    }

    public static boolean isFlintSteel (Material material) {
        switch (material) {
            case FLINT_AND_STEEL:
                return true;
            default:
                return false;
        }
    }

    public static boolean isCarrotStick (Material material) {
        switch (material) {
            case CARROT_STICK:
                return true;
            default:
                return false;
        }
    }

    public static boolean isRepairMaterial(Material material) {
        switch (material) {
            case LEATHER:
            case WOOD:
            case COBBLESTONE:
            case IRON_INGOT:
            case GOLD_INGOT:
            case DIAMOND:
                return true;
            default:
                return false;
        }
    }

    public static ItemStack getEnchantableItem(int index) {
        if (enchantableMaterial.get(index) != null) {
            return new ItemStack(enchantableMaterial.get(index));
        } else {
            return null;
        }
    }

    public static ArrayList<Material> getEnchantableMaterial() {
        return enchantableMaterial;
    }

    public static void setEnchantableMaterial(ArrayList<Material> enchantableMaterial) {
        UtilMaterial.enchantableMaterial = enchantableMaterial;
    }
}
