/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.registrodepessoas;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Date;
import java.util.Arrays;
import java.time.LocalDate;
/**
 *
 * @author joaod
 */
public class Conexao {
    private String senha;
    private String port;
    private String nome;
    private String host;
    private String usuario;
    private String url;
    private static final Dotenv dotenv=Dotenv.load();
    
    public Conexao(){
        this.senha=dotenv.get("SENHA");
        this.port=dotenv.get("PORT");
        this.nome=dotenv.get("NOME");
        this.host=dotenv.get("HOST");
        this.usuario=dotenv.get("USUARIO");
        this.url= "jdbc:mysql://" + host + ":" + port + "/" + nome + "?useSSL=false&serverTimezone=UTC";
    }
    
    public ArrayList<String> filtrar(String query){
        ArrayList<String> resultadoFiltragem = new ArrayList<>();
        
        try(Connection conexao=DriverManager.getConnection(url,usuario,senha);
            Statement stmt=conexao.createStatement();
            ResultSet resultado = stmt.executeQuery(query)){
            int numColunas=resultado.getMetaData().getColumnCount();
            while(resultado.next()){
                for(int i=1;i<=numColunas;i++){
                    resultadoFiltragem.add(resultado.getString(i));
                }
            }
        }
        catch(SQLException ex){
            System.out.println("Ocorreu erro ao tentar acessar banco de dados: "+ex.getMessage());
        }
        return resultadoFiltragem;
    }
    
    public boolean cpfUnico(String cpf ,ArrayList<String>lista ){
        for(int i=0;i<lista.size();i++){
            if(cpf.equals(lista.get(i))){
                return false;
            }
        }
        return true;
    }
    
    public boolean cpfValido(String cpf){
        if(cpf.length()==11){
            int[] cpfArray=new int[11];
            int[] resultadosSomas=new int[10];
            int somaFinal,ciclos=0;
            for(int i=0;i<11;i++){
                cpfArray[i]=Character.getNumericValue(cpf.charAt(i));
            }
            
            for(int i=8;i>=0;i--){
                int multiplicador=ciclos+2;
                resultadosSomas[i]=cpfArray[i]*multiplicador;
                ciclos++;
            }
            somaFinal= Arrays.stream(resultadosSomas).sum();
            if(somaFinal%11<2&&cpfArray[9]!=0){
                return false;
            }
            else if(somaFinal%11>=2&&11-(somaFinal%11)!=cpfArray[9]){
                return false;
            }
            ciclos=0;
            for(int i=9;i>=0;i--){
                int multiplicador=ciclos+2;
                resultadosSomas[i]=cpfArray[i]*multiplicador;
                ciclos++;
            }
            somaFinal= Arrays.stream(resultadosSomas).sum();
            if(somaFinal%11<2&&cpfArray[10]!=0){
                return false;
            }
            else if(somaFinal%11>=2&&(11-somaFinal%11)!=cpfArray[10]){
                return false;
            }
            return true;
        }
        return false;
    }
    

    //cpf,nascimento,nome,data_registro,editor_do_registro
    public boolean editarPessoa(String cpfAntigo ,String cpfNovo,String nome, String nascimento, String editorDoRegistro){
        if(cpfValido(cpfNovo)){
            String[] partesData=nascimento.split("/");
            int dia = Integer.parseInt(partesData[0]);
            int mes = Integer.parseInt(partesData[1]);
            int ano = Integer.parseInt(partesData[2]);
            LocalDate Nascimento=LocalDate.of(ano,mes,dia);
            LocalDate dataRegistro=LocalDate.now();
            String query="UPDATE pessoas SET cpf=?, nascimento=?,nome=?,data_registro=?,editor_do_registro=? WHERE cpf=?";
            try(Connection conn=DriverManager.getConnection(url,usuario,senha);PreparedStatement stmt= conn.prepareStatement(query);){
                stmt.setString(1,cpfNovo);
                stmt.setDate(2,Date.valueOf(Nascimento));
                stmt.setString(3,nome);
                stmt.setDate(4,Date.valueOf(dataRegistro));
                stmt.setString(5,editorDoRegistro);
                stmt.setString(6,cpfAntigo);
                int linhasAfetadas = stmt.executeUpdate();
                return linhasAfetadas!=0;
            } 
            catch (SQLException e) {
                System.out.println("Erro ao editar pessoa: " + e.getMessage());
            }

        }
        return false;
    }
    
