package projeto.bd.poo.meu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
// import javax.swing.*;
// import java.awt.*;

//ARQUIVO DE CONEXÃO COM O BD
public class Conectarbd {
    private static final String URL = "jdbc:mysql://localhost:3306/projetobdpoo";
    private static final String USER = "root"; // User padrão do XAMPP
    private static final String PASS = ""; // Senha padrão do XAMPP

    // Iniciar conexão
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver MySQL não encontrado.", e);
        }
        return DriverManager.getConnection(URL, USER, PASS);
    }

    // Fechar a conexão
    public static void closeConnection(Connection conn) throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }

    public class sql {
        // ----------------- Criação das tabelas - se ainda não existirem -----------------
        public static void createVendas() {
            String sql = "CREATE TABLE IF NOT EXISTS vendas (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "idCarro INT NOT NULL, " +
                    "idCliente INT NOT NULL, " +
                    "data DATE NOT NULL, " +
                    "valor DECIMAL(10, 2) NOT NULL, " +
                    "metodoPagamento VARCHAR(50) NOT NULL, " +
                    "observacoes TEXT, " +
                    "FOREIGN KEY (idCarro) REFERENCES carros(id), " + // Referência ao id do carro
                    "FOREIGN KEY (idCliente) REFERENCES clientes(id)" + // Referência ao id do cliente
                    ")";
            try (Connection conn = getConnection()) {
                conn.createStatement().execute(sql);
                System.out.println("Tabela 'vendas' criada com sucesso.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public static void createCarros() {
            String sql = "CREATE TABLE IF NOT EXISTS carros (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "marca VARCHAR(50) NOT NULL, " +
                    "modelo VARCHAR(50) NOT NULL, " +
                    "ano YEAR NOT NULL, " +
                    "preco DECIMAL(10, 2) NOT NULL, " +
                    "cor VARCHAR(30) NOT NULL, " +
                    "placa VARCHAR(10) NOT NULL, " +
                    "chassi VARCHAR(20) NOT NULL, " +
                    "status VARCHAR(20) NOT NULL" +
                    ")";
            try (Connection conn = getConnection()) {
                conn.createStatement().execute(sql);
                System.out.println("Tabela 'carros' criada com sucesso.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public static void createClientes() {
            String sql = "CREATE TABLE IF NOT EXISTS clientes (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "nome VARCHAR(100) NOT NULL, " +
                    "email VARCHAR(100) NOT NULL, " + // Vai identificar se é clientes, vendedor ou gerente;
                    "telefone VARCHAR(15), " + // Formato: (00) 00000-0000
                    "cidade VARCHAR(50) NOT NULL," +
                    "cpf VARCHAR(14) NOT NULL, " + // Formato: 000.000.000-00
                    "cep VARCHAR(10) NOT NULL, " + // Formato: 00000-000
                    "estado VARCHAR(2) NOT NULL " + // Sigla do estado (ex: SP, RJ, MG, etc.)
                    ")";
            try (Connection conn = getConnection()) {
                conn.createStatement().execute(sql);
                System.out.println("Tabela 'clientes' criada com sucesso.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // ------------------ Inserção de dados de EXEMPLO ------------------
        public static void inserirDadosExemplo() {
            try (Connection conn = getConnection()) {
                // Inserir dados de exemplo na tabela 'carros'
                String insertCarro = "INSERT INTO carros (marca, modelo, ano, preco, cor, placa, chassi, status) VALUES " +
                    "('Toyota', 'Corolla', '2020', 90000.00, 'Prata', 'ABC1234', '1HGBH41JXMN109186', 'Disponível'), " +
                    "('Honda',  'Civic',   '2019', 85000.00, 'Preto', 'XYZ5678', '1HGCM82633A123456', 'Disponível'), " +
                    // Mais registros para testes:
                    "('Toyota', 'Corolla', '2020', 92000.00, 'Branco', 'DEF2345', '2HGES16555H123456', 'Disponível'), " +
                    "('Ford',   'Focus',   '2020', 78000.00, 'Preto',   'GHI3456', '3FAHP0HA6AR123456', 'Vendido'), " +
                    "('Honda',  'Civic',   '2022', 88000.00, 'Cinza',  'JKL4567', '19XFC2F59GE123456', 'Disponível')";
                conn.createStatement().executeUpdate(insertCarro);

                // Inserir dados de exemplo na tabela 'clientes'
                String insertCliente = "INSERT INTO clientes (nome, email, telefone, cidade, cpf, cep, estado) VALUES " +
                    "('João Silva',  'joao.silva@example.com',  '123456789', 'São Paulo',    '12345678900', '01234567', 'SP'), " +
                    "('Maria Souza','maria.souza@example.com','987654321','Rio de Janeiro','98765432100','76543210','RJ'), " +
                    // Mais registros para testes:
                    "('Pedro Alves','pedro.alves@example.com','555123456','Belo Horizonte','11122233344','30123456','MG'), " +
                    "('Ana Castro', 'ana.castro@example.com', '666987654','Curitiba',     '55566677788','81234567','PR')";
                conn.createStatement().executeUpdate(insertCliente);

                // Inserir dados de exemplo na tabela 'vendas'
                String insertVenda = "INSERT INTO vendas (idCarro, idCliente, data, valor, metodoPagamento, observacoes) VALUES " +
                    "(1, 1, '2023-01-01', 90000.00, 'Cartão de Crédito', 'Venda realizada com sucesso'), " +
                    "(2, 2, '2023-01-02', 85000.00, 'Dinheiro',           'Venda realizada com sucesso'), " +
                    // Mais registros para testes:
                    "(3, 1, '2023-02-15', 92000.00, 'Pix',                'Cliente voltou a comprar o mesmo modelo'), " +
                    "(4, 3, '2023-03-10', 78000.00, 'Boleto',             'Oferta especial'), " +
                    "(5, 4, '2023-04-05', 88000.00, 'Cartão de Débito',   'Venda concluída')";
                conn.createStatement().executeUpdate(insertVenda);

                System.out.println("Dados de exemplo inseridos com sucesso.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        //Criar tudo de uma vez e inserir dados de exemplo - apenas para testes e demonstração
        public static void criarTudoEInserirDadosExemplo() {
            createCarros();
            createClientes();
            createVendas();
            inserirDadosExemplo();
        }

        // ------------------- Inserção de dados que o usuário pode inserir ------------------

        // Vendas
        public static void inserirDadosVendas(int idCarro, int idCliente, String data, double valor, String metodoPagamento, String observacoes) {
            // Criando a tabela vendas se não existir
            createVendas();
            // A instrução SQL com exatamente 6 colunas e 6 "?" no mesmo número
            String sql = "INSERT INTO vendas "
                       + "(`idCarro`, `idCliente`, `data`, `valor`, `metodoPagamento`, `observacoes`) "
                       + "VALUES (?, ?, ?, ?, ?, ?)";

            try (Connection conn = getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setInt(1, idCarro);
                ps.setInt(2, idCliente);

                // Transforma a String “YYYY-MM-DD” em java.sql.Date
                ps.setDate(3, java.sql.Date.valueOf(data));

                ps.setDouble(4, valor);
                ps.setString(5, metodoPagamento);
                ps.setString(6, observacoes);

                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                // Se der erro de contagem de colunas, a stack trace mostrará a linha exata
            }
        }

        // Clientes
        public static void inserirDadosClientes(String nome, String email, String telefone, String cidade, String cpf, String cep, String estado) {
            // Criando a tabela clientes se não existir
            createClientes();
            // A instrução SQL com exatamente 7 colunas e 7 "?" no mesmo número
            String sql = "INSERT INTO clientes " +
                 "(nome, email, telefone, cidade, cpf, cep, estado) " +
                 "VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (Connection conn = getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, nome);
                ps.setString(2, email);
                ps.setString(3, telefone);
                ps.setString(4, cidade);
                ps.setString(5, cpf);
                ps.setString(6, cep);
                ps.setString(7, estado);

                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        // Carros
        public static void inserirDadosCarros(String marca, String modelo, int ano, double preco, String cor, String placa, String chassi, String status) {
            // Criando a tabela carros se não existir
            createCarros();
            // a instrução SQL com exatamente 8 colunas e 8 "?" no mesmo número
            String insertCarro =
                "INSERT INTO carros " +
                "(marca, modelo, ano, preco, cor, placa, chassi, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(insertCarro)) {

                ps.setString(1, marca);
                ps.setString(2, modelo);
                ps.setInt(3, ano);        // para coluna YEAR, aceita int
                ps.setDouble(4, preco);
                ps.setString(5, cor);
                ps.setString(6, placa);
                ps.setString(7, chassi);
                ps.setString(8, status);  // resultado de status.toString()

                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        //---------------------- SELECTS ----------------------

        // Selecionar todos os carros no estoque (tabela carros)
        public static String selecionarTodosCarros() {
            String sql = "SELECT * FROM carros";
            StringBuilder sb = new StringBuilder();
            try (Connection conn = getConnection()) {
                var stmt = conn.createStatement();
                var rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    System.out.printf("ID: %d | %s - %s (%d) R$%.2f | Cor: %s | Placa: %s | Chassi: %s | Status: %s%n",
                            rs.getInt("id"), rs.getString("modelo"), rs.getString("marca"),
                            rs.getInt("ano"), rs.getDouble("preco"), rs.getString("cor"),
                            rs.getString("placa"), rs.getString("chassi"), rs.getString("status"));
                    // Mostrando as informações na gui:
                    sb.append(String.format("ID: %d | %s - %s (%d) R$%.2f | Cor: %s | Placa: %s | Chassi: %s | Status: %s%n",
                            rs.getInt("id"), rs.getString("modelo"), rs.getString("marca"),
                            rs.getInt("ano"), rs.getDouble("preco"), rs.getString("cor"),
                            rs.getString("placa"), rs.getString("chassi"), rs.getString("status")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return sb.toString(); // Retorna o StringBuilder com os dados formatados
        }
        
        // Selecionar apenas os carros disponíveis:
        public static String selecionarCarrosDisponíveis() {
            String sql = "SELECT * FROM carros WHERE status = 'Disponível'";
            StringBuilder sb = new StringBuilder();
            try (Connection conn = getConnection()) {
                var stmt = conn.createStatement();
                var rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    System.out.printf("ID: %d | %s - %s (%d) R$%.2f | Cor: %s | Placa: %s | Chassi: %s | Status: %s%n",
                            rs.getInt("id"), rs.getString("modelo"), rs.getString("marca"),
                            rs.getInt("ano"), rs.getDouble("preco"), rs.getString("cor"),
                            rs.getString("placa"), rs.getString("chassi"), rs.getString("status"));
                    // Mostrando as informações na gui:
                    sb.append(String.format("ID: %d | %s - %s (%d) R$%.2f | Cor: %s | Placa: %s | Chassi: %s | Status: %s%n",
                            rs.getInt("id"), rs.getString("modelo"), rs.getString("marca"),
                            rs.getInt("ano"), rs.getDouble("preco"), rs.getString("cor"),
                            rs.getString("placa"), rs.getString("chassi"), rs.getString("status")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return sb.toString(); // Retorna o StringBuilder com os dados formatados
        }
        

        // Selecionar todos os clientes cadastrados (tabela clientes)
        public static String selecionarTodosClientes() {
            String sql = "SELECT * FROM clientes"; 
            // WHERE email NOT LIKE ('%@funcionario.com') AND email NOT LIKE ('%@gerente.com') // Excluindo emails de funcionários e gerentes
            StringBuilder sb = new StringBuilder();
            
            try (Connection conn = getConnection()) {
                var stmt = conn.createStatement();
                var rs = stmt.executeQuery(sql);
                sb.append(String.format("%-5s %-30s %-15s %-25s %-15s %-10s %-20s %-5s%n",
                        "ID", "Nome", "Telefone", "Email", "CPF", "CEP", "Cidade", "Estado"));
                sb.append("-".repeat(120)).append("\n");
                
                while (rs.next()) {
                    System.out.printf("ID: %d | %s - %s - %s | CPF: %s | Endereço: %s, %s - %s%n",
                            rs.getInt("id"), rs.getString("nome"), rs.getString("telefone"),
                            rs.getString("email"), rs.getString("cpf"), rs.getString("cep"),
                            rs.getString("cidade"), rs.getString("estado"));
                    // Mostrando as informações na gui:
                    sb.append(String.format("%-5d %-30s %-15s %-25s %-15s %-10s %-20s %-5s%n",
                            rs.getInt("id"), rs.getString("nome"), rs.getString("telefone"),
                            rs.getString("email"), rs.getString("cpf"), rs.getString("cep"),
                            rs.getString("cidade"), rs.getString("estado")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return sb.toString(); // Retorna o StringBuilder com os dados formatados
        }

        // Selecionar todas as vendas realizadas (tabela vendas)
        public static String selecionarTodasVendas() {
            String sql = "SELECT * FROM vendas";
            StringBuilder sb = new StringBuilder();
            try (Connection conn = getConnection()) {
                var stmt = conn.createStatement();
                var rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    System.out.printf("Venda ID: %d | Carro ID: %d | Cliente ID: %d | Data: %s | Valor: R$%.2f | Método de Pagamento: %s | Observações: %s%n",
                            rs.getInt("id"), rs.getInt("idCarro"), rs.getInt("idCliente"),
                            rs.getDate("data"), rs.getDouble("valor"), rs.getString("metodoPagamento"),
                            rs.getString("observacoes"));
                    // Mostrando as informações na gui:
                    sb.append(String.format("Venda ID: %d | Carro ID: %d | Cliente ID: %d | Data: %s | Valor: R$%.2f | Método de Pagamento: %s | Observações: %s%n",
                            rs.getInt("id"), rs.getInt("idCarro"), rs.getInt("idCliente"),
                            rs.getDate("data"), rs.getDouble("valor"), rs.getString("metodoPagamento"),
                            rs.getString("observacoes")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return sb.toString(); // Retorna o StringBuilder com os dados formatados
        }

        // Selecionar vendas por cliente (tabela vendas) - saber se tem clientes que compraram mais de um carro
        public static String selecionarVendasPorCliente() {
            String sql = """
                    SELECT 
                        c.id,
                        c.nome AS nomeCliente,
                        COUNT(v.idCliente) AS totalCompras
                    FROM
                        vendas v
                    INNER JOIN clientes c
                            ON v.idCliente = c.id
                    GROUP BY
                        c.id,
                        c.nome
                    ORDER BY
                        totalCompras DESC
                    LIMIT 10; -- Limitar ao top 10
            """;
            StringBuilder sb = new StringBuilder();
            try (Connection conn = getConnection()) {
                var stmt = conn.prepareStatement(sql);
                var rs = stmt.executeQuery();
                while (rs.next()) {
                    System.out.println("Cliente ID: " + rs.getInt("id") +
                        " | Nome: " + rs.getString("nomeCliente") +
                        " | Total de Vendas: " + rs.getInt("totalCompras"));
                    // Mostrando as informações na gui:
                    sb.append(String.format("Cliente ID: %d | Nome: %s | Total de Vendas: %d%n",
                            rs.getInt("id"), rs.getString("nomeCliente"), rs.getInt("totalCompras")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return sb.toString(); // Retorna o StringBuilder com os dados formatados
        }

        // Selecionar vendas por nome do carro (tabela vendas) - saber se tem carros que foram vendidos mais de uma vez
        // Pegando por nome, marca e modelo do carro
        public static String selecionarVendasPorCarro() {
            String sql = """
                SELECT
                    c.marca             AS marcaCarro,
                    c.modelo            AS modeloCarro,
                    COUNT(*)            AS totalVendas
                FROM
                    vendas v
                    INNER JOIN carros c
                        ON v.idCarro = c.id
                GROUP BY
                    c.marca,
                    c.modelo
                ORDER BY
                    totalVendas DESC
                LIMIT 10; -- Limitar ao top 10
            """;
            StringBuilder sb = new StringBuilder();
            try (Connection conn = getConnection()) {
                var stmt = conn.prepareStatement(sql);
                var rs = stmt.executeQuery();
                while (rs.next()) {
                    System.out.println("Carro: " + rs.getString("marcaCarro") +
                        " | Modelo: " + rs.getString("modeloCarro") +
                        " | Total de Vendas: " + rs.getInt("totalVendas"));
                    // Mostrando as informações na gui:
                    sb.append(String.format("Carro: %s | Modelo: %s | Total de Vendas: %d%n",
                            rs.getString("marcaCarro"), rs.getString("modeloCarro"), rs.getInt("totalVendas")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return sb.toString(); // Retorna o StringBuilder com os dados formatados
        }

        // Selecionar vendas por data (tabela vendas) - saber se tem vendas em um período específico
        // Pegando só o Ano e o mês da data da venda
        public static String selecionarVendasPorData() {
            String sql = """
                    SELECT
                        v.data AS dataVenda,
                        COUNT(*)      AS totalVendas
                    FROM
                        vendas v
                    GROUP BY
                        v.data
                    ORDER BY
                        dataVenda DESC
                    LIMIT 10; -- Limitar ao top 10
                """;
            StringBuilder sb = new StringBuilder();
            try (Connection conn = getConnection()) {
                var stmt = conn.prepareStatement(sql);
                var rs = stmt.executeQuery();
                while (rs.next()) {
                    System.out.println("Data da Venda: " + rs.getString("dataVenda") +
                        " | Total de Vendas: " + rs.getInt("totalVendas"));
                    // Mostrando as informações na gui:
                    sb.append(String.format("Data da Venda: %s | Total de Vendas: %d%n",
                            rs.getString("dataVenda"), rs.getInt("totalVendas")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return sb.toString(); // Retorna o StringBuilder com os dados formatados
        }
        

        // Selecionar vendas por valor (tabela vendas) - talvez uma média de preço dos carros vendidos
        public static String selecionarVendasPorValor() {
            String sql = """
                    SELECT
                        AVG(valor) AS mediaValor, -- média dos preços dos carros vendidos
                        MIN(valor) AS menorValor, -- menor preço dos carros vendidos
                        MAX(valor) AS maiorValor -- maior preço dos carros vendidos
                    FROM
                        vendas;
                """;
            StringBuilder sb = new StringBuilder();
            try (Connection conn = getConnection()) {
                var stmt = conn.prepareStatement(sql);
                var rs = stmt.executeQuery();
                if (rs.next()) {
                    System.out.println("Média de Valor: R$" + rs.getDouble("mediaValor") +
                        " | Menor Valor: R$" + rs.getDouble("menorValor") +
                        " | Maior Valor: R$" + rs.getDouble("maiorValor"));
                    // Mostrando as informações na gui:
                    sb.append(String.format("Média de Valor: R$%.2f | Menor Valor: R$%.2f | Maior Valor: R$%.2f%n",
                            rs.getDouble("mediaValor"), rs.getDouble("menorValor"), rs.getDouble("maiorValor")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return sb.toString(); // Retorna o StringBuilder com os dados formatados
        }

        // Selecionar carros por marca (tabela carros) - saber a marca mais vendida
        public static String selecionarCarrosPorMarca() {
            String sql = """
                    SELECT
                        marca,
                        COUNT(*) AS totalCarros
                    FROM
                        carros
                    GROUP BY
                        marca
                    ORDER BY
                        totalCarros DESC
                    LIMIT 10; -- Limitar ao top 10
                """;
            StringBuilder sb = new StringBuilder();
            try (Connection conn = getConnection()) {
                var stmt = conn.prepareStatement(sql);
                var rs = stmt.executeQuery();
                while (rs.next()) {
                    System.out.println("Marca: " + rs.getString("marca") +
                        " | Total de Carros: " + rs.getInt("totalCarros"));
                    // Mostrando as informações na gui:
                    sb.append(String.format("Marca: %s | Total de Carros: %d%n",
                            rs.getString("marca"), rs.getInt("totalCarros")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return sb.toString(); // Retorna o StringBuilder com os dados formatados
        }

        // Selecionar carros por modelo (tabela carros)- saber o modelo mais vendido
        public static String selecionarCarrosPorModelo() {
            String sql = """
                    SELECT
                        modelo,
                        COUNT(*) AS totalCarros
                    FROM
                        carros
                    GROUP BY
                        modelo
                    ORDER BY
                        totalCarros DESC
                    LIMIT 10; -- Limitar ao top 10
                """;
            StringBuilder sb = new StringBuilder();
            try (Connection conn = getConnection()) {
                var stmt = conn.prepareStatement(sql);
                var rs = stmt.executeQuery();
                while (rs.next()) {
                    System.out.println("Modelo: " + rs.getString("modelo") +
                        " | Total de Carros: " + rs.getInt("totalCarros"));
                    // Mostrando as informações na gui:
                    sb.append(String.format("Modelo: %s | Total de Carros: %d%n",
                            rs.getString("modelo"), rs.getInt("totalCarros")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return sb.toString(); // Retorna o StringBuilder com os dados formatados
        }

        // Selecionar carros por ano (tabela carros) - saber se tem carros de um ano específico
        // Pegando só o mês e o ano do carro
        public static String selecionarCarrosPorAno() {
            String sql = """
                    SELECT
                        DATE_FORMAT(c.ano, '%Y-%m') AS dataVenda,
                        COUNT(*) AS totalCarros
                    FROM
                        carros c
                    GROUP BY
                        dataVenda
                    ORDER BY
                        totalCarros DESC
                    LIMIT 10; -- Limitar ao top 10
                """;
            StringBuilder sb = new StringBuilder();
            try (Connection conn = getConnection()) {
                var stmt = conn.prepareStatement(sql);
                var rs = stmt.executeQuery();
                while (rs.next()) {
                    System.out.println("Data: " + rs.getString("dataVenda") +
                        " | Total de Carros: " + rs.getInt("totalCarros"));
                    // Mostrando as informações na gui:
                    sb.append(String.format("Data: %s | Total de Carros: %d%n",
                            rs.getString("dataVenda"), rs.getInt("totalCarros")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return sb.toString(); // Retorna o StringBuilder com os dados formatados
        }

        // Selecionar carros por cor (tabela carros) - saber se tem carros de uma cor específica
        public static String selecionarCarrosPorCor() {
            String sql = """
                    SELECT
                        cor,
                        COUNT(*) AS totalCarros
                    FROM
                        carros
                    GROUP BY
                        cor
                    ORDER BY
                        totalCarros DESC
                    LIMIT 10; -- Limitar ao top 10
                """;
            StringBuilder sb = new StringBuilder();
            try (Connection conn = getConnection()) {
                var stmt = conn.prepareStatement(sql);
                var rs = stmt.executeQuery();
                while (rs.next()) {
                    System.out.println("Cor: " + rs.getString("cor") +
                        " | Total de Carros: " + rs.getInt("totalCarros"));
                    // Mostrando as informações na gui:
                    sb.append(String.format("Cor: %s | Total de Carros: %d%n",
                            rs.getString("cor"), rs.getInt("totalCarros")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return sb.toString(); // Retorna o StringBuilder com os dados formatados
        }

        // Função para atualizar o status do carro (tabela carros) - se o carro foi vendido ou não
        public static void atualizarStatusCarro(int idCarro, String novoStatus) {
            String sql = "UPDATE carros SET status = ? WHERE id = ?";
            try (Connection conn = getConnection()) {
                var stmt = conn.prepareStatement(sql);
                stmt.setString(1, novoStatus);
                stmt.setInt(2, idCarro);
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Status do carro atualizado com sucesso.");
                } else {
                    System.out.println("Nenhum carro encontrado com o ID: " + idCarro);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Função para deletar um carro (tabela carros) - se o carro foi vendido ou não
        public static void deletarCarro(int idCarro) {
            String sql = "DELETE FROM carros WHERE id = ?";
            try (Connection conn = getConnection()) {
                var stmt = conn.prepareStatement(sql);
                stmt.setInt(1, idCarro);
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Carro deletado com sucesso.");
                } else {
                    System.out.println("Nenhum carro encontrado com o ID: " + idCarro);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Função para deletar uma venda (tabela vendas) - se houver algum problema com a venda
        // ou se o cliente desistiu da compra
        public static void deletarVenda(int idVenda) {
            String sql = "DELETE FROM vendas WHERE id = ?";
            try (Connection conn = getConnection()) {
                var stmt = conn.prepareStatement(sql);
                stmt.setInt(1, idVenda);
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Venda deletada com sucesso.");
                } else {
                    System.out.println("Nenhuma venda encontrada com o ID: " + idVenda);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}