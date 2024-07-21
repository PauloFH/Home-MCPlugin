package enxada.TesteRecrutamento.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import enxada.TesteRecrutamento.Home;
import java.util.HashMap;
import java.util.UUID;

public class HomeCommand implements CommandExecutor {
    private final Home plugin;
    private final HashMap<UUID, Long> cooldowns;

    public HomeCommand(Home plugin) {
        this.plugin = plugin;
        this.cooldowns = new HashMap<>();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Este comando só pode ser usado por jogadores.");
            return true;
        }

        plugin.controllimit(player);
        if (args.length < 1) {
             player.sendMessage("Uso correto: /home <nome_da_home>");
            return true;
        }
        //Verifica se o player está de cooldown
        UUID playerUUID = player.getUniqueId();
        if (cooldowns.containsKey(playerUUID)) {
            long lastUsed = cooldowns.get(playerUUID);
            int cooldown = plugin.getCooldown();
            if ((System.currentTimeMillis() - lastUsed) / 1000 < cooldown) {
                player.sendMessage(ChatColor.RED+ "Você deve esperar " + ChatColor.AQUA + cooldown +" segundos "+ ChatColor.RED+"entre usos do comando.");
                return true;
            }
        }
        //pega a localização da home no banco de dados
        String homeName = args[0];
        Location homeLocation = plugin.getDatabaseManager().getHome(player, homeName);
        if (homeLocation == null) {
            player.sendMessage(ChatColor.RED+"A home '" + ChatColor.GOLD+homeName + ChatColor.RED+"' não foi encontrada.");
            return true;
        }

        //Se tudo certo teleportar o player e adicionar o cooldown
        player.teleport(homeLocation);
        player.sendMessage(ChatColor.GOLD+"Teleportado para a home " + homeName + " com sucesso!!");
        cooldowns.put(playerUUID, System.currentTimeMillis());
        if (plugin.getConfig().getBoolean("teleport-particles")) {
            int countParticles = plugin.getConfig().getInt("particles-count");
            player.getWorld().spawnParticle(Particle.PORTAL, homeLocation, countParticles);
        }
        return true;
    }
}