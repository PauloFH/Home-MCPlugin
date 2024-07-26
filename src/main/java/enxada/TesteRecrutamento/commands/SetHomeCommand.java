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
        String homeName = args[0];

        // Verificar se o nome da home não começa com um número e não contém caracteres especiais
        if (!homeName.matches("^[a-zA-Z][a-zA-Z0-9_-]*$")) {
            player.sendMessage(ChatColor.RED + "O nome da home deve começar com uma letra e pode conter apenas letras, números, hífens e sublinhados.");
            return true;
        }

        // Verificar se o jogador atingiu o limite de homes e a nova home não é uma alteração de uma existente e não é admin
        if (plugin.getDatabaseManager().coutHomes(player) == plugin.getConfig().getInt("max-homes") && !sender.hasPermission("homeconfig.use")) {
            if (plugin.getDatabaseManager().getHome(player, homeName) == null) {
                player.sendMessage(ChatColor.RED + "Você já atingiu o limite de homes.");
                return true;
            }
        }

        plugin.controllimit(player);
        plugin.getDatabaseManager().setHome(player, homeName, player.getLocation());
        player.sendMessage("Home '" + homeName + "' definida com sucesso!");
        return true;
    }
}