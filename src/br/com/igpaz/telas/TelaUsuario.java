package br.com.igpaz.telas;

/**
 *
 * @author mazinho
 */
import java.sql.*;
import br.com.igpaz.dal.ModuloConexao;
import java.awt.Color;
import java.security.MessageDigest;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;

public class TelaUsuario extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form TelaUsuario
     */
    public TelaUsuario() {
        initComponents();
        conexao = ModuloConexao.conector();
        this.pesquisa_avancada();
        lblUsuario.setForeground(Color.red);
        lblUsuario.setText(TelaPrincipal.lblUsuario.getText());
    }

//metodo para adicionar usuarios
    private void adicionar() {

        String password = txtUsuSenha.getText();

        String sql = "insert into cnc (usuario,email,senha,perfil) values (?,?,?,?)";
        try {

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte messageDiget[] = md.digest(password.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();

            for (byte b : messageDiget) {

                sb.append(String.format("%02X", 0xFF & b));

            }

            String senhahex = sb.toString();

            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtUsuNome.getText().toUpperCase());
            pst.setString(2, txtEmail.getText());
            pst.setString(3, senhahex);
            pst.setString(4, cboUsuPerfil.getSelectedItem().toString());
//vlidação dos campos Obrigatorios
            if ((txtEmail.getText().isEmpty()) || (txtEmail.getText().isEmpty()) || (txtUsuSenha.getText().isEmpty())) {

                JOptionPane.showMessageDialog(null, "Campos Obrigatorios.");

            } else {
//esse codigo atualiza o banco de dados
                int adicionado = pst.executeUpdate();
//A llinha abaixo serve de apoio ao codigo           
                // System.out.println(password);

                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Cadastro inseridos com sucesso.");
                    txtUsuId.setText(null);
                    txtUsuNome.setText(null);
                    txtEmail.setText(null);
                    txtUsuSenha.setText(null);
                    this.pesquisa_avancada();

                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    private void auterar() {
        String sql = "update cnc set usuario=?, login=?, senha=?, perfil=? where iduser=? ";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtUsuNome.getText().toUpperCase());
            pst.setString(2, txtEmail.getText());
            pst.setString(3, txtUsuSenha.getText());
            pst.setString(4, cboUsuPerfil.getSelectedItem().toString());
            pst.setString(5, txtUsuId.getText());
//vlidação dos campos Obrigatorios
            if ((txtUsuId.getText().isEmpty()) || (txtEmail.getText().isEmpty()) || (txtUsuNome.getText().isEmpty()) || (txtUsuSenha.getText().isEmpty())) {

                JOptionPane.showMessageDialog(null, "Campos obrigatorios.");

            } else {
//esse codigo atualiza o banco de dados
                int adicionado = pst.executeUpdate();
//A llinha abaixo serve de apoio ao codigo           
// System.out.println(adicionado);
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Cadastro auterados com sucesso.");
                    txtUsuId.setText(null);
                    txtUsuNome.setText(null);
                    txtEmail.setText(null);
                    txtUsuSenha.setText(null);
                    this.pesquisa_avancada();

                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    //o metodo a seguir remove cadastros de usuarios
    private void remover() {
        if (txtEmail.getText().isEmpty() || txtEmail.getText().length() < 4) {

            JOptionPane.showMessageDialog(null, "Não pode deletar um campo vazio. Click na tabela para setar campos.");
            txtEmail.requestFocus();

        } else {

            int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza da remoção de Cadastro.", "Atenção", JOptionPane.YES_NO_OPTION);
            if (JOptionPane.YES_OPTION == confirma) {
                String sql = "delete from cnc where iduser=?";
                try {
                    pst = conexao.prepareStatement(sql);
                    pst.setString(1, txtUsuId.getText());
                    int apagado = pst.executeUpdate();
                    if (apagado > 0) {
                        JOptionPane.showMessageDialog(null, "Cadastro removido com sucesso.");

                        txtUsuId.setText(null);
                        txtUsuNome.setText(null);
                        txtEmail.setText(null);
                        txtUsuSenha.setText(null);
                        this.pesquisa_avancada();
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }

            }

        }
    }

    private void pesquisa_avancada() {
        String sql = "select iduser as ID,usuario as Usuário,email as Email from cnc where email like ?";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtEmail.getText() + "%");

            rs = pst.executeQuery();
            tblUsuario.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void setar_campos() {

        int setar = tblUsuario.getSelectedRow();
        txtUsuId.setText(tblUsuario.getModel().getValueAt(setar, 0).toString());
        txtUsuNome.setText(tblUsuario.getModel().getValueAt(setar, 1).toString());
        txtEmail.setText(tblUsuario.getModel().getValueAt(setar, 2).toString());

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtUsuId = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cboUsuPerfil = new javax.swing.JComboBox<>();
        btnUsucreate = new javax.swing.JButton();
        btnUseUpdate = new javax.swing.JButton();
        btnUseDelete = new javax.swing.JButton();
        lblCamObr = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUsuario = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        lblUsuario = new javax.swing.JLabel();
        txtUsuSenha = new javax.swing.JPasswordField();
        txtUsuNome = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Usuario");
        setPreferredSize(new java.awt.Dimension(740, 550));

        jLabel2.setText("E-mail:");

        jLabel4.setText("*Senha:");

        jLabel5.setText("*Perfil Usuario:");

        cboUsuPerfil.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ADMIN", "MASTER", "USER", " " }));
        cboUsuPerfil.setToolTipText("");

        btnUsucreate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/igpaz/icones/add.png"))); // NOI18N
        btnUsucreate.setToolTipText("Adicionar");
        btnUsucreate.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnUsucreate.setPreferredSize(new java.awt.Dimension(64, 64));
        btnUsucreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsucreateActionPerformed(evt);
            }
        });

        btnUseUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/igpaz/icones/update.png"))); // NOI18N
        btnUseUpdate.setToolTipText("Modificar");
        btnUseUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnUseUpdate.setPreferredSize(new java.awt.Dimension(64, 64));
        btnUseUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUseUpdateActionPerformed(evt);
            }
        });

        btnUseDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/igpaz/icones/delete.png"))); // NOI18N
        btnUseDelete.setToolTipText("Deletar");
        btnUseDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnUseDelete.setPreferredSize(new java.awt.Dimension(64, 64));
        btnUseDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUseDeleteActionPerformed(evt);
            }
        });

        lblCamObr.setText("*Campos obrigatorios");

        txtEmail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtEmailKeyReleased(evt);
            }
        });

        tblUsuario.setModel(new javax.swing.table.DefaultTableModel(
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
        tblUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblUsuarioMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblUsuario);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Seja bem vindo");

        lblUsuario.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUsuario.setText("USUÁRIO");

        jLabel6.setText("*Nome:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(281, 281, 281)
                        .addComponent(jLabel1)
                        .addGap(67, 67, 67)
                        .addComponent(lblCamObr, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(281, 281, 281)
                        .addComponent(lblUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(108, 108, 108)
                        .addComponent(jLabel6)
                        .addGap(6, 6, 6)
                        .addComponent(txtUsuNome, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(112, 112, 112)
                        .addComponent(jLabel2)
                        .addGap(6, 6, 6)
                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(108, 108, 108)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(6, 6, 6)
                                .addComponent(txtUsuSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(12, 12, 12)
                                .addComponent(cboUsuPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(28, 28, 28)
                        .addComponent(btnUsucreate, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addComponent(btnUseUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addComponent(btnUseDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(lblCamObr))
                .addGap(6, 6, 6)
                .addComponent(lblUsuario)
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel6))
                    .addComponent(txtUsuNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel2))
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(jLabel4))
                            .addComponent(txtUsuSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(jLabel5))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(cboUsuPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnUsucreate, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnUseUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnUseDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE)
                .addContainerGap())
        );

        setBounds(0, 0, 740, 550);
    }// </editor-fold>//GEN-END:initComponents

    private void btnUsucreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsucreateActionPerformed
        // Adicionar usuário
        adicionar();
    }//GEN-LAST:event_btnUsucreateActionPerformed

    private void btnUseUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUseUpdateActionPerformed
        // Auterar usuário
        auterar();
    }//GEN-LAST:event_btnUseUpdateActionPerformed

    private void btnUseDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUseDeleteActionPerformed
        //Remover usuário
        remover();
    }//GEN-LAST:event_btnUseDeleteActionPerformed

    private void tblUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUsuarioMouseClicked
        // Setar Campos
        setar_campos();
    }//GEN-LAST:event_tblUsuarioMouseClicked

    private void txtEmailKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmailKeyReleased
        // Perquisa Avançada
        pesquisa_avancada();
    }//GEN-LAST:event_txtEmailKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnUseDelete;
    private javax.swing.JButton btnUseUpdate;
    private javax.swing.JButton btnUsucreate;
    private javax.swing.JComboBox<String> cboUsuPerfil;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JLabel lblCamObr;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JTable tblUsuario;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtUsuId;
    private javax.swing.JTextField txtUsuNome;
    private javax.swing.JPasswordField txtUsuSenha;
    // End of variables declaration//GEN-END:variables
}
