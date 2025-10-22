package net.oldschoolminecraft.bsee;

import org.bukkit.plugin.java.JavaPlugin;

public class BetterInvsee extends JavaPlugin
{
    public void onEnable()
    {
        getCommand("invsee").setExecutor(new InvseeCommand());
        getServer().getPluginManager().registerEvents(new EventProcessor(), this);

        System.out.println("BetterInvsee enabled");
    }

    public void onDisable()
    {
        System.out.println("BetterInvsee disabled");
    }
}
