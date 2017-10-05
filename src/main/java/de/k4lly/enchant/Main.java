package main.java.de.k4lly.enchant;

import main.java.de.k4lly.enchant.controller.ConfigController;
import main.java.de.k4lly.enchant.controller.PluginController;
import main.java.de.k4lly.enchant.listener.*;
import main.java.de.k4lly.enchant.listener.commands.*;
import main.java.de.k4lly.enchant.objects.*;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private PluginController controller = new PluginController();
    private final String enable = "[" + this.getName() + "] Loaded " + this.getDescription().getName() + " in Version " + this.getDescription().getVersion() + " succesfully!";
    private final String regEnchant = "[" + this.getName() + "] Successfully registered all Enchantments";
    private final String regEvent = "[" + this.getName() + "] Successfully registered Events!";

    @Override
    public void onEnable() {
        controller.setMain(this);
        PluginManager pluginManager = getServer().getPluginManager();
        registerEvent(pluginManager);
        controller.setConfig(new ConfigController(controller));
        registerRecipe();
        System.out.print(enable);
    }

    private void registerEvent(PluginManager pluginManager) {
        Command command = new Command(controller);
        CommandVersion commandVersion = new CommandVersion(controller);
        CommandSafeMining commandSafeMining = new CommandSafeMining(controller);
        this.getCommand("k4llyEnchant").setExecutor(command);
        this.getCommand("kE").setExecutor(command);
        this.getCommand("kEVersion").setExecutor(commandVersion);
        //this.getCommand("kEsafemining").setExecutor(commandSafeMining);
        pluginManager.registerEvents(new Anvil(controller), this);
        pluginManager.registerEvents(new Crafting(controller), this);
        registerEnchantment(pluginManager);
        System.out.print(regEvent);
    }

    private void registerRecipe() {
        if (this.getConfig().getBoolean("enableTakeEnchantment")) {
            for (Material enchItem : UtilMaterial.getEnchantableMaterial()) {
                ShapelessRecipe sr = new ShapelessRecipe(new ItemStack(Material.ENCHANTED_BOOK, 1));
                sr.addIngredient(Material.BOOK);
                sr.addIngredient(Material.EXP_BOTTLE);
                sr.addIngredient(enchItem);
                this.getServer().addRecipe(sr);
            }
        }
    }

    public void registerEnchantment(PluginManager pluginManager) {
        //pluginManager.registerEvents(new Enchanting(controller), this);
        //pluginManager.registerEvents(new EnchantmentNightVision(controller), this);
        //pluginManager.registerEvents(new EnchantmentXPBoost(controller), this);
        //pluginManager.registerEvents(new EnchantmentWither(controller), this);
        //pluginManager.registerEvents(new EnchantmentPoisonTouch(controller), this);
        //pluginManager.registerEvents(new EnchantmentFireTouch(controller), this);
        //pluginManager.registerEvents(new EnchantmentRapidFire(controller), this);
        pluginManager.registerEvents(new InfiniteArrow(controller), this);
        System.out.print(regEnchant);
    }

    @Override
    public void onDisable() {
    }
}
