package enxada.TesteRecrutamento.commands;

import enxada.TesteRecrutamento.Home;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HomeConfigCommand implements CommandExecutor {
    private final Home plugin;

    public HomeConfigCommand(Home plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player) || !sender.hasPermission("homeconfig.use")) {
            sender.sendMessage("Você não tem permissão para usar este comando.");
            return true;
        }
        if (args.length < 2) {
            sender.sendMessage("Uso correto: /homeconfig <cooldown|particles> <valor>");
            return true;
        }
        String setting = args[0];
        String value = args[1];
        //modifica o arquivo de configuração
        switch (setting.toLowerCase()) {
            case "cooldown":
                try {
                    int cooldown = Integer.parseInt(value);
                    plugin.getConfig().set("cooldown", cooldown);
                    plugin.saveConfig();
                    plugin.reloadConfig();
                    sender.sendMessage(ChatColor.GOLD + "Cooldown alterado para " + cooldown + " segundos.");
                } catch (NumberFormatException e) {
                    sender.sendMessage(ChatColor.RED + "Valor de cooldown inválido.");
                }
                break;
            case "particles":
                boolean particles = Boolean.parseBoolean(value);
                plugin.getConfig().set("teleport-particles", particles);
                plugin.saveConfig();
                plugin.reloadConfig();
                sender.sendMessage(ChatColor.GOLD + "Exibição de partículas alterada para " + particles + ".");
                break;
            default:
                sender.sendMessage(ChatColor.RED + "Configuração desconhecida."+ChatColor.AQUA +" Uso correto: /homeconfig <cooldown|particles> <valor>");
                break;
        }

        return true;
    }
}
