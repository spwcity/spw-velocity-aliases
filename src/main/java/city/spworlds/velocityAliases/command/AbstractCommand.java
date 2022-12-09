package city.spworlds.velocityAliases.command;

import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCommand extends Command implements CommandExecutor, TabCompleter {
    private CommandExecutor commandExecutor;
    private TabCompleter tabCompleter;

    public AbstractCommand(String name) {
        super(name);
        this.setExecutor(this);
        this.setTabCompleter(this);
    }

    public void setExecutor(CommandExecutor executor) {
        commandExecutor = executor;
    }

    public void setTabCompleter(TabCompleter completer) {
        tabCompleter = completer;
    }

    public abstract boolean execute(CommandSender sender, String label, String[] args);

    public List<String> complete(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        execute(sender, label, args);
        return true;
    }


    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return filter(complete(sender, args), args);
    }

    private List<String> filter(List<String> list, String[] args) {
        if(list == null) return null;
        String last = args[args.length - 1];
        List<String> result = new ArrayList<>();
        for(String arg : list) {
            if(arg.toLowerCase().startsWith(last.toLowerCase())) result.add(arg);
        }
        return result;
    }

    public void register() {
        try {
            final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");

            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());

            commandMap.register(getName(), this);
        }catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

}
