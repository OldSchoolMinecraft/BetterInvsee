package net.oldschoolminecraft.bsee;

import net.minecraft.server.EntityPlayer;
import net.minecraft.server.ICrafting;
import net.minecraft.server.IInventory;
import net.minecraft.server.ItemStack;
import net.oldschoolminecraft.poseidon.InventoryChangedEvent;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.craftbukkit.block.CraftBlock;
import org.bukkit.craftbukkit.block.CraftChest;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.craftbukkit.inventory.CraftInventory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class EventProcessor implements Listener
{
    @EventHandler
    public void onItemPickup(PlayerPickupItemEvent event)
    {
        WrappedChestInventoryViewer container = ContainerManager.getInstance().getContainerByTarget(event.getPlayer());
        if (container != null) // someone is currently viewing this players inventory
            container.setTargetViewContents(((CraftPlayer)event.getPlayer()).getHandle().inventory.items);
    }

    @EventHandler
    public void onItemDropped(PlayerDropItemEvent event)
    {
        WrappedChestInventoryViewer container = ContainerManager.getInstance().getContainerByTarget(event.getPlayer());
        if (container != null) // someone is currently viewing this players inventory
            container.setTargetViewContents(((CraftPlayer)event.getPlayer()).getHandle().inventory.items);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event)
    {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK)
        {
            System.out.println(event.getPlayer().getName() + " right clicked block");
            Block block = event.getClickedBlock();
            if (block.getType() != Material.CHEST)
            {
                System.out.println("It's NOT a chest!");
                System.out.println("It is a: " + block.getType().name());
                return;
            } else System.out.println("It's a chest!");
            event.setCancelled(true);
            ContainerManager.getInstance().openWrappedChest(event.getPlayer(), (CraftBlock)block, ((CraftInventory)((CraftChest)block.getState()).getInventory()).getInventory());
        }
    }

    @EventHandler
    public void onInventoryChange(InventoryChangedEvent event)
    {
        WrappedChestInventoryViewer container = ContainerManager.getInstance().getContainerByTarget(event.getPlayer());
        if (container != null) // someone is currently viewing this players inventory
            container.setTargetViewContents(((CraftPlayer)event.getPlayer()).getHandle().inventory.items);
    }
}
