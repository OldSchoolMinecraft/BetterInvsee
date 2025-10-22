package net.oldschoolminecraft.bsee;

import net.minecraft.server.*;
import org.bukkit.entity.Player;

public final class WrappedChestInventoryViewer extends ContainerChest {

    private final EntityPlayer viewer;
    private final EntityPlayer target;
    private final IInventory backing;

    public WrappedChestInventoryViewer(EntityPlayer viewer,
                                       EntityPlayer target,
                                       IInventory playerInv,
                                       IInventory backing) {
        super(playerInv, backing);
        this.viewer  = viewer;
        this.target = target;
        this.backing = backing;
    }

    private void setSlot(int slot, int id, int amount, int data) {
        backing.setItem(slot, new ItemStack(id, Math.max(1, amount), data));
    }

    private void clearRange(int fromInclusive, int toExclusive) {
        for (int i = fromInclusive; i < toExclusive; i++) backing.setItem(i, null);
    }

    // Permission-gate moving items via click-and-drop
    @Override public ItemStack a(int slot, int button, boolean shift, EntityHuman who) {
        if ((((Player)who.getBukkitEntity()).hasPermission("binvsee.modify") || ((Player)who.getBukkitEntity()).isOp())) {
            ItemStack ret = super.a(slot, button, shift, who);
            // update target player's inventory
            target.inventory.items = backing.getContents();
            return ret;
        }

        return null; // don't allow modification - read only
    }

    @Override
    public void a(EntityHuman var1) {
        super.a(var1);
        ContainerManager.getInstance().markClosed((Player)var1.getBukkitEntity());
    }

    @Override public ItemStack a(int slotIndex) {
        return null;
    }

    @Override public boolean b(EntityHuman human) { return true; }

    public void setTargetViewContents(ItemStack[] contents)
    {
        for (int slot = 0; slot < contents.length; slot++)
        {
            ItemStack stack = contents[slot];
            if (stack != null && stack.count <= 0)
            {
                backing.setItem(slot, null);
                continue;
            }
            backing.setItem(slot, contents[slot]);
        }
    }
}