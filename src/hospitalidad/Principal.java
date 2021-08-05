/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospitalidad;

import hospitalidad.Gestores.GestionPersonasBD;
import hospitalidad.Gestores.GestionViajesBD;
import hospitalidad.Gestores.GestionTiposViajeroBD;
import hospitalidad.beans.PersonaBean;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author vPalomo
 */
public class Principal extends javax.swing.JFrame {

    private ArrayList<PersonaBean> listaPersonas;
    private String filtroViaje;
    private String filtroTipoViajero;
    
    /**
     * Creates new form Principal
     */
    public Principal() {
        initComponents();
        
     
        cargaTabla(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaPersonas = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        comboViaje = new javax.swing.JComboBox<>();
        comboTipoViajero = new javax.swing.JComboBox<>();
        jTextField1 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Hospitalidad de Toledo");

        tablaPersonas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Id", "DNI", "Nombre", "Fecha Nac.", "Tipo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tablaPersonas);
        if (tablaPersonas.getColumnModel().getColumnCount() > 0) {
            tablaPersonas.getColumnModel().getColumn(0).setResizable(false);
            tablaPersonas.getColumnModel().getColumn(0).setPreferredWidth(25);
            tablaPersonas.getColumnModel().getColumn(1).setResizable(false);
            tablaPersonas.getColumnModel().getColumn(1).setPreferredWidth(100);
            tablaPersonas.getColumnModel().getColumn(2).setResizable(false);
            tablaPersonas.getColumnModel().getColumn(2).setPreferredWidth(200);
            tablaPersonas.getColumnModel().getColumn(3).setResizable(false);
            tablaPersonas.getColumnModel().getColumn(3).setPreferredWidth(70);
            tablaPersonas.getColumnModel().getColumn(4).setResizable(false);
            tablaPersonas.getColumnModel().getColumn(4).setPreferredWidth(50);
        }

        jLabel2.setText("Viaje");

        jLabel3.setText("Tipo viajero");

        comboViaje.setModel(GestionViajesBD.getModeloComboViajes());
        comboViaje.setSelectedIndex(comboViaje.getModel().getSize()-1);
        comboViaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboViajeActionPerformed(evt);
            }
        });
        comboViaje.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                comboViajePropertyChange(evt);
            }
        });

        comboTipoViajero.setModel(GestionTiposViajeroBD.getModeloComboTipoViajero());

        jTextField1.setText("jTextField1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 586, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(comboViaje, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(42, 42, 42)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboTipoViajero, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(528, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(comboViaje, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboTipoViajero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 652, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void comboViajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboViajeActionPerformed
        cargaTabla(true);
    }//GEN-LAST:event_comboViajeActionPerformed

    private void comboViajePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_comboViajePropertyChange
        cargaTabla(true);
    }//GEN-LAST:event_comboViajePropertyChange

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
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Principal().setVisible(true);
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> comboTipoViajero;
    private javax.swing.JComboBox<String> comboViaje;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTable tablaPersonas;
    // End of variables declaration//GEN-END:variables

    private void cargaTabla(boolean tipo) {
        filtroViaje=comboViaje.getModel().getElementAt(comboViaje.getSelectedIndex()).split(" - ")[0];
        filtroTipoViajero=comboTipoViajero.getModel().getElementAt(comboTipoViajero.getSelectedIndex()).split(" - ")[1];
        listaPersonas = GestionPersonasBD.getListaPersonas(tipo,filtroViaje,filtroTipoViajero);
        DefaultTableModel datosTabla = (DefaultTableModel) tablaPersonas.getModel();
        for (int i = datosTabla.getRowCount(); i > 0; i--) {
            datosTabla.removeRow(i - 1);
        }
        for (PersonaBean persona : listaPersonas){
            datosTabla.addRow(new Object[]{
                ""+persona.getIdPersona(),
                persona.getDNI(),
                persona.getApellidos()+", "+persona.getNombre(),
                persona.getFechaNacimiento(),
                GestionTiposViajeroBD.getTipoViajero(filtroViaje, persona.getIdPersona()).getNombreTipo()
            });
        }
    }

    
}
