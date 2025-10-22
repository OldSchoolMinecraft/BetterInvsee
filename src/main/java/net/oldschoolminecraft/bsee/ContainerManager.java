package net.oldschoolminecraft.bsee;

import net.minecraft.server.*;
import org.bukkit.block.BlockFace;
import org.bukkit.block.ContainerBlock;
import org.bukkit.craftbukkit.block.CraftBlock;
import org.bukkit.craftbukkit.block.CraftChest;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.craftbukkit.inventory.CraftInventory;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class ContainerManager
{
    // viewer, wrapped container chest map
    private HashMap<Player, WrappedChestInventoryViewer> viewerContainerMap = new HashMap<>();
    private HashMap<Player, WrappedChestInventoryViewer> targetContainerMap = new HashMap<>();

    public WrappedChestInventoryViewer getContainerByTarget(Player target)
    {
        return targetContainerMap.get(target);
    }

    public void openInvseeView(Player player, Player target, IInventory backing)
    {
        EntityPlayer nms = ((CraftPlayer) player).getHandle();

        nms.a(backing);
        int winId = nms.activeContainer.windowId;

        WrappedChestInventoryViewer ro = new WrappedChestInventoryViewer(nms, ((CraftPlayer) target).getHandle(), nms.inventory, backing);
        viewerContainerMap.put(player, ro);
        targetContainerMap.put(target, ro);
        ro.windowId = winId;
        ro.a((ICrafting) nms);
        nms.activeContainer = ro;
        nms.updateInventory(ro);
        System.out.println("Opened invsee view for " + player.getName() + " of target: " + target.getName());
    }

    public void openWrappedChest(Player player, CraftBlock block, IInventory backing)
    {
        EntityPlayer nms = ((CraftPlayer) player).getHandle();

        nms.a(backing);
        int winId = nms.activeContainer.windowId;

        boolean isDoubleChest = false;
        CraftBlock otherChest = null;
        for (BlockFace face : new BlockFace[]{BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST})
        {
            if (block.getRelative(face, 1).getTypeId() == Block.CHEST.id) {
                isDoubleChest = true;
                otherChest = (CraftBlock) block.getRelative(face, 1);
                break;
            }
        }

        WrappedChest ro = null;

        if (isDoubleChest) {
            ro = new WrappedChest(player, block, nms.inventory, new InventoryLargeChest("Large Chest", backing, ((CraftInventory)((CraftChest)otherChest.getState()).getInventory()).getInventory()));
            System.out.println("Opened wrapped double chest for " + player.getName());
            System.out.println("1st size: " + backing.getContents().length);
            System.out.println("2nd size: " + ((CraftInventory)((CraftChest)otherChest.getState()).getInventory()).getInventory().getContents().length);
        } else {
            ItemStack[] singleInvContents = backing.getContents();
            System.out.println("singleInvContents size: " + singleInvContents.length);
            SimpleInventory singleInv = new SimpleInventory("Chest", 26);
            singleInv.setContents(singleInvContents);
            ro = new WrappedChest(player, block, nms.inventory, singleInv);
            System.out.println("Opened wrapped single chest for " + player.getName());
        }

        ro.windowId = winId;
        ro.a((ICrafting) nms);
        nms.activeContainer = ro;
        nms.updateInventory(ro);
        System.out.println("Sending inventory update to: " + player.getName());
    }

    public IInventory buildBacking54(String title, ItemStack[] viewerItems, ItemStack[] targetPlayerItems)
    {
        SimpleInventory mainInv = new SimpleInventory(title, 54);
        SimpleInventory viewerInv = new SimpleInventory(title, 0);
        mainInv.setContents(targetPlayerItems);
//        viewerInv.setContents(viewerItems);

        return new InventoryLargeChest(
                title,
                mainInv,
                viewerInv
        );
    }

    private IInventory getIInventory(Player player)
    {
        return ((CraftPlayer)player).getHandle().inventory;
    }

    private static final ContainerManager instance = new ContainerManager();
    public static ContainerManager getInstance()
    {
        return instance;
    }

    public void markClosed(Player player)
    {
        viewerContainerMap.remove(player);
    }
}
