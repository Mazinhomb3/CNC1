package br.com.igpaz.telas;

import br.com.igpaz.dal.ModuloConexao;
import java.awt.Color;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;

public final class TelaVisualizar extends javax.swing.JInternalFrame {

    static JLabel lblUsuarioFinal;

    ArrayList nome = new ArrayList();
    ArrayList bairro = new ArrayList();
    ArrayList lider = new ArrayList();

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public TelaVisualizar() {

        initComponents();
        conexao = ModuloConexao.conector();
        //Popula as Combobox
        //this.pesquisa_avancada();
        this.populaCmbRede();

        this.populaCmbDataIn();

        //Colocar cor nas Labels
        lblDataTela.setForeground(Color.red);
        lblUsuariofinal.setForeground(Color.red);
        lblUsuariofinal.setText(TelaPrincipal.lblUsuario.getText().toUpperCase());

        //formato da data
        Date data = new Date();
        DateFormat formatador = DateFormat.getDateInstance(DateFormat.MEDIUM);
        lblDataTela.setText(formatador.format(data));

        Calendar calendarData = Calendar.getInstance();
        int numeroDiasParaSubtrair = 0;
        calendarData.add(Calendar.DATE, numeroDiasParaSubtrair);
        Date dataInicial = calendarData.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        lblData1.setText(format.format(dataInicial));

    }

    //Popula Combobox Cor_rede
    public void populaCmbRede() {

        String sql = "select distinct cor_rede_lider from tbl_dados";

        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {

                cmbCorRede.addItem(rs.getString("cor_rede_lider"));

            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, e);
        }

    }

    public void populaCmbDataIn() {

        String sql = "select distinct data_lider from tbl_dados";

        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {

                java.sql.Date date = rs.getDate("data_lider");
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String dateStr = dateFormat.format(date);

                cmbDataIn.addItem(dateStr);
                cmbDataOut.addItem(dateStr);

            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, e);
        }

    }

    private void pesquisa_avancada() {
        String sql = "select cod_lider_rede as COD,nome_lider as NOME,membros_celula as MC,membroscomp_celula as MCP,convidadospres_celula as CPC,criancas_celula as CRIANÇAS, totalpres_celula as TPC, mda_celula as MDA, ge_celula as GE, oferta_celula as OFERTA, digitador_lider as DIGITOU, tipo_cel_dados as TIPO from tbl_dados where cor_rede_lider = ? AND data_lider BETWEEN ? and ? ";

        try {

            String date = cmbDataIn.getSelectedItem().toString();
            String dateOut = cmbDataOut.getSelectedItem().toString();
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            System.out.println(date);

            Date date1 = format.parse(date);
            Date data2 = format.parse(dateOut);
            DateFormat formatBR = new SimpleDateFormat("yyyy-MM-dd");
            String dataFormatadaIn = formatBR.format(date1);
            String DataFormatadaOut = formatBR.format(data2);
            
           

            pst = conexao.prepareStatement(sql);
            pst.setString(1, cmbCorRede.getSelectedItem().toString());
            pst.setString(2, dataFormatadaIn);
            pst.setString(3, DataFormatadaOut);
            rs = pst.executeQuery();
            tblDados.setModel(DbUtils.resultSetToTableModel(rs));

            //System.out.println(1);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtId_dados = new javax.swing.JTextField();
        txtIdPastor = new javax.swing.JTextField();
        lblData = new javax.swing.JLabel();
        lblData1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        lblDataTela = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDados = new javax.swing.JTable();
        lblUsuariofinal = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cmbCorRede = new javax.swing.JComboBox<>();
        cmbDataIn = new javax.swing.JComboBox<>();
        cmbDataOut = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        btnPesquisar = new javax.swing.JButton();

        lblData.setText("jLabel5");

        lblData1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblData1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblData1.setText("Data");

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Visualizar Cadastro Dados");
        setPreferredSize(new java.awt.Dimension(656, 487));
        setRequestFocusEnabled(false);

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel11.setText("Data:");

        lblDataTela.setText("data");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblDataTela, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(lblDataTela, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tblDados.setModel(new javax.swing.table.DefaultTableModel(
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
        tblDados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDadosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDados);

        lblUsuariofinal.setText("USUÁRIO");

        jLabel2.setText("DATA INICIAL");

        jLabel3.setText("DATA FINAL");

        cmbCorRede.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));

        jLabel4.setText("REDE");

        btnPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/igpaz/icones/lupa.png"))); // NOI18N
        btnPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(286, 286, 286)
                        .addComponent(lblUsuariofinal, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cmbCorRede, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(cmbDataOut, javax.swing.GroupLayout.Alignment.TRAILING, 0, 128, Short.MAX_VALUE)
                                .addComponent(cmbDataIn, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPesquisar))
                .addGap(0, 62, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPesquisar)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblUsuariofinal)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel2)
                                    .addComponent(cmbDataIn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(cmbDataOut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbCorRede, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))))
                .addGap(9, 9, 9)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        setBounds(0, 0, 641, 487);
    }// </editor-fold>//GEN-END:initComponents

    private void tblDadosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDadosMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_tblDadosMouseClicked

    private void btnPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarActionPerformed
        // TODO add your handling code here:
        this.pesquisa_avancada();
    }//GEN-LAST:event_btnPesquisarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPesquisar;
    private javax.swing.JComboBox<String> cmbCorRede;
    private javax.swing.JComboBox<String> cmbDataIn;
    private javax.swing.JComboBox<String> cmbDataOut;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblData;
    public static javax.swing.JLabel lblData1;
    private javax.swing.JLabel lblDataTela;
    public static javax.swing.JLabel lblUsuariofinal;
    private javax.swing.JTable tblDados;
    private javax.swing.JTextField txtIdPastor;
    private javax.swing.JTextField txtId_dados;
    // End of variables declaration//GEN-END:variables

}
