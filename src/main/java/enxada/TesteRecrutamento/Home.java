package enxada.TesteRecrutamento;

import org.bukkit.plugin.java.JavaPlugin;

public final class Home extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("\\u001B[32m"+this.toString() + ":: Plugin habilitado!");

    }

    @Override
    public void onDisable() {
        getLogger().info("\\u001B[32m"+this.toString()+ ":: plugin foi desabilitado com sucesso!!");
    }
}
