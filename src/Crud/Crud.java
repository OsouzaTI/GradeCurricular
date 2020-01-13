/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Crud;
import Conection.conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.grade;
/**
 *
 * @author osouza
 */
public class Crud {
    
    public void update_config(int index)
    {
        Connection con = conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            
            stmt = con.prepareStatement("SELECT * FROM tb_config WHERE id = 1");            
            rs = stmt.executeQuery();
            int rows = 0;
            while(rs.next())
            {
                rows++;
            }
            
            
            
            if(rows == 0)
            {      
                stmt = con.prepareStatement("INSERT INTO tb_config (default_periodo) VALUES (?)");
                stmt.setInt(1, 3);                     
                stmt.executeUpdate();
                System.out.print("Entrou em Insert -> "+ rs);
            }
            else
            {
                stmt = con.prepareStatement("UPDATE tb_config SET default_periodo = ? WHERE id = 1");
                stmt.setInt(1, index);                     
                stmt.executeUpdate();
                
                System.out.print("Entrou em Update ");
                
            }
            
            JOptionPane.showMessageDialog(null, "Salvo com sucesso!");
        } catch (SQLException ex ) {
            JOptionPane.showMessageDialog(null, "Erro ao Salvar periodo: "+ex);
        }
        finally
        {
            conexao.closeConnection(con, stmt);
        }
        
    }
    
    public int get_config()
    {
        Connection con = conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int n = 0;
        try {
            stmt = con.prepareStatement("SELECT * FROM tb_config WHERE id = 1");            
            rs = stmt.executeQuery();
                       
            while(rs.next())
            {
                n = rs.getInt("default_periodo");
            }
           
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao chamar sql config: "+ex);
        }
        finally
        {
            conexao.closeConnection(con, stmt);
            return n;
        }
        
    }
    
    public void createTable()
    {
        Connection con = conexao.getConnection();
        PreparedStatement stmt = null;
        String[] tabelasNomes       = {"tb_seg", "tb_ter",   "tb_qua",  "tb_qui",  "tb_sex",  "tb_sab",  "tb_dom" };
        try {
            
            stmt = con.prepareStatement("CREATE TABLE tb_config (" +                                                                                
                                        "  id INT not null primary key GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)," +
                                        "  default_periodo INT not null" +
                                        ")");

            stmt.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Banco de dados será criado, espere alguns segundos!");
            
            for(String tabelas : tabelasNomes)
            {
            
                stmt = con.prepareStatement("CREATE TABLE "+tabelas+"(" +                                                                                
                                        "  id INT not null primary key GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)," +
                                        "  hora     varchar(200) ," +                                        
                                        "  materia  varchar(200) ," +                                        
                                        "  periodo  varchar(200) ," +                                        
                                        "  sala     varchar(200)  " +                                        
                                        ")");
                
                stmt.executeUpdate();
                
            }
            
            JOptionPane.showMessageDialog(null, "Banco Criado!");
            
        } catch (SQLException ex) {
            
            return;
            //JOptionPane.showMessageDialog(null, "Erro ao criar Banco: "+ex);            
            
        }
        finally
        {
            conexao.closeConnection(con, stmt);
        }
        
    }
    
    public void create(grade g, String tabelaNome)
    {
        Connection con = conexao.getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement("INSERT INTO "+tabelaNome+" (HORA, MATERIA, PERIODO, SALA) VALUES (?, ?, ?, ?)");
            stmt.setString(1, g.getHora());
            stmt.setString(2, g.getMateria());
            stmt.setString(3, g.getPeriodo());
            stmt.setString(4, g.getSala());
            
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Salvo com sucesso!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar: "+ex);
        }
        finally
        {
            conexao.closeConnection(con, stmt);
        }
        
    }
    
    public void update(grade g, String tabelaNome)
    {
        Connection con = conexao.getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement("UPDATE "+tabelaNome+" SET hora = ?, materia = ?, SALA = ? WHERE id = ?");
            stmt.setString(1, g.getHora());
            stmt.setString(2, g.getMateria());
            stmt.setString(3, g.getSala());
            stmt.setInt(4, g.getId());
           
            
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar: "+ex);
        }
        finally
        {
            conexao.closeConnection(con, stmt);
        }
        
    }
    
    public void delete(grade g, String tabelaNome)
    {
        Connection con = conexao.getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement("DELETE FROM "+tabelaNome+" WHERE id = ?");
            stmt.setInt(1, g.getId());           
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Deletado com sucesso!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Deletar: "+ex);
        }
        finally
        {
            conexao.closeConnection(con, stmt);
        }
        
    }
    
    public List<grade> read(String nomeTabela, String periodo)
    {
        Connection con = conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        List<grade> gradeList = new ArrayList<>();
        
        try {
            stmt = con.prepareStatement("SELECT * from "+nomeTabela+" WHERE periodo = ? ");
            stmt.setString(1, periodo);
            rs = stmt.executeQuery();
            
            while(rs.next())
            {
                grade g = new grade();
                
                g.setId(rs.getInt("ID"));
                g.setHora(rs.getString("HORA"));
                g.setMateria(rs.getString("MATERIA"));
                g.setSala(rs.getString("SALA"));
                gradeList.add(g);
            }            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar dados! -> "+ex);
        }
        finally
        {
            conexao.closeConnection(con, stmt, rs);
        }
        
        return gradeList;
        
    }
    
}
