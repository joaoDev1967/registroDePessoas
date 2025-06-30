package com.mycompany.registrodepessoas;
//https://paletadecores.com/paleta/93cbe0/81b9ce/6fa7bb/5c95a9/4a8396/

public class RegistroDePessoas {
    
    private static String usuario;
    
    public static String getUsuario(){
        return usuario;
    }
    
    public static void setUsuario(String nome){
        usuario=nome;
    }
    
    
    public static void main(String[] args) {
        /*
        java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    new RegistrosFrame().setVisible(true);
                }
            });*/
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginFrame().setVisible(true);
            }
        });
    }

}
