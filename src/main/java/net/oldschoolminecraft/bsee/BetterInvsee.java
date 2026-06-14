package net.oldschoolminecraft.bsee;

import net.minecraft.server.EntityPlayer;
import net.minecraft.server.InventoryPlayer;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.craftbukkit.inventory.CraftInventoryPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class BetterInvsee extends JavaPlugin
{
    public void onEnable()
    {
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
        System.out.println("BetterInvsee disabled");
    }
}
