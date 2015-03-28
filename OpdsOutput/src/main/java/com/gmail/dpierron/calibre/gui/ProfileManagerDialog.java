/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ProfileManagerDialog.java
 *
 * Created on 10 juin 2010, 14:26:23
 */

package com.gmail.dpierron.calibre.gui;

import com.gmail.dpierron.calibre.configuration.ConfigurationManager;
import com.gmail.dpierron.tools.i18n.Localization;
import com.gmail.dpierron.tools.Helper;

import javax.swing.*;
import java.io.File;


/**
 * @author David Pierron
 */
public class ProfileManagerDialog extends javax.swing.JDialog {

  /**
   * Creates new form ProfileManagerDialog
   */
  public ProfileManagerDialog(java.awt.Frame parent, boolean modal) {
    super(parent, modal);
    initComponents();
    translateTexts();
  }

  private void loadProfiles() {
    DefaultListModel listOfProfiles = new DefaultListModel();
    for (String profile : ConfigurationManager.getExistingConfigurations()) {
      if ("default".equalsIgnoreCase(profile))
        continue;
      listOfProfiles.addElement(profile);
    }
    lstProfiles.setModel(listOfProfiles);
  }

  private void renameProfile(int index) {
    if (index < 0 || index >= lstProfiles.getModel().getSize())
      return;

    String profile = (String) lstProfiles.getModel().getElementAt(index);
    String newProfile = JOptionPane.showInputDialog(Localization.Main.getText("gui.profile.rename.msg", profile), profile);
    if ("default".equalsIgnoreCase(newProfile))
      return;
    if (Helper.isNotNullOrEmpty(newProfile) && !newProfile.equals(profile)) {
      File profileFile =
          new File(ConfigurationManager.getConfigurationDirectory(), profile + ConfigurationManager.PROFILES_SUFFIX);
      if (profileFile.exists()) {
        profileFile.renameTo(new File(ConfigurationManager.getConfigurationDirectory(), newProfile + ConfigurationManager.PROFILES_SUFFIX));
        // #c2o-10:  If current profile is the one renamed then need to refresh main screen
        if (ConfigurationManager.getCurrentProfileName().equals(profile)) {
          ConfigurationManager.setCurrentProfileName(newProfile);
        }
        loadProfiles();
      }
    }
  }

  private void deleteProfiles(int[] indices) {
    if (indices == null || indices.length == 0)
      return;

    for (int index : indices) {
      String profile = (String) lstProfiles.getModel().getElementAt(index);
      int result = JOptionPane
          .showConfirmDialog(this, Localization.Main.getText("gui.profile.delete.msg", profile), null, JOptionPane.OK_CANCEL_OPTION,
              JOptionPane.QUESTION_MESSAGE);
      if (result != JOptionPane.CANCEL_OPTION) {
        File profileFile =
            new File(ConfigurationManager.getConfigurationDirectory(), profile + ConfigurationManager.PROFILES_SUFFIX);
        if (profileFile.exists()) {
          profileFile.delete();
        }
      }
    }
    loadProfiles();
  }

  /**
   * Apply localization to this dialog
   */
  private void translateTexts() {
    cmdNew.setText(Localization.Main.getText("gui.profile.new"));
    cmdRename.setText(Localization.Main.getText("gui.profile.rename"));
    cmdDelete.setText((Localization.Main.getText("gui.profile.delete")));
    cmdClose.setText((Localization.Main.getText("gui.profile.close")));
  }
  /**
   * This method is called from within the constructor to
   * reset the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        scrProfiles = new javax.swing.JScrollPane();
        lstProfiles = new javax.swing.JList();
        loadProfiles();
        pnlButtons = new javax.swing.JPanel();
        cmdNew = new javax.swing.JButton();
        cmdRename = new javax.swing.JButton();
        cmdDelete = new javax.swing.JButton();
        pnlButtons1 = new javax.swing.JPanel();
        cmdClose = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(Localization.Main.getText("gui.menu.profiles.manage")); // NOI18N
        getContentPane().setLayout(new java.awt.GridBagLayout());

        scrProfiles.setViewportView(lstProfiles);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(scrProfiles, gridBagConstraints);

        cmdNew.setText("cmdNew");
        cmdNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdNewActionPerformed(evt);
            }
        });
        pnlButtons.add(cmdNew);

        cmdRename.setText("cmdRename");
        cmdRename.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdRenameActionPerformed(evt);
            }
        });
        pnlButtons.add(cmdRename);

        cmdDelete.setText("cmdDelete");
        cmdDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdDeleteActionPerformed(evt);
            }
        });
        pnlButtons.add(cmdDelete);
        cmdDelete.getAccessibleContext().setAccessibleName(null);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(pnlButtons, gridBagConstraints);

        cmdClose.setText("cmdClose");
        cmdClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdCloseActionPerformed(evt);
            }
        });
        pnlButtons1.add(cmdClose);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(pnlButtons1, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

  private void cmdRenameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdRenameActionPerformed
    int index = lstProfiles.getSelectedIndex();
    if (index < 0) {
      String msg = Localization.Main.getText("gui.profile.rename.select");
      JOptionPane.showMessageDialog(this, msg, "", JOptionPane.WARNING_MESSAGE);
      return;
    }
    renameProfile(index);
  }//GEN-LAST:event_cmdRenameActionPerformed

  private void cmdDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdDeleteActionPerformed
    int indices[] = lstProfiles.getSelectedIndices();
    if (indices.length < 1) {
      String msg = Localization.Main.getText("gui.profile.delete.select");
      JOptionPane.showMessageDialog(this, msg, "", JOptionPane.WARNING_MESSAGE);
      return;
    }
    deleteProfiles(indices);
  }//GEN-LAST:event_cmdDeleteActionPerformed

  private void cmdNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdNewActionPerformed
    String newProfileName = JOptionPane.showInputDialog(Localization.Main.getText("gui.profile.new.msg"));
    if ("default".equalsIgnoreCase(newProfileName))
      return;
    ConfigurationManager.copyCurrentProfile(newProfileName);
    loadProfiles();
  }//GEN-LAST:event_cmdNewActionPerformed

    private void cmdCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdCloseActionPerformed
      this.setVisible(false);
    }//GEN-LAST:event_cmdCloseActionPerformed

  /**
   * @param args the command line arguments
   */
  public static void main(String args[]) {
    java.awt.EventQueue.invokeLater(new Runnable() {
      public void run() {
        ProfileManagerDialog dialog = new ProfileManagerDialog(new javax.swing.JFrame(), true);
        dialog.addWindowListener(new java.awt.event.WindowAdapter() {
          public void windowClosing(java.awt.event.WindowEvent e) {
            System.exit(0);
          }
        });
        dialog.setVisible(true);
      }
    });
  }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cmdClose;
    private javax.swing.JButton cmdDelete;
    private javax.swing.JButton cmdNew;
    private javax.swing.JButton cmdRename;
    private javax.swing.JList lstProfiles;
    private javax.swing.JPanel pnlButtons;
    private javax.swing.JPanel pnlButtons1;
    private javax.swing.JScrollPane scrProfiles;
    // End of variables declaration//GEN-END:variables

}
