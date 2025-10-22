package net.oldschoolminecraft.bsee;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class InvseeCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (!(sender.hasPermission("binvsee.peek") || sender.isOp()))
        {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return true;
        }

        if (!(sender instanceof Player))
        {
            sender.sendMessage(ChatColor.RED + "This command can only be used by players.");
            return true;
        }

        if (args.length != 1)
        {
            sender.sendMessage(ChatColor.RED + "Usage: /invsee <player>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);

        ContainerManager.getInstance().openInvseeView((Player) sender, target, ContainerManager.getInstance().buildBacking54("Inventory of " + target.getName(), ((CraftPlayer)sender).getHandle().inventory.items, ((CraftPlayer)target).getHandle().inventory.items));

        return true;
    }
}