    public boolean crtDentroLista (char crt,ArrayList<Character> lista){
        for(int i=0;i<lista.size();i++){
            if(crt==lista.get(i)){
                return true;
            }
        }
        return false;
    }
    
    public boolean dataValida(String data){
        ArrayList<Character> nums = new ArrayList<>(Arrays.asList('0', '1', '2','3','4','5','6','7','8','9'));
        ArrayList<Character> numsValidosPrimeiroDigDia = new ArrayList<>(Arrays.asList('0', '1','2','3'));  
        ArrayList<Character> numsValidosPrimeiroDigMes = new ArrayList<>(Arrays.asList('0', '1'));  
        for(int i=0;i<10;i++){
            char crtAtual=data.charAt(i);
            if(i<2){
                if(i==0){
                    if(!crtDentroLista(crtAtual,numsValidosPrimeiroDigDia)){
                        return false;
                    }
                }
                else{
                    if(!crtDentroLista(crtAtual,nums)){
                        return false;
                    }
                }
            }
            else if(i==2){
                if(crtAtual!='/'){
                    System.out.println("A1");
                    return false;
                }
            }
            else if(i==3){
                if(!crtDentroLista(crtAtual,numsValidosPrimeiroDigMes)){
                    return false;
                }
            }
            else if(i==4){
                if(!crtDentroLista(crtAtual,nums)){
                    return false;
                }
            }
            else if(i==5){
                if(crtAtual!='/'){
                    return false;
                }
            }
            else if(i>10){
                if(!crtDentroLista(crtAtual,nums)){
                    return false;
                }
            }
        }

        return true;
    }
    
    public boolean adicionarPessoa(String cpf,String nome, String nascimento, ArrayList<String>cpfs){
        Connection conexao=null;
        String editorRegistro=RegistroDePessoas.getUsuario();
        if(cpfValido(cpf)&&cpfUnico(cpf,cpfs)&&dataValida(nascimento)){
            String[] partesData=nascimento.split("/");
            int dia = Integer.parseInt(partesData[0]);
            int mes = Integer.parseInt(partesData[1]);
            int ano = Integer.parseInt(partesData[2]);
            
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                conexao = DriverManager.getConnection(url,usuario,senha);
                String insercaoPadrao="INSERT INTO pessoas(cpf,nome,nascimento,data_registro,editor_do_registro) VALUES(?,?,?,?,?)";
                PreparedStatement prepInsercao =conexao.prepareStatement(insercaoPadrao);
                LocalDate Nascimento=LocalDate.of(ano,mes,dia);
                LocalDate dataRegistro=LocalDate.now();
                prepInsercao.setString(1, cpf); 
                prepInsercao.setString(2, nome);
                prepInsercao.setDate(3, Date.valueOf(Nascimento));
                prepInsercao.setDate(4, Date.valueOf(dataRegistro)); 
                prepInsercao.setString(5, editorRegistro); 
                prepInsercao.executeUpdate();
                prepInsercao.close();
                System.out.println("Operação bem sucedida!!!");
            }
            catch(ClassNotFoundException ex){
                System.out.println("classe nao encontrada");
            }
            catch(SQLException ex){
                System.out.println("Ocorreu erro ao tentar acessar banco de dados: "+ex.getMessage());
            } 
            finally{
                if(conexao!=null){
                    try{
                        conexao.close();
                    }
                    catch(SQLException e){
                        System.out.println("Erro:  "+e.getMessage());
                    }
                }
            } 
            return true;
        }
        //depois fazer verificação de data na função de editar pessoa
        
        return false;
    }
    
    public boolean apagar(String cpf,ArrayList<String>cpfs){
        if(!cpfUnico(cpf,cpfs)){
            String query="DELETE FROM pessoas WHERE cpf=?";
            try(Connection conn=DriverManager.getConnection(url,usuario,senha);PreparedStatement stmt= conn.prepareStatement(query);){
                stmt.setString(1,cpf);
                int linhasAfetadas = stmt.executeUpdate();
                return linhasAfetadas!=0;
            } 
            catch (SQLException e) {
                System.out.println("Erro ao excluir pessoa: " + e.getMessage());
            }

        }
        return false;
    }
    
        
    
}
