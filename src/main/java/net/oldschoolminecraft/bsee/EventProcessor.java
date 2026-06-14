package net.oldschoolminecraft.bsee;

import net.oldschoolminecraft.poseidon.InventoryChangedEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class EventProcessor implements Listener
{
    @EventHandler
    public void onItemPickup(PlayerPickupItemEvent event)
    {
        //
    }

    @EventHandler
    public void onItemDropped(PlayerDropItemEvent event)
    {
        //
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event)
    {
        //
    }

    @EventHandler
    public void onInventoryChange(InventoryChangedEvent event)
    {
        //
    }
}
