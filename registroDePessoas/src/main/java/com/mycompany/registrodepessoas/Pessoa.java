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
public class Pessoa {
    private String cpf;
    private String nome;
    private String nascimento;
    private String data_registro;
    private String editor_do_registro;
    Pessoas pessoas=new Pessoas();
    
    public Pessoa(String coluna, String filtro){
        ArrayList<String> colunaBuscada=null;
        ArrayList<String> dadosPessoa=new ArrayList<>();
        boolean encontrado=false;
        if(coluna.equals("cpf")){
            colunaBuscada=pessoas.Cpf();
        }
        else if(coluna.equals("nome")){
            colunaBuscada=pessoas.Nome();
        }
        else if(coluna.equals("nascimento")){
            colunaBuscada=pessoas.Nascimento();
        }
        else if(coluna.equals("data_edicao")){
            colunaBuscada=pessoas.Data_registro();
        }
        else if(coluna.equals("editor_do_registro")){
            colunaBuscada=pessoas.Editor_do_registro();
        }
        for(int i=0;i<pessoas.Cpf().size();i++){
            if(filtro.equals(colunaBuscada.get(i))){
                this.cpf=pessoas.Cpf().get(i);
                this.nome=pessoas.Nome().get(i);
                this.nascimento=pessoas.Nascimento().get(i);
                this.data_registro=pessoas.Data_registro().get(i);
                this.editor_do_registro=pessoas.Editor_do_registro().get(i);
                encontrado=true;
            }

            else if(!encontrado){
                this.cpf=null;
                this.nome=null;
                this.nascimento=null;
                this.data_registro=null;
                this.editor_do_registro=null;
                System.out.println("Nada encontrado :(");
            }
        }
    }
    
    public String Cpf(){
        return cpf;
    }
    public String Nome(){
        return nome;
    }
    public String Nascimento(){
        return nascimento;
    }
    public String Data_registro(){
        return data_registro;
    }
    public String Editor_do_registro(){
        return editor_do_registro;
    }
}
