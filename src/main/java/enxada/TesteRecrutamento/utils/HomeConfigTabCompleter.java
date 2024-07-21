package enxada.TesteRecrutamento.utils;

import enxada.TesteRecrutamento.Home;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeConfigTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return Arrays.asList("cooldown", "particles", "particles-count", "max-homes");
        } else if (args.length == 2) {
            if ("particles".equalsIgnoreCase(args[0])) {
                return Arrays.asList("true", "false");
            }
        }
        return new ArrayList<>();
    }
}

