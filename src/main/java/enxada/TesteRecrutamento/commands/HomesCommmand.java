package enxada.TesteRecrutamento.commands;

import enxada.TesteRecrutamento.Home;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class HomesCommmand  implements CommandExecutor {
    private final Home plugin;

    public HomesCommmand(Home plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED + "Este comando só pode ser usado por jogadores.");
            return true;
        }
        if (args.length == 0) {
            listHomes(player);
        }
        else {
            player.sendMessage(ChatColor.RED + "Uso correto: /homes");

        }
        return true;
    }
    public void listHomes(Player player) {
        String homes = plugin.getDatabaseManager().listHomes(player);
        if (homes.isEmpty()) {
            player.sendMessage(ChatColor.RED + "Você não tem nenhuma home definida.");
        }
        else {
            player.sendMessage(ChatColor.GOLD + player.getDisplayName()+" homes : " + homes);
        }

    }

}
