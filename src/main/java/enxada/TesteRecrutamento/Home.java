package enxada.TesteRecrutamento;

import org.bukkit.plugin.java.JavaPlugin;

public final class Home extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info(this.toString() + ":: Plugin habilitado!");

    }

    @Override
    public void onDisable() {
        getLogger().info(this.toString()+ ":: plugin foi desabilitado com sucesso!!");
    }
}
