package city.spworlds.velocityAliases.command;

import com.google.common.collect.Lists;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class VelocityConnectCommandBase extends AbstractCommand {
    private final Plugin plugin;
    private final String serverName;

    public VelocityConnectCommandBase(Plugin plugin, PluginCommand pluginCommand, String serverName) {
        super(pluginCommand);
        this.plugin = plugin;
        this.serverName = serverName;
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        try {
            Player player = (Player) sender;
            player.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§aSending you to " + this.serverName + " server..."));

            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("Connect");
            out.writeUTF(serverName);
            player.sendPluginMessage(this.plugin, "BungeeCord", out.toByteArray());

        } catch (ClassCastException e) {
            sender.sendMessage("§l§4You must be a player to execute this command");
        }
    }

    @Override
    public List<String> complete(CommandSender sender, String[] args) {
        return Lists.newArrayList();
    }
}
