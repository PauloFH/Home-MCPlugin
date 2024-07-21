package enxada.TesteRecrutamento.commands;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import enxada.TesteRecrutamento.Home;
import org.bukkit.plugin.Plugin;

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

        // Check for cooldown
        UUID playerUUID = player.getUniqueId();
        if (cooldowns.containsKey(playerUUID)) {
            long lastUsed = cooldowns.get(playerUUID);
            int cooldown = plugin.getCooldown();
            if ((System.currentTimeMillis() - lastUsed) / 1000 < cooldown) {
                player.sendMessage("Você deve esperar " + cooldown + " segundos entre usos do comando.");
                return true;
            }
        }

        if (args.length < 1) {
            player.sendMessage("Uso correto: /home <nome_da_home>");
            return true;
        }

        String homeName = args[0];
        Location homeLocation = plugin.getDatabaseManager().getHome(player, homeName);

        if (homeLocation == null) {
            player.sendMessage("A home '" + homeName + "' não foi encontrada.");
            return true;
        }

        // Teleport player and apply cooldown
        player.teleport(homeLocation);
        player.sendMessage("Teleportado para a home '" + homeName + "'.");
        cooldowns.put(playerUUID, System.currentTimeMillis());
        if (plugin.getConfig().getBoolean("teleport-particles")) {
            player.getWorld().spawnParticle(Particle.PORTAL, homeLocation, 100);
        }
        return true;
    }
}