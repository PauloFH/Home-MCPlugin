package enxada.TesteRecrutamento.commands;

import enxada.TesteRecrutamento.Home;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeConfigCommand implements CommandExecutor, TabCompleter {
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
        if (args.length != 2) {
            sender.sendMessage("Uso correto: /homeconfig <cooldown|particles|particles-count|max-homes> <valor>");
            return true;
        }
        String setting = args[0];
        String value = args[1];

        switch (setting.toLowerCase()) {
            case "cooldown":
                try {
                    int cooldown = Integer.parseInt(value);
                    if (cooldown < 1) {
                        sender.sendMessage(ChatColor.RED + "Valor de cooldown inválido. Deve ser maior que 0.");
                        return true;
                    }
                    plugin.getConfig().set("cooldown", cooldown);
                    plugin.saveConfig();
                    plugin.reloadConfig();
                    sender.sendMessage(ChatColor.GOLD + "Cooldown alterado para " + ChatColor.AQUA + cooldown + ChatColor.GOLD + " segundos.");
                } catch (NumberFormatException e) {
                    sender.sendMessage(ChatColor.RED + "Valor de cooldown inválido. Deve ser um número inteiro.");
                }
                break;

            case "particles":
                if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
                    boolean particles = Boolean.parseBoolean(value);
                    plugin.getConfig().set("teleport-particles", particles);
                    plugin.saveConfig();
                    plugin.reloadConfig();
                    sender.sendMessage(ChatColor.GOLD + "Exibição de partículas alterada para " + ChatColor.AQUA + particles + ChatColor.GOLD + ".");
                } else {
                    sender.sendMessage(ChatColor.RED + "Valor de partículas inválido. Use 'true' ou 'false'.");
                }
                break;

            case "particles-count":
                try {
                    int particlesCount = Integer.parseInt(value);
                    if (particlesCount < 1 || particlesCount > 1000) {
                        sender.sendMessage(ChatColor.RED + "Valor de quantidade de partículas inválido. Deve ser entre 1 e 1000.");
                        return true;
                    }
                    plugin.getConfig().set("particles-count", particlesCount);
                    plugin.saveConfig();
                    plugin.reloadConfig();
                    sender.sendMessage(ChatColor.GOLD + "Quantidade de partículas alterada para " + ChatColor.AQUA + particlesCount + ChatColor.GOLD + ".");
                } catch (NumberFormatException e) {
                    sender.sendMessage(ChatColor.RED + "Valor de quantidade de partículas inválido. Deve ser um número inteiro.");
                }
                break;

            case "max-homes":
                try {
                    int homesLimit = Integer.parseInt(value);
                    if (homesLimit < 1) {
                        sender.sendMessage(ChatColor.RED + "Valor de limite de homes inválido. Deve ser maior que 0.");
                        return true;
                    }
                    plugin.getConfig().set("max-homes", homesLimit);
                    plugin.saveConfig();
                    plugin.reloadConfig();
                    sender.sendMessage(ChatColor.GOLD + "Limite de homes alterado para " + ChatColor.AQUA + homesLimit + ChatColor.GOLD + ".");
                } catch (NumberFormatException e) {
                    sender.sendMessage(ChatColor.RED + "Valor de limite de homes inválido. Deve ser um número inteiro.");
                }
                break;

            default:
                sender.sendMessage(ChatColor.RED + "Configuração desconhecida. Uso correto: /homeconfig <cooldown|particles|particles-count|max-homes> <valor>");
                break;
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            // Sugestões para o primeiro argumento
            return Arrays.asList("cooldown", "particles", "particles-count", "max-homes");
        } else if (args.length == 2) {
            // Sugestões para o segundo argumento com base na configuração selecionada
            switch (args[0].toLowerCase()) {
                case "cooldown":
                case "particles-count":
                case "max-homes":
                    completions.add("1");
                    completions.add("10");
                    completions.add("100");
                    completions.add("500");
                    completions.add("1000");
                    break;

                case "particles":
                    completions.add("true");
                    completions.add("false");
                    break;

                default:
                    break;
            }
        }

        return completions;
    }
}
