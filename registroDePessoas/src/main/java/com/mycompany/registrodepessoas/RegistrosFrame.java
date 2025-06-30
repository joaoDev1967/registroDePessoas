/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.registrodepessoas;

import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.time.format.DateTimeFormatter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import javax.swing.table.TableModel;
import java.io.IOException;



/**
 *
 * @author joaod
 */
public class RegistrosFrame extends javax.swing.JFrame {

    /**
     * Creates new form RegistrosFrame
     */
    ArrayList<String> cpfs;
    ArrayList<String> nomes;
    ArrayList<String> nascimentos;
    ArrayList<String> datasDeRegistro;
    ArrayList<String> editoresDeRegistro;
    String usuario=RegistroDePessoas.getUsuario();
    LocalDate dataRegistro=LocalDate.now();
    String DataRegistro=dataPadrao(dataRegistro);
    Conexao conexao=new Conexao();
    String cpfSelecionado;
    private static javax.swing.JTable table;
    
    public static void setTable(javax.swing.JTable Table){
        table=Table;
    }
    
    public static javax.swing.JTable getTable(){
        return table;
    }
    
    public RegistrosFrame() {
        initComponents();
        carregarDados();
        inserirDados();
        carregarDataUsuario();
    }
    private void carregarDados(){
        Pessoas pessoas=new Pessoas();
        cpfs=pessoas.Cpf();
        nomes=pessoas.Nome();
        nascimentos=pessoas.Nascimento();
        datasDeRegistro=pessoas.Data_registro();
        editoresDeRegistro=pessoas.Editor_do_registro();
    }
    
    private String dataPadrao(LocalDate data){
        DateTimeFormatter formatter =DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String Data=data.format(formatter);
        return Data;
    }
    
    
    private void carregarDataUsuario(){
        editorRegistroEntry.setText(usuario);
        dataRegistroEntry.setText(DataRegistro);
    }
    
    private void inserirDados(){
        DefaultTableModel modelo = (DefaultTableModel) registrosTable.getModel();
        modelo.setRowCount(0);
        for(int i=0;i<cpfs.size();i++){
            LocalDate dataReg=LocalDate.parse(datasDeRegistro.get(i));
            LocalDate dataNasc=LocalDate.parse(nascimentos.get(i));
            String dataRegFormatada=dataPadrao(dataReg);
            String dataNascFormatada=dataPadrao(dataNasc);
            modelo.addRow(new Object[]{
                cpfs.get(i),
                nomes.get(i),
                dataNascFormatada,
                dataRegFormatada,
                editoresDeRegistro.get(i)
            });
        setTable(registrosTable);
    }}
    
