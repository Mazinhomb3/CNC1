package br.com.igpaz.telas;

import br.com.igpaz.dal.ModuloConexao;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import net.proteanit.sql.DbUtils;

public final class TelaVisualizar extends javax.swing.JInternalFrame {

    static JLabel lblUsuarioFinal;

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public TelaVisualizar() {

        initComponents();
        conexao = ModuloConexao.conector();

        this.populaCmbRede();
        this.populaCmbDataIn();
        this.pesquisa();

        
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

    private void setar() {

        DefaultTableModel modelo = (DefaultTableModel) tblDados.getModel();
        tblDados.setRowSorter(new TableRowSorter(modelo));

    }

    //Popula Combobox Cor_rede
    public void populaCmbRede() {

        String sql = "select distinct cor_rede_lider from tbl_dados order by cor_rede_lider asc";

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

        String sql = "select distinct data_lider from tbl_dados ORDER BY data_lider ASC";

        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {

                java.sql.Date date = rs.getDate("data_lider");
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String dateStr = dateFormat.format(date);

                cmbDataIn.addItem(dateStr);

            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, e);
        }

    }

    private void pesquisa_avancada() {
        String sql = "select id_lider AS ID, cod_lider_rede as COD,nome_lider as NOME,membros_celula as MC,membroscomp_celula as MCP,convidadospres_celula as CPC,criancas_celula as CRIANÇAS, totalpres_celula as TPC, mda_celula as MDA, ge_celula as GE, oferta_celula as OFERTA,data_lider AS DATA , digitador_lider as DIGITOU, tipo_cel_dados as TIPO from tbl_dados where cor_rede_lider = ? AND data_lider = ? ";

        try {

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            String date = cmbDataIn.getSelectedItem().toString();
            System.out.println(date);
            Date date1 = format.parse(date);
            DateFormat formatBR = new SimpleDateFormat("yyyy-MM-dd");
            String dataFormatadaIn = formatBR.format(date1);

            pst = conexao.prepareStatement(sql);
            pst.setString(1, cmbCorRede.getSelectedItem().toString());
            pst.setString(2, dataFormatadaIn);

            rs = pst.executeQuery();
            tblDados.setModel(DbUtils.resultSetToTableModel(rs));

            //System.out.println(1);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void pesquisa() {
        String sql = "select id_lider AS ID, cod_lider_rede as COD,nome_lider as NOME,cor_rede_lider as REDE, membros_celula as MC,membroscomp_celula as MCP,convidadospres_celula as CPC,criancas_celula as CRIANÇAS, totalpres_celula as TPC, mda_celula as MDA, ge_celula as GE, oferta_celula as OFERTA,data_lider AS DATA , digitador_lider as DIGITOU, tipo_cel_dados as TIPO from tbl_dados";

        try {

            /* SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

            String date = cmbDataIn.getSelectedItem().toString();
            System.out.println(date);
            Date date1 = format.parse(date);
            DateFormat formatBR = new SimpleDateFormat("yyyy-MM-dd");
            String dataFormatadaIn = formatBR.format(date1);
             */
            pst = conexao.prepareStatement(sql);
            // pst.setString(1, cmbCorRede.getSelectedItem().toString());
            // pst.setString(2, dataFormatadaIn);

            rs = pst.executeQuery();
            tblDados.setModel(DbUtils.resultSetToTableModel(rs));

            //System.out.println(1);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void setacampos() {
        int setar = tblDados.getSelectedRow();
        txtId.setText(tblDados.getModel().getValueAt(setar, 0).toString());
        txtMTC.setText(tblDados.getModel().getValueAt(setar, 3).toString());
        txtMCP.setText(tblDados.getModel().getValueAt(setar, 4).toString());
        txtCP.setText(tblDados.getModel().getValueAt(setar, 5).toString());
        txtC.setText(tblDados.getModel().getValueAt(setar, 6).toString());
        txtMDA.setText(tblDados.getModel().getValueAt(setar, 8).toString());
        txtGE.setText(tblDados.getModel().getValueAt(setar, 9).toString());
        txtOferta.setText(tblDados.getModel().getValueAt(setar, 10).toString());
        txtDataTr.setText(cmbDataIn.getSelectedItem().toString());

    }

    private void auterarDados() {
        String sql = "update  tbl_dados set data_lider=?, membros_celula=? , membroscomp_celula=?, convidadospres_celula=?, criancas_celula=?, totalpres_celula=?,mda_celula=?, ge_celula=?, oferta_celula=?, tipo_cel_dados=? where id_lider=?";
        try {

            String dataimput = txtDataTr.getText();
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Date date1 = format.parse(dataimput);
            DateFormat formatBR = new SimpleDateFormat("yyyy-MM-dd");
            String dateFormated = formatBR.format(date1);

            String mcp = txtMCP.getText();
            int mcp1 = Integer.parseInt(mcp);
            String convPres = txtCP.getText();
            int convPres1 = Integer.parseInt(convPres);
            String criancas = txtC.getText();
            int criancas1 = Integer.parseInt(criancas);
            int total = mcp1 + convPres1 + criancas1;
            String total1 = String.valueOf(total);

            pst = conexao.prepareStatement(sql);

            pst.setString(1, dateFormated);
            pst.setString(2, txtMTC.getText());
            pst.setString(3, txtMCP.getText());
            pst.setString(4, txtCP.getText());
            pst.setString(5, txtC.getText());
            pst.setString(6, total1);
            pst.setString(7, txtMDA.getText());
            pst.setString(8, txtGE.getText());
            pst.setString(9, txtOferta.getText());
            pst.setString(10, cmbTipo.getSelectedItem().toString());
            pst.setString(11, txtId.getText());

            if ((txtDataTr.getText().isEmpty())) {

                JOptionPane.showMessageDialog(null, "Campos Obrigatórios.");

            } else {

//esse codigo atualiza o banco de dados
                int adicionado = pst.executeUpdate();
//A llinha abaixo serve de apoio ao codigo           
// System.out.println(adicionado);
                if (adicionado > 0) {

                    JOptionPane.showMessageDialog(null, "Dados auterados com sucessos.");

                    cmbCorRede.setName("");
                    populaCmbRede();
                    populaCmbDataIn();

                    txtId.setText(null);
                    txtMTC.setText(null);
                    txtMCP.setText(null);
                    txtCP.setText(null);
                    txtC.setText(null);
                    txtOferta.setText(null);
                    txtMDA.setText(null);
                    txtGE.setText(null);
                    txtDataTr.setText(null);

                    pesquisa_avancada();

                }
            }
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
        cmbCorRede = new javax.swing.JComboBox<>();
        cmbDataIn = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        btnAlterar = new javax.swing.JButton();
        txtMCP = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtMTC = new javax.swing.JTextField();
        txtCP = new javax.swing.JTextField();
        txtC = new javax.swing.JTextField();
        txtMDA = new javax.swing.JTextField();
        txtGE = new javax.swing.JTextField();
        txtId = new javax.swing.JTextField();
        cmbTipo = new javax.swing.JComboBox<>();
        txtDataTr = new javax.swing.JFormattedTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        btnPesquisar = new javax.swing.JButton();
        txtOferta = new javax.swing.JTextField();
        btnLimpar = new javax.swing.JButton();

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

        tblDados.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
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

        jLabel2.setText("DATA");

        cmbCorRede.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));
        cmbCorRede.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cmbCorRedeMouseClicked(evt);
            }
        });

        jLabel4.setText("REDE");

        btnAlterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/igpaz/icones/update.png"))); // NOI18N
        btnAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarActionPerformed(evt);
            }
        });

        jLabel5.setText("MTC");

        jLabel6.setText("MCP");

        jLabel7.setText("CP");

        jLabel8.setText("C");

        jLabel9.setText("OFERTA");

        jLabel10.setText("MDA's");

        jLabel12.setText("TIPO");

        txtId.setEditable(false);

        cmbTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ADULTO", "CRIANCA" }));

        txtDataTr.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter()));

        jLabel13.setText("GE's");

        jLabel14.setText("DATA");

        btnPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/igpaz/icones/lupa.png"))); // NOI18N
        btnPesquisar.setPreferredSize(new java.awt.Dimension(47, 47));
        btnPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarActionPerformed(evt);
            }
        });

        btnLimpar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/igpaz/icones/pincel.gif"))); // NOI18N
        btnLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(cmbDataIn, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cmbCorRede, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel4)))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(lblUsuariofinal, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(128, 128, 128)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(cmbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))))
            .addGroup(layout.createSequentialGroup()
                .addGap(649, 649, 649)
                .addComponent(jLabel12))
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(txtMTC, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(txtMCP, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(txtCP, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(txtC, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(txtOferta, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(txtMDA, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(txtGE, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtDataTr, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel5)
                .addGap(57, 57, 57)
                .addComponent(jLabel6)
                .addGap(66, 66, 66)
                .addComponent(jLabel7)
                .addGap(74, 74, 74)
                .addComponent(jLabel8)
                .addGap(63, 63, 63)
                .addComponent(jLabel9)
                .addGap(46, 46, 46)
                .addComponent(jLabel10)
                .addGap(62, 62, 62)
                .addComponent(jLabel13)
                .addGap(69, 69, 69)
                .addComponent(jLabel14))
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 720, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(cmbDataIn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addComponent(cmbCorRede, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel2)
                        .addGap(13, 13, 13)
                        .addComponent(jLabel4))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblUsuariofinal)
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(cmbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(3, 3, 3)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtMTC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMCP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtOferta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMDA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtGE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDataTr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel5))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel6))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel7))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel8))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel9))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel10))
                    .addComponent(jLabel13)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        setBounds(0, 0, 740, 550);
    }// </editor-fold>//GEN-END:initComponents

    private void tblDadosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDadosMouseClicked
        // TODO add your handling code here:
        setacampos();
        setar();

    }//GEN-LAST:event_tblDadosMouseClicked

    private void btnPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarActionPerformed
        // TODO add your handling code here:
        pesquisa_avancada();
    }//GEN-LAST:event_btnPesquisarActionPerformed

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed
        // TODO add your handling code here:
        auterarDados();
    }//GEN-LAST:event_btnAlterarActionPerformed

    private void cmbCorRedeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmbCorRedeMouseClicked
        // TODO add your handling code here:
        //pesquisa_avancada();
    }//GEN-LAST:event_cmbCorRedeMouseClicked

    private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparActionPerformed
        // TODO add your handling code here:
        pesquisa();
    }//GEN-LAST:event_btnLimparActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnLimpar;
    private javax.swing.JButton btnPesquisar;
    private javax.swing.JComboBox<String> cmbCorRede;
    private javax.swing.JComboBox<String> cmbDataIn;
    private javax.swing.JComboBox<String> cmbTipo;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblData;
    public static javax.swing.JLabel lblData1;
    private javax.swing.JLabel lblDataTela;
    public static javax.swing.JLabel lblUsuariofinal;
    private javax.swing.JTable tblDados;
    private javax.swing.JTextField txtC;
    private javax.swing.JTextField txtCP;
    private javax.swing.JFormattedTextField txtDataTr;
    private javax.swing.JTextField txtGE;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtIdPastor;
    private javax.swing.JTextField txtId_dados;
    private javax.swing.JTextField txtMCP;
    private javax.swing.JTextField txtMDA;
    private javax.swing.JTextField txtMTC;
    private javax.swing.JTextField txtOferta;
    // End of variables declaration//GEN-END:variables

}
