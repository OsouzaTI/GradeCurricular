/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conection;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
/**
 *
 * @author osouza
 */
public class conexao {
      
    private static final String Driver = "org.apache.derby.jdbc.EmbeddedDriver";
    private static final String URL = "jdbc:derby:dbgrade;create=true";
    //private static final String USER = "root";//JOptionPane.showInputDialog("Digite o usuario do banco", "root");
    //private static final String PASS = "";//JOptionPane.showInputDialog("Digite a senha do banco(Enter to default)");
    
    public static Connection getConnection()
    {
         try {

            Class.forName(Driver);
            Connection conn = DriverManager.getConnection(URL);  
            return conn;
             
         } catch (ClassNotFoundException | SQLException ex) {

             throw new RuntimeException("Erro ao conectar: " + ex);

         }
    }

    public static void closeConnection(Connection con)
    {
        if(con!=null)
        {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(conexao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public static void closeConnection(Connection con, PreparedStatement stmt)
    {
        closeConnection(con);
        if(con!=null)
        {
            try {
                if(stmt!=null)
                {
                    stmt.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(conexao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }           
    }

    public static void closeConnection(Connection con, PreparedStatement stmt, ResultSet rs)
    {
        closeConnection(con, stmt);
        if(con!=null)
        {
            try {
                if(rs!=null)
                {
                    rs.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(conexao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }           
    }
       
}
