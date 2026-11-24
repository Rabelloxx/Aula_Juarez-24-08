package psv;

import java.sql.*;//Importação do pacote que contém a classe Connection

public class Conexao {//Nome da classe

    // >>> AJUSTE AQUI a senha do seu usuário root do MySQL <<<
    // (o tutorial original usava senha em branco)
    private static final String USUARIO = "root";
    private static final String SENHA = "";

    public static Connection abrirConexao() {
        Connection con = null;
        try {
            // Driver do Connector/J 8 e 9 (o 5.x antigo usava com.mysql.jdbc.Driver)
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();

            String url = "";
            url += "jdbc:mysql://127.0.0.1/estacionamento?";
            url += "user=" + USUARIO + "&password=" + SENHA;
            url += "&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

            con = DriverManager.getConnection(url);
            System.out.println("Conexão aberta.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return con;
    }

    public static void fecharConexao(Connection con) {
        try {
            con.close();
            System.out.println("Conexão fechada.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
