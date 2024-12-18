package br.com.igpaz.telas;

import java.sql.*;
import br.com.igpaz.dal.ModuloConexao;
import java.awt.Toolkit;
import java.text.DateFormat;
import javax.swing.JOptionPane;

public class TelaPrincipal extends javax.swing.JFrame {

    Connection conexao = null;

    public TelaPrincipal() {
        initComponents();
        setIcon();
        conexao = ModuloConexao.conector();

        //TelaConvetidos.lblUsuarioFinal = lblUsuario;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Desktop = new javax.swing.JDesktopPane();
        jLabel1 = new javax.swing.JLabel();
        lblData = new javax.swing.JLabel();
        lblUsuario = new javax.swing.JLabel();
        Menu = new javax.swing.JMenuBar();
        menCad = new javax.swing.JMenu();
        menCadUsuario = new javax.swing.JMenuItem();
        MenCadLidCel = new javax.swing.JMenuItem();
        menCadastroDados = new javax.swing.JMenuItem();
        menVisualizarDados = new javax.swing.JMenuItem();
        menPorcentagem = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        menSob = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        menSair = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("C. Dados Celulas");
        setMinimumSize(new java.awt.Dimension(950, 590));
        setResizable(false);
        setSize(new java.awt.Dimension(950, 590));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        Desktop.setPreferredSize(new java.awt.Dimension(740, 550));

        javax.swing.GroupLayout DesktopLayout = new javax.swing.GroupLayout(Desktop);
        Desktop.setLayout(DesktopLayout);
        DesktopLayout.setHorizontalGroup(
            DesktopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 750, Short.MAX_VALUE)
        );
        DesktopLayout.setVerticalGroup(
            DesktopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/igpaz/icones/logopazchurch120.png"))); // NOI18N

        lblData.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblData.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblData.setText("Data");

        lblUsuario.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblUsuario.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUsuario.setText("USUÁRIO");

        menCad.setText("Cadastro");
        menCad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menCadActionPerformed(evt);
            }
        });

        menCadUsuario.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_U, java.awt.event.InputEvent.ALT_DOWN_MASK));
        menCadUsuario.setText("Usuário");
        menCadUsuario.setEnabled(false);
        menCadUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menCadUsuarioActionPerformed(evt);
            }
        });
        menCad.add(menCadUsuario);

        MenCadLidCel.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.ALT_DOWN_MASK));
        MenCadLidCel.setText("Cad. Lider Célula");
        MenCadLidCel.setEnabled(false);
        MenCadLidCel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenCadLidCelActionPerformed(evt);
            }
        });
        menCad.add(MenCadLidCel);

        menCadastroDados.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.ALT_DOWN_MASK));
        menCadastroDados.setText("Cad.  Dados Célula");
        menCadastroDados.setEnabled(false);
        menCadastroDados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menCadastroDadosActionPerformed(evt);
            }
        });
        menCad.add(menCadastroDados);

        menVisualizarDados.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.ALT_DOWN_MASK));
        menVisualizarDados.setText("Visualizar Dados");
        menVisualizarDados.setEnabled(false);
        menVisualizarDados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menVisualizarDadosActionPerformed(evt);
            }
        });
        menCad.add(menVisualizarDados);

        menPorcentagem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.ALT_DOWN_MASK));
        menPorcentagem.setText("Porcentagem");
        menPorcentagem.setEnabled(false);
        menPorcentagem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menPorcentagemActionPerformed(evt);
            }
        });
        menCad.add(menPorcentagem);

        Menu.add(menCad);
        menCad.getAccessibleContext().setAccessibleName("&Cadastro");

        jMenu4.setText("Ajuda");

        menSob.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, java.awt.event.InputEvent.ALT_DOWN_MASK));
        menSob.setText("Sobre");
        menSob.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menSobActionPerformed(evt);
            }
        });
        jMenu4.add(menSob);

        Menu.add(jMenu4);

        jMenu3.setText("Opções");

        menSair.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_DOWN_MASK));
        menSair.setText("Sair");
        menSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menSairActionPerformed(evt);
            }
        });
        jMenu3.add(menSair);

        Menu.add(jMenu3);

        setJMenuBar(Menu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Desktop, javax.swing.GroupLayout.PREFERRED_SIZE, 750, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblData, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblData)
                .addGap(74, 74, 74)
                .addComponent(lblUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(247, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(Desktop, javax.swing.GroupLayout.DEFAULT_SIZE, 532, Short.MAX_VALUE)
                .addContainerGap())
        );

        setSize(new java.awt.Dimension(950, 590));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // As linhas Abaixo Substituem a lbldata pela data do sistem
        java.util.Date data = new java.util.Date();
        DateFormat formatador = DateFormat.getDateInstance(DateFormat.MEDIUM);
        lblData.setText(formatador.format(data));

    }//GEN-LAST:event_formWindowActivated

    private void menSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menSairActionPerformed
        // Exibe uma Caixa de Dialogo
        int sair = JOptionPane.showConfirmDialog(null, "Tem Ceteza de Sair?", " Atenção ", JOptionPane.YES_NO_OPTION);
        if (sair == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }//GEN-LAST:event_menSairActionPerformed

    private void menSobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menSobActionPerformed
        // Chama a tela sobre
        TelaSobre sobre = new TelaSobre();
        sobre.setVisible(true);
    }//GEN-LAST:event_menSobActionPerformed

    private void menCadUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menCadUsuarioActionPerformed
        // Chama a tela usuarios
        TelaUsuario usuario = new TelaUsuario();
        usuario.setVisible(true);
        Desktop.add(usuario);
    }//GEN-LAST:event_menCadUsuarioActionPerformed

    private void menCadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menCadActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_menCadActionPerformed

    private void MenCadLidCelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenCadLidCelActionPerformed
        // TODO add your handling code here:
        TelaCadastroLiderCelula cadastrolidercelula = new TelaCadastroLiderCelula();
        cadastrolidercelula.setVisible(true);
        Desktop.add(cadastrolidercelula);
    }//GEN-LAST:event_MenCadLidCelActionPerformed

    private void menCadastroDadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menCadastroDadosActionPerformed
        // TODO add your handling code here:
        TelaCadastroDados telacadastrodados = new TelaCadastroDados();
        telacadastrodados.setVisible(true);
        Desktop.add(telacadastrodados);
    }//GEN-LAST:event_menCadastroDadosActionPerformed

    private void menVisualizarDadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menVisualizarDadosActionPerformed
        // TODO add your handling code here:
        TelaVisualizar telavisualizar = new TelaVisualizar();
        telavisualizar.setVisible(true);
        Desktop.add(telavisualizar);
    }//GEN-LAST:event_menVisualizarDadosActionPerformed

    private void menPorcentagemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menPorcentagemActionPerformed
        // TODO add your handling code here:
         TelaPorcentagem telaPorcentagem = new TelaPorcentagem();
        telaPorcentagem.setVisible(true);
        Desktop.add(telaPorcentagem);
    }//GEN-LAST:event_menPorcentagemActionPerformed

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
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane Desktop;
    public static javax.swing.JMenuItem MenCadLidCel;
    private javax.swing.JMenuBar Menu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    public static javax.swing.JLabel lblData;
    public static javax.swing.JLabel lblUsuario;
    private javax.swing.JMenu menCad;
    public static javax.swing.JMenuItem menCadUsuario;
    public static javax.swing.JMenuItem menCadastroDados;
    public static javax.swing.JMenuItem menPorcentagem;
    private javax.swing.JMenuItem menSair;
    private javax.swing.JMenuItem menSob;
    public static javax.swing.JMenuItem menVisualizarDados;
    // End of variables declaration//GEN-END:variables

    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/br/com/igpaz/icones/logopazchurch120.png")));

    }

}
