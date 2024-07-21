package enxada.TesteRecrutamento;

import enxada.TesteRecrutamento.commands.*;
import enxada.TesteRecrutamento.utils.ConnectDB;
import enxada.TesteRecrutamento.utils.HomeConfigTabCompleter;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

/**
 * @author PauloHolanda
 * Home
 * Classe principal do plugin
 */

public final class Home extends JavaPlugin {
    private ConnectDB connectDB;
    private int cooldown;

    @Override
    public void onEnable() {
        // Carrega o arquivo de configuração ou cria se não existir
        configurations();

        // Initialize o banco de dados
        this.connectDB = new ConnectDB(this);
        connectDB.initialize();
        // Inicializa os comandos
        InitializeCommands();
        getLogger().info(ChatColor.DARK_GREEN +this.toString() + ":: Plugin habilitado!");
    }
    @Override
    public void onDisable() {
        //finaliza a conexão para evitar danos ao sevidor/banco
        if (connectDB != null) {
            connectDB.closeConnection();
        }
        getLogger().info(ChatColor.DARK_GREEN +this.toString()+ ":: Plugin foi desabilitado com sucesso!");
    }

    public void InitializeCommands(){
        Objects.requireNonNull(getCommand("home")).setExecutor(new HomeCommand(this));
        Objects.requireNonNull(getCommand("sethome")).setExecutor(new SetHomeCommand(this));
        Objects.requireNonNull(getCommand("homes")).setExecutor(new HomesCommmand(this));
        Objects.requireNonNull(getCommand("delhome")).setExecutor(new DelhomeCommand(this));
        Objects.requireNonNull(getCommand("homeconfig")).setExecutor(new HomeConfigCommand(this));
        Objects.requireNonNull(getCommand("homeconfig")).setTabCompleter( new HomeConfigTabCompleter());
    }
    public void configurations() {
        saveDefaultConfig();
        FileConfiguration config = getConfig();
        cooldown = getConfig().getInt("cooldown", 10);
        boolean teleportParticles = getConfig().getBoolean("teleport-particles", true);
        int particlesCount = getConfig().getInt("particles-count", 100);
        int maxHomes = getConfig().getInt("max-homes", 5);
        config.addDefault("cooldown", cooldown);
        config.addDefault("teleport-particles", teleportParticles);
        config.addDefault("particles-count", particlesCount);
        config.addDefault("max-homes", maxHomes);
        config.options().copyDefaults(true);
        saveConfig();
    }
    public  void controllimit(Player player){
        int homes = this.getDatabaseManager().coutHomes(player);
        int limit = this.getConfig().getInt("max-homes");
        if( homes> limit ){
            player.sendMessage(ChatColor.RED+"Você utrapassou o limite de homes e serão limpos as " +(homes - limit) +" mais antigas.");
            this.getDatabaseManager().deleteOldHomes(player, homes - limit);
        }
    }
    public int getCooldown() {
        return cooldown;
    }
    public ConnectDB getDatabaseManager() {
        return connectDB;
    }

}