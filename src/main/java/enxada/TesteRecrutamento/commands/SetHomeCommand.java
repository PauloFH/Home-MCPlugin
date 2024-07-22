package enxada.TesteRecrutamento.commands;


import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import enxada.TesteRecrutamento.Home;

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
        if (args.length != 1) {
            player.sendMessage("Uso correto: /sethome <nome_da_home>");
            return true;
        }
        //se o player já atingiu o limite de homes e a nova home não é uma alteração de uma existente e não é admin
        if (plugin.getDatabaseManager().coutHomes(player) == plugin.getConfig().getInt("max-homes")&& !sender.hasPermission("homeconfig.use")) {
            if (plugin.getDatabaseManager().getHome(player, args[0]) == null) {
                player.sendMessage(ChatColor.RED + "Você já atingiu o limite de homes.");
                return true;
            }
        }
        plugin.controllimit(player);
            String homeName = args[0];
            plugin.getDatabaseManager().setHome(player, homeName, player.getLocation());
            player.sendMessage("Home '" + homeName + "' definida com sucesso!");
            return true;
    }
}
