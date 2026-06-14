package net.oldschoolminecraft.bsee;

import net.minecraft.server.Container;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.InventoryPlayer;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.craftbukkit.inventory.CraftInventoryPlayer;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class BetterInvsee extends JavaPlugin
{
    private static BetterInvsee instance;

    public void onEnable()
    {
        instance = this;

        getCommand("invsee").setExecutor(new InvseeCommand());
        getServer().getPluginManager().registerEvents(new EventProcessor(), this);

        System.out.println("BetterInvsee enabled");
    }

    public static void peek(Player viewer, Player target)
    {
        EntityPlayer handle = ((CraftPlayer) viewer).getHandle();
        InventoryPlayer targetInv = ((CraftInventoryPlayer) target.getInventory()).getInventory();
        PeekInventory view = new PeekInventory(targetInv, target.getName());

        handle.a(view);
    }

    public void onDisable()
    {
        System.out.println("BetterInvsee disabled");
    }
}
