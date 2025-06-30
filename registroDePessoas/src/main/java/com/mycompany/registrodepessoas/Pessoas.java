/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.registrodepessoas;
import java.util.ArrayList;
/**
 *
 * @author joaod
 */
public class Pessoas {
    private ArrayList<String> cpf;
    private ArrayList<String> nome;
    private ArrayList<String> nascimento;
    private ArrayList<String> data_registro;
    private ArrayList<String> editor_do_registro;
    public static final String table="pessoas";
    
    public Pessoas(){
        Conexao conexao=new Conexao();
        this.cpf=conexao.filtrar("SELECT cpf FROM "+table);
        this.nome=conexao.filtrar("SELECT nome FROM "+table);
        this.nascimento=conexao.filtrar("SELECT nascimento FROM "+table);
        this.data_registro=conexao.filtrar("SELECT data_registro FROM "+table);
        this.editor_do_registro=conexao.filtrar("SELECT editor_do_registro FROM "+table);
    }
    
    public ArrayList<String> Cpf(){
        return cpf;
    }
    public ArrayList<String> Nome(){
        return nome;
    }
     public ArrayList<String> Nascimento(){
        return nascimento;
    }
     public ArrayList<String> Data_registro(){
        return data_registro;
    }
    public ArrayList<String> Editor_do_registro(){
        return editor_do_registro;
    }
}
