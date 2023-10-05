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
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import net.proteanit.sql.DbUtils;

public class TelaCadastroLiderCelula extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    ArrayList idlider = new ArrayList();

    public TelaCadastroLiderCelula() {
        initComponents();
        conexao = ModuloConexao.conector();
        this.pesquisa_avancada();
        this.populacmbTipoCel();
        this.setarColuna();

     

        //Verifica data do sistema
        Date data = new Date();
        DateFormat formatador = DateFormat.getDateInstance(DateFormat.SHORT);
        lblData.setText(formatador.format(data));
        lblData.setForeground(Color.red);

        Calendar calendarData = Calendar.getInstance();
        int numeroDiasParaSubtrair = 0;
        calendarData.add(Calendar.DATE, numeroDiasParaSubtrair);
        Date dataInicial = calendarData.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        lblDataFormatada.setText(format.format(dataInicial));

    }

    private void setarColuna() {
        
           DefaultTableModel modelo = (DefaultTableModel) tblPastor.getModel();
        tblPastor.setRowSorter(new TableRowSorter(modelo));

    }

    private void populaCmbTipoCel() {

        String sql = "select distinct tipo_cel_rede from tbl_redes";
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {

                cmbTipoCel.addItem(rs.getString("tipo_cel_rede"));

            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, e);
        }

    }

    private void pesquisarId() {

        String sql = "select * from tbl_redes where cod_lider_rede like ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtIdLider.getText() + "%");
            rs = pst.executeQuery();
            while (rs.next()) {
                txtSupRede.setText(rs.getString("superv_rede"));
                txtCorRede.setText(rs.getString("cor_rede"));
                txtPrRede.setText(rs.getString("pr_rede"));
                txtDistrito.setText(rs.getString("distrito_rede"));
                txtArea.setText(rs.getString("area_rede"));
                txtSetor.setText(rs.getString("setor_rede"));
                txtLider.setText(rs.getString("lider_cel_rede"));
                txtId.setText(rs.getString("id_rede"));

            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, e);
        }

    }

    public void autoCompleteId(String txt) {
        String complete = "";
        int start = txt.length();
        int last = txt.length();
        int a;
        for (a = 0; a < idlider.size(); a++) {
            if (idlider.get(a).toString().startsWith(txt)) {
                complete = idlider.get(a).toString();
                last = complete.length();
                break;

            }
        }
        if (last > start) {
            txtLider.setText(complete);
            txtLider.setCaretPosition(last);
            txtLider.moveCaretPosition(start);
        }
    }

    private void populacmbTipoCel() {

        String sql = "select distinct tipo_cel_rede from tbl_redes";

        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {

                cmbTipoCel.addItem(rs.getString("tipo_cel_rede"));
            }
        } catch (Exception e) {
        }

    }

    private void pesquisa_avancada() {
        String sql = "select id_rede as ID, superv_rede as Supervisor, cor_rede as Cor_Rede, pr_rede as Rede, distrito_rede as Distrito, area_rede as Area, setor_rede as Setor, lider_cel_rede as Lider, cod_lider_rede as Cod_Lider, data_rede as Data, tipo_cel_rede as Tipo from tbl_redes where lider_cel_rede like ?";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtLider.getText() + "%");

            rs = pst.executeQuery();
            tblPastor.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void setar_campos() {

        int setar = tblPastor.getSelectedRow();
        txtId.setText(tblPastor.getModel().getValueAt(setar, 0).toString());
        txtSupRede.setText(tblPastor.getModel().getValueAt(setar, 1).toString());
        txtCorRede.setText(tblPastor.getModel().getValueAt(setar, 2).toString());
        txtPrRede.setText(tblPastor.getModel().getValueAt(setar, 3).toString());
        txtDistrito.setText(tblPastor.getModel().getValueAt(setar, 4).toString());
        txtArea.setText(tblPastor.getModel().getValueAt(setar, 5).toString());
        txtSetor.setText(tblPastor.getModel().getValueAt(setar, 6).toString());
        txtLider.setText(tblPastor.getModel().getValueAt(setar, 7).toString());
        txtIdLider.setText(tblPastor.getModel().getValueAt(setar, 8).toString());
        cmbTipoCel.addItem(tblPastor.getModel().getValueAt(setar, 10).toString());

    }

    public void Limpar() {
        txtId.setText(null);
        txtSupRede.setText(null);
        txtCorRede.setText(null);
        txtPrRede.setText(null);
        txtDistrito.setText(null);
        txtArea.setText(null);
        txtSetor.setText(null);
        txtLider.setText(null);
        txtIdLider.setText(null);
        this.pesquisa_avancada();

    }

    private void adicionar() {
        String sql = "insert into tbl_redes ( superv_rede, cor_rede , pr_rede, distrito_rede, area_rede, setor_rede,lider_cel_rede, cod_lider_rede, data_rede, tipo_cel_rede) values (?,?,?,?,?,?,?,?,?,?) ";
        try {

            pst = conexao.prepareStatement(sql);

            pst.setString(1, txtSupRede.getText().toUpperCase());
            pst.setString(2, txtCorRede.getText().toUpperCase());
            pst.setString(3, txtPrRede.getText().toUpperCase());
            pst.setString(4, txtDistrito.getText().toUpperCase());
            pst.setString(5, txtArea.getText().toUpperCase());
            pst.setString(6, txtSetor.getText().toUpperCase());
            pst.setString(7, txtLider.getText().toUpperCase());
            pst.setString(8, txtIdLider.getText().toUpperCase());
            pst.setString(9, lblDataFormatada.getText());
            pst.setString(10, cmbTipoCel.getSelectedItem().toString());
            if ((txtSupRede.getText().isEmpty() || txtCorRede.getText().isEmpty() || txtPrRede.getText().isEmpty() || txtDistrito.getText().isEmpty()
                    || txtArea.getText().isEmpty() || txtSetor.getText().isEmpty() || txtLider.getText().isEmpty() || txtIdLider.getText().isEmpty())) {

                JOptionPane.showMessageDialog(null, "Campos Obrigatórios.");

            } else {

//esse codigo atualiza o banco de dados
                int adicionado = pst.executeUpdate();
//A llinha abaixo serve de apoio ao codigo           
// System.out.println(adicionado);
                if (adicionado > 0) {

                    JOptionPane.showMessageDialog(null, "Dados inseridos com sucesso.");
                    txtId.setText(null);
                    txtLider.setText(null);

                    this.pesquisa_avancada();

                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);

        }

    }

    private void auterar() {
        String sql = "update  tbl_redes set superv_rede=?, cor_rede=? , pr_rede=?, distrito_rede=?, area_rede=?, setor_rede=?,lider_cel_rede=?, cod_lider_rede=?, data_rede=?, tipo_cel_rede=? where id_rede=?";
        try {

            pst = conexao.prepareStatement(sql);

            pst.setString(1, txtSupRede.getText().toUpperCase());
            pst.setString(2, txtCorRede.getText().toUpperCase());
            pst.setString(3, txtPrRede.getText().toUpperCase());
            pst.setString(4, txtDistrito.getText().toUpperCase());
            pst.setString(5, txtArea.getText().toUpperCase());
            pst.setString(6, txtSetor.getText().toUpperCase());
            pst.setString(7, txtLider.getText().toUpperCase());
            pst.setString(8, txtIdLider.getText());
            pst.setString(9, lblDataFormatada.getText());
            pst.setString(10, cmbTipoCel.getSelectedItem().toString());
            pst.setString(11, txtId.getText());

            if ((txtSupRede.getText().isEmpty() || txtCorRede.getText().isEmpty() || txtPrRede.getText().isEmpty() || txtDistrito.getText().isEmpty()
                    || txtArea.getText().isEmpty() || txtSetor.getText().isEmpty() || txtLider.getText().isEmpty() || txtIdLider.getText().isEmpty())) {

                JOptionPane.showMessageDialog(null, "Campos Obrigatórios.");

            } else {

//esse codigo atualiza o banco de dados
                int adicionado = pst.executeUpdate();
//A llinha abaixo serve de apoio ao codigo           
// System.out.println(adicionado);
                if (adicionado > 0) {

                    JOptionPane.showMessageDialog(null, "Dados auterados com sucessos.");

                    txtId.setText(null);
                    txtSupRede.setText(null);
                    txtCorRede.setText(null);
                    txtPrRede.setText(null);
                    txtDistrito.setText(null);
                    txtArea.setText(null);
                    txtSetor.setText(null);
                    txtLider.setText(null);
                    txtIdLider.setText(null);
                    this.pesquisa_avancada();

                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);

        }

    }

    private void deletar() {
        if ((txtSupRede.getText().isEmpty() || txtCorRede.getText().isEmpty() || txtPrRede.getText().isEmpty() || txtDistrito.getText().isEmpty()
                || txtArea.getText().isEmpty() || txtSetor.getText().isEmpty() || txtLider.getText().isEmpty() || txtIdLider.getText().isEmpty())) {

            JOptionPane.showMessageDialog(null, "Você não pode deletar um campo vazio. Click na tabela.");
            txtLider.requestFocus();
        } else {

            int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza da remoção desse Cadastro.", "Atenção", JOptionPane.YES_NO_OPTION);
            if (JOptionPane.YES_OPTION == confirma) {

                String sql = "delete from tbl_redes where id_rede=?";

                try {

                    pst = conexao.prepareStatement(sql);
                    pst.setString(1, txtId.getText());
                    int apagado = pst.executeUpdate();
                    if (apagado > 0) {
                        JOptionPane.showMessageDialog(null, "Cadastro removido com sucesso.");

                        txtId.setText(null);
                        txtSupRede.setText(null);
                        txtCorRede.setText(null);
                        txtPrRede.setText(null);
                        txtDistrito.setText(null);
                        txtArea.setText(null);
                        txtSetor.setText(null);
                        txtLider.setText(null);
                        txtIdLider.setText(null);
                        this.pesquisa_avancada();

                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }

            }
        }
    }

    public void pesquisar() {

        String sql = "select * from tbl_redes where cod_lider_rede like ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtIdLider.getText() + "%");
            rs = pst.executeQuery();
            while (rs.next()) {
                txtSupRede.setText(rs.getString("superv_rede"));
                txtCorRede.setText(rs.getString("cor_rede"));
                txtPrRede.setText(rs.getString("pr_rede"));
                txtDistrito.setText(rs.getString("distrito_rede"));
                txtArea.setText(rs.getString("area_rede"));
                txtSetor.setText(rs.getString("setor_rede"));
                txtLider.setText(rs.getString("lider_cel_rede"));
                txtIdLider.setText(rs.getString("cod_lider_rede"));
                txtId.setText(rs.getString("id_rede"));

            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, e);
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtIdPastor = new javax.swing.JTextField();
        txtCodPastor = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        dataFormatada = new javax.swing.JLabel();
        lblDataFormatada = new javax.swing.JLabel();
        lblNome = new javax.swing.JLabel();
        lblCorRede = new javax.swing.JLabel();
        lblDistrito = new javax.swing.JLabel();
        btnLimpar = new javax.swing.JButton();
        btnAuterar = new javax.swing.JButton();
        btnDeletar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPastor = new javax.swing.JTable();
        lblPrenchaCampos = new javax.swing.JLabel();
        lblData = new javax.swing.JLabel();
        lblNome1 = new javax.swing.JLabel();
        lblDistrito1 = new javax.swing.JLabel();
        lblDistrito2 = new javax.swing.JLabel();
        txtSetor = new javax.swing.JTextField();
        lblDistrito3 = new javax.swing.JLabel();
        txtLider = new javax.swing.JTextField();
        lblDistrito4 = new javax.swing.JLabel();
        txtIdLider = new javax.swing.JTextField();
        txtSupRede = new javax.swing.JTextField();
        txtPrRede = new javax.swing.JTextField();
        txtCorRede = new javax.swing.JTextField();
        txtDistrito = new javax.swing.JTextField();
        txtArea = new javax.swing.JTextField();
        btnAdicionar = new javax.swing.JButton();
        btnPesquisar = new javax.swing.JButton();
        lblDistrito5 = new javax.swing.JLabel();
        cmbTipoCel = new javax.swing.JComboBox<>();

        txtCodPastor.setEditable(false);

        jLabel1.setText("COD.");

        dataFormatada.setText("jLabel2");

        lblDataFormatada.setText("jLabel2");

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Cadastro Lider");
        setPreferredSize(new java.awt.Dimension(740, 550));

        lblNome.setText("*Superv. Rede:");

        lblCorRede.setText("*Cor da Rede:");

        lblDistrito.setText("*Distrito:");

        btnLimpar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/igpaz/icones/pincel.gif"))); // NOI18N
        btnLimpar.setToolTipText("Limpar");
        btnLimpar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparActionPerformed(evt);
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

        tblPastor.setModel(new javax.swing.table.DefaultTableModel(
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
        tblPastor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPastorMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblPastor);

        lblPrenchaCampos.setText("* Campos Obrigatórios");

        lblData.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblData.setText("jLabel1");

        lblNome1.setText("*Nome Pr.");

        lblDistrito1.setText("*Superv. Área:");

        lblDistrito2.setText("*Superv. Setor:");

        txtSetor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSetorKeyPressed(evt);
            }
        });

        lblDistrito3.setText("Líder Célula:");

        txtLider.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtLiderKeyPressed(evt);
            }
        });

        lblDistrito4.setText("Cod. Célula:");

        txtIdLider.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtIdLiderKeyPressed(evt);
            }
        });

        txtSupRede.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSupRedeKeyPressed(evt);
            }
        });

        txtPrRede.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPrRedeKeyPressed(evt);
            }
        });

        txtCorRede.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCorRedeKeyPressed(evt);
            }
        });

        txtDistrito.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDistritoKeyPressed(evt);
            }
        });

        txtArea.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtAreaKeyPressed(evt);
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

        btnPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/igpaz/icones/read.png"))); // NOI18N
        btnPesquisar.setToolTipText("Pesquisar");
        btnPesquisar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarActionPerformed(evt);
            }
        });

        lblDistrito5.setText("Tipo de Célula:");

        cmbTipoCel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(lblData, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(339, 339, 339)
                        .addComponent(lblPrenchaCampos))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(154, 154, 154)
                        .addComponent(lblNome)
                        .addGap(18, 18, 18)
                        .addComponent(txtSupRede, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(160, 160, 160)
                        .addComponent(lblCorRede)
                        .addGap(18, 18, 18)
                        .addComponent(txtCorRede, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(184, 184, 184)
                        .addComponent(lblNome1)
                        .addGap(18, 18, 18)
                        .addComponent(txtPrRede, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(193, 193, 193)
                        .addComponent(lblDistrito)
                        .addGap(18, 18, 18)
                        .addComponent(txtDistrito, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(159, 159, 159)
                        .addComponent(lblDistrito1)
                        .addGap(18, 18, 18)
                        .addComponent(txtArea, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(155, 155, 155)
                        .addComponent(lblDistrito2)
                        .addGap(18, 18, 18)
                        .addComponent(txtSetor, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(170, 170, 170)
                        .addComponent(lblDistrito3)
                        .addGap(18, 18, 18)
                        .addComponent(txtLider, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 720, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(171, 171, 171)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnAdicionar)
                                .addGap(18, 18, 18)
                                .addComponent(btnPesquisar)
                                .addGap(18, 18, 18)
                                .addComponent(btnLimpar)
                                .addGap(18, 18, 18)
                                .addComponent(btnAuterar)
                                .addGap(18, 18, 18)
                                .addComponent(btnDeletar))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblDistrito4)
                                .addGap(18, 18, 18)
                                .addComponent(txtIdLider, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(39, 39, 39)
                                .addComponent(lblDistrito5)
                                .addGap(6, 6, 6)
                                .addComponent(cmbTipoCel, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(2, 2, 2))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(lblData))
                    .addComponent(lblPrenchaCampos))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(lblNome))
                    .addComponent(txtSupRede, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(lblCorRede))
                    .addComponent(txtCorRede, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(lblNome1))
                    .addComponent(txtPrRede, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(lblDistrito))
                    .addComponent(txtDistrito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(lblDistrito1))
                    .addComponent(txtArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDistrito2)
                    .addComponent(txtSetor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(lblDistrito3))
                    .addComponent(txtLider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(lblDistrito4))
                    .addComponent(txtIdLider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDistrito5, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbTipoCel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnDeletar)
                    .addComponent(btnAdicionar)
                    .addComponent(btnPesquisar)
                    .addComponent(btnLimpar)
                    .addComponent(btnAuterar))
                .addGap(6, 6, 6)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        setBounds(0, 0, 740, 550);
    }// </editor-fold>//GEN-END:initComponents

    private void tblPastorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPastorMouseClicked
        // Setar Campos
        setar_campos();
        
    }//GEN-LAST:event_tblPastorMouseClicked

    private void btnAuterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAuterarActionPerformed
        // Auterar
        auterar();
    }//GEN-LAST:event_btnAuterarActionPerformed

    private void btnDeletarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeletarActionPerformed
        // delete
        deletar();
    }//GEN-LAST:event_btnDeletarActionPerformed

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
        // TODO add your handling code here:
        adicionar();
    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparActionPerformed
        // limpar
        Limpar();
    }//GEN-LAST:event_btnLimparActionPerformed

    private void btnPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarActionPerformed
        // TODO add your handling code here:
        pesquisar();
    }//GEN-LAST:event_btnPesquisarActionPerformed

    private void txtSupRedeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSupRedeKeyPressed
        // TODO add your handling code here:
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_BACK_SPACE:

                break;
            case KeyEvent.VK_ENTER:
                txtCorRede.requestFocus();
                break;
            default:
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
        }
    }//GEN-LAST:event_txtSupRedeKeyPressed

    private void txtCorRedeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCorRedeKeyPressed
        // TODO add your handling code here:
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_BACK_SPACE:

                break;
            case KeyEvent.VK_ENTER:
                txtPrRede.requestFocus();
                break;
            default:
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
        }
    }//GEN-LAST:event_txtCorRedeKeyPressed

    private void txtPrRedeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrRedeKeyPressed
        // TODO add your handling code here:
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_BACK_SPACE:

                break;
            case KeyEvent.VK_ENTER:
                txtDistrito.requestFocus();
                break;
            default:
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
        }
    }//GEN-LAST:event_txtPrRedeKeyPressed

    private void txtDistritoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDistritoKeyPressed
        // TODO add your handling code here:
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_BACK_SPACE:

                break;
            case KeyEvent.VK_ENTER:
                txtArea.requestFocus();
                break;
            default:
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
        }
    }//GEN-LAST:event_txtDistritoKeyPressed

    private void txtAreaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAreaKeyPressed
        // TODO add your handling code here:
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_BACK_SPACE:

                break;
            case KeyEvent.VK_ENTER:
                txtSetor.requestFocus();
                break;
            default:
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
        }
    }//GEN-LAST:event_txtAreaKeyPressed

    private void txtSetorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSetorKeyPressed
        // TODO add your handling code here:
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_BACK_SPACE:

                break;
            case KeyEvent.VK_ENTER:
                txtLider.requestFocus();
                break;
            default:
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
        }
    }//GEN-LAST:event_txtSetorKeyPressed

    private void txtLiderKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLiderKeyPressed
        // TODO add your handling code here:
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_BACK_SPACE:

                break;
            case KeyEvent.VK_ENTER:
                txtIdLider.requestFocus();
                break;
            default:
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
        }
    }//GEN-LAST:event_txtLiderKeyPressed

    private void txtIdLiderKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIdLiderKeyPressed
        // TODO add your handling code here:
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_BACK_SPACE:
                break;
            case KeyEvent.VK_ENTER:
                txtLider.setText(txtLider.getText());
                // txtNumero.requestFocus();
                break;
            default:
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        String txt = txtLider.getText();
                        autoCompleteId(txt);
                        pesquisarId();
                    }
                });
        }

    }//GEN-LAST:event_txtIdLiderKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnAuterar;
    private javax.swing.JButton btnDeletar;
    private javax.swing.JButton btnLimpar;
    private javax.swing.JButton btnPesquisar;
    private javax.swing.JComboBox<String> cmbTipoCel;
    private javax.swing.JLabel dataFormatada;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCorRede;
    private javax.swing.JLabel lblData;
    private javax.swing.JLabel lblDataFormatada;
    private javax.swing.JLabel lblDistrito;
    private javax.swing.JLabel lblDistrito1;
    private javax.swing.JLabel lblDistrito2;
    private javax.swing.JLabel lblDistrito3;
    private javax.swing.JLabel lblDistrito4;
    private javax.swing.JLabel lblDistrito5;
    private javax.swing.JLabel lblNome;
    private javax.swing.JLabel lblNome1;
    private javax.swing.JLabel lblPrenchaCampos;
    private javax.swing.JTable tblPastor;
    private javax.swing.JTextField txtArea;
    private javax.swing.JTextField txtCodPastor;
    private javax.swing.JTextField txtCorRede;
    private javax.swing.JTextField txtDistrito;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtIdLider;
    private javax.swing.JTextField txtIdPastor;
    private javax.swing.JTextField txtLider;
    private javax.swing.JTextField txtPrRede;
    private javax.swing.JTextField txtSetor;
    private javax.swing.JTextField txtSupRede;
    // End of variables declaration//GEN-END:variables
}
