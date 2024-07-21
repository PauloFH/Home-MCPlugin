package enxada.TesteRecrutamento.commands;

import enxada.TesteRecrutamento.Home;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class DelhomeCommand implements CommandExecutor {
    private final Home plugin;

    public DelhomeCommand(Home plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //verifica se o sender é um player
        if (!(sender instanceof Player)) {
            sender.sendMessage("Este comando só pode ser usado por jogadores.");
            return true;
        }
        if (args.length < 1) {
            sender.sendMessage("Uso correto: /delhome <nome_da_home>");
            return true;
        }
        String homeName = args[0];
        //deleta a home
        deleteHome((Player) sender, homeName);
        return true;
    }

    public void deleteHome(Player player, String homeName) {
        //verifica se a home existe para aquele player
        if (plugin.getDatabaseManager().getHome(player, homeName) == null) {
            player.sendMessage(ChatColor.RED + "A home '" + homeName + "' não foi encontrada.");
            return;
        }
        //deleta a home
        plugin.getDatabaseManager().deleteHome(player, homeName);
        player.sendMessage(ChatColor.GOLD + "Home '" + homeName + "' deletada com sucesso!");
    }
}
