package enxada.TesteRecrutamento;

import enxada.TesteRecrutamento.commands.HomeConfigCommand;
import enxada.TesteRecrutamento.commands.HomesCommmand;
import enxada.TesteRecrutamento.utils.ConnectDB;
import enxada.TesteRecrutamento.utils.HomeConfigTabCompleter;
import org.bukkit.ChatColor;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import enxada.TesteRecrutamento.commands.HomeCommand;
import enxada.TesteRecrutamento.commands.SetHomeCommand;
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
        saveDefaultConfig();
        FileConfiguration config = getConfig();
        cooldown = getConfig().getInt("cooldown", 10);
        boolean teleportParticles = getConfig().getBoolean("teleport-particles", true);
        saveDefaultConfig();

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
        Objects.requireNonNull(getCommand("delhome")).setExecutor(new HomesCommmand(this));
        Objects.requireNonNull(getCommand("homeconfig")).setExecutor(new HomeConfigCommand(this));
        Objects.requireNonNull(getCommand("homeconfig")).setTabCompleter( new HomeConfigTabCompleter());
    }
    public int getCooldown() {
        return cooldown;
    }
    public ConnectDB getDatabaseManager() {
        return connectDB;
    }

}