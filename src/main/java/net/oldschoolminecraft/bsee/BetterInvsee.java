package net.oldschoolminecraft.bsee;

import net.minecraft.server.EntityPlayer;
import net.minecraft.server.InventoryPlayer;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.craftbukkit.inventory.CraftInventoryPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class BetterInvsee extends JavaPlugin
{
    private UpdateManager updateManager;

    public void onEnable()
    {
        updateManager = new UpdateManager(this, "https://micro.os-mc.net/plugin_ci/BetterInvsee/latest");
        updateManager.checkForUpdates();

        getCommand("invsee").setExecutor(new InvseeCommand());

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
        updateManager.checkForUpdates();
        System.out.println("BetterInvsee disabled");
    }
}
