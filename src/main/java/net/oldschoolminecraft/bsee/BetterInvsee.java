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
        Container opened = handle.activeContainer;

        // No close event exists on this API, so poll for the window closing to return
        // any items the viewer left in the filler slots (slots 40-53).
        scheduleScratchReturn(viewer, handle, opened, view);
    }

    private static void scheduleScratchReturn(Player viewer, EntityPlayer handle, Container opened, PeekInventory view)
    {
        final int[] taskId = new int[1];
        taskId[0] = Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, () ->
        {
            boolean stillOpen = viewer.isOnline() && handle.activeContainer == opened;
            if (stillOpen) return;
            returnScratch(viewer, view);
            Bukkit.getScheduler().cancelTask(taskId[0]);
        }, 10L, 10L);
    }

    private static void returnScratch(Player viewer, PeekInventory view)
    {
        net.minecraft.server.ItemStack[] scratch = view.getScratch();
        for (int i = 0; i < scratch.length; i++)
        {
            net.minecraft.server.ItemStack nms = scratch[i];
            if (nms == null) continue;
            scratch[i] = null;
            if (!viewer.isOnline())
                continue; // viewer gone; their session items vanish with the session
            ItemStack stack = new CraftItemStack(nms);
            HashMap<Integer, ItemStack> overflow = viewer.getInventory().addItem(stack);
            for (ItemStack drop : overflow.values())
            {
                viewer.getWorld().dropItem(viewer.getLocation(), drop);
            }
        }
    }

    public void onDisable()
    {
        System.out.println("BetterInvsee disabled");
    }
}
