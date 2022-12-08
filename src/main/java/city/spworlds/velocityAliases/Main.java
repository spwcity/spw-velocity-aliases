package city.spworlds.velocityAliases;

import city.spworlds.velocityAliases.command.VelocityConnectCommandBase;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class Main extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);

        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        saveDefaultConfig();
        this.registerCommands();

        getLogger().info("Hello! SPWorlds velocity aliases enabled! uuuuuno <- ABOBA");
    }

    @Override
    public void onDisable() {
        getServer().getMessenger().unregisterOutgoingPluginChannel(this, "BungeeCord");
        getLogger().info("Bye. SPWorlds velocity aliases disabled! _Teleport <- ABOBA");
    }

    private void registerCommands() {
        List<?> servers = (List<?>) getConfig().getValues(true).get("servers");
        getLogger().info(servers.toString());

        for (Object server_a : servers) {
            Map<?, ?> server = (Map<?, ?>) server_a;
            Object[] keys = server.keySet().toArray();
            List<?> commands = (List<?>) server.get(keys[0]);

            for (Object command_a : commands) {
                String command = command_a.toString();
                new VelocityConnectCommandBase(this, getCommand(command), keys[0].toString());

                getLogger().info("Registered command '" + command + "' to server '" + keys[0].toString() + "'");
            }
        }
    }
}
