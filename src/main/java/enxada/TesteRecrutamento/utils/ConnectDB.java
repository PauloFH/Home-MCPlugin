package enxada.TesteRecrutamento.utils;
import enxada.TesteRecrutamento.Home;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import java.sql.*;
import java.util.Objects;

/**
 * @author PauloHolanda
 * ConnectDB
 * Classe que gerencia a conexão com o banco de dados
 */

public class ConnectDB {
    private final Home plugin;
    private Connection connection;
    private final String host, database, username, password;
    private final int port;

    //constructor e definição do servidor local
    public ConnectDB(Home plugin) {
        this.plugin = plugin;
        this.host = "localhost";
        this.port = 3306;
        this.database = "minecrafthome";
        this.username = "root";
        this.password = "root";
    }

    //inicialização da conexão com o banco de dados
    public void initialize() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            createDatabase();
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
            createTable();
        } catch (ClassNotFoundException | SQLException e) {
           plugin.getLogger().severe("Erro ao conectar ao banco de dados :: "+e.getMessage());
        }
    }
    //Garantia que o banco de dados exista para evitar erros em primeiro boot
    private void createDatabase() {
        //conectar na raiz para criar o banco de dados
        try (Connection tempConnection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port, username, password);
             Statement stmt = tempConnection.createStatement()) {
            String sql = "CREATE DATABASE IF NOT EXISTS " + database;
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            plugin.getLogger().severe("Erro ao criar o banco de dados :: "+e.getMessage());
        }
    }
    //Garantia que a tabela exista para evitar erros em primeiro boot
    private void createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS homes ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "player_uuid VARCHAR(36) NOT NULL,"
                + "home_name VARCHAR(50) NOT NULL,"
                + "world VARCHAR(50) NOT NULL,"
                + "x DOUBLE NOT NULL,"
                + "y DOUBLE NOT NULL,"
                + "z DOUBLE NOT NULL,"
                + "UNIQUE KEY unique_home (player_uuid, home_name))";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        }catch (SQLException e) {
            plugin.getLogger().severe("Erro ao criar a tabela :: "+e.getMessage());
        }
    }
    //definição da home do jogador, inserindo ou atualizando no banco de dados (ON DUPLICATE KEY UPDATE)
    public void setHome(Player player, String homeName, Location location) {
        String sql = "INSERT INTO homes (player_uuid, home_name, world, x, y, z) VALUES (?, ?, ?, ?, ?, ?) "
                + "ON DUPLICATE KEY UPDATE world=?, x=?, y=?, z=?";
        try (PreparedStatement prepared_statement = connection.prepareStatement(sql)) {
            prepared_statement.setString(1, player.getUniqueId().toString());
            prepared_statement.setString(2, homeName);
            prepared_statement.setString(3, Objects.requireNonNull(location.getWorld()).getName());
            prepared_statement.setDouble(4, location.getX());
            prepared_statement.setDouble(5, location.getY());
            prepared_statement.setDouble(6, location.getZ());
            prepared_statement.setString(7, location.getWorld().getName());
            prepared_statement.setDouble(8, location.getX());
            prepared_statement.setDouble(9, location.getY());
            prepared_statement.setDouble(10, location.getZ());
            prepared_statement.executeUpdate();
        } catch (SQLException e) {
            plugin.getLogger().severe("Erro ao definir a home :: "+e.getMessage());
        }
    }
    //busca de home do jogador no banco de dados
    public Location getHome(Player player, String homeName) {
        String sql = "SELECT * FROM homes WHERE player_uuid = ? AND home_name = ?";
        try (PreparedStatement prepared_statement = connection.prepareStatement(sql)) {
            prepared_statement.setString(1, player.getUniqueId().toString());
            prepared_statement.setString(2, homeName);
            ResultSet rs = prepared_statement.executeQuery();
            if (rs.next()) {
                String worldName = rs.getString("world");
                double x = rs.getDouble("x");
                double y = rs.getDouble("y");
                double z = rs.getDouble("z");
                return new Location(plugin.getServer().getWorld(worldName), x, y, z);
            }
        } catch (SQLException e) {
            plugin.getLogger().severe("Erro ao obter a home :: "+e.getMessage());
        }
        return null;
    }
    //fechamento da conexão com o banco de dados
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            plugin.getLogger().severe("Erro ao fechar a conexão :: "+e.getMessage());
        }
    }
    //listagem de homes do jogador
    public String listHomes(Player player) {
        String sql = "SELECT home_name FROM homes WHERE player_uuid = ?";
        try (PreparedStatement prepared_statement = connection.prepareStatement(sql)) {
            prepared_statement.setString(1, player.getUniqueId().toString());
            ResultSet rs = prepared_statement.executeQuery();
            StringBuilder homes = new StringBuilder();
            while (rs.next()) {
                homes.append(rs.getString("home_name")).append("\n ");
            }
            return homes.toString();
        } catch (SQLException e) {
            plugin.getLogger().severe("Erro ao listar as homes :: "+e.getMessage());
            return null;
        }
    }
    //deletar a home do jogador no banco de dados
    public void deleteHome(Player player, String homeName) {
        String sql = "DELETE FROM homes WHERE player_uuid = ? AND home_name = ?";
        try (PreparedStatement prepared_statement = connection.prepareStatement(sql)) {
            prepared_statement.setString(1, player.getUniqueId().toString());
            prepared_statement.setString(2, homeName);
            prepared_statement.executeUpdate();
        } catch (SQLException e) {
            plugin.getLogger().severe("Erro ao deletar a home :: "+e.getMessage());
        }
    }
}