    private boolean inserirDadosFiltrados(String cpf,String nome,String nascimento,String editorRegistro,String dataRegistro){
        //erro de troca de colunas
        DefaultTableModel modelo = (DefaultTableModel) registrosTable.getModel();
        ArrayList<ArrayList<String>> rows= new ArrayList<>();
        boolean apenasCpf=false;
        
        for(int i=0;i<cpfs.size();i++){
            LocalDate dataReg=LocalDate.parse(datasDeRegistro.get(i));
            LocalDate dataNasc=LocalDate.parse(nascimentos.get(i));
            String dataRegFormatada=dataPadrao(dataReg);
            String dataNascFormatada=dataPadrao(dataNasc);
            ArrayList<String> row= new ArrayList<>();
            row.add(cpfs.get(i));
            row.add(nomes.get(i));
            row.add(dataNascFormatada);
            row.add(dataRegFormatada);
            row.add(editoresDeRegistro.get(i));
            rows.add(row);
        }
        for(int i=0;i<rows.size();i++){
            System.out.println(rows.get(i));
        }
        if(nome.equals("")&&nascimento.equals("")&&editorRegistro.equals("")&&dataRegistro.equals("")){
            apenasCpf=true;
        }
        
        if(cpf.equals("")){
            if(apenasCpf){
                //se todos o parametros estiverem vazios
                inserirDados();
                return true;
            }
            if(!nome.equals("")){
                System.out.println("1");
                for(int i=rows.size()-1;i>=0;i--){
                    if(!rows.get(i).get(1).equals(nome)){
                        rows.remove(i);
                    }
                }
            }
            if(!nascimento.equals("")){
                System.out.println("2");
                 for(int i=rows.size()-1;i>=0;i--){
                    if(!rows.get(i).get(2).equals(nascimento)){
                        rows.remove(i);
                    }
                }
            }
            
            if(!dataRegistro.equals("")){
                System.out.println("4");
                 for(int i=rows.size()-1;i>=0;i--){
                    if(!rows.get(i).get(3).equals(dataRegistro)){
                        rows.remove(i);
                    }
                }
            }
            
            if(!editorRegistro.equals("")){
                System.out.println("3");
                 for(int i=rows.size()-1;i>=0;i--){
                    if(!rows.get(i).get(4).equals(editorRegistro)){
                        rows.remove(i);
                    }
                }
            }
            modelo.setRowCount(0);
            for(int i=0;i<rows.size();i++){
                modelo.addRow(new Object[]{
                    rows.get(i).get(0),
                    rows.get(i).get(1),
                    rows.get(i).get(2),
                    rows.get(i).get(3),
                    rows.get(i).get(4)
                });
            }
        }
        else{
            if(apenasCpf){
                for(int i=0;i<cpfs.size();i++){
                    if(cpf.equals(cpfs.get(i))){
                        modelo.setRowCount(0);
                        modelo.addRow(new Object[]{
                            cpfs.get(i),
                            nomes.get(i),
                            nascimentos.get(i),
                            datasDeRegistro.get(i),
                            editoresDeRegistro.get(i)
                            
                        });
                        setTable(registrosTable);
                        return true;
                    }
                }
            }
            else{
                modelo.setRowCount(0);
                JOptionPane.showMessageDialog(this, "Nenhum registro encontrado que\n corresponda a esses dados", "Erro", JOptionPane.ERROR_MESSAGE);
                return true;
            }
        }
        
        return false;
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel20 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        limparBt = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cpfEntry = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        nomeEntry = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        nascimentoEntry = new javax.swing.JTextField();
        jPanel13 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        dataRegistroEntry = new javax.swing.JTextField();
        jPanel16 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        editorRegistroEntry = new javax.swing.JTextField();
        jPanel22 = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        jPanel25 = new javax.swing.JPanel();
        criarBt = new javax.swing.JButton();
        apagarBt = new javax.swing.JButton();
        filtrarBt = new javax.swing.JButton();
        alterarBt = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        exportarBt = new javax.swing.JButton();
        jPanel21 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        registrosTable = new javax.swing.JTable();

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel4.setBackground(new java.awt.Color(92, 149, 169));
        jPanel4.setForeground(new java.awt.Color(255, 255, 255));
        jPanel4.setMaximumSize(new java.awt.Dimension(500, 750));
        jPanel4.setPreferredSize(new java.awt.Dimension(500, 750));

        jPanel2.setBackground(new java.awt.Color(92, 149, 169));
        jPanel2.setPreferredSize(new java.awt.Dimension(500, 70));

        jLabel1.setFont(new java.awt.Font("Calibri Light", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Dados da Pessoa");

        limparBt.setBackground(new java.awt.Color(74, 131, 150));
        limparBt.setFont(new java.awt.Font("Calibri Light", 0, 12)); // NOI18N
        limparBt.setForeground(new java.awt.Color(255, 255, 255));
        limparBt.setText("Limpar");
        limparBt.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        limparBt.setPreferredSize(null);
        limparBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                limparBtActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(limparBt, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64)
                .addComponent(jLabel1)
                .addContainerGap(139, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(144, 144, 144))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(limparBt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(169, 169, 169))
        );

        jPanel4.add(jPanel2);

        jPanel3.setBackground(new java.awt.Color(92, 149, 169));
        jPanel3.setPreferredSize(new java.awt.Dimension(500, 100));
        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel5.setBackground(new java.awt.Color(92, 149, 169));

        jPanel6.setBackground(new java.awt.Color(92, 149, 169));

        jLabel2.setFont(new java.awt.Font("Calibri Light", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("CPF");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(cpfEntry, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cpfEntry, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel5.add(jPanel6);

        jPanel3.add(jPanel5, java.awt.BorderLayout.CENTER);

        jPanel4.add(jPanel3);

        jPanel7.setBackground(new java.awt.Color(92, 149, 169));
        jPanel7.setPreferredSize(new java.awt.Dimension(500, 100));
        jPanel7.setLayout(new java.awt.BorderLayout());

        jPanel8.setBackground(new java.awt.Color(92, 149, 169));

        jPanel9.setBackground(new java.awt.Color(92, 149, 169));

        jLabel3.setFont(new java.awt.Font("Calibri Light", 0, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Nome");

        nomeEntry.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nomeEntryActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(nomeEntry, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nomeEntry, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel8.add(jPanel9);

        jPanel7.add(jPanel8, java.awt.BorderLayout.CENTER);

        jPanel4.add(jPanel7);

        jPanel10.setBackground(new java.awt.Color(92, 149, 169));
        jPanel10.setPreferredSize(new java.awt.Dimension(500, 100));
        jPanel10.setLayout(new java.awt.BorderLayout());

        jPanel11.setBackground(new java.awt.Color(92, 149, 169));

        jPanel12.setBackground(new java.awt.Color(92, 149, 169));

        jLabel4.setFont(new java.awt.Font("Calibri Light", 0, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Nascimento");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(nascimentoEntry, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nascimentoEntry, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel11.add(jPanel12);

        jPanel10.add(jPanel11, java.awt.BorderLayout.CENTER);

        jPanel4.add(jPanel10);

        jPanel13.setBackground(new java.awt.Color(92, 149, 169));
        jPanel13.setPreferredSize(new java.awt.Dimension(500, 100));
        jPanel13.setLayout(new java.awt.BorderLayout());

        jPanel14.setBackground(new java.awt.Color(92, 149, 169));

        jPanel15.setBackground(new java.awt.Color(92, 149, 169));

        jLabel5.setFont(new java.awt.Font("Calibri Light", 0, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Data Registro");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(dataRegistroEntry, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dataRegistroEntry, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel14.add(jPanel15);

        jPanel13.add(jPanel14, java.awt.BorderLayout.CENTER);

        jPanel4.add(jPanel13);

        jPanel16.setBackground(new java.awt.Color(92, 149, 169));
        jPanel16.setPreferredSize(new java.awt.Dimension(500, 100));
        jPanel16.setLayout(new java.awt.BorderLayout());

        jPanel17.setBackground(new java.awt.Color(92, 149, 169));

        jPanel18.setBackground(new java.awt.Color(92, 149, 169));

        jLabel6.setFont(new java.awt.Font("Calibri Light", 0, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Editor do Registro");

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(editorRegistroEntry, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(editorRegistroEntry, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel17.add(jPanel18);

        jPanel16.add(jPanel17, java.awt.BorderLayout.CENTER);

        jPanel4.add(jPanel16);

        jPanel22.setBackground(new java.awt.Color(92, 149, 169));
        jPanel22.setPreferredSize(new java.awt.Dimension(500, 100));
        jPanel22.setLayout(new java.awt.BorderLayout());

        jPanel23.setBackground(new java.awt.Color(92, 149, 169));
        jPanel23.setPreferredSize(new java.awt.Dimension(489, 120));

        jPanel24.setBackground(new java.awt.Color(92, 149, 169));
        jPanel24.setLayout(new java.awt.BorderLayout());

        jPanel25.setBackground(new java.awt.Color(92, 149, 169));

        criarBt.setBackground(new java.awt.Color(74, 131, 150));
        criarBt.setFont(new java.awt.Font("Calibri Light", 0, 18)); // NOI18N
        criarBt.setForeground(new java.awt.Color(255, 255, 255));
        criarBt.setText("Criar");
        criarBt.setPreferredSize(new java.awt.Dimension(100, 53));
        criarBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                criarBtActionPerformed(evt);
            }
        });

        apagarBt.setBackground(new java.awt.Color(74, 131, 150));
        apagarBt.setFont(new java.awt.Font("Calibri Light", 0, 18)); // NOI18N
        apagarBt.setForeground(new java.awt.Color(255, 255, 255));
        apagarBt.setText("Apagar");
        apagarBt.setPreferredSize(new java.awt.Dimension(100, 53));
        apagarBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                apagarBtActionPerformed(evt);
            }
        });

        filtrarBt.setBackground(new java.awt.Color(74, 131, 150));
        filtrarBt.setFont(new java.awt.Font("Calibri Light", 0, 18)); // NOI18N
        filtrarBt.setForeground(new java.awt.Color(255, 255, 255));
        filtrarBt.setText("Filtrar");
        filtrarBt.setPreferredSize(new java.awt.Dimension(100, 53));
        filtrarBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filtrarBtActionPerformed(evt);
            }
        });

        alterarBt.setBackground(new java.awt.Color(74, 131, 150));
        alterarBt.setFont(new java.awt.Font("Calibri Light", 0, 18)); // NOI18N
        alterarBt.setForeground(new java.awt.Color(255, 255, 255));
        alterarBt.setText("Alterar");
        alterarBt.setPreferredSize(new java.awt.Dimension(100, 53));
        alterarBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alterarBtActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(alterarBt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(criarBt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(filtrarBt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(apagarBt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(apagarBt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel25Layout.createSequentialGroup()
                        .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(filtrarBt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(alterarBt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(criarBt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel24.add(jPanel25, java.awt.BorderLayout.CENTER);

        jPanel23.add(jPanel24);

        jPanel22.add(jPanel23, java.awt.BorderLayout.CENTER);

        jPanel4.add(jPanel22);

        getContentPane().add(jPanel4, java.awt.BorderLayout.CENTER);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMaximumSize(new java.awt.Dimension(770, 32767));
        jPanel1.setPreferredSize(new java.awt.Dimension(770, 750));

        jPanel19.setBackground(new java.awt.Color(255, 255, 255));
        jPanel19.setPreferredSize(new java.awt.Dimension(779, 100));

        jLabel7.setFont(new java.awt.Font("Calibri Light", 0, 36)); // NOI18N
        jLabel7.setText("Registros");

        exportarBt.setBackground(new java.awt.Color(74, 131, 150));
        exportarBt.setFont(new java.awt.Font("Calibri Light", 0, 18)); // NOI18N
        exportarBt.setForeground(new java.awt.Color(255, 255, 255));
        exportarBt.setText("Exportar PDF");
        exportarBt.setPreferredSize(new java.awt.Dimension(100, 53));
        exportarBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportarBtActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 466, Short.MAX_VALUE)
                .addComponent(exportarBt, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(exportarBt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel19);

        jPanel21.setBackground(new java.awt.Color(255, 255, 255));
        jPanel21.setFont(new java.awt.Font("Calibri Light", 0, 12)); // NOI18N
        jPanel21.setPreferredSize(new java.awt.Dimension(779, 650));

        registrosTable.setBackground(new java.awt.Color(111, 167, 187));
        registrosTable.setFont(new java.awt.Font("Calibri Light", 0, 12)); // NOI18N
        registrosTable.setForeground(new java.awt.Color(255, 255, 255));
        registrosTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CPF", "Nome", "Nascimento", "Data de registro", "Editor do registro"
            }
        ));
        registrosTable.setFocusable(false);
        registrosTable.setGridColor(new java.awt.Color(51, 51, 51));
        registrosTable.setSelectionBackground(new java.awt.Color(147, 203, 224));
        registrosTable.setSelectionForeground(new java.awt.Color(255, 255, 255));
        registrosTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                registrosTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(registrosTable);

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 733, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 511, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(112, 112, 112))
        );

        jPanel1.add(jPanel21);

        getContentPane().add(jPanel1, java.awt.BorderLayout.EAST);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    
    
    private void nomeEntryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nomeEntryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nomeEntryActionPerformed

    private void filtrarBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filtrarBtActionPerformed
        inserirDadosFiltrados(cpfEntry.getText(),nomeEntry.getText(),nascimentoEntry.getText(),editorRegistroEntry.getText(),dataRegistroEntry.getText());
        cpfEntry.setText("");
        nomeEntry.setText("");
        nascimentoEntry.setText("");
    }//GEN-LAST:event_filtrarBtActionPerformed

    
    private void alterarBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alterarBtActionPerformed
        // Fazer alteração que coloca data de hoje caso esteja uma outra la no lugar
        boolean cpfIgual=false;
        if(cpfEntry.getText().equals(cpfSelecionado)){
            cpfIgual=true;
        }
        if(editorRegistroEntry.getText().equals(usuario)&&dataRegistroEntry.getText().equals(DataRegistro)){
           if(conexao.editarPessoa(cpfSelecionado,cpfEntry.getText(),nomeEntry.getText(),
                    nascimentoEntry.getText(),editorRegistroEntry.getText())&&
                    (cpfIgual||conexao.cpfUnico(cpfEntry.getText(), cpfs))){
                carregarDados();
                inserirDados();
                JOptionPane.showMessageDialog(this, "Registro alterado com sucesso!", "=)", JOptionPane.INFORMATION_MESSAGE);
            }
            else{
                JOptionPane.showMessageDialog(this, "CPF invalido ou já registrado!", "Erro", JOptionPane.ERROR_MESSAGE);
            } 
        }
        else{
            JOptionPane.showMessageDialog(this, "Você só pode alterar registros em nome\ndo usuario logado e utilizando a data atual", "Erro", JOptionPane.ERROR_MESSAGE);
            carregarDataUsuario(); 
        }
        
        
    }//GEN-LAST:event_alterarBtActionPerformed

    private void criarBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_criarBtActionPerformed
        if(editorRegistroEntry.getText().equals(usuario)&&dataRegistroEntry.getText().equals(DataRegistro)){
            if(conexao.adicionarPessoa(cpfEntry.getText(), nomeEntry.getText(), nascimentoEntry.getText(), cpfs)){
                carregarDados();
                inserirDados();
                JOptionPane.showMessageDialog(this, "Pessoa adicionada com sucesso!", "=)", JOptionPane.INFORMATION_MESSAGE); 
            }
            else if(!conexao.cpfValido(cpfEntry.getText())||!conexao.cpfUnico(cpfEntry.getText(), cpfs)){
                JOptionPane.showMessageDialog(this, "CPF invalido ou já registrado!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
            else{
                JOptionPane.showMessageDialog(this, "Data invalida!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
            
        }
        else{
            JOptionPane.showMessageDialog(this, "Você só pode criar novos registro em nome\ndo usuario logado e utilizando a data atual", "Erro", JOptionPane.ERROR_MESSAGE);
            carregarDataUsuario();
        }
    }//GEN-LAST:event_criarBtActionPerformed

    private void apagarBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_apagarBtActionPerformed
        if(conexao.apagar(cpfEntry.getText(), cpfs)){
            carregarDados();
            inserirDados();
            JOptionPane.showMessageDialog(this,"Registro apagado","=)",JOptionPane.INFORMATION_MESSAGE);
        }
        else{
            JOptionPane.showMessageDialog(this, "CPF indicado não está registrado e não pode ser apagado", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_apagarBtActionPerformed

    private void exportarBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportarBtActionPerformed
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new pdfFrame().setVisible(true);
            }
        });
    }//GEN-LAST:event_exportarBtActionPerformed

    private void limparBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_limparBtActionPerformed
        cpfEntry.setText("");
        nomeEntry.setText("");
        nascimentoEntry.setText("");
        editorRegistroEntry.setText("");
        dataRegistroEntry.setText("");
    }//GEN-LAST:event_limparBtActionPerformed

    private void registrosTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_registrosTableMouseClicked
        int linhaSelecionada=registrosTable.getSelectedRow();
        String cpf= registrosTable.getValueAt(linhaSelecionada,0).toString();
        String nome= registrosTable.getValueAt(linhaSelecionada,1).toString();
        String nascimento= registrosTable.getValueAt(linhaSelecionada,2).toString();
        String dataRegistro=registrosTable.getValueAt(linhaSelecionada,3).toString();
        String editorRegistro=registrosTable.getValueAt(linhaSelecionada,4).toString();
        if(linhaSelecionada!=-1){
            cpfEntry.setText(cpf);
            nomeEntry.setText(nome);
            nascimentoEntry.setText(nascimento);
            dataRegistroEntry.setText(dataRegistro);
            editorRegistroEntry.setText(editorRegistro);
            cpfSelecionado=cpf;
        }
    }//GEN-LAST:event_registrosTableMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(RegistrosFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegistrosFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegistrosFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegistrosFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton alterarBt;
    private javax.swing.JButton apagarBt;
    private javax.swing.JTextField cpfEntry;
    private javax.swing.JButton criarBt;
    private javax.swing.JTextField dataRegistroEntry;
    private javax.swing.JTextField editorRegistroEntry;
    private javax.swing.JButton exportarBt;
    private javax.swing.JButton filtrarBt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton limparBt;
    private javax.swing.JTextField nascimentoEntry;
    private javax.swing.JTextField nomeEntry;
    private javax.swing.JTable registrosTable;
    // End of variables declaration//GEN-END:variables
}