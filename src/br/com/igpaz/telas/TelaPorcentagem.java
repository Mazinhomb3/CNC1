package br.com.igpaz.telas;

import br.com.igpaz.dal.ModuloConexao;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author mazinhocosta
 */
public class TelaPorcentagem extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    ArrayList dados = new ArrayList();

    public TelaPorcentagem() {
        initComponents();
        conexao = ModuloConexao.conector();

        //DefaultTableModel modelo = (DefaultTableModel) tblporcentagem.getModel();
        //tblporcentagem.setRowSorter(new TableRowSorter(modelo));
        this.populaCmbRede();
        this.pesquisa_avancada();

        Date data1 = new Date();
        DateFormat formatador = DateFormat.getDateInstance(DateFormat.SHORT);
        lblData.setText(formatador.format(data1));
        lblData.setForeground(Color.blue);

    }

    //Popula Combobox Cor_rede
    public void populaCmbRede() {

        String sql = "select distinct cor_rede from tbl_redes order by cor_rede asc";

        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {

                cmbRede.addItem(rs.getString("cor_rede"));

            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, e);
        }

    }

    private void armazenar() {

        String sql = "select * from tbl_redes where cor_rede = ? limit 1 ";

        try {

            dados.clear();

            pst = conexao.prepareStatement(sql);
            pst.setString(1, cmbRede.getSelectedItem().toString());
            rs = pst.executeQuery();

            while (rs.next()) {
                String cor_rede = rs.getString("cor_rede");
                String sup_rede = rs.getString("superv_rede");
                String pr_rede = rs.getString("pr_rede");
                String distrito_rede = rs.getString("distrito_rede");
                String area_rede = rs.getString("area_rede");
                String setor_rede = rs.getString("setor_rede");

                dados.add(cor_rede);
                dados.add(sup_rede);
                dados.add(pr_rede);
                dados.add(distrito_rede);
                dados.add(area_rede);
                dados.add(setor_rede);

            }

        } catch (Exception e) {

        }

    }

    public void inserirDados() {

        String sql = "INSERT INTO tbl_porcentagem ( cor_rede,superv_rede,pr_rede,distrito_rede, ce, cr, cf, entregue, porcentagem,atrazado,data_porcent) values (?,?,?,?,?,?,?,?,?,?,?) ";

        try {

            String databr1 = txtDate2.getText();
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Date dataInt = format.parse(databr1);
            DateFormat formatado = new SimpleDateFormat("yyyy/MM/dd");
            String DataFormatada = formatado.format(dataInt);
            System.out.println(DataFormatada);

            pst = conexao.prepareStatement(sql);

            pst.setString(1, dados.get(0).toString());
            pst.setString(2, dados.get(1).toString());
            pst.setString(3, dados.get(2).toString());
            pst.setString(4, dados.get(3).toString());
            pst.setString(5, txtCE.getText());
            pst.setString(6, txtCr.getText());
            pst.setString(7, txtCf.getText());
            pst.setString(8, txtEntregue.getText());
            pst.setString(9, txtPorcent.getText());
            pst.setString(10, txtAtraz.getText());
            pst.setString(11, DataFormatada);

            if (txtCE.getText().isEmpty() || txtCr.getText().isEmpty() || txtCf.getText().isEmpty() || txtEntregue.getText().isEmpty() || txtPorcent.getText().isEmpty() || txtAtraz.getText().isEmpty() || txtDate2.getText().isEmpty()) {

                JOptionPane.showMessageDialog(null, "Campos obrigatórios");

            } else {
                System.out.println(DataFormatada);
                int adicionado = pst.executeUpdate();

                if (adicionado > 0) {

                    JOptionPane.showMessageDialog(null, "Dados inseridos com sucesso.");

                    txtCE.setText(null);
                    txtCr.setText(null);
                    txtCf.setText(null);
                    txtEntregue.setText(null);
                    txtPorcent.setText(null);
                    txtAtraz.setText(null);
                    txtDate2.setText(null);

                    this.pesquisa_avancada();

                }

            }

        } catch (Exception e) {
        }

    }

    private void porcentagem() {

        if (txtCE.getText().isEmpty() || txtEntregue.getText().isEmpty()) {

            JOptionPane.showMessageDialog(null, "Prencha todos os dados!");

        } else {

            String CF = txtEntregue.getText();
            double CF1 = Integer.parseInt(CF);
            String CE = txtCE.getText();
            double CE1 = Integer.parseInt(CE);
            double RS = CF1 / CE1;
            double PC = 100;
            double RSF = RS * PC;

            txtPorcent.setText(String.format("%.2f", RSF));

        }

    }

    private void alterardados() {

        String sql = "update  tbl_porcentagem set ce=?, cr=?, cf=?, entregue=?, porcentagem=?, atrazado=?, data_porcent=?  where id=?";

        try {

            String databr1 = txtDate2.getText();
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Date dataInt = format.parse(databr1);
            DateFormat formatado = new SimpleDateFormat("yyyy/MM/dd");
            String DataFormatada = formatado.format(dataInt);

            pst = conexao.prepareStatement(sql);

            pst.setString(1, txtCE.getText());
            pst.setString(2, txtCr.getText());
            pst.setString(3, txtCf.getText());
            pst.setString(4, txtEntregue.getText());
            pst.setString(5, txtPorcent.getText());
            pst.setString(6, txtAtraz.getText());
            pst.setString(7, DataFormatada);
            pst.setString(8, lblId.getText());

            if (txtCE.getText().isEmpty() || txtCr.getText().isEmpty() || txtCf.getText().isEmpty() || txtEntregue.getText().isEmpty() || txtPorcent.getText().isEmpty() || txtAtraz.getText().isEmpty() || txtDate2.getText().isEmpty()) {

                JOptionPane.showMessageDialog(null, "Campos obrigatórios");

            } else {

                int adicionado = pst.executeUpdate();

                if (adicionado > 0) {

                    JOptionPane.showMessageDialog(null, "Dados inseridos com sucesso.");

                    txtCE.setText(null);
                    txtCr.setText(null);
                    txtCf.setText(null);
                    txtEntregue.setText(null);
                    txtPorcent.setText(null);
                    txtAtraz.setText(null);
                    txtDate2.setText(null);

                    this.pesquisa_avancada();

                }

            }

        } catch (Exception e) {
        }

    }

    private void pesquisa_avancada() {
        String sql = "Select id AS ID, cor_rede as REDE,superv_rede AS SUPERV,pr_rede AS PR_REDE,distrito_rede AS DISTRITO,ce AS EXISTENTES, cr AS CRIANCA, cf AS FALTANDO, entregue AS ENTREGUE, porcentagem AS PORCENTAGEM, atrazado AS ATRAZADO,data_porcent as DATA from tbl_porcentagem ORDER by ce DESC ";

        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();

            tblporcentagem.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void setacampos() {

        try {

            int setar = tblporcentagem.getSelectedRow();

            lblId.setText(tblporcentagem.getModel().getValueAt(setar, 0).toString());
            cmbRede.addItem(tblporcentagem.getModel().getValueAt(setar, 1).toString());
            String superv_rede = tblporcentagem.getModel().getValueAt(setar, 2).toString();
            String pr_rede = tblporcentagem.getModel().getValueAt(setar, 3).toString();
            String distrito_rede = tblporcentagem.getModel().getValueAt(setar, 4).toString();
            txtCE.setText(tblporcentagem.getModel().getValueAt(setar, 5).toString());
            txtCr.setText(tblporcentagem.getModel().getValueAt(setar, 6).toString());
            txtCf.setText(tblporcentagem.getModel().getValueAt(setar, 7).toString());
            txtEntregue.setText(tblporcentagem.getModel().getValueAt(setar, 8).toString());
            txtPorcent.setText(tblporcentagem.getModel().getValueAt(setar, 9).toString());
            txtAtraz.setText(tblporcentagem.getModel().getValueAt(setar, 10).toString());

            String date = tblporcentagem.getModel().getValueAt(setar, 11).toString();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = format.parse(date);
            DateFormat formatBR = new SimpleDateFormat("dd/MM/yyyy");
            String dataFormatadaIn = formatBR.format(date1);
            txtDate2.setText(dataFormatadaIn);

            //System.out.println(dataFormatadaIn);
        } catch (Exception e) {
        }

    }

    private void deletar() {

        String sql = "delete from tbl_porcentagem where id=?";

        try {

            int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza da remoção desse cadastro.", "Atenção", JOptionPane.YES_NO_OPTION);

            if (JOptionPane.YES_OPTION == confirma) {

                pst = conexao.prepareStatement(sql);

                pst.setString(1, lblId.getText().toString());

                int apagado = pst.executeUpdate();

                if (apagado > 0) {

                    JOptionPane.showMessageDialog(null, "Cadastro removido com sucesso.");

                    txtCE.setText(null);
                    txtCr.setText(null);
                    txtCf.setText(null);
                    txtEntregue.setText(null);
                    txtPorcent.setText(null);
                    txtAtraz.setText(null);
                    txtDate2.setText(null);

                    this.pesquisa_avancada();
                }

            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Não foi possivel encontrar o ID");

        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cmbRede = new javax.swing.JComboBox<>();
        lblData = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtCE = new javax.swing.JTextField();
        txtCf = new javax.swing.JTextField();
        txtCr = new javax.swing.JTextField();
        txtEntregue = new javax.swing.JTextField();
        txtPorcent = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtAtraz = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblporcentagem = new javax.swing.JTable();
        btnPesquisar = new javax.swing.JButton();
        btnAuterar = new javax.swing.JButton();
        btnDeletar = new javax.swing.JButton();
        btnAdicionar = new javax.swing.JButton();
        lblId = new javax.swing.JLabel();
        txtDate2 = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Porcentagem de envelopes");
        setPreferredSize(new java.awt.Dimension(740, 550));

        cmbRede.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbRedeActionPerformed(evt);
            }
        });

        lblData.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        lblData.setText("Data");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("PORCENTAGEM DE ENVELOPES RECEBIDOS");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("REDE");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("SEMANA");

        txtEntregue.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtEntregueKeyPressed(evt);
            }
        });

        txtPorcent.setEditable(false);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("CE");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("CR");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("CF");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("ENTREGUE");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText("%");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setText("ATRAZ. SEM");

        tblporcentagem.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblporcentagem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblporcentagemMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblporcentagem);

        btnPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/igpaz/icones/read.png"))); // NOI18N
        btnPesquisar.setToolTipText("Pesquisar");
        btnPesquisar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarActionPerformed(evt);
            }
        });

        btnAuterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/igpaz/icones/update.png"))); // NOI18N
        btnAuterar.setToolTipText("Alterar");
        btnAuterar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnAuterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAuterarActionPerformed(evt);
            }
        });

        btnDeletar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/igpaz/icones/delete.png"))); // NOI18N
        btnDeletar.setToolTipText("Deletar");
        btnDeletar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnDeletar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeletarActionPerformed(evt);
            }
        });

        btnAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/igpaz/icones/add.png"))); // NOI18N
        btnAdicionar.setToolTipText("Adicionar");
        btnAdicionar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarActionPerformed(evt);
            }
        });

        lblId.setText("lblId");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(221, 221, 221)
                        .addComponent(jLabel1)
                        .addGap(117, 117, 117)
                        .addComponent(lblData))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(lblId))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(12, 12, 12)
                                                .addComponent(jLabel4))
                                            .addComponent(txtCE, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(6, 6, 6)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(14, 14, 14)
                                                .addComponent(jLabel5))
                                            .addComponent(txtCr, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(6, 6, 6)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(14, 14, 14)
                                                .addComponent(jLabel6))
                                            .addComponent(txtCf, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel7)
                                            .addComponent(txtEntregue, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(11, 11, 11)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(22, 22, 22)
                                                .addComponent(jLabel8))
                                            .addComponent(txtPorcent, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(24, 24, 24)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel9)
                                            .addComponent(txtAtraz, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(85, 85, 85)
                                        .addComponent(cmbRede, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addComponent(btnAdicionar)
                                .addGap(6, 6, 6)
                                .addComponent(btnPesquisar)
                                .addGap(6, 6, 6)
                                .addComponent(btnAuterar)
                                .addGap(6, 6, 6)
                                .addComponent(btnDeletar))))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 728, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(164, 164, 164)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(190, 190, 190))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtDate2, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(160, 160, 160))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(lblId))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(lblData)))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbRede, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDate2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(10, 10, 10)
                        .addComponent(txtCE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(10, 10, 10)
                        .addComponent(txtCr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(10, 10, 10)
                        .addComponent(txtCf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(10, 10, 10)
                        .addComponent(txtEntregue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(10, 10, 10)
                        .addComponent(txtPorcent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(10, 10, 10)
                        .addComponent(txtAtraz, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnAdicionar)
                            .addComponent(btnPesquisar)
                            .addComponent(btnAuterar)
                            .addComponent(btnDeletar))))
                .addGap(29, 29, 29)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        setBounds(0, 0, 740, 550);
    }// </editor-fold>//GEN-END:initComponents

    private void btnPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarActionPerformed
        // TODO add your handling code here:


    }//GEN-LAST:event_btnPesquisarActionPerformed

    private void btnAuterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAuterarActionPerformed
        // Auterar
        armazenar();
        alterardados();

    }//GEN-LAST:event_btnAuterarActionPerformed

    private void btnDeletarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeletarActionPerformed
        // delete
        deletar();


    }//GEN-LAST:event_btnDeletarActionPerformed

    private void cmbRedeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbRedeActionPerformed
        // TODO add your handling code here:


    }//GEN-LAST:event_cmbRedeActionPerformed

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
        // TODO add your handling code here:
        armazenar();
        inserirDados();
    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void txtEntregueKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEntregueKeyPressed
        // TODO add your handling code here:

        switch (evt.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                porcentagem();
                txtAtraz.requestFocus();
                break;
            case KeyEvent.VK_TAB:
                porcentagem();
                txtAtraz.requestFocus();
                break;
            default:
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
        }

    }//GEN-LAST:event_txtEntregueKeyPressed

    private void tblporcentagemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblporcentagemMouseClicked
        // TODO add your handling code here:
        setacampos();
    }//GEN-LAST:event_tblporcentagemMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnAuterar;
    private javax.swing.JButton btnDeletar;
    private javax.swing.JButton btnPesquisar;
    private javax.swing.JComboBox<String> cmbRede;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblData;
    private javax.swing.JLabel lblId;
    private javax.swing.JTable tblporcentagem;
    private javax.swing.JTextField txtAtraz;
    private javax.swing.JTextField txtCE;
    private javax.swing.JTextField txtCf;
    private javax.swing.JTextField txtCr;
    private javax.swing.JTextField txtDate2;
    private javax.swing.JTextField txtEntregue;
    private javax.swing.JTextField txtPorcent;
    // End of variables declaration//GEN-END:variables
}
