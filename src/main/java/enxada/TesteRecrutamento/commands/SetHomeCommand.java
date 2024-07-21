package enxada.TesteRecrutamento.commands;
/**
 * @Author: PauloHolanda
 * SetHomeCommand
 * Classe que gerencia o comando /sethome
 */
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import enxada.TesteRecrutamento.Home;

import java.util.Objects;

public class SetHomeCommand implements CommandExecutor {
    private final Home plugin;

    public SetHomeCommand(Home plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Este comando só pode ser usado por jogadores.");
            return true;
        }
        if (args.length < 1) {
            player.sendMessage("Uso correto: /sethome <nome_da_home>");
            return true;
        }
        String homeName = args[0];
        plugin.getDatabaseManager().setHome(player, homeName, player.getLocation());
        player.sendMessage("Home '" + homeName + "' definida com sucesso!");

        return true;
    }
}