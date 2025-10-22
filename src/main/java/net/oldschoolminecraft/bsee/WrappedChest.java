package net.oldschoolminecraft.bsee;

import net.minecraft.server.ContainerChest;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.IInventory;
import net.minecraft.server.ItemStack;
import org.bukkit.block.Chest;
import org.bukkit.block.ContainerBlock;
import org.bukkit.craftbukkit.block.CraftBlock;
import org.bukkit.craftbukkit.block.CraftChest;
import org.bukkit.entity.Player;

/**
 * Detect when players move items in and out of chests they are viewing
 */
public class WrappedChest extends ContainerChest
{
    private Player viewer;
    private CraftBlock containerBlock;
    private IInventory playerInv, backing;

    public WrappedChest(Player viewer, CraftBlock containerBlock, IInventory playerInv, IInventory backing)
    {
        super(playerInv, backing);

        this.viewer = viewer;
        this.containerBlock = containerBlock;
        this.playerInv = playerInv;
        this.backing = backing;
    }

    // detect when items are moved in/out of the chest
    @Override public ItemStack a(int slot, int button, boolean shift, EntityHuman who) {
        ItemStack ret = super.a(slot, button, shift, who);
        if (playerInv == null) System.out.println("playerInv == null");
        if (viewer == null) System.out.println("viewer == null");
        WrappedChestInventoryViewer inventoryViewer = ContainerManager.getInstance().getContainerByTarget(viewer);
        if (inventoryViewer != null) inventoryViewer.setTargetViewContents(playerInv.getContents());
        return ret;
    }

    @Override protected void a(ItemStack stack, int from, int to, boolean reverse) {
        super.a(stack, from, to, reverse);
        WrappedChestInventoryViewer inventoryViewer = ContainerManager.getInstance().getContainerByTarget(viewer);
        if (inventoryViewer != null) inventoryViewer.setTargetViewContents(playerInv.getContents());
    }

    @Override public ItemStack a(int slotIndex) {
        ItemStack ret = super.a(slotIndex);
        WrappedChestInventoryViewer inventoryViewer = ContainerManager.getInstance().getContainerByTarget(viewer);
        if (inventoryViewer != null) inventoryViewer.setTargetViewContents(playerInv.getContents());
        return ret;
    }

    @Override public boolean b(EntityHuman human) { return true; }
}
