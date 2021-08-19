/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospitalidad;

import hospitalidad.Gestores.GestionAutobusesBD;
import static hospitalidad.Gestores.GestionAutobusesBD.getListaAutobuses;
import hospitalidad.Gestores.GestionTiposViajeroBD;
import hospitalidad.Gestores.GestionViajesBD;
import hospitalidad.beans.AutobusBean;
import java.awt.Window;
import java.util.ArrayList;
import javax.swing.SwingUtilities;

/**
 *
 * @author vPalomo
 */
public class OpcionesGuardarPersonaAutobusModal extends javax.swing.JPanel {

    /**
     * Creates new form OpcionesGuardarViajeModal
     */
    private String opcionBoton="C"; //C es Cancelar y G guardar
    public String getBoton(){
        return opcionBoton;
    }
    public void setBoton(String opcion) {
        opcionBoton=opcion;
    }
    public OpcionesGuardarPersonaAutobusModal() {
        initComponents();
        jComboPeregrinacion.setSelectedIndex(jComboPeregrinacion.getModel().getSize()-1);
        cargaComboAutobuses();
    }
    /**
     * Devuelve la opcion elegida en el combo de peregrinacion
     * @return 
     */
    public String getPeregrinacion(){
        return jComboPeregrinacion.getModel().getElementAt(jComboPeregrinacion.getSelectedIndex()).split(" - ")[0];
    }
    
    public String getAutobus(){
        return jComboAutobuses.getModel().getElementAt(jComboAutobuses.getSelectedIndex()).split(" - ")[0];
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
        jComboPeregrinacion = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jComboAutobuses = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        jLabel1.setText("Peregrinación:");

        jComboPeregrinacion.setModel(GestionViajesBD.getModeloComboViajes());
        jComboPeregrinacion.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jComboPeregrinacionPropertyChange(evt);
            }
        });

        jLabel2.setText("Autobus:");

        jButton1.setText("Guardar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Cancelar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel3.setText("Elegir peregrinación y el autobús a asignar a todas las personas seleccionadas");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboAutobuses, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboPeregrinacion, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(19, 19, 19))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboPeregrinacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jComboAutobuses, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        opcionBoton="C";
        Window w = SwingUtilities.getWindowAncestor(this);
        w.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        opcionBoton="G";
        Window w = SwingUtilities.getWindowAncestor(this);
        w.setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jComboPeregrinacionPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jComboPeregrinacionPropertyChange
        cargaComboAutobuses();
    }//GEN-LAST:event_jComboPeregrinacionPropertyChange


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboAutobuses;
    private javax.swing.JComboBox<String> jComboPeregrinacion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    // End of variables declaration//GEN-END:variables

    private void cargaComboAutobuses() {
        String idViaje=jComboPeregrinacion.getModel().getElementAt(jComboPeregrinacion.getSelectedIndex()).split(" - ")[0];
        jComboAutobuses.setModel(GestionAutobusesBD.getModeloComboAutobuses(idViaje));
    }
}
