package net.oldschoolminecraft.bsee;

import net.minecraft.server.*;

public class PeekInventory implements IInventory
{
    private IInventory targetInv;
    private String targetName;

    public PeekInventory(IInventory targetInv, String targetName)
    {
        this.targetInv = targetInv;
        this.targetName = targetName;
    }

    @Override
    public int getSize()
    {
        return 36;
    }

    @Override
    public ItemStack getItem(int i)
    {
        return (i >= 0 && i < targetInv.getContents().length) ? targetInv.getContents()[i] : null;
    }

    @Override
    public ItemStack splitStack(int i, int amount)
    {
        if (i < 0 || i >= targetInv.getContents().length) return null;
        ItemStack s = targetInv.getContents()[i];
        if (s == null) return null;
        if (s.count <= amount) { targetInv.getContents()[i] = null; return s; }
        ItemStack part = new ItemStack(s.id, amount, s.getData());
        s.count -= amount;
        if (s.count <= 0) targetInv.getContents()[i] = null;
        return part;
    }

    @Override
    public void setItem(int i, ItemStack s)
    {
        if (i < 0 || i >= targetInv.getContents().length) return;
        targetInv.getContents()[i] = s;
        if (s != null && s.count > 64) s.count = 64;
    }

    @Override
    public String getName()
    {
        return "Inventory of " + targetName;
    }

    @Override
    public int getMaxStackSize()
    {
        return 64;
    }

    @Override
    public void update() {}

    @Override
    public boolean a_(EntityHuman entityHuman)
    {
        return true;
    }

    @Override
    public ItemStack[] getContents()
    {
        return targetInv.getContents();
    }

    public ItemStack[] getScratch()
    {
        ItemStack[] contents = targetInv.getContents();
        ItemStack[] scratch = new ItemStack[14];
        for (int i = 40; i < contents.length; i++)
        {
            scratch[i - 40] = contents[i];
        }
        return scratch;
    }
}
